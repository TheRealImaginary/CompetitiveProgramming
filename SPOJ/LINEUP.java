package SPOJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class LINEUP {

	static final long oo = 1L << 57;
	static final int N = 11;

	static long[][] a, dp;

	static long solve(int idx, int msk) {
		if (idx == N)
			return msk == (1 << N) - 1 ? 0 : -oo;
		if (dp[idx][msk] != -1)
			return dp[idx][msk];
		long ans = -oo;
		for (int i = 0; i < N; i++)
			if (a[idx][i] > 0 && (msk & (1 << i)) == 0)
				ans = Math.max(ans, solve(idx + 1, msk | (1 << i)) + a[idx][i]);
		return dp[idx][msk] = ans;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			a = new long[N][N];
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					a[i][j] = sc.nextLong();
			dp = new long[N][1 << N];
			for (int i = 0; i < N; i++)
				Arrays.fill(dp[i], -1);
			out.println(solve(0, 0));
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
