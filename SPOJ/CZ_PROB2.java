package SPOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class CZ_PROB2 {

	static final int MAX = 40000;

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int TC = sc.nextInt();
		while (TC-- > 0) {
			int n = sc.nextInt();
			long ans = 1;
			for (int d = 2; d * 1L * d <= n; d++) {
				int exp = 0;
				while (n % d == 0) {
					n /= d;
					exp++;
				}
				ans *= (long) (Math.pow(d, exp + 1) - 1) / (d - 1);
			}
			if (n != 1)
				ans *= (long) (Math.pow(n, 2) - 1) / (n - 1);
			out.println(ans);
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
