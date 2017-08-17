package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UVA_10902_PickUpSticks {

	static final double EPS = 1e-9;

	static class Point implements Comparable<Point> {

		double x, y;

		Point(double a, double b) {
			x = a;
			y = b;
		}

		@Override
		public int compareTo(Point p) {
			return (int) (x - p.x);
		}

		public double dist(Point p) {
			return Math.sqrt((x - p.x) * (x - p.x) + ((y - p.y) * (y - p.y)));
		}

		public boolean above(Point p) {
			return y > p.y;
		}

		public boolean between(Point p, Point q) {
			return x < Math.max(p.x, q.x) + EPS && x + EPS > Math.min(p.x, q.x) && y < Math.max(p.y, q.y) + EPS
					&& y + EPS > Math.min(p.y, q.y);
		}

		public String toString() {
			return x + " " + y;
		}
	}

	static class Line {

		int id;
		Point pp, qq;
		double a, b, c;

		public Line(Point p, Point q) {
			if (Math.abs(p.x - q.x) < EPS) {
				a = 1.0;
				b = 0.0;
				c = -p.x;
			} else {
				a = (p.y - q.y) / (q.x - p.x);
				b = 1.0;
				c = -((p.x * a) + p.y);
			}
			pp = p;
			qq = q;
		}

		public Point intersect(Line l) {
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

		public boolean AreParallel(Line l) {
			return Math.abs(a - l.a) < EPS && Math.abs(b - l.b) < EPS;
		}

		public boolean SameLine(Line l) {
			return AreParallel(l) && Math.abs(c - l.c) < EPS;
		}

		public String toString() {
			return a + " " + b + " " + c;
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			int j = 1;
			ArrayList<Line> l = new ArrayList<>();
			while (n-- > 0) {
				Point a = new Point(sc.nextDouble(), sc.nextDouble());
				Point b = new Point(sc.nextDouble(), sc.nextDouble());
				ArrayList<Line> temp = new ArrayList<>();
				Line l1 = new Line(a, b);
				l1.id = j++;
				int i;
				for (i = 0; i < l.size(); i++) {
					boolean top = true;
					Line ll = l.get(i);
					if (l1.AreParallel(ll)) {
						// as they are actually line segments, they can over
						// lap, so we need to check
						if (l1.SameLine(ll)) {
							if (ll.pp.between(l1.pp, l1.qq) || ll.qq.between(l1.pp, l1.qq))
								top = false;
						}
					} else {
						Point p = l1.intersect(ll);
						if (p.between(l1.pp, l1.qq) && p.between(ll.pp, ll.qq))
							top = false;
					}
					if (top) {
						temp.add(l.get(i));
					}
				}
				temp.add(l1);
				l = temp;
			}
			pw.printf("Top sticks: ");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < l.size(); i++) {
				sb.append(l.get(i).id);
				if (i != l.size() - 1)
					sb.append(", ");
				else
					sb.append(".");
			}
			pw.println(sb);
		}
		pw.close();
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
