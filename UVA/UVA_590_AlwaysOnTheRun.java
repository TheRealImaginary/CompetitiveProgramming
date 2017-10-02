package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class UVA_590_AlwaysOnTheRun {

	static final int oo = (int) 1e9;
	static int[][][] adjMatrix;
	static int[][] dp, numDays;
	static int N, K;

	static void sol(int[][] num_days, int[][][] adjMatrix, int Case, int n, int k, PrintWriter out) throws IOException {
		int[][] dp = new int[n + 1][k + 1]; // city i, on day j
		for (int i = 0; i <= n; i++)
			Arrays.fill(dp[i], oo);
		dp[1][0] = 0;
		for (int d = 1; d <= k; d++) {
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					if (i == j)
						continue;
					int x = (d - 1) % num_days[j][i] + 1;
					if (adjMatrix[j][i].length > x && adjMatrix[j][i][x] != 0 && dp[j][d - 1] != oo)
						dp[i][d] = Math.min(dp[i][d], dp[j][d - 1] + adjMatrix[j][i][x]);
				}
			}
		}
		out.printf("Scenario #%d\n", Case);
		if (dp[n][k] == oo)
			out.println("No flight possible.");
		else
			out.printf("The best flight costs %d.\n", dp[n][k]);
		out.println();
	}

	static int solve(int node, int day) {
		if (day == K)
			return node == N ? 0 : oo;
		if (dp[node][day] != -1)
			return dp[node][day];
		int res = oo;
		for (int next = 1; next <= N; next++)
			if (next != node && adjMatrix[node][next].length != 0
					&& adjMatrix[node][next][day % numDays[node][next] + 1] != 0)
				res = Math.min(res, solve(next, day + 1) + adjMatrix[node][next][day % numDays[node][next] + 1]);
		return dp[node][day] = res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int Case = 1;
		boolean f = true;
		while (true) {
			int n = sc.nextInt(), k = sc.nextInt();
			if (n == k && n == 0)
				break;
			adjMatrix = new int[n + 1][n + 1][];
			numDays = new int[n + 1][n + 1];
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					if (i == j)
						continue;
					int numD = sc.nextInt();
					numDays[i][j] = numD;
					adjMatrix[i][j] = new int[numD + 1];
					for (int d = 1; d <= numD; d++) {
						adjMatrix[i][j][d] = sc.nextInt();
					}
				}
			}
			// sol(numDays, adjMatrix, Case++, n, k, out);
			dp = new int[n + 1][k + 1];
			for (int i = 0; i <= n; i++)
				Arrays.fill(dp[i], -1);
			N = n;
			K = k;
			out.printf("Scenario #%d\n", Case++);
			int ans = solve(1, 0);
			if (ans == oo)
				out.println("No flight possible.");
			else
				out.printf("The best flight costs %d.\n", ans);
			out.println();
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

		public byte nextByte() throws IOException {
			return Byte.parseByte(next());
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
