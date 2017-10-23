package UVA;

import java.io.*;
import java.util.StringTokenizer;

public class UVA_10325_TheLottery {

    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        while (sc.ready()) {
            long n = sc.nextLong();
            int m = sc.nextInt();
            int[] a = new int[m];
            for (int i = 0; i < m; i++)
                a[i] = sc.nextInt();
            long sum = 0;
            for (int msk = 0; msk < 1 << m; msk++) {
                int bits = 0;
                long div = 1;
                for (int i = 0; i < m; i++) {
                    if ((msk & 1 << i) != 0) {
                        bits++;
                        div = lcm(div, a[i]);
                    }
                }
                if ((bits & 1) != 0)
                    sum -= n / div;
                else
                    sum += n / div;
            }
            out.println(sum);
        }
        out.close();
    }

    static class MyScanner {

        BufferedReader br;
        StringTokenizer st;

        MyScanner(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        boolean ready() throws IOException {
            return br.ready();
        }
    }
}
