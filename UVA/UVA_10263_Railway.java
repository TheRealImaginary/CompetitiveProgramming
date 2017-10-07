package UVA;

import java.io.*;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-Each two points form a Line Segment, so we need to find Closest Point from a Point
 *				 to the Line Segments, the distance to this point is the Closest Distance.
 */
public class UVA_10263_Railway {

    static final double oo = 1e9;
    static final double EPS = 1e-9;

    static class Vector {
        double x, y;

        public Vector(double a, double b) {
            x = a;
            y = b;
        }

        public Vector(Point a, Point b) {
            x = b.x - a.x;
            y = b.y - a.y;
        }

        public double dot(Vector v) {
            return (x * v.x) + (y * v.y);
        }

        public double norm2() {
            return (x * x) + (y * y);
        }

        public Vector scale(double s) {
            return new Vector(x * s, y * s);
        }
    }

    static class Point {
        double x, y;

        public Point(double xx, double yy) {
            x = xx;
            y = yy;
        }

        public double dist(Point p) {
            return hyp(x - p.x, y - p.y);
        }

        public double hyp(double dx, double dy) {
            return Math.sqrt(dx * dx + dy * dy);
        }

        public Point translate(Vector v) {
            return new Point(x + v.x, y + v.y);
        }

        public static Point closestPoint(Point p, Point a, Point b) {
            Vector ap = new Vector(a, p), ab = new Vector(a, b);
            double u = ap.dot(ab) / ab.norm2();
            if (u < 0.0)
                return a;
            if (u > 1.0)
                return b;
            return a.translate(ab.scale(u));
        }

        @Override
        public String toString() {
            return x + " " + y;
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        // ZOJ.MyScanner sc = new ZOJ.MyScanner("in.txt");
        PrintWriter out = new PrintWriter(System.out);
        while (sc.ready()) {
            Point p = new Point(sc.nextDouble(), sc.nextDouble());
            int n = sc.nextInt();
            Point[] points = new Point[n + 1];
            for (int i = 0; i <= n; i++)
                points[i] = new Point(sc.nextDouble(), sc.nextDouble());
            double min = oo;
            Point ans = null;
            for (int i = 0; i < n; i++) {
                Point other = Point.closestPoint(p, points[i], points[i + 1]);
                double res = other.dist(p);
                if (res < min) {
                    min = res;
                    ans = other;
                }
            }
            out.printf("%.4f\n%.4f\n", ans.x, ans.y);
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

        public MyScanner(String file) throws IOException {
            br = new BufferedReader(new FileReader(new File(file)));
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
            for (int i = start; i < x.length(); i++) {
                if (x.charAt(i) == '.') {
                    res = Long.parseLong(sb.toString());
                    sb = new StringBuilder("0");
                    dec = true;
                } else {
                    sb.append(x.charAt(i));
                    if (dec)
                        f *= 10;
                }
            }
            res += Long.parseLong(sb.toString()) / f;
            return res * (neg ? -1 : 1);
        }

        public boolean ready() throws IOException {
            return br.ready();
        }
    }
}
