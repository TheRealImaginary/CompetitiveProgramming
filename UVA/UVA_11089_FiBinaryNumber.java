package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class UVA_11089_FiBinaryNumber {

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);

		long[] fib = new long[50];
		fib[0] = 0;
		fib[1] = 1;
		for (int i = 2; i <= 49; i++)
			fib[i] = fib[i - 1] + fib[i - 2];
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt();
			int i = 49;
			while (fib[i] > n)
				i--;
			StringBuilder sb = new StringBuilder();
			for (; i > 1; i--) {
				if (fib[i] <= n) {
					sb.append(1);
					n -= fib[i];
				} else
					sb.append(0);
			}
			pw.println(sb);
		}
		pw.close();
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

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}
