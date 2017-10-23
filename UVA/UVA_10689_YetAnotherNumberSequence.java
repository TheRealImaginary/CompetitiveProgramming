package UVA;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class UVA_10689_YetAnotherNumberSequence {

    private static BigInteger[] fib;
    private static final int MAX = 15000;

    private static void preprocess() {
        fib = new BigInteger[MAX + 1];
        fib[0] = BigInteger.ZERO;
        fib[1] = BigInteger.ONE;
        for (int i = 2; i <= MAX; i++)
            fib[i] = fib[i - 1].add(fib[i - 2]);
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);

        preprocess();

        final int[] PisanoPeriod = new int[]{60, 300, 1500, 15000};

        int t = sc.nextInt();
        while (t-- > 0) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int n = sc.nextInt();
            int m = sc.nextInt();
            int mod = (int) Math.pow(10, m);
            int period = PisanoPeriod[m - 1];
            if (n != 0) {
                BigInteger ans = fib[(n) % period].multiply(BigInteger.valueOf(b % mod))
                        .add(fib[(n - 1) % period].multiply(BigInteger.valueOf(a % mod)));
                // System.err.println(fib[n % period]);
                pw.println(ans.mod(BigInteger.valueOf(mod)));
            } else {
                BigInteger ans = fib[0].add(BigInteger.valueOf(a)).mod(BigInteger.valueOf(mod));
                pw.println(ans);
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

        public byte nextByte() throws IOException {
            return Byte.parseByte(next());
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
