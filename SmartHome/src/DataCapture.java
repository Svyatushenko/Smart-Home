import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class DataCapture {

	static Scanner in;
	static PrintWriter out;
	
	public static void main(String[] args) {
		try {
			in = new Scanner(new File("Environment.in"));
			out = new PrintWriter(new File ("Environment.out"));
			int n = in.nextInt(); // количество датчиков
			int k = in.nextInt(); // количество устройств
			int m = in.nextInt(); // количество состояний в автомате
			int r = in.nextInt(); // количество значимых предикатов
			Sensor [] sensors = new Sensor [n];
			Device [] devices = new Device [k];
			for (int i = 0; i < n; i++) {
				String s = in.next();
				int tmp1 = -1, tmp2 = -1, tmp3 = -1;
				if (s.equals("id")) {
					tmp1 = Integer.parseInt(in.next());
					s = in.next();
					s = in.next();
				}
				if (s.equals("sensorType")) {
					tmp2 = Integer.parseInt(in.next());
					s = in.next();
					s = in.next();
				}
				if (s.equals("location")) {
					tmp3 = Integer.parseInt(in.next());
				}
				sensors[i] = new Sensor(tmp1, tmp2, tmp3);
			}
			for (int i = 0; i < k; i++) {
				String s = in.next();
				int tmp1 = -1, tmp2 = -1, tmp3 = -1;
				double tmp4 = -1;
				if (s.equals("id")) {
					tmp1 = Integer.parseInt(in.next());
					s = in.next();
					s = in.next();
				}
				if (s.equals("deviceType")) {
					tmp2 = Integer.parseInt(in.next());
					s = in.next();
					s = in.next();
				}
				if (s.equals("location")) {
					tmp3 = Integer.parseInt(in.next());
					s = in.next();
					s = in.next();
				}
				if (s.equals("power")) {
					tmp4 = Double.parseDouble(in.next());
				}
				devices[i] = new Device(tmp1, tmp2, tmp3, tmp4);
			}
			Genetic gen = new Genetic (n, k, m, r);
			out.println(gen.best.fitness);
			gen.best.write(out);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(111);
		 } finally {
			 out.close();
		}
	}
}
