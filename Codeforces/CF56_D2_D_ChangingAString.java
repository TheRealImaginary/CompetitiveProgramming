package Codeforces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class CF56_D2_D_ChangingAString {

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		char[] s = sc.next().toCharArray(), t = sc.next().toCharArray();
		int n = s.length, m = t.length;
		int[][] dp = new int[n + 1][m + 1];
		for (int i = 0; i <= n; i++)
			dp[i][0] = i;
		for (int i = 0; i <= m; i++)
			dp[0][i] = i;
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= m; j++) {
				if (s[i - 1] == t[j - 1])
					dp[i][j] = dp[i - 1][j - 1];
				else
					dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
			}
		out.println(dp[n][m]);
		for (int i = s.length, j = t.length; i > 0 || j > 0;) {
			int min = i > 0 && j > 0 ? Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) : 0;
			if (min == dp[i][j]) {
				i--;
				j--;
			} else {
				if (i == 0 || (j > 0 && min == dp[i][j - 1])) {
					out.printf("INSERT %d %s\n", i + 1, t[--j]);
				} else if (j == 0 || min == dp[i - 1][j]) {
					out.printf("DELETE %d\n", i--);
				} else {
					out.printf("REPLACE %d %s\n", i--, t[--j]);
				}
			}
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

		long nextLong() throws IOException {
			return Long.parseLong(next());
		}
	}
}
