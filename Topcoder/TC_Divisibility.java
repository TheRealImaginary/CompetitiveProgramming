package Topcoder;

// https://community.topcoder.com/tc?module=HSProblemStatement&pm=6658&rd=10068
public class TC_Divisibility {

    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    private static long solve(int n, int[] a) {
        long res = 0;
        int m = a.length;
        for (int msk = 0; msk < 1 << m; msk++) {
            long lcm = 1;
            for (int i = 0; i < m; i++)
                if ((msk & (1 << i)) != 0)
                    lcm = lcm(lcm, a[i]);
            if ((Integer.bitCount(msk) & 1) == 0)
                res += n / lcm;
            else
                res -= n / lcm;
        }
        return n - res;
    }

    public static int numDivisible(int L, int R, int[] a) {
        return (int) (solve(R, a) - solve(L - 1, a));
    }

    public static void main(String[] args) {
        System.err.println(numDivisible(293, 784, new int[]{1}));
        System.err.println(numDivisible(255, 734, new int[]{2}));
        System.err.println(numDivisible(579000, 987654, new int[]{1, 2}));
        System.err.println(numDivisible(1, 1000000000, new int[]{2, 3}));
        System.err.println(numDivisible(132099861, 896205951, new int[]{401, 997, 953, 131, 311, 647, 823, 11, 233,
                307, 137}));
    }
}
