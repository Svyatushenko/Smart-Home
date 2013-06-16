import java.util.Arrays;
import java.util.Random;

public class Genetic {

	int psize;
	Automaton[] popul;
	Automaton best;

	double Eps = 0.1;

	Random rand = new Random();

	Genetic(int n, int k, int m, int r) {
		this.psize = n; // ������ ���������
		popul = createPopulation(n, k, m, r);
		best = runGenetic();
	}

	private Automaton[] createPopulation(int n, int k, int m, int r) {
		Automaton[] p = new Automaton[psize];
		for (int i = 0; i < psize; i++) {
			p[i] = new Automaton(n, k, m, r); // ��������� ��������
		}
		return p;
	}

	private Automaton runGenetic() {
		do {
			fitnessFunction();
			Arrays.sort(popul);
			iteration();
		} while (popul[0].fitness - popul[psize].fitness > Eps);
		return popul[0]; // ������ �����
	}

	private void fitnessFunction() {
		for (int i = 0; i < psize; i++) {
			// �������� popul[i].fitness
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
				// ��������, ���� �� ������:
				p = rand.nextDouble();
				if (p < probability)
					newpopul[i].wasUsed = true;
			}
		}
	}

	private void crossover(Automaton[] newpopul) {
		for (int i = 0; i < psize; i++) {
			//�������� ������� ��������
			int index1 = rand.nextInt(psize);
			double p1 = rand.nextDouble();
			double probability1 = newpopul[index1].fitness / popul[0].fitness;
			//�������� ������� ��������
			int index2 = rand.nextInt(psize);
			double p2 = rand.nextDouble();
			double probability2 = newpopul[index2].fitness / popul[0].fitness;
			
			//���� ��� ����� ������������:
			if (p1 < probability1 && p2 < probability2 &&
					!newpopul[index2].wasUsed && !newpopul[index2].wasUsed) {
				for (int j = 0; j < newpopul[index1].m; j++) {
					State [] childs = new State [2];
					
					childs = cross(newpopul[index1].states[j], newpopul[index2].states[j]);
					newpopul[index1].states[j] = childs[0];
					newpopul[index2].states[j] = childs[1];
					
					newpopul[index1].wasUsed = true;
					newpopul[index2].wasUsed = true;
				}
			}
		}
	}
	
	private State [] cross (State st1, State st2) {
		State [] childs = new State [2];
		choosePred(st1, st2, childs);	//����� �������� ��� �������� ����������
		int point = rand.nextInt(st1.auto.rows);
		childs[0] = fillChild (st1, st2, point); //�������� ������� ������� �������
		childs[1] = fillChild (st1, st2, point); //�������� ������� ������� �������
		return childs;				
	}
	
	private void choosePred (State st1, State st2, State [] childs) {
		int sizePred = st1.inPred.length;
		int jSt1 = 0; //������� �������� ���������� ��� ������� ��� ������� �������
		int jSt2 = 0; //������� �������� ���������� ��� ������� ��� ������� �������
		for (int i = 0; i < sizePred; i++) {
			if (st1.inPred[i] && st2.inPred[i]) { //����������� ���������� ��������� �����
				childs[0].inPred[i] = true;
				childs[1].inPred[i] = true;
				jSt1++;
				jSt2++;
			} else {
				State heir = new State();
				if (jSt1 < st1.auto.r && jSt2 < st2.auto.r) { //���� � ����� ���� �����
					double p = rand.nextDouble();			//�� ������������� �����
					if (p < 0.5) heir = st1;
					else heir = st2;
				} else {
					if (jSt1 < st1.auto.r) heir = st1;  //����� ����, � ���� ���� �����
					if (jSt2 < st2.auto.r) heir = st2;
				}
				if (heir.equals(st1)) {
					jSt1++;
					childs[0].inPred[i] = true;
				}
				else {
					jSt2++;
					childs[0].inPred[i] = true;
				}
			}
		}	
	}
	
	private State fillChild (State st1, State st2, int point) {
		State child = new State();
		
		return child;		
	}

	private Automaton[] mutation(Automaton[] newpopul) {
		for (int i = 0; i < psize; i++) {
			// �������� ����� �� newpopul, ������ �� ���� wasUsed
		}
		return newpopul;
	}

}
