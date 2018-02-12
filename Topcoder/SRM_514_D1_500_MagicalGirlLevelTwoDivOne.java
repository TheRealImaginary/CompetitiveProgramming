package Topcoder;

import java.util.Arrays;

/**
 * Since patterns are repeated we can solve the problem only for N by M sub-matrix.
 * Let's define f(row, msk) to be the number of ways have sum odd/even as defined by
 * msk. Transitions would be summation of f(row + 1, msk ^ now) * NumberOfWays for all possible `now`
 * Now we need to calculate the NumberOfWays to make a specific row have an odd sum and each cell
 * have a parity defined by a bit in msk, This is easy if for each cell we calculate the number of
 * ways for it have a certain parity. Since patterns are decided depending on cell that are
 * equivalent to it mod n and m (r = r1 mod n or c = c1 mod m) we can use this in our calculation.
 */
public class SRM_514_D1_500_MagicalGirlLevelTwoDivOne {

    private static final int MOD = (int) 1e9 + 7;

    private static int R, C, N, M;
    private static char[][] grid;
    private static long[][] parityWays, dp;

    private static long add(long a, long b) {
        return (a + b) % MOD;
    }

    private static long mult(long a, long b) {
        return (a * b) % MOD;
    }

    private static long solve(int row, int msk) {
        if (row == N) return msk == (1 << M) - 1 ? 1 : 0;
        if (dp[row][msk] != -1) return dp[row][msk];
        long res = 0;
        for (int rowsParity = 0; rowsParity < 1 << M; rowsParity++)
            res = add(res, mult(parityWays[row][rowsParity], solve(row + 1, msk ^ rowsParity)));
        return dp[row][msk] = res;
    }

    public static int theCount(String[] palette, int n, int m) {
        R = palette.length;
        C = palette[0].length();
        N = n;
        M = m;
        grid = new char[R][C];
        for (int i = 0; i < R; i++)
            grid[i] = palette[i].toCharArray();
        long[][][] ways = new long[2][N][M];
        for (int row = 0; row < N; row++)
            for (int col = 0; col < M; col++)
                for (int parity = 0; parity < 2; parity++) {
                    ways[parity][row][col] = 1;
                    for (int r1 = row; r1 < R; r1 += N)
                        for (int c1 = col; c1 < C; c1 += M) {
                            if (grid[r1][c1] == '.')
                                ways[parity][row][col] = mult(ways[parity][row][col], parity == 0 ? 4 : 5);
                            else if (((grid[r1][c1] - '0') & 1) != parity)
                                ways[parity][row][col] = 0;
                        }
                }
        parityWays = new long[N][1 << M];
        for (int row = 0; row < N; row++)
            for (int msk = 0; msk < 1 << M; msk++) {
                long res = 1;
                for (int col = 0; col < M; col++)
                    res = mult(res, ways[(msk >> col) & 1][row][col]);
                if ((Integer.bitCount(msk) & 1) == 0)
                    res = 0;
                parityWays[row][msk] = res;
            }
        dp = new long[N][1 << M];
        for (int i = 0; i < N; i++)
            Arrays.fill(dp[i], -1);
        return (int) solve(0, 0);
    }

    public static void main(String[] args) {
        System.err.println(theCount(new String[]{"12", "2."}, 2, 2));
        System.err.println(theCount(new String[]{"21", "1."}, 2, 2));
        System.err.println(theCount(new String[]{"...", "...", "..."}, 1, 1));
        System.err.println(theCount(new String[]{"...1.2.3", "4.5.6...", "...7.8.9", "1.2.3..."}, 4, 4));
    }
}
