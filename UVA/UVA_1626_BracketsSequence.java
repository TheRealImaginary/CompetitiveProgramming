package UVA;

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
 *				-We need to create a regular bracket sequence that contains the given
 *				 sequence as a subsequence. In other words we need to insert brackets in any
 *				 place in the string to make it a regular bracket sequence. Let f(i, j) be
 *				 the minimum number of characters needed to be inserted to make substring i to j
 *				 a regular bracket sequence. f(i, i) = 1, f(i, i + 1) = 0 if [] or () 2 otherwise.
 *				 f(i, j) = min(f(i, k) + f(k + 1, j)) for all k from i to j exclusive.
 */
public class UVA_1626_BracketsSequence {

	static final int oo = 1 << 28;

	static PrintWriter out;
	static char[] c;
	static int[][] dp;

	static boolean isValid(char a, char b) {
		return (a == '(' && b == ')') || (a == '[' && b == ']');
	}

	static void print(int i, int j) {
		if (i > j)
			return;
		if (dp[i][j] == 0) {
			for (int k = i; k <= j; k++)
				out.print(c[k]);
		} else if (i == j) {
			if (c[i] == '(' || c[i] == ')')
				out.print("()");
			else
				out.print("[]");
		} else if (i + 1 == j) {
			if (c[i] == ')' || c[i] == '(')
				out.print("()");
			else if (c[i] == ']' || c[i] == '[')
				out.print("[]");
			if (c[j] == '(' || c[j] == ')')
				out.print("()");
			else if (c[j] == '[' || c[j] == ']')
				out.print("[]");
		} else if (isValid(c[i], c[j]) && dp[i][j] == dp[i + 1][j - 1]) {
			out.print(c[i]);
			print(i + 1, j - 1);
			out.print(c[j]);
			return;
		} else {
			for (int k = i; k < j; k++)
				if (dp[i][j] == dp[i][k] + dp[k + 1][j]) {
					print(i, k);
					print(k + 1, j);
					return;
				}
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			sc.nextLine();
			c = sc.nextLine().toCharArray();
			int n = c.length;
			dp = new int[n][n];
			for (int i = 0; i < n; i++) {
				Arrays.fill(dp[i], oo);
				dp[i][i] = 1;
			}
			for (int len = 2; len <= n; len++)
				for (int j = 0; j < n - len + 1; j++) {
					int k = j + len - 1;
					if (len == 2)
						dp[j][k] = isValid(c[j], c[k]) ? 0 : 2;
					else if (isValid(c[j], c[k]))
						dp[j][k] = dp[j + 1][k - 1];
					for (int l = j; l < k; l++)
						dp[j][k] = Math.min(dp[j][k], dp[j][l] + dp[l + 1][k]);
				}
			print(0, n - 1);
			out.println();
			if (tc > 0)
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
