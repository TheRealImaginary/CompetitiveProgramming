package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *
 */
public class UVA_833_WaterFalls {

	static final int oo = 1 << 28;

	static class Point {
		double x, y;

		Point(double a, double b) {
			x = a;
			y = b;
		}

		boolean above(LineSegment ls) {
			return ccw(ls.p, ls.q);
		}

		boolean ccw(Point p, Point q) {
			return new Vector(p, q).cross(new Vector(p, this)) > 0;
		}

		boolean between(LineSegment ls) {
			return ls.p.x <= x && x <= ls.q.x;
		}

		double closestDistance(LineSegment ls) {
			Vector ap = new Vector(ls.p, this), ab = new Vector(ls.p, ls.q);
			double u = ap.dot(ab) / ab.norm2();
			if (u < 0.0)
				return dist2(ls.p);
			if (u > 1.0)
				return dist2(ls.q);
			return dist2(ls.p.translate(ab.scale(u)));
		}

		double dist2(Point p) {
			return (p.x - x) * (p.x - x) + (p.y - y) * (p.y - y);
		}

		Point translate(Vector v) {
			return new Point(x + v.x, y + v.y);
		}

		@Override
		public String toString() {
			return x + " " + y;
		}
	}

	static class Vector {
		double x, y;

		Vector(double a, double b) {
			x = a;
			y = b;
		}

		Vector(Point a, Point b) {
			x = b.x - a.x;
			y = b.y - a.y;
		}

		double dot(Vector v) {
			return (x * v.x) + (y * v.y);
		}

		double cross(Vector v) {
			return (x * v.y) - (y * v.x);
		}

		double norm2() {
			return (x * x) + (y * y);
		}

		Vector scale(double s) {
			return new Vector(x * s, y * s);
		}
	}

	static class LineSegment {
		Point p, q;

		LineSegment(Point a, Point b) {
			if (a.x < b.x) {
				p = a;
				q = b;
			} else {
				p = b;
				q = a;
			}
		}

		Point getLower() {
			return p.y < q.y ? p : q;
		}

		@Override
		public String toString() {
			return String.format("(%s) (%s)", p, q);
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt();
			ArrayList<LineSegment> lines = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				Point a = new Point(sc.nextInt(), sc.nextInt()), b = new Point(sc.nextInt(), sc.nextInt());
				if (a.x == b.x)
					continue;
				lines.add(new LineSegment(a, b));
			}
			// System.err.println(Arrays.toString(lines));
			int q = sc.nextInt();
			while (q-- > 0) {
				Point p = new Point(sc.nextInt(), sc.nextInt());
				while (true) {
					int j = -1;
					double min = oo;
					for (int i = 0; i < lines.size(); i++) {
						if (p.between(lines.get(i)) && p.above(lines.get(i))) {
							double dist = p.closestDistance(lines.get(i));
							if (min > dist) {
								min = dist;
								j = i;
							}
						}
					}
					// System.err.println(q + " " + j + " " + min + " " + p);
					if (j == -1)
						break;
					Point a = lines.get(j).getLower();
					p.x = a.x;
					p.y = a.y;
				}
				out.printf("%.0f\n", p.x);
			}
			if (tc > 0)
				out.println();
		}
		out.flush();
		out.close();
	}

	static class MyScanner {

		BufferedReader br;
		StringTokenizer st;

		MyScanner(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
		}

		MyScanner(String filename) throws IOException {
			br = new BufferedReader(new FileReader(new File(filename)));
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
