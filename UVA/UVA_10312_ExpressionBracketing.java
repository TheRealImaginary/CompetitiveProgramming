package UVA;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * @author Mazen Magdy
 *
 *               Note: Can also be solved using DP.
 */
public class UVA_10312_ExpressionBracketing {

    private static BigInteger[] superCat, Cat;
    private static final int MAX = 27;

    private static void preprocess() {
        superCat = new BigInteger[MAX];
        Cat = new BigInteger[MAX];
        Cat[0] = BigInteger.valueOf(1);
        for (int i = 1; i < MAX; i++)
            Cat[i] = ((BigInteger.valueOf(i << 1).multiply(BigInteger.valueOf((i << 1) - 1))).multiply(Cat[i - 1]))
                    .divide((BigInteger.valueOf((i + 1) * i)));
        superCat[1] = superCat[2] = BigInteger.valueOf(1);
        for (int i = 3; i < MAX; i++)
            superCat[i] = ((BigInteger.valueOf(3 * ((i << 1) - 3)).multiply(superCat[i - 1]))
                    .subtract(BigInteger.valueOf((i - 3)).multiply(superCat[i - 2]))).divide(BigInteger.valueOf(i));
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        // MyScanner sc = new MyScanner(new FileInputStream("in.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        preprocess();

        while (sc.ready()) {
            int n = sc.nextInt();
            pw.println(superCat[n].subtract(Cat[n - 1]));
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
