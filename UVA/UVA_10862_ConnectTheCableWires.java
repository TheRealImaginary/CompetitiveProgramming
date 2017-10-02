package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class UVA_10862_ConnectTheCableWires {

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		BigInteger[] fib = new BigInteger[4001];
		fib[0] = BigInteger.valueOf(0L);
		fib[1] = BigInteger.valueOf(1L);
		for (int i = 2; i <= 4000; i++)
			fib[i] = fib[i - 1].add(fib[i - 2]);
		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			pw.println(fib[n << 1]);
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
