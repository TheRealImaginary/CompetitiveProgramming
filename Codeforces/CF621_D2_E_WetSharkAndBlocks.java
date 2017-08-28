package Codeforces;

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
 *				-We can use the recurrence dp[i][j] = sum(cnt[d] * dp[i - 1][(a * 10 + d) % x] for all d >= 0 & d <= 9
 *				 where (a * 10 + d) % x == j. This would TLE. So we could speed it up using Matrix Power.
 *				 dp[i][j] depends on `x` values, so we construct a vector [dp[0][0], dp[0][1], .. dp[0][k - 1]]
 *				 and a `x by x` matrix where matrix[i][j] is equal to the number of numbers d giving modulo j when added on
 *				 number i. We raise this matrix power b and multiply it by the vector. 
 */
public class CF621_D2_E_WetSharkAndBlocks {

	static final int MAX = 10;
	static final long MOD = (long) 1e9 + 7;

	static long[][] matrixMult(long[][] a, long[][] b) {
		int n = a.length;
		long[][] c = new long[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				for (int k = 0; k < n; k++) {
					c[i][j] += (a[i][k] * b[k][j]) % MOD;
					c[i][j] %= MOD;
				}
		return c;
	}

	static long[][] matrixPower(long[][] a, long exp) {
		int n = a.length;
		long[][] res = new long[n][n];
		for (int i = 0; i < n; i++)
			res[i][i] = 1;
		while (exp > 0) {
			if ((exp & 1) != 0)
				res = matrixMult(res, a);
			a = matrixMult(a, a);
			exp >>= 1;
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), b = sc.nextInt(), k = sc.nextInt(), x = sc.nextInt();
		int[] cnt = new int[MAX];
		for (int i = 0; i < n; i++)
			cnt[sc.nextInt()]++;
		long[][] matrix = new long[x][x];
		for (int i = 0; i < x; i++)
			for (int j = 0; j <= 9; j++) {
				matrix[i][(i * 10 + j) % x] += cnt[j];
				matrix[i][(i * 10 + j) % x] %= MOD;
			}
		matrix = matrixPower(matrix, b);
		out.println(matrix[0][k] % MOD);
		out.flush();
		out.close();
	}

	static class MyScanner {

		BufferedReader br;
		StringTokenizer st;

		MyScanner(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
		}

		MyScanner(String filename) throws IOException {
			br = new BufferedReader(new FileReader(new File(filename)));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		String nextLine() throws IOException {
			return br.readLine();
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
