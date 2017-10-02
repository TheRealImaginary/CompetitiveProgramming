package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class UVA_10543_TravelingPolitician {

	static final int oo = 1 << 28;
	static final int MAX = 51;

	static int N, M, K;
	static ArrayList<Integer>[] g;
	static int[][] dp;

	static int solve(int prev, int k) {
		if (prev == N - 1 && k >= K)
			return k;
		if (k >= MAX)
			return oo;
		if (dp[prev][k] != -1)
			return dp[prev][k];
		int res = oo;
		for (int v : g[prev])
			if (v != prev)
				res = Math.min(res, solve(v, k + 1));
		return dp[prev][k] = res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			N = sc.nextInt();
			if (N == 0)
				break;
			M = sc.nextInt();
			K = sc.nextInt();
			g = new ArrayList[N];
			for (int i = 0; i < N; i++)
				g[i] = new ArrayList<>();
			for (int i = 0; i < M; i++) {
				int u = sc.nextInt(), v = sc.nextInt();
				g[u].add(v);
			}
			// System.err.println(Arrays.toString(g));
			dp = new int[N][MAX];
			for (int i = 0; i < N; i++)
				Arrays.fill(dp[i], -1);
			int ans = solve(0, 1);
			out.println(ans == oo ? "LOSER" : ans);
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

		MyScanner(String filename) throws IOException {
			br = new BufferedReader(new FileReader(new File(filename)));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		boolean ready() throws IOException {
			return br.ready();
		}
	}
}
