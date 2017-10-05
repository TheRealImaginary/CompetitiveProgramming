package Codeforces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-If the k-th bit is zero we need any two adjacent numbers to not have this bit
 *				 set this is equal to this recurrence f(i) = f(i - 1) + f(i - 2) with base cases
 *				 f(1) = 2 and f(2) = 3. If the k-th bit is set we need to have at least two adjacent
 *				 bits set, hence it is 2^n - f(n)
 */
public class CF551_D2_D_GukiZAndBinaryOperations {

	static long MOD;

	static long[][] matrixMultiply(long[][] a, long[][] b) {
		long[][] res = new long[2][2];
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				for (int k = 0; k < 2; k++)
					res[i][j] = add(res[i][j], mult(a[i][k], b[k][j]));
		return res;
	}

	static long[][] matrixPower(long[][] base, long exp) {
		long[][] res = new long[2][2];
		for (int i = 0; i < 2; i++)
			res[i][i] = 1;
		while (exp > 0) {
			if ((exp & 1) != 0)
				res = matrixMultiply(res, base);
			base = matrixMultiply(base, base);
			exp >>= 1;
		}
		return res;
	}

	static long modPow(long base, long exp) {
		long res = 1;
		while (exp > 0) {
			if ((exp & 1) != 0)
				res = mult(res, base);
			base = mult(base, base);
			exp >>= 1;
		}
		return res;
	}

	static long sub(long a, long b) {
		return (((a - b) % MOD) + MOD) % MOD;
	}

	static long add(long a, long b) {
		return (a + b) % MOD;
	}

	static long mult(long a, long b) {
		return (a * b) % MOD;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		long n = sc.nextLong(), k = sc.nextLong();
		int l = sc.nextInt();
		MOD = sc.nextLong();
		if ((l == 0 && k != 0) || (l < 63 && k >= 1L << l))
			out.println(0);
		else {
			long[][] matrix = { { 1, 1 }, { 1, 0 } };
			long f = matrixPower(matrix, n + 1)[0][0], pow2 = modPow(2, n);
			long res = 1;
			for (int i = 0; i < l; i++) {
				if ((k & (1L << i)) == 0)
					res = mult(res, f);
				else
					res = mult(res, sub(pow2, f));
			}
			out.println(res % MOD);
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

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}
