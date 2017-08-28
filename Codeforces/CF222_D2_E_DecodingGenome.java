package Codeforces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-To solve this problem We could have used f(i, j) which is the number of strings that can be made
 *				 of length `i` and the last letter is `j`, which would be calculated as follows: sum(f(i - 1, k))
 *				 such that k != j if k cannot go after j. This would TLE, We could Matrix Power to speed it up;
 *				 We construct an `M x 1` Matrix (Vector, we will can it X) which contains the base case 
 *				 `f(1, j)` and then another Matrix (Transition Matrix) where m[i][j] is 1 iff j can be 
 *				 put after i. Then answer is the sum of the numbers in the result vector from T^(n - 1) * X.
 */
public class CF222_D2_E_DecodingGenome {

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
		long n = sc.nextLong();
		int m = sc.nextInt(), k = sc.nextInt();
		long[][] matrix = new long[m][m];
		for (int i = 0; i < m; i++)
			Arrays.fill(matrix[i], 1);
		for (int i = 0; i < k; i++) {
			char[] c = sc.next().trim().toCharArray();
			int u = -1, v = -1;
			if (Character.isUpperCase(c[0]))
				u = c[0] - 'A';
			else
				u = c[0] - 'a';
			if (Character.isUpperCase(c[1]))
				v = c[1] - 'A' + 26;
			else
				v = c[1] - 'a';
			matrix[u][v] = 0;
		}
		if (n == 1)
			out.println(m);
		else {
			matrix = matrixPower(matrix, n - 1);
			long[][] base = new long[m][m];
			for (int i = 0; i < m; i++)
				base[i][0] = 1;
			matrix = matrixMult(matrix, base);
			long ans = 0;
			for (int i = 0; i < m; i++)
				ans = (ans + matrix[i][0]) % MOD;
			out.println(ans);
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
