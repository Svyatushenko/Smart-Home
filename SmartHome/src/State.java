
public class State {
	
	Automaton auto; // �������, �������� ����������� ���������
	
		boolean[] inPred; // �������� ���������
		int[][] output;
		int[] nextState;
		
		public State () {
			output = new int[auto.rows][auto.k];
			inPred = new boolean[auto.n + auto.k];
			nextState = new int [auto.rows];
		}

		public State(Automaton a) { // ������� ��������� ���������

			auto = a;
			output = new int[auto.rows][auto.k];
			inPred = new boolean[auto.n + auto.k];
			nextState = new int [auto.rows];

			int tmp = 0;
			while (tmp < auto.r) { // �������� r �� n+k ������� ����������
				for (int i = 0; i < auto.n + auto.k; i++) {
					inPred[i] = auto.rand.nextBoolean();
					if (inPred[i])
						tmp++;
					if (tmp == auto.r)
						break;
				}
			}

			for (int i = 0; i < auto.rows; i++) { //��������� �������� �����������
				nextState[i] = auto.rand.nextInt(auto.m);
				for (int j = 0; j < auto.k; j++) {
					output[i][j] = auto.rand.nextInt(2);
				}
			}
		}
	}
