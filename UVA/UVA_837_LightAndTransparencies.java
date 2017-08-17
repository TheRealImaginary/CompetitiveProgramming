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
 *				-We find for each interval of (x1, x2) the lines that are in them
 *				 and multiply their r.
 */
public class UVA_837_LightAndTransparencies {

	static class LineSegment implements Comparable<LineSegment> {

		Point p, q;
		double r;

		public LineSegment(Point a, Point b) {
			p = min(a, b);
			q = max(a, b);
		}

		Point min(Point p, Point q) {
			if (p.x < q.x)
				return p;
			else
				return q;
		}

		Point max(Point p, Point q) {
			if (p.x < q.x)
				return q;
			else
				return p;
		}

		@Override
		public int compareTo(LineSegment l) {
			return Double.compare(p.x, l.p.x);
		}

		public String toString() {
			return p + " " + q;
		}
	}

	static class Point implements Comparable<Point> {

		// Global Variables
		static final double EPS = 1e-9;
		static final double oo = 1e9;

		double x, y;

		public Point() {
			x = 0.0;
			y = 0.0;
		}

		public Point(double xx, double yy) {
			x = xx;
			y = yy;
		}

		public double dist(Point p) {
			return hyp(x - p.x, y - p.y);
		}

		public double hyp(double dx, double dy) {
			return Math.sqrt(dx * dx + dy * dy);
		}

		public Point rotate(double theta) {
			double rad = DEG_to_RAD(theta);
			double cosine = Math.cos(rad);
			double sine = Math.sin(rad);
			return new Point((x * cosine - y * sine), (x * sine + y * cosine));
		}

		public boolean between(Point p, Point q) {
			return x < Math.max(p.x, q.x) + EPS && x + EPS > Math.min(p.x, q.x) && y < Math.max(p.y, q.y) + EPS
					&& y + EPS > Math.min(p.y, q.y);
		}

		public boolean higher(Point p) {
			return y > p.y;
		}
		
		public boolean toLeft(Point p) {
			return x < p.x;
		}

		@Override
		public int compareTo(Point p) {
			if (Math.abs(x - p.x) > EPS)
				return Double.compare(x, p.x);
			if (Math.abs(y - p.y) > EPS)
				return Double.compare(y, p.y);
			return 0;
		}

		public String toString() {
			return x + " " + y;
		}

		// Static Factory
		static final double sq(double x) {
			return x * x;
		}

		static final double round(double x) {
			return Math.round(x * 1000) / 1000.0;
		}

		static final double DEG_to_RAD(double theta) {
			return theta * Math.PI / 180;
		}

		static final double RAD_to_DEG(double theta) {
			return theta * 180 / Math.PI;
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt();
			double[] x = new double[n << 1];
			LineSegment[] ls = new LineSegment[n];
			int k = 0;
			for (int i = 0; i < n; i++) {
				Point a = new Point(sc.nextDouble(), sc.nextDouble());
				Point b = new Point(sc.nextDouble(), sc.nextDouble());
				ls[i] = new LineSegment(a, b);
				ls[i].r = sc.nextDouble();
				x[k++] = a.x;
				x[k++] = b.x;
			}
			Arrays.sort(x);
			Arrays.sort(ls);
			int nn = (n << 1);
			pw.println(nn + 1);
			for (int i = 0; i < nn; i++) {
				if (i == 0) {
					pw.printf("-inf %.3f 1.000\n", x[i]);
					continue;
				}
				double res = 1.0;
				for (int j = 0; j < n; j++) {
					if (ls[j].p.x <= x[i - 1] && ls[j].q.x >= x[i])
						res *= ls[j].r;
				}
				pw.printf("%.3f %.3f %.3f\n", x[i - 1], x[i], res);
				if (i == nn - 1)
					pw.printf("%.3f +inf 1.000\n", x[i]);
			}
			if (t != 0)
				pw.println();
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
			for (int i = start; i < x.length(); i++)
				if (x.charAt(i) == '.') {
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				} else {
					sb.append(x.charAt(i));
					if (dec)
						f *= 10;
				}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg ? -1 : 1);
		}

		public boolean ready() throws IOException {
			return br.ready();
		}

	}
}
