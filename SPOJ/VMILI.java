package SPOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We keep applying Convex Hull to remaining points.
 */
public class VMILI { // TLE

	static class Point implements Comparable<Point> {
		int x, y, index;

		Point(int a, int b, int i) {
			x = a;
			y = b;
			index = i;
		}

		static boolean ccw(Point a, Point b, Point c) {
			return new Vector(a, b).cross(new Vector(a, c)) >= 0;
		}

		@Override
		public String toString() {
			return x + " " + y + " " + index;
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

		Vector(Point p, Point q) {
			x = q.x - p.x;
			y = q.y - p.y;
		}

		double cross(Vector v) {
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

		@Override
		public String toString() {
			return Arrays.toString(g);
		}
	}

	static boolean[] done;

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		Point[] p = new Point[n];
		for (int i = 0; i < n; i++)
			p[i] = new Point(sc.nextInt(), sc.nextInt(), i);
		int ans = 0;
		done = new boolean[n];
		while (p.length > 2) {
			Polygon hull = Polygon.convexHull(p);
			Point[] newPoints = new Point[n];
			for (int i = 0; i < hull.g.length; i++)
				done[hull.g[i].index] = true;
			int size = 0;
			for (int i = 0; i < p.length; i++) {
				if (!done[p[i].index])
					newPoints[size++] = p[i];
			}
			p = Arrays.copyOf(newPoints, size);
			ans++;
		}
		out.println(ans);
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
