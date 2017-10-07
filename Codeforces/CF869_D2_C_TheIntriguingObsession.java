package Codeforces;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Mazen Magdy
 *              -For two islands of same color they must not be directly connected
 *               to each other, Moreover, no islands of color `a` would be connected
 *               to another island of `b` such that be already has another island
 *               of color `a` connected to it as this would mean they exists a
 *               shortest path of length 2. Let f(a, b) be the number of ways to
 *               connect two islands of color a,b. f(a, b) = f(a - 1, b) + b * f(a - 1, b - 1),
 *               meaning we eithe ignore island `a` and donot connect it or
 *               we connect `a` and `b` together using only one edge, as using more
 *               would induce a shortest path of length 2. we multiply by `b` as
 *               there are `b` ways for the connection.
 */
public class CF869_D2_C_TheIntriguingObsession {

    static final long MOD = 998244353L;
    static long[][] dp;

    static long mult(long a, long b) {
        return (a * b) % MOD;
    }

    static long add(long a, long b) {
        return (a + b) % MOD;
    }

    static long solve(int a, int b) {
        if (a < 0 || b < 0)
            return 0;
        if (a == 0 || b == 0)
            return 1;
        if (dp[a][b] != -1)
            return dp[a][b];
        return dp[a][b] = add(solve(a - 1, b), mult(b, solve(a - 1, b - 1)));
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
        int MAX = Math.max(a, Math.max(b, c));
        dp = new long[MAX + 1][MAX + 1];
        for (int i = 0; i <= MAX; i++)
            Arrays.fill(dp[i], -1);
        out.println(mult(solve(a, b), mult(solve(a, c), solve(b, c))));
        out.flush();
        out.close();
    }

    static class MyScanner {
        StringTokenizer st;
        BufferedReader br;

        public MyScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public double nextDouble() throws IOException {
            String x = next();
            StringBuilder sb = new StringBuilder("0");
            double res = 0, f = 1;
            boolean dec = false, neg = false;
            int start = 0;
            if (x.charAt(0) == '-') {
                neg = true;
                start++;
            }
            for (int i = start; i < x.length(); i++)
                if (x.charAt(i) == '.') {
                    res = Long.parseLong(sb.toString());
                    sb = new StringBuilder("0");
                    dec = true;
                } else {
                    sb.append(x.charAt(i));
                    if (dec)
                        f *= 10;
                }
            res += Long.parseLong(sb.toString()) / f;
            return res * (neg ? -1 : 1);
        }

        public boolean ready() throws IOException {
            return br.ready();
        }
    }
}
