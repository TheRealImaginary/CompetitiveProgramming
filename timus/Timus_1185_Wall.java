package timus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Timus_1185_Wall {

	static class Point implements Comparable<Point> {
		int x, y;

		Point(int a, int b) {
			x = a;
			y = b;
		}

		double dist(Point p) {
			return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
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
		Point[] g;

		Polygon(Point[] p) {
			g = p;
		}

		double perimeter() {
			double sum = 0.0;
			for (int i = 0; i < g.length - 1; i++)
				sum += g[i].dist(g[i + 1]);
			return sum;
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

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), l = sc.nextInt();
		Point[] vertices = new Point[n];
		for (int i = 0; i < n; i++)
			vertices[i] = new Point(sc.nextInt(), sc.nextInt());
		out.printf("%.0f\n", Polygon.convexHull(vertices).perimeter() + 2 * Math.PI * l);
		out.flush();
		out.close();
	}

	static class MyScanner {

		BufferedReader br;
		StringTokenizer st;

		MyScanner(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		String nextLine() throws IOException {
			return br.readLine();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		boolean ready() throws IOException {
			return br.ready();
		}
	}
}
