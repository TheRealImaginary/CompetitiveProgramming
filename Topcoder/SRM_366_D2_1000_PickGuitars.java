package Topcoder;

import java.util.Arrays;

/*-
 * @author Mazen Magdy
 *				-When the first element is chosen only 1 group will be formed, for any other element
 *				 chosen after the first depending on its position groups might be formed. Let us assume
 *				 we have an array representing the numbers (it's not a circle for now) Then a player can choose
 *				 any element from this array and it will then break into two groups. If the array has two endpoints
 *				 i and j and the player chose element k; then we have two groups, i, k - 1 and k + 1, j. Now the other
 *				 player can choose an element in the same way from both these groups. So f(i, j) is the maximum
 *				 amount that will be taken from subsegment i to j. f(i, j) = Max(sum(i, j) - f(i, k - 1) + f(k + 1, j)) 
 *				 for all k between i and j. Now in the main problem the array was cyclic, so we need to try taking each
 *				 element and run f(i, j) on the remaining elements as they turn into an array, we need to be careful when
 *				 building the array. [2, 1, 4, 5] if we remove 1 we end up with >> [4, 5, 2]
 */
public class SRM_366_D2_1000_PickGuitars {

	static int N;
	static int[] a;
	static int[][] dp;

	static int solve(int i, int j) {
		if (i > j)
			return 0;
		if (i == j)
			return a[i];
		if (dp[i][j] != -1)
			return dp[i][j];
		int res = 0, sum = 0;
		for (int k = i; k <= j; k++)
			sum += a[k];
		for (int k = i; k <= j; k++)
			res = Math.max(res, sum - (solve(i, k - 1) + solve(k + 1, j)));
		return dp[i][j] = res;
	}

	public static int maxValue(int[] guitarValues) {
		int sum = 0;
		N = guitarValues.length;
		a = new int[N - 1];
		for (int i = 0; i < N; i++)
			sum += guitarValues[i];
		dp = new int[N][N];
		int ans = 0;
		for (int i = 0; i < N; i++) {
			for (int k = 0, j = (i + 1) % N; j != i; j = (j + 1) % N) {
				a[k++] = guitarValues[j];
			}
			for (int j = 0; j < N; j++)
				Arrays.fill(dp[j], -1);
			ans = Math.max(ans, sum - solve(0, N - 2));
		}
		return ans;
	}

	public static void main(String[] args) {
		System.err.println(maxValue(new int[] { 1, 5, 3, 4, 5 }));
		System.err.println(maxValue(new int[] { 4, 8, 4 }));
		System.err.println(maxValue(new int[] { 2, 1, 4, 1, 2, 1, 8, 1 }));
	}
}
