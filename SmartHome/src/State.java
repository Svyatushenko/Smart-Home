
public class State {
	
	Automaton auto; // автомат, которому принадлежит состояние
	
		boolean[] inPred; // значимые предикаты
		int[][] output;
		int[] nextState;
		
		public State () {
			output = new int[auto.rows][auto.k];
			inPred = new boolean[auto.n + auto.k];
			nextState = new int [auto.rows];
		}

		public State(Automaton a) { // создать рандомное состояние

			auto = a;
			output = new int[auto.rows][auto.k];
			inPred = new boolean[auto.n + auto.k];
			nextState = new int [auto.rows];

			int tmp = 0;
			while (tmp < auto.r) { // выбираем r из n+k входных предикатов
				for (int i = 0; i < auto.n + auto.k; i++) {
					inPred[i] = auto.rand.nextBoolean();
					if (inPred[i])
						tmp++;
					if (tmp == auto.r)
						break;
				}
			}

			for (int i = 0; i < auto.rows; i++) { //случайные выходные воздействия
				nextState[i] = auto.rand.nextInt(auto.m);
				for (int j = 0; j < auto.k; j++) {
					output[i][j] = auto.rand.nextInt(2);
				}
			}
		}
	}
