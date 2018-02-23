package UVA;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * We get the intersections of the rectangle and use a DisjointSet to find
 * rectangles intersecting with the first rectangle. Then we use line sweep
 * to find the area of this region.
 */
public class UVA_870_IntersectingRectangles {

    private static final double EPS = 1e-9;

    private static class Point {
        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public boolean between(Point p, Point q) {
            return x < Math.max(p.x, q.x) + EPS && x + EPS > Math.min(p.x, q.x) && y < Math.max(p.y, q.y) + EPS
                    && y + EPS > Math.min(p.y, q.y);
        }

        public boolean inside(Rectangle r) {
            return x + EPS > r.points[0].x && x < r.points[1].x + EPS
                    && y + EPS > r.points[0].y && y < r.points[2].y + EPS;
        }
    }

    private static class Line {
        double a, b, c;

        public Line(Point p, Point q) {
            if (Math.abs(p.x - q.x) < EPS) {
                a = 1.0;
                b = 0.0;
                c = -p.x;
            } else {
                a = -(p.y - q.y) / (p.x - q.x);
                b = 1.0;
                c = -((p.x * a) + p.y);
            }
        }

        public Point intersect(Line l) {
            if (parallel(l))
                return null;
            double x = (b * l.c - c * l.b) / (a * l.b - b * l.a);
            double y;
            if (Math.abs(b) < EPS)
                y = -l.a * x - l.c;
            else
                y = -a * x - c;
            return new Point(x, y);
        }

        public boolean parallel(Line l) {
            return Math.abs(a - l.a) < EPS && Math.abs(b - l.b) < EPS;
        }

        public boolean sameLine(Line l) {
            return parallel(l) && Math.abs(c - l.c) < EPS;
        }
    }

    private static class LineSegment {
        Point p, q;

        public LineSegment(Point a, Point b) {
            p = a;
            q = b;
        }

        boolean intersect(LineSegment ls) {
            Line l1 = new Line(p, q), l2 = new Line(ls.p, ls.q);
            if (l1.parallel(l2)) {
                return l1.sameLine(l2) &&
                        (p.between(ls.p, ls.q) || q.between(ls.p, ls.q) || ls.p.between(p, q) || ls.q.between(p, q));
            }
            Point c = l1.intersect(l2);
            return c.between(p, q) && c.between(ls.p, ls.q);
        }
    }

    private static class Rectangle {
        Point[] points; // lower left, lower right, upper right, upper left

        public Rectangle(int x1, int y1, int x2, int y2) {
            points = new Point[4];
            points[0] = new Point(x1, y1);
            points[1] = new Point(x2, y1);
            points[2] = new Point(x2, y2);
            points[3] = new Point(x1, y2);
        }

        public boolean intersect(Rectangle r) {
            for (int i = 0; i < 4; i++)
                if (points[i].inside(r)) return true;
            for (int i = 0; i < 4; i++) {
                LineSegment lineSegment = new LineSegment(points[i], points[(i + 1) % 4]);
                for (int j = 0; j < 4; j++)
                    if (lineSegment.intersect(new LineSegment(r.points[j], r.points[(j + 1) % 4])))
                        return true;
            }
            return false;
        }
    }

    private static class UnionFind {
        int[] rep, rank;

        public UnionFind(int n) {
            rep = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) rep[i] = i;
        }

        public int findSet(int x) {
            return rep[x] == x ? x : (rep[x] = findSet(rep[x]));
        }

        public boolean sameSet(int x, int y) {
            return findSet(x) == findSet(y);
        }

        public void union(int x, int y) {
            if (sameSet(x, y)) return;
            x = findSet(x);
            y = findSet(y);
            if (rank[x] > rank[y]) rep[y] = x;
            else {
                rep[x] = y;
                if (rank[x] == rank[y]) rank[y]++;
            }
        }
    }

    public static class Event implements Comparable<Event> {
        public static final int START = 1;
        public static final int END = 2;

        double x;
        int type, idx;

        public Event(double x, int type, int idx) {
            this.x = x;
            this.type = type;
            this.idx = idx;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "x=" + x +
                    ", type=" + type +
                    ", idx=" + idx +
                    '}';
        }

        @Override
        public int compareTo(Event e) {
            return Double.compare(x, e.x);
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int TC = sc.nextInt();
        while (TC-- > 0) {
            int N = sc.nextInt();
            Rectangle[] rectangles = new Rectangle[N];
            for (int i = 0; i < N; i++)
                rectangles[i] = new Rectangle(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
            UnionFind uf = new UnionFind(N);
            for (int i = 0; i < N; i++) {
                Rectangle current = rectangles[i];
                for (int j = 0; j < N; j++)
                    if (current.intersect(rectangles[j])) uf.union(i, j);
            }
            List<Integer> rectangleIndices = new ArrayList<>();
            for (int i = 0; i < N; i++)
                if (uf.sameSet(0, i)) rectangleIndices.add(i);
            List<Event> vertical = new ArrayList<>();
            List<Event> horizontal = new ArrayList<>();
            for (int i = 0; i < rectangleIndices.size(); i++) {
                int idx = rectangleIndices.get(i);
                Rectangle r = rectangles[idx];
                vertical.add(new Event(r.points[0].x, Event.START, idx));
                vertical.add(new Event(r.points[1].x, Event.END, idx));

                horizontal.add(new Event(r.points[0].y, Event.START, idx));
                horizontal.add(new Event(r.points[2].y, Event.END, idx));
            }
            Collections.sort(vertical);
            Collections.sort(horizontal);
            boolean[] active = new boolean[N];
            active[vertical.get(0).idx] = true;
            double area = 0;
            for (int i = 1; i < vertical.size(); i++) {
                Event e = vertical.get(i);
                double deltaX = e.x - vertical.get(i - 1).x, yBegin = 0, totalY = 0;
                for (int j = 0, cnt = 0; j < horizontal.size(); j++) {
                    if (!active[horizontal.get(j).idx]) continue;
                    if (cnt == 0) yBegin = horizontal.get(j).x;
                    if (horizontal.get(j).type == Event.START) cnt++;
                    else {
                        cnt--;
                        if (cnt == 0) totalY += (horizontal.get(j).x - yBegin);
                    }
                }
                area += deltaX * totalY;
                active[e.idx] = e.type == Event.START;
            }
            out.printf("%.0f\n", area);
            if(TC > 0) out.println();
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

        public boolean ready() throws IOException {
            return br.ready();
        }
    }
}
