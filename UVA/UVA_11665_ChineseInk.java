package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-Two Polygon's Intersect iff either their segments intersects or
 *				 a vertex is inside a polygon. We use Disjoint Sets to count the
 *				 number of disjoint sets of Polygons.
 */
public class UVA_11665_ChineseInk {

	static final double EPS = 1e-9;

	static class Point {
		double x, y;

		Point(double a, double b) {
			x = a;
			y = b;
		}

		boolean between(Point p, Point q) {
			return x < Math.max(p.x, q.x) + EPS && x + EPS > Math.min(p.x, q.x) && y < Math.max(p.y, q.y) + EPS
					&& y + EPS > Math.min(p.y, q.y);
		}

		static boolean ccw(Point a, Point b, Point c) {
			return new Vector(a, b).cross(new Vector(a, c)) > 0;
		}

		static double angle(Point a, Point o, Point b) {
			Vector oa = new Vector(o, a), ob = new Vector(o, b);
			return Math.acos(oa.dot(ob) / Math.sqrt(oa.norm2() * ob.norm2()));
		}

		@Override
		public String toString() {
			return x + " " + y;
		}
	}

	static class Vector {
		double x, y;

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
			if (areParallel(l))
				return null;
			double x = (b * l.c - c * l.b) / (a * l.b - b * l.a);
			double y;
			if (Math.abs(b) < EPS)
				y = -l.a * x - l.c;
			else
				y = -a * x - c;
			return new Point(x, y);
		}

		boolean areParallel(Line l) {
			return Math.abs(a - l.a) < EPS && Math.abs(b - l.b) < EPS;
		}

		boolean sameLine(Line l) {
			return areParallel(l) && Math.abs(c - l.c) < EPS;
		}

		@Override
		public String toString() {
			return a + " " + b + " " + c;
		}
	}

	static class LineSegment {

		Point p, q;

		public LineSegment(Point a, Point b) {
			p = a;
			q = b;
		}

		boolean intersect(LineSegment ls) {
			Line l1 = new Line(p, q), l2 = new Line(ls.p, ls.q);
			if (l1.areParallel(l2)) {
				if (l1.sameLine(l2))
					return p.between(ls.p, ls.q) || q.between(ls.p, ls.q) || ls.p.between(p, q) || ls.q.between(p, q);
				return false;
			}
			Point c = l1.intersect(l2);
			return c.between(p, q) && c.between(ls.p, ls.q);
		}
	}

	static class Polygon {
		int n;
		Point[] g;

		Polygon(Point[] p) {
			n = p.length;
			g = p;
		}

		boolean intersects(Polygon p) {
			for (int i = 0; i < n; i++)
				if (p.inside(g[i]))
					return true;
			for (int i = 0; i < p.n; i++)
				if (inside(p.g[i]))
					return true;
			for (int i = 0; i < n; i++) {
				LineSegment ls = new LineSegment(g[i], g[(i + 1) % n]);
				for (int j = 0; j < p.n; j++)
					if (ls.intersect(new LineSegment(p.g[j], p.g[(j + 1) % p.n])))
						return true;
			}
			return false;
		}

		boolean inside(Point p) {
			double sum = 0.0;
			for (int i = 0; i < n; i++) {
				double angle = Point.angle(g[i], p, g[(i + 1) % n]);
				if ((Math.abs(angle) < EPS || Math.abs(angle - Math.PI) < EPS) && p.between(g[i], g[(i + 1) % n]))
					return true;
				if (Point.ccw(p, g[i], g[(i + 1) % n]))
					sum += angle;
				else
					sum -= angle;
			}
			return Math.abs(2 * Math.PI - Math.abs(sum)) < EPS;
		}

		@Override
		public String toString() {
			return Arrays.toString(g);
		}
	}

	static class UnionFind {
		int[] rep, rank;
		int numSets;

		UnionFind(int n) {
			rep = new int[n];
			rank = new int[n];
			numSets = n;
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
			numSets--;
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			Polygon[] shapes = new Polygon[n];
			for (int i = 0; i < n; i++) {
				sc.st = new StringTokenizer(sc.nextLine());
				Point[] p = new Point[sc.st.countTokens() >> 1];
				for (int j = 0; sc.st.hasMoreTokens(); j++)
					p[j] = new Point(sc.nextInt(), sc.nextInt());
				shapes[i] = new Polygon(p);
			}
			UnionFind uf = new UnionFind(n);
			for (int i = 0; i < n; i++) {
				Polygon p = shapes[i];
				for (int j = i + 1; j < n; j++){
					if(uf.sameSet(i, j))
						continue;
					if (p.intersects(shapes[j]))
						uf.union(i, j);
				}
			}
			out.println(uf.numSets);
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
