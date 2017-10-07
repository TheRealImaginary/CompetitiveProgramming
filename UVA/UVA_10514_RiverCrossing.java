package UVA;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Mazen Magdy
 *              -Model the input as a graph, islands and riverbanks are nodes and connect
 *               them with edges with the cost of the minimum distance between them, then use
 *               Dijkstra or FloydWarshall.
 */
public class UVA_10514_RiverCrossing {

    private static final int oo = 1 << 28;

    static class Point {
        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        Point translate(Vector v) {
            return new Point(x + v.x, y + v.y);
        }

        double dist(Point p) {
            return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
        }

        static double distToLineSegment(Point p, Point a, Point b) {
            Vector ap = new Vector(a, p), ab = new Vector(a, b), bp = new Vector(b, p), ba = new Vector(b, a);
            double u = ap.dot(ab) / ab.norm2();
            if (u < 0.0)
                return a.dist(p);
            if (u > 1.0)
                return b.dist(p);
            return a.translate(ab.scale(u)).dist(p);
        }

        @Override
        public String toString() {
            return String.format("(%f, %f)", x, y);
        }
    }

    static class Vector {
        double x, y;

        public Vector(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public Vector(Point a, Point b) {
            x = b.x - a.x;
            y = b.y - a.y;
        }

        double dot(Vector v) {
            return (x * v.x) + (y * v.y);
        }

        double norm2() {
            return (x * x) + (y * y);
        }

        @Override
        public String toString() {
            return x + " " + y;
        }

        public Vector scale(double s) {
            return new Vector(x * s, y * s);
        }
    }

    static class Polygon {
        int n;
        Point[] g;

        public Polygon(int n, Point[] g) {
            this.n = n;
            this.g = g;
        }

        @Override
        public String toString() {
            return Arrays.toString(g);
        }

        public double dist(Point p) {
            double res = oo;
            for (int i = 0; i < n; i++)
                res = Math.min(res, Point.distToLineSegment(p, g[i], g[(i + 1) % n]));
            return res;
        }

        public double dist(Point a, Point b) {
            double res = oo;
            for (int i = 0; i < n; i++)
                res = Math.min(res, Point.distToLineSegment(g[i], a, b));
            return res;
        }

        public double dist(Polygon p) {
            double res = oo;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < p.n; j++)
                    res = Math.min(res, Point.distToLineSegment(g[i], p.g[j], p.g[(j + 1) % p.n]));
            for (int i = 0; i < p.n; i++)
                for (int j = 0; j < n; j++)
                    res = Math.min(res, Point.distToLineSegment(p.g[i], g[j], g[(j + 1) % n]));
            return res;
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int TC = sc.nextInt();
        while (TC-- > 0) {
            int N = sc.nextInt(), M = sc.nextInt(), K = sc.nextInt();
            Point[] a = new Point[N];
            for (int i = 0; i < N; i++)
                a[i] = new Point(sc.nextDouble(), sc.nextDouble());
            Point[] b = new Point[M];
            for (int i = 0; i < M; i++)
                b[i] = new Point(sc.nextDouble(), sc.nextDouble());
            Polygon[] islands = new Polygon[K];
            for (int i = 0; i < K; i++) {
                int n = sc.nextInt();
                Point[] p = new Point[n];
                for (int j = 0; j < n; j++)
                    p[j] = new Point(sc.nextDouble(), sc.nextDouble());
                islands[i] = new Polygon(n, p);
            }
            int V = K + 2, s = K, t = K + 1;
            double[][] adjMatrix = new double[V][V];
            for (int i = 0; i < V; i++) {
                Arrays.fill(adjMatrix[i], oo);
                adjMatrix[i][i] = 0;
            }
            double min = oo;
            for (int i = 0; i < N; i++)
                for (int j = 0; j < M; j++)
                    min = Math.min(min, Math.min(j == M - 1 ? oo : Point.distToLineSegment(a[i], b[j], b[j + 1]), i
                            == N - 1 ? oo : Point.distToLineSegment(b[j], a[i], a[i + 1])));
            adjMatrix[s][t] = adjMatrix[t][s] = min;
            for (int i = 0; i < K; i++) {
                double toSource = oo;
                for (int j = 0; j < N; j++)
                    toSource = Math.min(toSource, Math.min(islands[i].dist(a[j]), j == N - 1 ? oo : islands[i].dist
                            (a[j], a[j + 1])));
                adjMatrix[s][i] = adjMatrix[i][s] = toSource;
                double toSink = oo;
                for (int j = 0; j < M; j++)
                    toSink = Math.min(toSink, Math.min(islands[i].dist(b[j]), j == M - 1 ? oo : islands[i].dist(b[j],
                            b[j + 1])));
                adjMatrix[i][t] = adjMatrix[t][i] = toSink;
                for (int j = i + 1; j < K; j++)
                    adjMatrix[i][j] = adjMatrix[j][i] = islands[i].dist(islands[j]);
            }
            for (int k = 0; k < V; k++)
                for (int i = 0; i < V; i++)
                    for (int j = 0; j < V; j++)
                        adjMatrix[i][j] = Math.min(adjMatrix[i][j], adjMatrix[i][k] + adjMatrix[k][j]);
            out.printf("%.3f\n", adjMatrix[s][t]);
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
