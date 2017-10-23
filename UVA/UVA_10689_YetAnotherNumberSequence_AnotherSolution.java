package UVA;

import java.io.*;
import java.util.StringTokenizer;

public class UVA_10689_YetAnotherNumberSequence_AnotherSolution {

    static int mod;

    static int[][] multMatrix(int[][] a, int[][] b) {
        int[][] res = new int[2][2];

        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++)
                    res[i][j] += a[i][k] * b[k][j];
                res[i][j] %= mod;
            }
        return res;
    }

    static int[][] matrixPower(int[][] base, int n) {
        if (n == 1)
            return base;
        int[][] a = matrixPower(base, n >> 1);
        a = multMatrix(a, a);
        if ((n & 1) != 0)
            a = multMatrix(a, base);
        return a;
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);
        int t = sc.nextInt();
        while (t-- > 0) {
            int a = sc.nextInt(), b = sc.nextInt(), n = sc.nextInt(), m = sc.nextInt();
            int[] modVal = {10, 100, 1000, 10000};
            int[][] q = {{1, 1}, {1, 0}};
            mod = modVal[m - 1];
            if (n == 0)
                pw.println(a % mod);
            else if (n == 1)
                pw.println(b % mod);
            else {
                int[][] ans = matrixPower(q, n - 1);
                long res = (ans[0][0] * b) % mod + (ans[0][1] * a) % mod;
                res %= mod;
                pw.println(res);
            }
        }
        pw.close();
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

        public boolean ready() throws IOException {
            return br.ready();
        }
    }
}
