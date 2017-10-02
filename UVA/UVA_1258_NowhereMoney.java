package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UVA_1258_NowhereMoney {

	static final int MAX = 91;

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);

		long[] fib = new long[MAX];
		fib = new long[MAX];
		fib[0] = fib[1] = 1;
		for (int i = 2; i < MAX; i++)
			fib[i] = fib[i - 1] + fib[i - 2];

		while (sc.ready()) {
			long n = sc.nextLong();
			ArrayList<Integer> slots = new ArrayList<>();
			ArrayList<Long> amount = new ArrayList<>();
			out.println(n);
			for (int i = MAX - 1; i >= 0 && n > 0; i--)
				if (n >= fib[i]) {
					slots.add(i);
					amount.add(fib[i]);
					n -= fib[i];
				}
			for (int i = 0; i < slots.size(); i++) {
				out.print(slots.get(i));
				out.print(" ");
			}
			out.println();
			for (int i = 0; i < amount.size(); i++) {
				out.print(amount.get(i));
				out.print(" ");
			}
			out.println("\n");
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
