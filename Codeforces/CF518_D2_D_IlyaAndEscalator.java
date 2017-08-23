package Codeforces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CF518_D2_D_IlyaAndEscalator {

	static int N, T;
	static double p;
	static double[][] dp;

	static double solve(int people, int time) {
		if (time == 0 && people == 0)
			return 1;
		if (time < 0 || people < 0)
			return 0;
		if (dp[people][time] != -1)
			return dp[people][time];
		if (people == 1)
			return dp[people][time] = (p * solve(people - 1, time - 1)) + solve(people, time - 1);
		return dp[people][time] = (p * solve(people - 1, time - 1)) + ((1 - p) * solve(people, time - 1));
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		N = sc.nextInt();
		p = sc.nextDouble();
		T = sc.nextInt();
		dp = new double[N + 1][T + 1];
		for (int i = 0; i <= N; i++)
			Arrays.fill(dp[i], -1);
		double ans = 0;
		for (int people = 1; people <= N; people++)
			ans += solve(people, T);
		out.println(ans);
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

		String nextLine() throws IOException {
			return br.readLine();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		double nextDouble() throws IOException {
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

		boolean ready() throws IOException {
			return br.ready();
		}
	}
}
