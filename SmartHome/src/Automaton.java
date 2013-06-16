import java.io.PrintWriter;
import java.util.Random;

public class Automaton implements Comparable<Automaton> {

	public final int n; //���-�� �������� (����)
	public final int k; //���-�� ����-� (�����)
	public final int m; //���-�� ��������� ��������
	public final int r; //���-�� �������� ����������
	
	double fitness;
	State[] states;
	int start;
	int curState;
	boolean wasUsed; // ��� ��� �������� ���� �� ����������
	
	int rows; //���-�� ����� � �������� ���������

	@Override
	public int compareTo(Automaton arg0) {
		return new Double(this.fitness).compareTo(arg0.fitness);
	}

	Random rand = new Random();

	
	public Automaton(int n, int k, int m, int r) {
		this.n = n;
		this.k = k;
		this.m = m;
		this.r = r;
		this.rows = BinaryMath.times(r);

		states = new State[m];
		for (int i = 0; i < m; i++) {
			states[i] = new State(this);
		}

		start = rand.nextInt(m);
	}

	public int[] step(int[] env) {
		int ans = BinaryMath.toTen(env);
		curState = states[curState].nextState[ans];
		return states[curState].output[ans];
	}

	public void write (PrintWriter out) {
		for (int i = 0; i < m; i++) {
			out.println(i);
			for (int j = 0; j < rows; j++) {
				out.print(states[i].nextState[j]+ " ");
				for (int l = 0; l < k; l++) {
					out.print(states[i].output[j][l] + " ");
				}
			}
			out.println();
		}
	}
}
