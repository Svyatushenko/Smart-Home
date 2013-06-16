import java.util.ArrayList;
import java.util.Collections;

public class BinaryMath {
	
	static public int times(int q) { //степень двойки
		int res = 1;
		for (int i = 0; i < q; i++) {
			res *= 2;
		}
		return res;
	}
	
	static public int toTen (int [] a) {
		int n = a.length;
		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans += a[i] * times(n - i - 1);
		}
		return ans;
	}
	
	static public ArrayList<Integer> toBinary (int x) {
		ArrayList<Integer> ans = new ArrayList<Integer>();
		while (x > 1) {
			ans.add(x % 2);
			x = x / 2;
		}
		ans.add(x);
		Collections.reverse(ans);
		return ans;
	}

}