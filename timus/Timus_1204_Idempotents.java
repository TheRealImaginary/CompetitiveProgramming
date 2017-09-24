package timus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-The number of solutions to x^2 = x (mod m) is equal to 2^k, where k is
 *				 the number of prime factors of m. Solution is based on the Chinese Remainder
 *				 Theorem.
 * @see https://crypto.stanford.edu/pbc/notes/numbertheory/crt.html
 */
public class Timus_1204_Idempotents {

	static long modPow(long base, long exp, long MOD) {
		long res = 1;
		while (exp > 0) {
			if ((exp & 1) != 0)
				res = (res * base) % MOD;
			base = (base * base) % MOD;
			exp >>= 1;
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt();
			for (int d = 2; d * d < n; d++) {
				if (n % d == 0) {
					long p = d, q = n / d;
					long pi = modPow(p, q - 2, q), qi = modPow(q, p - 2, p);
					out.printf("%d %d %d %d\n", 0, 1, Math.min(p * pi, q * qi), Math.max(p * pi, q * qi));
					break;
				}
			}
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
	}
}
