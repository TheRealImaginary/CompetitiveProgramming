package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-Try each part, could be either repeating part and rest is not
 *				 we multiply by the 10^m where m is the length of the cycle creating
 *				 number (10^m)x, now (10^m)x - x = (10^(m-1))x = a, we solve for x, we pick the
 *				 one giving minimal den.
 */
public class UVA_10555_DeadFraction {

	static final long oo = 1L << 57;

	static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	static long get(char[] c, int s, int e) {
		long res = 0;
		for (int i = s; i < e; i++)
			res = res * 10 + (c[i] - '0');
		return res;
	}

	static long pow(long base, long exp) {
		long res = 1;
		while (exp > 0) {
			if ((exp & 1) != 0)
				res = (res * base);
			exp >>= 1;
			base = (base * base);
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			String s = sc.next();
			if (s.equals("0"))
				break;
			char[] c = s.toCharArray();
			int start = 2, end = -1;
			for (int i = 3; i < c.length; i++)
				if (c[i] == '.') {
					end = i;
					break;
				}
			long b = oo, a = oo;
			for (int i = start; i < end; i++) {
				long np = get(c, start, i), p = get(c, i, end);
				long num = (pow(10, end - i) - 1) * np + p, den = pow(10, i - start) * (pow(10, end - i) - 1),
						gcd = gcd(num, den);
				num /= gcd;
				den /= gcd;
				if (den < b) {
					a = num;
					b = den;
				}
			}
			out.printf("%d/%d\n", a, b);
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
