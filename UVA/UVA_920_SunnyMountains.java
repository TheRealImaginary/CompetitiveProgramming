package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class UVA_920_SunnyMountains {

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

		public String toString() {
			return x + " " + y;
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt();
			Point[] p = new Point[n];
			for (int i = 0; i < n; i++)
				p[i] = new Point(sc.nextDouble(), sc.nextDouble());
			Arrays.sort(p);
			// System.err.println(Arrays.toString(p));
			double total = 0, maxY = 0.0;
			for (int i = n - 2; i >= 0; i--) {
				double dist = 0.0;
				if (p[i].above(new Point(0.0, maxY))) {
					double dy = p[i + 1].y - p[i].y;
					dist = p[i].dist(p[i + 1]);
					double sunny = p[i].y - maxY;
					sunny = sunny * dist / dy;
					total += sunny;
				}
				maxY = Math.max(maxY, p[i].y);
			}
			pw.printf("%.2f\n", Math.abs(total));
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
