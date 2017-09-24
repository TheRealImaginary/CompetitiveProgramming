package Topcoder;

import java.util.Arrays;

/*-
 * @author Mazen Magdy
 *				-All points generated will be bounded by the Convex Hull
 *				 of the Original Points.
 */
public class SRM_249_D1_1000_CultureGrowth {

	static class Point implements Comparable<Point> {
		int x, y;

		Point(int a, int b) {
			x = a;
			y = b;
		}

		static boolean ccw(Point a, Point b, Point c) {
			return new Vector(a, b).cross(new Vector(a, c)) >= 0;
		}

		@Override
		public String toString() {
			return x + " " + y;
		}

		@Override
		public int compareTo(Point p) {
			if (x != p.x)
				return x - p.x;
			if (y != p.y)
				return y - p.y;
			return 0;
		}
	}

	static class Vector {
		int x, y;

		Vector(Point a, Point b) {
			x = b.x - a.x;
			y = b.y - a.y;
		}

		int cross(Vector v) {
			return (x * v.y) - (y * v.x);
		}

		@Override
		public String toString() {
			return x + " " + y;
		}
	}

	static class Polygon {

		Point[] p;

		Polygon(Point[] p) {
			this.p = p;
		}

		double area() {
			double area = 0.0;
			for (int i = 0; i < p.length - 1; i++)
				area += p[i].x * p[i + 1].y - p[i].y * p[i + 1].x;
			return Math.abs(area) / 2.0;
		}

		static Polygon convexHull(Point[] points) {
			int n = points.length;
			Arrays.sort(points);
			Point[] ans = new Point[n << 1];
			int size = 0, start = 0;

			for (int i = 0; i < n; i++) {
				Point p = points[i];
				while (size - start >= 2 && !Point.ccw(ans[size - 2], ans[size - 1], p))
					--size;
				ans[size++] = p;
			}
			start = --size;

			for (int i = n - 1; i >= 0; i--) {
				Point p = points[i];
				while (size - start >= 2 && !Point.ccw(ans[size - 2], ans[size - 1], p))
					--size;
				ans[size++] = p;
			}
			return new Polygon(Arrays.copyOf(ans, size));
		}
	}

	static double finalTray(int[] x, int[] y) {
		int n = x.length;
		Point[] p = new Point[n];
		for (int i = 0; i < n; i++)
			p[i] = new Point(x[i], y[i]);
		return Polygon.convexHull(p).area();
	}

	public static void main(String[] args) {
		System.err.println(finalTray(new int[] { 0, 3, 6, 10 }, new int[] { 0, 3, 6, 10 }));
		System.err.println(finalTray(new int[] { 10 }, new int[] { 240 }));
		System.err.println(finalTray(new int[] { 10, 15, 3, 37 }, new int[] { 49, 49, 12, 8 }));
	}
}
