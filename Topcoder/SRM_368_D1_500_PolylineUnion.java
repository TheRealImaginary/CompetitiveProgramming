package Topcoder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Mazen Magdy
 *              -Determine whether Two Polylines intersect then use DFS or UnionFind to
 *               count the number of components.
 *
 *               Note: Handle Polylines having one point
 */
public class SRM_368_D1_500_PolylineUnion {

    static final double EPS = 1e-9;

    static class Point {
        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public boolean between(Point p, Point q) {
            return x >= Math.min(p.x, q.x) && x <= Math.max(p.x, q.x) && y >= Math.min(p.y, q.y) && y <= Math.max(p
                    .y, q.y);
        }

        public boolean onLine(LineSegment ls) {
            return new Vector(this, ls.p).cross(new Vector(ls.p, ls.q)) == 0 && between(ls.p, ls.q);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (Double.compare(point.x, x) != 0) return false;
            return Double.compare(point.y, y) == 0;
        }

        @Override
        public String toString() {
            return String.format("(%f, %f)", x, y);
        }
    }

    static class Vector {
        double x, y;

        Vector(Point a, Point b) {
            x = b.x - a.x;
            y = b.y - a.y;
        }

        double cross(Vector v) {
            return (x * v.y) - (y * v.x);
        }
    }

    static class Line {
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

        boolean parallel(Line l) {
            return Math.abs(a - l.a) < EPS && Math.abs(b - l.b) < EPS;
        }

        boolean same(Line l) {
            return parallel(l) && Math.abs(c - l.c) < EPS;
        }

        Point intersect(Line l) {
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

        @Override
        public String toString() {
            return a + " " + b + " " + c;
        }
    }

    static class LineSegment {
        Point p, q;

        public LineSegment(Point p, Point q) {
            this.p = p;
            this.q = q;
        }

        boolean intersect(LineSegment ls) {
            if (p.equals(q)) {
                if (ls.p.equals(ls.q)) {
                    return p.equals(ls.p);
                }
            } else if (ls.p.equals(ls.q)) {
                return new Vector(p, q).cross(new Vector(p, ls.p)) == 0 && ls.p.between(p, q);
            }

            Line l1 = new Line(p, q), l2 = new Line(ls.p, ls.q);
            if (l1.parallel(l2)) {
                if (l1.same(l2))
                    return p.between(ls.p, ls.q) || q.between(ls.p, ls.q) || ls.p.between(p, q) || ls.q.between(p, q);
                return false;
            }
            Point c = l1.intersect(l2);
            return c.between(p, q) && c.between(ls.p, ls.q);
        }

        @Override
        public String toString() {
            return p + " " + q;
        }
    }

    static class UnionFind {
        int[] rank, rep;
        int setSize;

        UnionFind(int n) {
            rank = new int[n];
            rep = new int[n];
            setSize = n;
            for (int i = 0; i < n; i++)
                rep[i] = i;
        }

        int findSet(int x) {
            return rep[x] == x ? x : (rep[x] = findSet(rep[x]));
        }

        boolean sameSet(int x, int y) {
            return findSet(x) == findSet(y);
        }

        void union(int x, int y) {
            if (sameSet(x, y))
                return;
            x = findSet(x);
            y = findSet(y);
            if (rank[x] > rank[y])
                rep[y] = x;
            else {
                rep[x] = y;
                if (rank[x] == rank[y])
                    rank[y]++;
            }
            setSize--;
        }
    }

    public static int countComponents(String[] polylines) {
        StringBuilder sb = new StringBuilder();
        for (String s : polylines)
            sb.append(s);
        StringTokenizer st = new StringTokenizer(sb.toString());
        List<LineSegment> ls = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String[] s = st.nextToken().split("-");
            String[] previous = s[0].split(",");
            ls.add(new LineSegment(new Point(Integer.parseInt(previous[0]), Integer.parseInt(previous[1])),
                    new Point(Integer.parseInt(previous[0]), Integer.parseInt(previous[1]))));
            for (int i = 1; i < s.length; i++) {
                String[] current = s[i].split(",");
                ls.add(new LineSegment(new Point(Integer.parseInt(previous[0]), Integer.parseInt(previous[1])), new
                        Point(Integer.parseInt(current[0]), Integer.parseInt(current[1]))));
                previous = current;
                ls.add(new LineSegment(new Point(Integer.parseInt(previous[0]), Integer.parseInt(previous[1])),
                        new Point(Integer.parseInt(previous[0]), Integer.parseInt(previous[1]))));
            }
        }
        UnionFind uf = new UnionFind(ls.size());
        for (int i = 0; i < ls.size(); i++) {
            LineSegment lineSegment = ls.get(i);
            for (int j = 0; j < ls.size(); j++)
                if (i != j)
                    if (lineSegment.intersect(ls.get(j)))
                        uf.union(i, j);
        }
        return uf.setSize;
    }

    public static void main(String[] args) {
        System.err.println(countComponents(new String[]{"0,0-10,10 0,10-10,0"}));
        System.err.println(countComponents(new String[]{"0,0-10,5 5,0-15,5-10,10-5,5"}));
        System.err.println(countComponents(new String[]{"1", "3,0-5,5 4,0-4,20"}));
        System.err.println(countComponents(new String[]{"10,0-10,1-9,2-9,3-8,4 ", "8,2-9,2-10,3 ", "12,2-11,2-9,1"}));
        System.err.println(countComponents(new String[]{"0,0-10,0-0,0 20,0-8,0-20,0"}));
        System.err.println(countComponents(new String[]{"1,1 2,2 3,3 4,4 3,3-4,4"}));
        System.err.println(countComponents(new String[]{"10,10-20,10 20,10-15,18 15,18-10,10"}));
        System.err.println(countComponents(new String[]{"1,1 1,1"}));
    }
}
