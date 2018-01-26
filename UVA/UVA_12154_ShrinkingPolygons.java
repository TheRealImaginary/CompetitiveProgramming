package UVA;

import java.io.*;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * We notice that the circumference must be divisible by the number of sides of
 * the regular polygon. And so for each number of sides that divides the circumference
 * we check each starting node whether we can make a regular polygon of side
 * `circumference / number of sides` and we choose the minimum.
 */
public class UVA_12154_ShrinkingPolygons {

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        while (true) {
            int n = sc.nextInt();
            if (n == 0)
                break;
            Set<Integer> set = new TreeSet<>();
            int sum = 0;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                sum += a[i] = sc.nextInt();
                set.add(sum);
            }
            int ans = -1;
            for (int i = n; i >= 3 && ans == -1; i--)
                if (sum % i == 0) {
                    int side = sum / i;
                    for (int j = 0, st = 0; st <= side; st += a[j], j++) {
                        boolean can = true;
                        for (int k = st; k < sum && can; k += side)
                            can &= set.contains(k);
                        if (can) {
                            ans = n - i;
                            break;
                        }
                    }
                }
            out.println(ans);
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
