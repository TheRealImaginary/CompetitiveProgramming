package HackerRank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-For each 1 we calculate how many 1s are there in the range (i + 1, i + k), let's call it x.
 *				 The answer is the x / (n * n).
 */
public class SherlockAndProbability {

	static int getSum(int[] a, int l, int r) {
		return a[Math.min(r, a.length - 1)] - (l == 0 ? 0 : a[l - 1]);
	}

	static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt(), k = sc.nextInt();
			char[] c = sc.nextLine().toCharArray();
			int[] a = new int[n];
			for (int i = 0; i < n; i++) {
				a[i] = c[i] - '0';
				if (i > 0)
					a[i] += a[i - 1];
			}
			long res = 0;
			for (int i = 0; i < n; i++)
				if (c[i] == '1') {
					res++;
					res += (getSum(a, i + 1, i + k) << 1);
				}
			long gcd = gcd(res, n * 1L * n);
			out.printf("%d/%d\n", res / gcd, (n * 1L * n) / gcd);
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

		public MyScanner(String filename) throws IOException {
			br = new BufferedReader(new FileReader(new File(filename)));
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

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}
