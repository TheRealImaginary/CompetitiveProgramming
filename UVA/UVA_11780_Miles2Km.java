package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class UVA_11780_Miles2Km {

	static final int N = 1001;
	static final int MAX = 18;

	static int[] fib;
	static int[] dp;

	static int solve(int n) {
		if (dp[n] != -1)
			return dp[n];
		double original = 1.6 * n;
		int res = 0;
		for (int i = 1; i < MAX - 1 && fib[i] <= n; i++)
			if (Math.abs(original - fib[i + 1] - solve(n - fib[i])) < Math.abs(original - res))
				res = fib[i + 1] + solve(n - fib[i]);
		return dp[n] = res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);

		fib = new int[MAX];
		fib[1] = 1;
		for (int i = 2; i < MAX; i++)
			fib[i] = fib[i - 1] + fib[i - 2];
		dp = new int[N];
		Arrays.fill(dp, -1);
		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			out.printf("%.2f\n", Math.abs(1.6 * n - solve(n)));
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

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}
