package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-First digit can be anything from 1 to (base - 1), the following digits can be either
 *				(last digit - 1) or (last digit + 1) we use a mask to mark which digits are used. We use
 *				memoization since there are overlapping subproblems.
 */
public class UVA_11472_BeautifulNumbers {

	static final long MOD = (long) 1e9 + 7;
	static int base, M;
	static long[][][] dp;

	static long solve(int last, int idx, int msk) {
		if (idx == M)
			return 0;
		if (dp[last][idx][msk] != -1)
			return dp[last][idx][msk];
		long res = 0;
		if (msk == (1 << base) - 1)
			res++;
		if (last - 1 >= 0)
			res = (res + solve(last - 1, idx + 1, msk | 1 << (last - 1))) % MOD;
		if (last + 1 < base)
			res = (res + solve(last + 1, idx + 1, msk | 1 << (last + 1))) % MOD;
		return dp[last][idx][msk] = res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t-- > 0) {
			base = sc.nextInt();
			M = sc.nextInt();
			dp = new long[10][M][1 << base];
			for (int i = 0; i < 10; i++)
				for (int j = 0; j < M; j++)
					Arrays.fill(dp[i][j], -1);
			long res = 0;
			for (int d = 1; d < base; d++)
				res = (res + solve(d, 0, 1 << d)) % MOD;
			out.println(res);
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

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
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
