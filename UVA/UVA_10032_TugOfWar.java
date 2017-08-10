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
 *				-dp[j] represents how many people are needed to make a team with weight j.
 *				 The i-th bit will be set indicating we need i people.
 */
public class UVA_10032_TugOfWar {

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int N = sc.nextInt();
			int[] a = new int[N];
			int total = 0;
			for (int i = 0; i < N; i++)
				total += a[i] = sc.nextInt();
			long[] dp = new long[total + 1];
			dp[0] = 1;
			for (int i = 0; i < N; i++)
				for (int j = total; j >= 0; j--)
					if (j - a[i] >= 0 && dp[j - a[i]] != 0)
						dp[j] |= (dp[j - a[i]] << 1);
			int half = N >> 1;
			for (int i = total >> 1; i >= 0; i--) {
				if ((N & 1) != 0) {
					if ((dp[i] & (1L << half)) != 0 || (dp[i] & (1L << (half + 1))) != 0) {
						out.printf("%d %d\n", i, total - i);
						break;
					}
				} else if ((dp[i] & (1L << half)) != 0) {
					out.printf("%d %d\n", i, total - i);
					break;
				}
			}
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
