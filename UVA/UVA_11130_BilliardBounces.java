package UVA;

import java.io.*;
import java.util.StringTokenizer;

/**
 * The velocity has two components, horizontal and vertical, when the ball bounces
 * the component perpendicular to the side is reversed in direction. This is equivalent
 * to the ball continuing to another board.
 */
public class UVA_11130_BilliardBounces {

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        while (true) {
            int a = sc.nextInt(), b = sc.nextInt(), v = sc.nextInt(), theta = sc.nextInt(), s = sc.nextInt();
            if (a + b + v + theta + s == 0)
                break;
            double distance = v * s;
            out.printf("%.0f %.0f\n", distance * Math.cos(Math.toRadians(theta)) / (2 * a),
                    distance * Math.sin(Math.toRadians(theta)) / (2 * b));
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
