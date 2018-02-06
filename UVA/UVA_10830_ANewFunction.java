package UVA;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Answer is Summation from 2 to N of (floor(N / i) - 1) * i.
 * the value floor(N / i) - 1 is repeated for a-lot of values
 * so we calculate the length of the repeated values and
 * multiply it and sum to the answer.
 */
public class UVA_10830_ANewFunction {

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        for (int tc = 1; true; tc++) {
            long n = sc.nextLong();
            if (n == 0) break;
            long ans = 0;
            for (long r = n, m = 1, l = n / 2 + 1; r > 1; r = l - 1, m = n / r, l = n / (m + 1) + 1)
                ans += (m - 1) * (r - l + 1) * (r + l) / 2;
            out.printf("Case %d: %d\n", tc, ans);
        }
        out.flush();
        out.close();
    }

    private static class MyScanner {
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
