import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Genetic {

	int psize;
	Automaton[] popul;
	Automaton best;

	double Eps = 0.1;

	Random rand = new Random();

	Genetic(int n, int k, int m, int r) {
		this.psize = n; // размер популяции
		popul = createPopulation(n, k, m, r);
		best = runGenetic();
	}

	private Automaton[] createPopulation(int n, int k, int m, int r) {
		Automaton[] p = new Automaton[psize];
		for (int i = 0; i < psize; i++) {
			p[i] = new Automaton(n, k, m, r); // случайные автоматы
		}
		return p;
	}

	private Automaton runGenetic() {
		do {
			fitnessFunction();
			Arrays.sort(popul);
			iteration();
		} while (popul[0].fitness - popul[psize].fitness > Eps);
		return popul[0]; // лучшая особь
	}

	private void fitnessFunction() {
		for (int i = 0; i < psize; i++) {
			// изменить popul[i].fitness
		}
	}

	private void iteration() {
		Automaton[] newpopul = new Automaton[psize];
		select(newpopul);
		crossover(newpopul);
		mutation(newpopul);
		popul = newpopul;
	}

	private void select(Automaton[] newpopul) {
		int i = 0;
		while (i < psize) {
			int index = rand.nextInt(psize);
			double p = rand.nextDouble();
			double probability = popul[index].fitness / popul[0].fitness;

			if (p < probability) {
				newpopul[i] = popul[index];
				i++;
				// выбираем, кого не менять:
				p = rand.nextDouble();
				if (p < probability)
					newpopul[i].wasUsed = true;
			}
		}
	}

	private void crossover(Automaton[] newpopul) {
		for (int i = 0; i < psize; i++) {
			// выбираем первого родителя
			int index1 = rand.nextInt(psize);
			double p1 = rand.nextDouble();
			double probability1 = newpopul[index1].fitness / popul[0].fitness;
			// выбираем второго родителя
			int index2 = rand.nextInt(psize);
			double p2 = rand.nextDouble();
			double probability2 = newpopul[index2].fitness / popul[0].fitness;

			// если оба могут скрещиваться:
			if (p1 < probability1 && p2 < probability2
					&& !newpopul[index2].wasUsed && !newpopul[index2].wasUsed) {
				for (int j = 0; j < newpopul[index1].m; j++) {
					State[] childs = new State[2];

					childs = cross(newpopul[index1].states[j],
							newpopul[index2].states[j]);
					newpopul[index1].states[j] = childs[0];
					newpopul[index2].states[j] = childs[1];

					newpopul[index1].wasUsed = true;
					newpopul[index2].wasUsed = true;
				}
			}
		}
	}

	private State[] cross(State st1, State st2) {
		State[] childs = new State[2];
		choosePred(st1, st2, childs); // выбор значимых для потомков предикатов
		int point = rand.nextInt(st1.auto.rows);
		fillChild(st1, st2, childs[0], point); // заполним таблицу первого потомка
		fillChild(st1, st2, childs[1], point); // заполним таблицу второго потомка
		return childs;
	}

	private void choosePred(State st1, State st2, State[] childs) {
		int sizePred = st1.inPred.length;
		int jSt1 = 0; // сколько значимых предикатов уже выбрано для первого потомка
		int jSt2 = 0; // сколько значимых предикатов уже выбрано для второго потомка
		for (int i = 0; i < sizePred; i++) {
			if (st1.inPred[i] && st2.inPred[i]) { // пересечение предикатов
													// достается обоим
				childs[0].inPred[i] = true;
				childs[1].inPred[i] = true;
				jSt1++;
				jSt2++;
			} else {
				State heir = new State();
				if (jSt1 < st1.auto.r && jSt2 < st2.auto.r) { // если у обоих
					double p = rand.nextDouble(); 		// есть места,
					if (p < 0.5) heir = st1;			//то равновероятно обоим
					else heir = st2;
				} else {
					if (jSt1 < st1.auto.r) heir = st1; // иначе тому, у кого есть место
					if (jSt2 < st2.auto.r) heir = st2;
				}
				if (heir.equals(st1)) {
					jSt1++;
					childs[0].inPred[i] = true;
				} else {
					jSt2++;
					childs[0].inPred[i] = true;
				}
			}
		}
	}

	private void fillChild(State st1, State st2, State child, int point) {
		int sizePred = child.inPred.length;
		for (int q = 0; q < child.auto.rows; q++) {
			ArrayList<Integer> lines1 = new ArrayList<Integer>();
			ArrayList<Integer> lines2 = new ArrayList<Integer>();
			// двоичный вектор значений предикатов:
			ArrayList<Integer> chVect = BinaryMath.toBinary(q);
			
			//выберем влияющие строки
			for (int i = 0; i < child.auto.rows; i++) {
				ArrayList<Integer> vect = BinaryMath.toBinary(i);
				int jSt1 = 0;
				int jSt2 = 0;
				int jCh = 0;
				for (int j = 0; j < sizePred; j++) {
					if (st1.inPred[j]) jSt1++; // считаем, какой это номер
					if (st2.inPred[j]) jSt2++; // предиката в векторе
					if (child.inPred[j]) jCh++;

					if ((st1.inPred[j] && child.inPred[j])
							&& !((st2.inPred[j] && child.inPred[j]) && i >= point)) {
						if (vect.get(jSt1) == chVect.get(jCh)) {
							lines1.add(i);
						}
					}

					if ((st2.inPred[j] && child.inPred[j])
							&& !((st1.inPred[j] && child.inPred[j]) && i < point)) {
						if (vect.get(jSt2) == chVect.get(jCh)) {
							lines2.add(i);
						}
					}
				}
			}
			//выберем целевое состояние
			double [] p1 = new double[child.auto.m];
			double [] p2 = new double[child.auto.m];
			int linesize1 = lines1.size();
			int linesize2 = lines2.size();
			for (int i = 0; i < linesize1; i++) {
				p1[st1.nextState[lines1.get(i)]] += 1 / linesize1;
			}
			for (int i = 0; i < linesize2; i++) {
				p2[st2.nextState[lines2.get(i)]] += 1 / linesize2;
			}
			double [] probability = new double[child.auto.m];
			for (int i = 0; i < child.auto.m; i++) {
				probability[i] = p1[i] + p2[i];
			}
			int tmp = -1;
			while (tmp == -1) {
				int index = rand.nextInt(child.auto.m);
				double p = rand.nextDouble();
				if (p < probability[index]) tmp = index;
			}
			child.nextState[q] = tmp;
				
			//выбираем выходные воздействия
			for (int i = 0; i < child.auto.k; i++) {
				double q1 = 0;
				double q2 = 0;
				for (int j = 0; j < linesize1; j++) {
					q1 += st1.output[lines1.get(j)][i] / linesize1;
				}
				for (int j = 0; j < linesize2; j++) {
					q2 += st2.output[lines2.get(j)][i] / linesize2;
				}
				double p = rand.nextDouble();
				if (p < (q1 + q2) / 2) child.output[q][i] = 1;
				else child.output[q][i] = 0;
			}
		}
	}

	private void mutation(Automaton[] newpopul) {
		for (int i = 0; i < psize; i++) {
			if (!newpopul[i].wasUsed) {
				for (int j = 0; j < newpopul[i].m; j ++) {
					double probability = 1 - (newpopul[j].fitness / popul[0].fitness);
					mutationStates(newpopul[i].states[j], probability);
				}
				newpopul[i].wasUsed = true;
			}
		}
	}
	
	private void mutationStates(State st, double probability) {
		//мутиуем множство зачимых предикатов
		double p = rand.nextDouble();
		if (p < probability) {
			int index1 = 0;
			int index2 = 0;
			do {
				index1 = rand.nextInt(st.inPred.length);
			} while (st.inPred[index1]);
			do {
				index2 = rand.nextInt(st.inPred.length);
			} while (!st.inPred[index2]);
			st.inPred[index1] = false;
			st.inPred[index2] = true;
		}
		
		//мутиуем саму таблицу:
		for (int i = 0; i < st.auto.rows; i++) {
			p = rand.nextDouble();
			if (p < probability) {
				st.nextState[i] = rand.nextInt(st.auto.m); //целевые сост-я
			}
			for (int j = 0; j < st.auto.k; j++) {
				p = rand.nextDouble();
				if (p < probability / 2) {
					st.output[i][j] = rand.nextInt(2); //выходные воздействия
				}
			}
		}
	}

}
