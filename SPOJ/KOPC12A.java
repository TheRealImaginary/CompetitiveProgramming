package SPOJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class KOPC12A {

	static int N;
	static long[] h, c;

	static long f(long height) {
		long cost = 0;
		for (int i = 0; i < N; i++)
			cost += Math.abs(height - h[i]) * c[i];
		return cost;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			N = sc.nextInt();
			h = new long[N];
			for (int i = 0; i < N; i++)
				h[i] = sc.nextLong();
			c = new long[N];
			for (int i = 0; i < N; i++)
				c[i] = sc.nextLong();
			long left = 0, right = 10000;
			while (right - left > 2) {
				long l1 = left + (right - left) / 3, l2 = right - (right - left) / 3;
				if (f(l1) < f(l2))
					right = l2;
				else
					left = l1;
			}
			out.println(Math.min(f(left), Math.min(f(right), f((left + right) >> 1))));
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
