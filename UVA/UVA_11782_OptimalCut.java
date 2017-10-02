package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class UVA_11782_OptimalCut {

	static MyScanner sc;
	static int N;
	static int[] tree;
	static int[][] dp;

	static void build(int p, int h) throws IOException {
		tree[p] = sc.nextInt();
		if (h < N) {
			build(p << 1, h + 1);
			build((p << 1) + 1, h + 1);
		}
	}

	static int solve(int p, int k, int h) {
		if (h == N)
			return tree[p];
		if (dp[k][p] != -1)
			return dp[k][p];
		int ans = tree[p];
		for (int i = 1; i < k; i++)
			ans = Math.max(ans, solve(p << 1, i, h + 1) + solve((p << 1) + 1, k - i, h + 1));
		return dp[k][p] = ans;
	}

	public static void main(String[] args) throws IOException {
		sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			N = sc.nextInt();
			if (N == -1)
				break;
			int k = sc.nextInt();
			tree = new int[1 << (N + 1)];
			build(1, 0);
			dp = new int[k + 1][1 << (N + 1)];
			for (int i = 0; i <= k; i++)
				Arrays.fill(dp[i], -1);
			out.println(solve(1, k, 0));
		}
		out.flush();
		out.close();
	}

	static class MyScanner {

		BufferedReader br;
		StringTokenizer st;

		MyScanner(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		short nextShort() throws IOException {
			return Short.parseShort(next());
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		boolean ready() throws IOException {
			return br.ready();
		}
	}
}
