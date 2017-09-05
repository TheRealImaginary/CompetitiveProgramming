package Codeforces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-http://codeforces.com/blog/entry/5301
 */
public class CF224_D2_D_TwoStrings {

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		char[] s = sc.next().toCharArray(), t = sc.next().toCharArray();
		int n = s.length, m = t.length;
		int[] left = new int[n + 1];
		for (int i = 1, j = 0; i < n; i++) {
			if (j < m && s[i - 1] == t[j])
				j++;
			left[i] = j;
		}
		// System.err.println(Arrays.toString(left));
		int[] right = new int[n + 2];
		for (int i = n, j = 0; i >= 1; i--) {
			if (j < m && s[i - 1] == t[m - j - 1])
				j++;
			right[i] = j;
		}
		// System.err.println(Arrays.toString(right));
		int[][] cnt = new int[26][m + 1];
		for (int i = 0; i < 26; i++) {
			cnt[i][0] = 0;
			for (int j = 1; j <= m; j++) {
				cnt[i][j] = cnt[i][j - 1];
				if (t[j - 1] == 'a' + i)
					cnt[i][j]++;
			}
		}
		// System.err.println(Arrays.deepToString(cnt));
		for (int i = 1; i <= n; i++) {
			int l = Math.min(m, left[i - 1] + 1), r = Math.max(1, m - right[i + 1]), c = s[i - 1] - 'a';
			if (cnt[c][l] - (r == 0 ? 0 : cnt[c][r - 1]) <= 0) {
				System.out.println("No");
				return;
			}
		}
		System.out.println("Yes");
	}

	static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		public MyScanner(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
		}

		public MyScanner(String filename) throws FileNotFoundException {
			br = new BufferedReader(new FileReader(new File(filename)));
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public boolean ready() throws IOException {
			return br.ready();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}
	}
}
