package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class UVA_11161_HelpMyBrother {

	static final int MAX = 1501;

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);

		BigInteger[] fib = new BigInteger[MAX];
		fib[0] = BigInteger.ZERO;
		fib[1] = BigInteger.ONE;
		for (int i = 2; i < MAX; i++)
			fib[i] = fib[i - 1].add(fib[i - 2]);
		BigInteger[] sum = new BigInteger[MAX];
		sum[0] = BigInteger.ZERO;
		for (int i = 1; i < MAX; i++)
			sum[i] = fib[i].add(sum[i - 1]);

		for (int tc = 1; true; tc++) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			BigInteger write = !fib[n].testBit(0) ? fib[n].divide(BigInteger.valueOf(2))
					: fib[n].add(BigInteger.ONE).divide(BigInteger.valueOf(2));
			BigInteger before = sum[n - 1];
			out.printf("Set %d:\n%s\n", tc, write.add(before).subtract(BigInteger.ONE));
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
