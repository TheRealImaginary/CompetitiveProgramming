package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We check for each Line Segment if it intersects with another one.
 */
public class UVA_11343_IsolatedSegments {

	static final double EPS = 1e-9;

	static class Point {
		double x, y;

		Point(double a, double b) {
			x = a;
			y = b;
		}

		boolean between(Point a, Point b) {
			return x < Math.max(a.x, b.x) + EPS && x + EPS > Math.min(a.x, b.x) && y < Math.max(a.y, b.y) + EPS
					&& y + EPS > Math.min(a.y, b.y);
		}

		@Override
		public String toString() {
			return x + " " + y;
		}
	}

	static class Line {

		double a, b, c;

		Line(Point p, Point q) {
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

		Point intersect(Line l) {
			if (AreParallel(l))
				return null;
			double x = (b * l.c - c * l.b) / (a * l.b - b * l.a);
			double y;
			if (Math.abs(b) < EPS)
				y = -l.a * x - l.c;
			else
				y = -a * x - c;
			return new Point(x, y);
		}

		boolean AreParallel(Line l) {
			return Math.abs(a - l.a) < EPS && Math.abs(b - l.b) < EPS;
		}

		boolean SameLine(Line l) {
			return AreParallel(l) && Math.abs(c - l.c) < EPS;
		}

		@Override
		public String toString() {
			return a + " " + b + " " + c;
		}
	}

	static class LineSegment {
		Point s, e;

		LineSegment(Point a, Point b) {
			s = a;
			e = b;
		}

		boolean intersect(LineSegment ls) {
			Line l1 = new Line(s, e), l2 = new Line(ls.s, ls.e);
			if (l1.AreParallel(l2)) {
				if (l1.SameLine(l2))
					return s.between(ls.s, ls.e) || e.between(ls.s, ls.e) || ls.s.between(s, e) || ls.e.between(s, e);
				return false;
			}
			Point c = l1.intersect(l2);
			return c.between(s, e) && c.between(ls.s, ls.e);
		}

		@Override
		public String toString() {
			return s + " " + e;
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt();
			LineSegment[] lines = new LineSegment[n];
			for (int i = 0; i < n; i++)
				lines[i] = new LineSegment(new Point(sc.nextDouble(), sc.nextDouble()),
						new Point(sc.nextDouble(), sc.nextDouble()));
			int ans = 0;
			for (int i = 0; i < n; i++) {
				boolean f = true;
				for (int j = 0; j < n; j++) {
					if (i == j)
						continue;
					f &= !lines[i].intersect(lines[j]);
				}
				if (f)
					ans++;
			}
			out.println(ans);
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
