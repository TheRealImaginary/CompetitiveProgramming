package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We use an LIS-like DP, so for each point we will come to it from any point before
 *				 it, so we check all points before and take the one with the minimal time.
 *				 Hence, let f(i) be the minimum time need to reach point i, which can be
 *				 calculate by f(i) = min(f(j) + cost(j, i)) such that j >= 0 && j < i. Where
 *				 cost(j, i) is the cost of the distance `i - j` including the tire change.
 */
public class UVA_1211_AtomicCarRace {

	static final double oo = 1e15;
	static final int MAX = 10001;

	static int[] a;
	static double[] cost;
	static double b, v, e, f;
	static int r;

	// This is almost 1 second slower than pre-computing
	static double getCost(int start, int end) {
		double res = 0;
		for (int x = 0; x < a[end] - a[start]; x++)
			if (x >= r)
				res += (1 / (v - e * (x - r)));
			else
				res += (1 / (v - f * (r - x)));
		return res + (start == 0 ? 0 : b);
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			a = new int[n + 1];
			for (int i = 1; i <= n; i++)
				a[i] = sc.nextInt();
			b = sc.nextDouble();
			r = sc.nextInt();
			v = sc.nextDouble();
			e = sc.nextDouble();
			f = sc.nextDouble();
			cost = new double[MAX];
			for (int dist = 0; dist + 1 < MAX; dist++) {
				cost[dist + 1] = cost[dist];
				if (dist >= r)
					cost[dist + 1] += (1 / (v - e * (dist - r)));
				else
					cost[dist + 1] += (1 / (v - f * (r - dist)));
			}
			double[] dp = new double[n + 1];
			for (int i = 1; i <= n; i++) {
				double res = oo;
				for (int j = 0; j < i; j++) {
					res = Math.min(res, dp[j] + cost[a[i] - a[j]] + (j == 0 ? 0 : b));
					// res = Math.min(res, dp[j] + getCost(j, i));
				}
				dp[i] = res;
			}
			out.printf("%.4f\n", dp[n]);
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
