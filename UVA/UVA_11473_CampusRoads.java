package UVA;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Since first and last positions are fixed and We know the number
 * of trees to be planted We can get the maximum gap length and then
 * for each road we plant as many trees as needed.
 */
public class UVA_11473_CampusRoads {

    private static final double EPS = 1e-9;

    private static double sq(double x) {
        return x * x;
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(sq(x2 - x1) + sq(y2 - y1));
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int TC = sc.nextInt();
        for (int tc = 1; tc <= TC; tc++) {
            int N = sc.nextInt(), M = sc.nextInt();
            double[] x = new double[N], y = new double[N];
            for (int i = 0; i < N; i++) {
                x[i] = sc.nextDouble();
                y[i] = sc.nextDouble();
            }
            double gap = 0;
            for (int i = 1; i < N; i++)
                gap += distance(x[i], y[i], x[i - 1], y[i - 1]);
            gap /= (M - 1);
            double length = 0;
            out.printf("Road #%d:\n", tc);
            out.printf("%.2f %.2f\n", x[0], y[0]);
            for (int i = 1; i < N; i++) {
                length += distance(x[i], y[i], x[i - 1], y[i - 1]);
                while (length + EPS >= gap) {
                    length -= gap;
                    double angle = Math.atan2(y[i] - y[i - 1], x[i] - x[i - 1]);
                    out.printf("%.2f %.2f\n", x[i] - length * Math.cos(angle), y[i] - length * Math.sin(angle));
                }
            }
            out.println();
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
