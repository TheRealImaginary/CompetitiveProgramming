package Topcoder;

import java.util.Arrays;

/*-
 * @author Mazen Magdy
 * 				-We use f(week, extra) where `week` is how many weeks are left and `extra` are
 * 				 how many lotteries we lost. Let `p` be the probability that we are chosen for
 * 				 lottery which can be calculated by (currentAmount / Sum Of All Amounts) where
 * 				 we can recover currentAmount from `week` and `extra`. Then f(week, extra) = 
 * 				 p * f(week - 1, extra) + (1 - p) * f(week - 1, extra + 1), with the base case
 * 				 f(0, extra) = currentAmount.
 */

// 2010 TCO Algorithm Online Round 4 - Division I, Level One
public class TC_BankLottery {

	static int N, W;
	static int[] a;
	static int amount;
	static double[][] dp;

	static double solve(int week, int extra) {
		int currentAmount = (a[0] + (((W - week) - (extra * amount / amount)) * amount));
		if (week == 0)
			return currentAmount;
		if (dp[week][extra] != -1)
			return dp[week][extra];
		int sum = 0;
		for (int i = 1; i < N; i++)
			sum += a[i];
		sum += currentAmount + (extra * amount);
		double p = currentAmount * 1.0 / sum, q = 1 - p,
				res = p * solve(week - 1, extra) + q * solve(week - 1, extra + 1);
		return dp[week][extra] = res;
	}

	public static double expectedAmount(int[] accounts, int weeklyAmount, int weekCount) {
		a = accounts;
		amount = weeklyAmount;
		N = a.length;
		W = weekCount;
		dp = new double[weekCount + 1][weekCount + 1];
		for (int i = 0; i <= weekCount; i++)
			Arrays.fill(dp[i], -1);
		return solve(weekCount, 0);
	}

	public static void main(String[] args) {
		System.err.println(expectedAmount(new int[] { 100, 100 }, 100, 2));
		System.err.println(expectedAmount(new int[] { 2, 2, 2 }, 1, 2));
		System.err.println(expectedAmount(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 100, 20));
	}
}
