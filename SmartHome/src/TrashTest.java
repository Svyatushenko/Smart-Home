import java.io.*;
import java.util.*;

public class TrashTest implements Runnable {

	public static void main(String[] args) {
		new Thread(new TrashTest()).start();
	}

	BufferedReader in;
	PrintWriter out;
	StringTokenizer st;

	public void run() {
		try {
			in = new BufferedReader(new FileReader(new File("TrashTest.in")));
			out = new PrintWriter("TrashTest.out");
			solve();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(111);
		} finally {
			out.close();
		}
	}

	String nextToken() {
		try {
			while (st == null || !st.hasMoreElements()) {
				st = new StringTokenizer(in.readLine());
			}
		} catch (Exception e) {
			return "";
		}
		return st.nextToken();
	}

	int nextInt() {
		return Integer.parseInt(nextToken());
	}

	void solve() {
		int n = nextInt();
		Automaton test = new Automaton(1, 1, n, 1);
		State [] ololo = new State[2];
		ololo[0] = test.states[0];
		
		Random r = new Random();
		out.println(BinaryMath.times(n));
		int [] a = new int [n];
		for (int i = 0; i < n; i++) {
			a[i] = r.nextInt(2);
			out.print(a[i] + " ");
		}
		out.println();
		out.println(BinaryMath.toTen(a));
	}
}
