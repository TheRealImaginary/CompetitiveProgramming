package UVA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class UVA_10303_HowManyTrees {

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner(new FileInputStream("in.txt"));
		PrintWriter pw = new PrintWriter(System.out);
		BigInteger[] cat = new BigInteger[1001];
		cat[0] = BigInteger.valueOf(1);
		for (int i = 1; i <= 1000; i++)
			cat[i] = ((BigInteger.valueOf(i << 1).multiply(BigInteger.valueOf((i << 1) - 1))).multiply(cat[i - 1]))
					.divide((BigInteger.valueOf((i + 1) * i)));
		while (sc.ready()) {
			int n = sc.nextInt();
			pw.println(cat[n]);
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
