package Topcoder;

public class SRM_373_D2_1000_RectangleCrossings {

    private static final double EPS = 1e-9;

    private static class Point {
        double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public boolean strictlyBetween(Point p, Point q) {
            return x > Math.min(p.x, q.x) && x < Math.max(p.x, q.x) && y > Math.min(p.y, q.y) && y < Math.max(p
                    .y, q.y);
        }

        public boolean between(Point p, Point q) {
            return x + EPS > Math.min(p.x, q.x) && x < Math.max(p.x, q.x) + EPS && y + EPS > Math.min(p.y, q.y) &&
                    y < Math.max(p.y, q.y) + EPS;
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
            return x + " " + y;
        }
    }

    private static class Vector {
        double x, y;

        Vector(Point a, Point b) {
            x = b.x - a.x;
            y = b.y - a.y;
        }

        double cross(Vector v) {
            return (x * v.y) - (y * v.x);
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

        public LineSegment(Point p, Point q) {
            this.p = p;
            this.q = q;
        }

        boolean intersect(LineSegment ls) {
            if (p.equals(ls.p) || p.equals(ls.q) || q.equals(ls.p) || q.equals(ls.q))
                return true;
            Line l1 = new Line(p, q), l2 = new Line(ls.p, ls.q);
            if (l1.parallel(l2)) {
                if (l1.sameLine(l2))
                    return p.between(ls.p, ls.q) || q.between(ls.p, ls.q) || ls.p.between(p, q) || ls.q.between(p, q);
                return false;
            }
            Point c = l1.intersect(l2);
            return c.between(p, q) && c.between(ls.p, ls.q);
        }

        public boolean inside(Rectangle r) {
            return p.strictlyBetween(r.lowerLeft, r.upperRight) || q.strictlyBetween(r.lowerLeft, r.upperRight);
        }

        public boolean intersects(Rectangle r) {
            return intersect(new LineSegment(r.lowerLeft, r.lowerRight)) || intersect(new LineSegment(r.lowerRight, r
                    .upperRight)) || intersect(new LineSegment(r.upperRight, r.upperLeft)) ||
                    intersect(new LineSegment(r.upperLeft, r.lowerLeft));
        }

        @Override
        public String toString() {
            return p + " " + q;
        }
    }

    private static class Rectangle {
        Point lowerLeft, lowerRight, upperLeft, upperRight;

        public Rectangle(Point lowerLeft, Point upperRight) {
            this.lowerLeft = lowerLeft;
            this.upperRight = upperRight;
            this.lowerRight = new Point(upperRight.x, lowerLeft.y);
            this.upperLeft = new Point(lowerLeft.x, upperRight.y);
        }

        @Override
        public String toString() {
            return lowerLeft + " " + lowerRight + " " + upperRight + " " + upperLeft;
        }

        public double area() {
            return Math.abs(upperRight.x - lowerLeft.x) * Math.abs(upperRight.y - lowerLeft.y);
        }
    }

    public static int[] countAreas(String[] rectangles, String[] segments) {
        int a = 0, b = 0;
        Rectangle[] shapes = new Rectangle[rectangles.length];
        for (int i = 0; i < rectangles.length; i++) {
            String[] points = rectangles[i].split(" ");
            shapes[i] = new Rectangle(new Point(Integer.parseInt(points[0]), Integer.parseInt(points[1])), new Point
                    (Integer.parseInt(points[2]), Integer.parseInt(points[3])));
        }
        LineSegment[] lineSegments = new LineSegment[segments.length];
        for (int i = 0; i < segments.length; i++) {
            String[] points = segments[i].split(" ");
            lineSegments[i] = new LineSegment(new Point(Integer.parseInt(points[0]), Integer.parseInt(points[1])), new
                    Point(Integer.parseInt(points[2]), Integer.parseInt(points[3])));
        }
        for (int i = 0; i < shapes.length; i++) {
            boolean hasAPoint = false, hasIntersection = false;
            Rectangle now = shapes[i];
            for (int j = 0; j < lineSegments.length; j++) {
                if (lineSegments[j].inside(now))
                    hasAPoint = true;
                if (lineSegments[j].intersects(now))
                    hasIntersection = true;
            }
            if (hasAPoint)
                a += now.area();
            else if (hasIntersection)
                b += now.area();
        }
        return new int[]{a, b};
    }

    public static void main(String[] args) {
        System.err.println(countAreas(new String[]{"-1000 -1000 1000 1000"}, new String[]{"-525 245 222 243"}));
        System.err.println(countAreas(new String[]{"1 1 2 2", "1 4 2 5", "5 5 6 7", "7 7 9 9"}, new String[]{"1 2 1 " +
                "5"}));
        System.err.println(countAreas(new String[]{"1 1 3 3", "4 4 5 5", "6 6 7 7", "8 8 9 9", "51 22 344 352",
                "-124" +
                        " -235 -12 -1"}, new String[]{"-100 -2 300 300"}));
        System.err.println(countAreas(new String[]{"1 1 3 3", "4 4 5 5", "6 6 7 7", "8 8 9 9", "51 22 344 352",
                "-124" +
                        " -235 -12 -1"}, new String[]{"-104 -103 202 201"}));
    }
}
