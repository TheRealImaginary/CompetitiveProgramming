package SPOJ;

import java.io.*;
import java.util.StringTokenizer;

public class NGM2 {

    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        long n = sc.nextLong();
        int k = sc.nextInt();
        long[] a = new long[k];
        for (int i = 0; i < k; i++)
            a[i] = sc.nextLong();
        long ans = 0;
        for (int msk = 0; msk < 1 << k; msk++) {
            long lcm = 1;
            for (int i = 0; i < k; i++)
                if ((msk & (1 << i)) != 0)
                    lcm = lcm(lcm, a[i]);
            if ((Integer.bitCount(msk) & 1) == 0)
                ans += n / lcm;
            else
                ans -= n / lcm;
        }
        out.println(ans);
        out.flush();
        out.close();
    }

    static class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        public MyScanner(InputStream is) throws IOException {
            br = new BufferedReader(new InputStreamReader(is));
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }
}
