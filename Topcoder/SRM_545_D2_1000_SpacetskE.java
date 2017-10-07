package Topcoder;

/**
 * @author Mazen Magdy
 *              -We find the number of points on a line for each starting point
 *               and add to the answer C(n, k)
 */
public class SRM_545_D2_1000_SpacetskE {

    private static final long MOD = (long) 1e9 + 7;
    private static final int MAX = 301;

    public static int countsets(int L, int H, int K) {
        if(K == 1){
            return (L + 1) * (H + 1);
        }
        long[][] comb = new long[MAX][MAX];
        comb[0][0] = 1;
        for (int i = 1; i < MAX; i++) {
            comb[i][0] = 1;
            for (int j = 1; j <= i; j++)
                comb[i][j] = add(comb[i - 1][j - 1], comb[i - 1][j]);
        }
        long ans = 0;
        for (int start = 0; start <= L; start++)
            for (int dx = -L; dx <= L; dx++)
                for (int dy = 1; dy <= H; dy++)
                    if (gcd(Math.abs(dx), Math.abs(dy)) == 1) {
                        int count = 0, x = start, y = 0;
                        while (x >= 0 && x <= L && y <= H) {
                            x += dx;
                            y += dy;
                            count++;
                        }
                        ans = add(ans, comb[count][K]);
                    }
        return (int) ans;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private static long add(long a, long b) {
        return (a + b) % MOD;
    }

    public static void main(String[] args) {
        System.err.println(countsets(1, 1, 2));
        System.err.println(countsets(1, 1, 1));
        System.err.println(countsets(2, 2, 1));
        System.err.println(countsets(2, 2, 2));
        System.err.println(countsets(5, 5, 3));
        System.err.println(countsets(70, 52, 18));
    }
}
