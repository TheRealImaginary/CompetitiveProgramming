package Codeforces;

import java.io.*;
import java.util.StringTokenizer;

/**
 * @author Mazen Magdy
 *              -Let's draw an `n * n` grid, columns would represent position in an array
 *              and rows (height) would represent the element we put in the array. A Path
 *              going from lower left cell to upper right cell going only right and up
 *              would represent a placement of numbers in a non-decreasing order. Hence
 *              the answer is the number of paths which is (2n - 1)C(n - 1) because we
 *              need to insert at least 1, meaning any premutation of `uuull`.
 */
public class CF57_C_Array {

    private static final int MOD = (int) 1e9 + 7;

    private static int[] fact, invfact;

    private static int sub(int a, int b) {
        return (((a - b) % MOD) + MOD) % MOD;
    }
    private static int mult(int a, int b) {
        return (int) ((a * 1L * b) % MOD);
    }

    private static int modPow(int base, int exp) {
        int res = 1;
        while (exp > 0) {
            if ((exp & 1) != 0)
                res = mult(res, base);
            base = mult(base, base);
            exp >>= 1;
        }
        return res;
    }

    private static int nCk(int n, int k) {
        return mult(mult(fact[n], invfact[n - k]), invfact[k]);
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt();
        fact = new int[n << 1];
        invfact = new int[n << 1];
        fact[0] = fact[1] = invfact[0] = invfact[1] = 1;
        for (int i = 2; i < n << 1; i++) {
            fact[i] = mult(i, fact[i - 1]);
            invfact[i] = modPow(fact[i], MOD - 2);
        }
        out.println(sub(mult(nCk((n << 1) - 1, n - 1), 2), n));
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
