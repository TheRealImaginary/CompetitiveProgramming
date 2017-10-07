package timus;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Mazen Magdy
 *              -Find for any two adjacent x-coord the maximum y difference, and minimize with difference
 *               in x-coord since we need a square.
 */
public class Timus_1235_CricketField {

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return x + " " + y;
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt(), W = sc.nextInt(), H = sc.nextInt();
        List<Point> points = new ArrayList<Point>();
        points.add(new Point(0, 0));
        points.add(new Point(W, H));
        for (int i = 0; i < n; i++)
            points.add(new Point(sc.nextInt(), sc.nextInt()));
        int ans = 0;
        Point corner = new Point(0, 0);
        for (int i = 0; i < points.size(); i++) {
            Point now = points.get(i);
            for (int j = 0; j < points.size(); j++) {
                if (i == j || now.x > points.get(j).x)
                    continue;
                Point other = points.get(j);
                List<Integer> y = new ArrayList<Integer>();
                y.add(0);
                y.add(H);
                for (int k = 0; k < points.size(); k++) {
                    Point p = points.get(k);
                    if (p.x > now.x && p.x < other.x)
                        y.add(p.y);
                }
                int max = 0, index = -1;
                Collections.sort(y);
                for (int k = 1; k < y.size(); k++) {
                    if (y.get(k) - y.get(k - 1) > max) {
                        max = y.get(k) - y.get(k - 1);
                        index = k - 1;
                    }
                }
                int side = Math.min(max, other.x - now.x);
                if (ans < side) {
                    ans = side;
                    corner = new Point(now.x, y.get(index));
                }
            }
        }
        out.printf("%s %d\n", corner, ans);
        out.flush();
        out.close();
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
