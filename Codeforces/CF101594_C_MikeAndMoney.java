package Codeforces;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CF101594_C_MikeAndMoney {

    private static final long oo = 1L << 56;

    private static int N, M;
    private static long[] a, b;
    private static long[][] dp;
    private static ArrayList<Integer>[] masks;

    private static long solve(int idx, int msk) {
        if (msk == (1 << M) - 1)
            return 0;
        if (idx == N)
            return oo;
        if (dp[idx][msk] != -1)
            return dp[idx][msk];
        long res = oo;
        for (int mask : masks[msk]) {
            long sum = 0;
            for (int i = 0; i < M; i++)
                if ((mask & (1 << i)) != 0)
                    sum += b[i];
            res = Math.min(res, solve(idx + 1, msk | mask) + (a[idx] >= sum ? 0 : sum - a[idx]));
        }
        return dp[idx][msk] = res;
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        N = sc.nextInt();
        M = sc.nextInt();
        a = new long[N];
        for (int i = 0; i < N; i++)
            a[i] = sc.nextLong();
        b = new long[M];
        for (int i = 0; i < M; i++)
            b[i] = sc.nextLong();
        dp = new long[N][1 << M];
        for (int i = 0; i < N; i++)
            Arrays.fill(dp[i], -1);
        masks = new ArrayList[1 << M];
        masks[0] = new ArrayList<>();
        for (int msk = 0; msk < 1 << M; msk++)
            masks[0].add(msk);
        for (int msk = 1; msk < 1 << M; msk++) {
            if (masks[msk] == null)
                masks[msk] = new ArrayList<>();
            for (int mask : masks[0])
                if ((mask & msk) == 0)
                    masks[msk].add(mask);
        }
        out.println(solve(0, 0));
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
