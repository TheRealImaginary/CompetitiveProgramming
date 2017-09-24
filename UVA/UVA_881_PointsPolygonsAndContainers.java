package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class UVA_881_PointsPolygonsAndContainers { // PE

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

		static double angle(Point a, Point o, Point b) {
			Vector oa = new Vector(o, a), ob = new Vector(o, b);
			return Math.acos(oa.dot(ob) / Math.sqrt(oa.norm2() * ob.norm2()));
		}

		static boolean ccw(Point a, Point b, Point c) {
			return new Vector(a, b).cross(new Vector(a, c)) > EPS;
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

	static class Polygon {
		int id;
		Point[] g;

		Polygon(Point[] p, int ID) {
			g = p;
			id = ID;
		}

		boolean inside(Polygon p) {
			for (int i = 0; i < g.length; i++)
				if (p.inside(g[i]))
					return true;
			return false;
		}

		boolean inside(Point p) {
			double sum = 0.0;
			for (int i = 0; i < g.length; i++) {
				double angle = Point.angle(g[i], p, g[(i + 1) % g.length]);
				if ((Math.abs(angle) < EPS || Math.abs(angle - Math.PI) < EPS)
						&& p.between(g[i], g[(i + 1) % g.length]))
					return true;
				if (Point.ccw(p, g[i], g[(i + 1) % g.length]))
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

	static Polygon[] shapes;
	static ArrayList<Integer>[] g;
	static int[] p, depth;
	static boolean[] vis;

	static void dfs(int u) {
		vis[u] = true;
		depth[u] = 0;
		int maxDepth = -1;
		for (int v : g[u]) {
			if (!vis[v]) {
				p[v] = u;
				dfs(v);
			}
			if (maxDepth < depth[v]) {
				p[v] = u;
				maxDepth = depth[v];
			}
		}
		depth[u] = maxDepth + 1;
	}

	static int find(int u, Point p) {
		for (int v : g[u])
			if (shapes[v].inside(p))
				return find(v, p);
		return u;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int TC = sc.nextInt();
		while (TC-- > 0) {
			int n = sc.nextInt();
			shapes = new Polygon[n];
			for (int i = 0; i < n; i++) {
				StringTokenizer st = new StringTokenizer(sc.nextLine());
				int id = Integer.parseInt(st.nextToken());
				Point[] p = new Point[st.countTokens() >> 1];
				for (int j = 0; st.hasMoreTokens(); j++)
					p[j] = new Point(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
				shapes[i] = new Polygon(p, id);
			}
			g = new ArrayList[n];
			for (int i = 0; i < n; i++)
				g[i] = new ArrayList<>();
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++) {
					if (i == j)
						continue;
					if (shapes[i].inside(shapes[j]))
						g[j].add(i);
				}
			p = new int[n];
			depth = new int[n];
			Arrays.fill(p, -1);
			vis = new boolean[n];
			for (int node = 0; node < n; node++)
				if (!vis[node])
					dfs(node);
			g = new ArrayList[n + 1];
			for (int i = 0; i <= n; i++)
				g[i] = new ArrayList<>();
			int root = n;
			for (int node = 0; node < n; node++) {
				if (p[node] == -1)
					g[root].add(node);
				else
					g[p[node]].add(node);
			}
			int Q = sc.nextInt();
			int[] ans = new int[Q];
			while (Q-- > 0) {
				int id = sc.nextInt() - 1;
				Point p = new Point(sc.nextDouble(), sc.nextDouble());
				ans[id] = find(root, p);
			}
			for (int i = 0; i < ans.length; i++)
				out.printf("%d %d\n", i + 1, ans[i] == n ? 0 : shapes[ans[i]].id);
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

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
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
	}
}
