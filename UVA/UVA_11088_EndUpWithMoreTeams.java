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
 *				-Since N is small we check each possible 3-pairings.
 */
public class UVA_11088_EndUpWithMoreTeams {

	static int N;
	static int[] a;
	static int[] dp;

	static int solve(int msk) {
		if (msk == (1 << N) - 1)
			return 0;
		if (dp[msk] != -1)
			return dp[msk];
		int res = 0;
		for (int i = 0; i < N; i++)
			if ((msk & (1 << i)) == 0)
				for (int j = 0; j < N; j++)
					if (i != j && (msk & (1 << j)) == 0)
						for (int k = 0; k < N; k++) {
							if (i == k || j == k || (msk & (1 << k)) != 0)
								continue;
							int ability = a[i] + a[j] + a[k];
							res = Math.max(res, solve(msk | (1 << i) | (1 << j) | (1 << k)) + (ability >= 20 ? 1 : 0));
						}
		return dp[msk] = res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		for (int t = 1; true; t++) {
			N = sc.nextInt();
			if (N == 0)
				break;
			a = new int[N];
			for (int i = 0; i < N; i++)
				a[i] = sc.nextInt();
			dp = new int[1 << N];
			Arrays.fill(dp, -1);
			out.printf("Case %d: %d\n", t, solve(0));
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
