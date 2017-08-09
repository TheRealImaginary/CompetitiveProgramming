package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 * 				-Mimicing the torus and then,
 * 				Using O(n^4) Brute Force to find the maximum answer.
 */
public class UVA_10827_MaximumSumOnATorus {

	static final int oo = 1 << 28;

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt();
			int[][] a = new int[(n << 1) - 1][(n << 1) - 1];
			for (int i = 0; i < (n << 1) - 1; i++)
				for (int j = 0; j < (n << 1) - 1; j++) {
					if (i < n && j < n) {
						a[i][j] = sc.nextInt();
						if (j != n - 1)
							a[i][j + n] = a[i][j];
						if (i != n - 1)
							a[i + n][j] = a[i][j];
						if (i != n - 1 && j != n - 1)
							a[i + n][j + n] = a[i][j];
					}
					if (j > 0)
						a[i][j] += a[i][j - 1];
					if (i > 0)
						a[i][j] += a[i - 1][j];
					if (i > 0 && j > 0)
						a[i][j] -= a[i - 1][j - 1];
				}
			int max = -oo;
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					for (int k = i; k < i + n; k++)
						for (int l = j; l < j + n; l++) {
							int sum = a[k][l];
							if (j > 0)
								sum -= a[k][j - 1];
							if (i > 0)
								sum -= a[i - 1][l];
							if (i > 0 && j > 0)
								sum += a[i - 1][j - 1];
							max = Math.max(max, sum);
						}
			out.println(max);
		}
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

		public double nextDouble() throws IOException {
			String x = next();
			StringBuilder sb = new StringBuilder("0");
			double res = 0, f = 1;
			boolean dec = false, neg = false;
			int start = 0;
			if (x.charAt(0) == '-') {
				neg = true;
				start++;
			}
			for (int i = start; i < x.length(); i++)
				if (x.charAt(i) == '.') {
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				} else {
					sb.append(x.charAt(i));
					if (dec)
						f *= 10;
				}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg ? -1 : 1);
		}

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}
