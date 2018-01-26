package Topcoder;

/**
 * The simple DP Approach would be f(i, j) = sum(f(i + 1, k)) for k >= j + 1 and k <= R[i + 1],
 * we could find a closed form for this summation using the fact that f(i, j + 1) = sum(f(i + 1, k))
 * for k >= j + 2 and k <= R[i + 1] hence f(i, j) = f(i + 1, j + 1) + f(i, j + 1) reducing the complexity.
 */
public class SRM_728_D2_500_IncreasingSequencesEasy {

    private static final int MOD = 998244353;
    private static final int MAX = 10000;

    private static int N;
    private static int[] l, r;
    private static int[][] dp;

    private static int add(int a, int b) {
        return (a + b) % MOD;
    }

    // solve(0, L[0]);
    // This is correct but StackOverFlows, We can
    // call the function gradually to calculate later
    // results and avoid this or just use bottom-up.
    private static int solve(int idx, int last) {
        if (idx == N)
            return 1;
        if (last > r[idx])
            return 0;
        if (last < l[idx])
            return solve(idx, l[idx]);
        if (dp[idx][last] != -1)
            return dp[idx][last];
        return dp[idx][last] = add(solve(idx + 1, last + 1), solve(idx, last + 1));
    }

    public static int count(int[] L, int[] R) {
        l = L;
        r = R;
        N = l.length;
        dp = new int[N + 1][MAX + 1];
        for (int i = 0; i <= MAX; i++)
            dp[0][i] = 1;
        for (int i = 1; i <= N; i++) {
            for (int j = L[i - 1]; j <= R[i - 1]; j++)
                dp[i][j] = dp[i - 1][j - 1];
            for (int j = 1; j <= MAX; j++)
                dp[i][j] = add(dp[i][j], dp[i][j - 1]);
        }
        return dp[N][MAX];
    }

    public static void main(String[] args) {
        new Thread(null, () -> {
            System.err.println(count(new int[]{1, 3, 1, 4}, new int[]{6, 5, 4, 6}));
            System.err.println(count(new int[]{10, 20}, new int[]{20, 30}));
            System.err.println(count(new int[]{4, 46, 46, 35, 20, 77, 20}, new int[]{41, 65, 84, 90, 49, 86, 88}));
            System.err.println(count(new int[]{1, 1, 1, 1, 1, 1, 1},
                    new int[]{10000, 10000, 10000, 10000, 10000, 10000, 10000}));
        }, "", 1 << 28).start();
    }
}
