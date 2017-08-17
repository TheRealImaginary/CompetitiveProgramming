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
 *				-For each two LineSegments that intersect each other we union them in
 *				 a DisjointSet then we use the Set to answer queries.
 */
public class UVA_273_JackStraws {

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

		boolean areParallel(Line l) {
			return Math.abs(a - l.a) < EPS && Math.abs(b - l.b) < EPS;
		}

		boolean sameLine(Line l) {
			return areParallel(l) && Math.abs(c - l.c) < EPS;
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

		@Override
		public String toString() {
			return a + " " + b + " " + c;
		}
	}

	static class LineSegment {
		Point p, q;

		LineSegment(Point a, Point b) {
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

	static class UnionFind {
		int[] rep, rank;

		UnionFind(int n) {
			rep = new int[n];
			rank = new int[n];
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
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt();
			LineSegment[] lines = new LineSegment[n];
			for (int i = 0; i < n; i++) {
				lines[i] = new LineSegment(new Point(sc.nextInt(), sc.nextInt()),
						new Point(sc.nextInt(), sc.nextInt()));
			}
			UnionFind uf = new UnionFind(n);
			for (int i = 0; i < n; i++) {
				LineSegment ls = lines[i];
				for (int j = i + 1; j < n; j++)
					if (ls.intersect(lines[j]))
						uf.union(i, j);
			}
			while (true) {
				int a = sc.nextInt(), b = sc.nextInt();
				if (a + b == 0)
					break;
				a--;
				b--;
				if (uf.sameSet(a, b))
					out.println("CONNECTED");
				else
					out.println("NOT CONNECTED");
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
