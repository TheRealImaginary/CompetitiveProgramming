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
 *				-We use sliding window technique, we slide a window keeping track of the count
 *				 of elements in this window that are in the range 1 to k.
 */
public class UVA_11536_SmallestSubArray {

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			int n = sc.nextInt(), m = sc.nextInt(), k = sc.nextInt();
			int[] a = new int[n];
			a[0] = 1;
			a[1] = 2;
			a[2] = 3;
			for (int i = 3; i < n; i++)
				a[i] = ((a[i - 1] + a[i - 2] + a[i - 3]) % m) + 1;
			int all = k;
			int[] cnt = new int[k + 1];
			int len = n + 1;
			for (int i = 0, j = 0; i < n; i++) {
				while (j < n && all > 0) {
					if (a[j] <= k) {
						if (cnt[a[j]] == 0)
							all--;
						cnt[a[j]]++;
					}
					j++;
				}
				if (all == 0)
					len = Math.min(len, j - i);
				if (a[i] <= k && --cnt[a[i]] == 0)
					all++;
			}
			out.printf("Case %d: %s\n", t, len == n + 1 ? "sequence nai" : len);
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
