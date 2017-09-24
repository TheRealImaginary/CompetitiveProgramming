package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * 
 * @author Mazen Magdy
 * 
 * 				 -Note: To improve the complexity, we could use prefix-sums on `phi` and then
 * 						use BinarySearch to find the greatest index with sum less than or equal
 * 						to `k` then loop to find the desired number.
 */
public class UVA_11327_EnumeratingRationalNumbers {

	static final int MAX = 200000;

	static long[] phi;

	static void eulerTotient() {
		phi = new long[MAX + 1];
		phi[1] = 2;
		for (int i = 2; i <= MAX; i++)
			phi[i] = i;
		for (int i = 2; i <= MAX; i++)
			if (phi[i] == i)
				for (int j = i; j <= MAX; j += i)
					phi[j] = (phi[j] / i) * (i - 1);
	}

	static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		eulerTotient();
		while (true) {
			long k = sc.nextLong();
			if (k == 0)
				break;
			for (int i = 1; i <= MAX; k -= phi[i], i++) {
				if (k <= phi[i]) {
					int cnt = 0;
					for (int j = 0; j <= i; j++) {
						if (gcd(j, i) == 1)
							cnt++;
						if (cnt == k) {
							out.printf("%d/%d\n", j, i);
							break;
						}
					}
					break;
				}
			}
		}
		out.flush();
		out.close();
	}

	static class MyScanner {
		StringTokenizer st;
		BufferedReader br;

		public MyScanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}
	}
}
