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
 *				-Since we can insert anywhere, to find the minimum amount of characters needed to
 *				 make the sequence a palindrome, we need to use LCS, but we need a-lot of memory and time
 *				 for this. Luckly since K is small, we can terminate early and explore a small portion of
 *				 the space.
 */
public class UVA_11753_CreatingPalindrome {

	static final int oo = 1 << 28;

	static int K;
	static int[] a;

	static int solve(int i, int j, int need) {
		if (need > K)
			return oo;
		if (i >= j)
			return need;
		if (a[i] == a[j])
			return solve(i + 1, j - 1, need);
		return Math.min(solve(i + 1, j, need + 1), solve(i, j - 1, need + 1));
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			int n = sc.nextInt();
			K = sc.nextInt();
			a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = sc.nextInt();
			int ans = solve(0, n - 1, 0);
			out.printf("Case %d: %s\n", t, ans > K || ans == oo ? "Too difficult" : ans == 0 ? "Too easy" : ans);
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
