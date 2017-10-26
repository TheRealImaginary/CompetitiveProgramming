package SPOJ;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Mazen Magdy
 *              -Number of distinct prime factors are <= 6, so we can use Exclusion-Inclusion
 *              on Prime Factors, to count the number of invalid sets and then remove then from
 *              the original answer.
 */
public class MSKYCODE {

    private static final int MAX = 10001;

    private static long nC3(long n) {
        return (n * (n - 1) * (n - 2)) / 6;
    }

    private static long nC4(long n) {
        return (n * (n - 1) * (n - 2) * (n - 3)) / 24;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private static int lcm(int a, int b) {
        return a * (b / gcd(a, b));
    }

    private static ArrayList<Integer> primeFactorize(int n) {
        ArrayList<Integer> res = new ArrayList<>();
        for (int d = 2; d * d <= n; d++) {
            if (n % d == 0)
                res.add(d);
            while (n % d == 0)
                n /= d;
        }
        if (n != 1)
            res.add(n);
        return res;
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
//        MyScanner sc = new MyScanner("in.txt");
        PrintWriter out = new PrintWriter(System.out);
        while (sc.ready()) {
            int n = sc.nextInt();
            long[] countDivisors = new long[MAX];
            long res = 0;
            for (int i = 0; i < n; i++) {
                List<Integer> primes = primeFactorize(sc.nextInt());
                List<Integer> divisors = new ArrayList<>();
                int size = primes.size();
                for (int msk = 1; msk < 1 << size; msk++) {
                    int lcm = 1;
                    for (int j = 0; j < size; j++)
                        if ((msk & (1 << j)) != 0)
                            lcm = lcm(lcm, primes.get(j));
                    long x = countDivisors[lcm];
                    if ((Integer.bitCount(msk) & 1) == 0)
                        res -= nC3(x);
                    else
                        res += nC3(x);
                    divisors.add(lcm);
                }
                for (int div : divisors)
                    countDivisors[div]++;
            }
            out.println(nC4(n) - res);
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
