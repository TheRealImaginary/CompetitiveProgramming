package Codeforces;

import java.io.*;
import java.util.StringTokenizer;

public class CF837_E_VasyasFunction {

    private static final long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private static long f(long a, long b) {
        if (b == 0)
            return 0;
        long g = gcd(a, b);
        long min = Long.MAX_VALUE, divisor = g;
        for (long d = 1; d * d <= a; d++) {
            if (a % d == 0) {
                if (d > g && d % g == 0) {
                    long x = (b % d) / g;
                    if (min > x) {
                        min = x;
                        divisor = d;
                    }
                }
                long otherDivisors = a / d;
                if (otherDivisors > g && otherDivisors % g == 0) {
                    long x = (b % otherDivisors) / g;
                    if (min > x) {
                        min = x;
                        divisor = otherDivisors;
                    }
                }
            }
        }
        return divisor == g ? b / g : min + f(a, b - g * min);
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        out.println(f(sc.nextLong(), sc.nextLong()));
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
