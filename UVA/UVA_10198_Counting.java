package UVA;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class UVA_10198_Counting {

    private static final int MAX = 1001;

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
//        MyScanner sc = new MyScanner("in.txt");
        PrintWriter out = new PrintWriter(System.out);

        BigInteger[] dp = new BigInteger[MAX];
        dp[0] = BigInteger.valueOf(1);
        for (int i = 1; i < MAX; i++) {
            dp[i] = dp[i - 1].multiply(BigInteger.valueOf(2));
            if (i >= 2)
                dp[i] = dp[i].add(dp[i - 2]);
            if (i >= 3)
                dp[i] = dp[i].add(dp[i - 3]);
        }

        while (sc.ready()) {
            out.println(dp[sc.nextInt()]);
        }
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
