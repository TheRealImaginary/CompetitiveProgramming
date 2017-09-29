package Topcoder;

import java.util.Arrays;

public class SRM_546_D1_500_FavouriteDigits {

	static final long oo = 1L << 59;

	static long N;
	static int D1, C1, D2, C2;
	static long[][][][][] dp;
	static char[] c;

	static long solve(int g, int z, int idx, int c1, int c2) {
		if (idx == 19)
			return c1 >= C1 && c2 >= C2 ? 0 : oo;
		if (dp[g][z][idx][c1][c2] != -1)
			return dp[g][z][idx][c1][c2];
		long res = oo, min = c[idx] - '0', pow = (long) Math.pow(10, 18 - idx);
		for (int d = 0; d <= 9; d++) {
			if (g == 0 && d < min)
				continue;
			int t1 = c1, t2 = c2;
			if (d == D1 && (D1 > 0 || z == 1))
				t1++;
			if (d == D2 && (D2 > 0 || z == 1))
				t2++;
			long now = solve((g == 1 || (d > min)) ? 1 : 0, z == 1 || d != 0 ? 1 : 0, idx + 1, t1, t2);
			if (now >= oo)
				continue;
			now += (d * pow);
			res = Math.min(res, now);
		}
		return dp[g][z][idx][c1][c2] = res;
	}

	public static long findNext(long n, int d1, int c1, int d2, int c2) {
		N = n;
		D1 = d1;
		D2 = d2;
		C1 = c1;
		C2 = c2;
		dp = new long[2][2][19][19][19];
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				for (int k = 0; k <= 18; k++)
					for (int l = 0; l <= 18; l++)
						Arrays.fill(dp[i][j][k][l], -1);
		c = new char[19];
		for (int i = 18; i >= 0; i--, n /= 10)
			c[i] = (char) ((n % 10) + '0');
		System.err.println(Arrays.toString(c));
		return solve(0, 0, 0, 0, 0);
	}

	public static void main(String[] args) {
		System.err.println(findNext(47, 1, 0, 2, 0));
		System.err.println(findNext(47, 5, 0, 9, 1));
		System.err.println(findNext(47, 5, 0, 3, 1));
		System.err.println(findNext(47, 2, 1, 0, 2));
		System.err.println(findNext(123456789012345L, 1, 2, 2, 4));
		System.err.println(findNext(92, 1, 1, 0, 0));
	}
}
