package Topcoder;

import java.util.Arrays;

public class SRM_288_D1_450_CannonBalls {

	static final int oo = 1 << 27;
	static final int MAX = 122;
	static int[] a;
	
	public static int fewestPiles(int n) {
		a = new int[MAX + 1];
		for (int i = 1; i <= MAX; i++)
			a[i] = (i * (i + 1)) >> 1;
		for (int i = 2; i <= MAX; i++)
			a[i] += a[i - 1];
		int[] dp = new int[n + 1];
		Arrays.fill(dp, oo);
		dp[0] = 0;
		for (int j = 0; j <= MAX; j++)
			for (int i = 1; i <= n; i++) {
				if (a[j] <= i)
					dp[i] = Math.min(dp[i], dp[i - a[j]] + 1);
			}
		return dp[n];
	}

	public static void main(String[] args) {
		System.err.println(fewestPiles(1));
		System.err.println(fewestPiles(5));
		System.err.println(fewestPiles(9));
		System.err.println(fewestPiles(15));
	}

}
