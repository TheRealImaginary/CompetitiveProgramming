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
 *				-First approach that comes in mind is using (cokes, ones, fives, tens) as out state
 *				 however this needs a-lot of memory, we can drop either one, five, ten (one has higher limit)
 *				 and recover it from the others.
 */
public class UVA_10626_BuyingCoke {

	static final int oo = 1 << 28;
	static final int COKES = 151;
	static final int FIVES = 151;
	static final int TENS = 101;

	static int total, maxCokes;
	static int[][][] dp;

	static int solve(int tens, int fives, int cokes) {
		if (cokes == 0)
			return 0;
		if (dp[tens][fives][cokes] != -1)
			return dp[tens][fives][cokes];
		int ones = total - (((maxCokes - cokes) << 3) + (5 * fives) + (10 * tens));
		int res = oo;
		if (ones >= 8)
			res = Math.min(res, solve(tens, fives, cokes - 1) + 8);
		if (fives >= 2)
			res = Math.min(res, solve(tens, fives - 2, cokes - 1) + 2);
		if (tens >= 1)
			res = Math.min(res, solve(tens - 1, fives, cokes - 1) + 1);
		if (ones >= 3 && fives >= 1)
			res = Math.min(res, solve(tens, fives - 1, cokes - 1) + 4);
		if (ones >= 3 && tens >= 1)
			res = Math.min(res, solve(tens - 1, fives + 1, cokes - 1) + 4);
		if(fives >= 1 && tens >= 1)
			res = Math.min(res, solve(tens - 1, fives, cokes - 1) + 2);
		return dp[tens][fives][cokes] = res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int cokes = sc.nextInt(), ones = sc.nextInt(), fives = sc.nextInt(), tens = sc.nextInt();
			dp = new int[TENS][FIVES][COKES];
			for (int i = 0; i < TENS; i++)
				for (int j = 0; j < FIVES; j++)
					Arrays.fill(dp[i][j], -1);
			total = ones + (5 * fives) + (10 * tens);
			maxCokes = cokes;
			out.println(solve(tens, fives, cokes));
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
