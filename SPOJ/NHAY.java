package SPOJ;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class NHAY {

	static int[] fail(char[] c) {
		int n = c.length;
		int[] f = new int[n];
		for (int i = 1, j = 0; i < n; i++) {
			while (j > 0 && c[i] != c[j])
				j = f[j - 1];
			if (c[i] == c[j])
				j++;
			f[i] = j;
		}
		return f;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner(new FileInputStream("in.txt"));
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			int n = sc.nextInt();
			String s = sc.next() + "$" + sc.next();
			int[] f = fail(s.toCharArray());
			// System.err.println(Arrays.toString(f));
			boolean found = false;
			for (int i = 0; i < f.length; i++) {
				if (f[i] == n) {
					out.println(i - (n + 1) - (n - 1));
					found = true;
				}
			}
			if (!found)
				out.println();
		}
		out.flush();
		out.close();
	}

	static class MyScanner {

		BufferedReader br;
		StringTokenizer st;

		MyScanner(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		boolean ready() throws IOException {
			return br.ready();
		}
	}
}
