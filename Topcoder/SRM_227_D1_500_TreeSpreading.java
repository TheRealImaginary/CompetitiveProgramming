package Topcoder;

import java.util.Arrays;

/*-
 * @author Mazen Magdy
 * 
 * 			Note: We can remove the index and our base case would be a = b = c = d = 0
 */
public class SRM_227_D1_500_TreeSpreading {

	static int N;
	static long[][][][][][] dp;

	static long solve(int last, int a, int b, int c, int d, int idx) {
		if (a < 0 || b < 0 || c < 0 || d < 0)
			return 0;
		if (idx == N)
			return 1;
		if (dp[last][a][b][c][d][idx] != -1)
			return dp[last][a][b][c][d][idx];
		long res = 0;
		if (last != 0)
			res += solve(0, a - 1, b, c, d, idx + 1);
		if (last != 1)
			res += solve(1, a, b - 1, c, d, idx + 1);
		if (last != 2)
			res += solve(2, a, b, c - 1, d, idx + 1);
		if (last != 3)
			res += solve(3, a, b, c, d - 1, idx + 1);
		return dp[last][a][b][c][d][idx] = res;
	}

	public static long countArrangements(int a, int b, int c, int d) {
		N = a + b + c + d;
		dp = new long[5][a + 1][b + 1][c + 1][d + 1][N];
		for (int i = 0; i < 5; i++)
			for (int j = 0; j <= a; j++)
				for (int k = 0; k <= b; k++)
					for (int l = 0; l <= c; l++)
						for (int m = 0; m <= d; m++)
							Arrays.fill(dp[i][j][k][l][m], -1);
		return solve(4, a, b, c, d, 0);
	}

	public static void main(String[] args) {
		System.err.println(countArrangements(1, 1, 0, 0));
		System.err.println(countArrangements(2, 2, 0, 0));
		System.err.println(countArrangements(1, 1, 1, 1));
		System.err.println(countArrangements(3, 2, 1, 1));
		System.err.println(countArrangements(10, 10, 10, 10));
	}
}
