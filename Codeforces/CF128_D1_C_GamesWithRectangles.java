package Codeforces;

import java.io.*;
import java.util.StringTokenizer;

/**
 * @author Mazen Magdy
 *              -We need to pair the number of rows and cells together we have
 *             `N - 1` values for row and `M - 1` values for column, hence
 *             it's (N - 1)C2k * (M - 1)C2k, as a square is represented by
 *             two vertices ((r1, c1), (r2, c2)).
 */
public class CF128_D1_C_GamesWithRectangles {

    private static final int MOD = (int) 1e9 + 7;

    private static int add(int a, int b) {
        return (a + b) % MOD;
    }

    private static int mult(int a, int b) {
        return (int) ((a * 1L * b) % MOD);
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt(), m = sc.nextInt(), k = sc.nextInt();
        int max = Math.max(Math.max(n, m), k << 1) + 1;
        int[][] comb = new int[max][max];
        comb[0][0] = 1;
        for (int i = 1; i < max; i++) {
            comb[i][0] = 1;
            for (int j = 1; j < max; j++)
                comb[i][j] = add(comb[i - 1][j], comb[i - 1][j - 1]);
        }
        out.println(mult(comb[n - 1][k << 1], comb[m - 1][k << 1]));
        out.flush();
        out.close();
    }

    static class MyScanner {
        StringTokenizer st;
        BufferedReader br;

        public MyScanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public MyScanner(String filename) throws IOException {
            br = new BufferedReader(new FileReader(new File(filename)));
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
