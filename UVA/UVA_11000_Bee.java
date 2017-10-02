package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class UVA_11000_Bee {

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		long[] male = new long[100];
		male[0] = 0;
		male[1] = 1;
		for (int i = 2; i <= 99; i++)
			male[i] = male[i - 1] + male[i - 2] + 1;
		long[] female = new long[100];
		female[0] = 1;
		for (int i = 1; i <= 99; i++)
			female[i] = male[i - 1] + 1;
		while (true) {
			int n = sc.nextInt();
			if (n == -1)
				break;
			pw.printf("%d %d\n", male[n], female[n] + male[n]);
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
