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

public class UVA_10223_HowManyNodes {

	static final int MAX = 22;

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);

		BigInteger[] cat = new BigInteger[MAX];
		cat[0] = BigInteger.valueOf(1);
		for (int i = 1; i < MAX; i++) {
			cat[i] = ((BigInteger.valueOf(i << 1).multiply(BigInteger.valueOf((i << 1) - 1))).multiply(cat[i - 1]))
					.divide((BigInteger.valueOf((i + 1) * i)));
		}
		while (sc.ready()) {
			BigInteger n = BigInteger.valueOf(sc.nextLong());
			int i;
			for (i = 1; i < MAX; i++)
				if (cat[i].compareTo(n) >= 0)
					break;
			out.println(i);
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
