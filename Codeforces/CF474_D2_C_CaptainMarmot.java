package Codeforces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class CF474_D2_C_CaptainMarmot {

	static final int oo = (int) 1e9;
	static final double EPS = 1e-9;
	static final double THETA = Math.PI / 2;

	static class Point {
		double x, y;

		Point(double a, double b) {
			x = a;
			y = b;
		}

		double sq(double x) {
			return x * x;
		}

		double dist2(Point p) {
			return sq(x - p.x) + sq(y - p.y);
		}

		Point rotate(Point p, double theta) {
			double cosine = Math.cos(theta);
			double sine = Math.sin(theta);
			double x = this.x - p.x, y = this.y - p.y;
			return new Point((x * cosine - y * sine) + p.x, (x * sine + y * cosine) + p.y);
		}

		@Override
		public boolean equals(Object o) {
			Point p = (Point) o;
			return Math.abs(x - p.x) < EPS && Math.abs(y - p.y) < EPS;
		}

		@Override
		public String toString() {
			return x + "," + y;
			// return String.format("(%s,%s)", x, y);
		}
	}

	static boolean check(Point a, Point b, Point c, Point d) {
		if (a.equals(b) || a.equals(c) || a.equals(d) || b.equals(c) || b.equals(d) || c.equals(d))
			return false;
		double d1 = a.dist2(b), d2 = a.dist2(c), d3 = a.dist2(d);
		if (Math.abs(d1 - d2) < EPS && Math.abs(2 * d2 - d3) < EPS) {
			double d4 = d.dist2(b), d5 = d.dist2(c);
			return Math.abs(d4 - d5) < EPS && Math.abs(d4 - d1) < EPS;
		}
		if (Math.abs(d2 - d3) < EPS && Math.abs(2 * d2 - d1) < EPS) {
			double d4 = b.dist2(c), d5 = b.dist2(d);
			return Math.abs(d4 - d5) < EPS && Math.abs(d4 - d2) < EPS;
		}
		if (Math.abs(d1 - d3) < EPS && Math.abs(2 * d1 - d2) < EPS) {
			double d4 = c.dist2(b), d5 = c.dist2(d);
			return Math.abs(d4 - d5) < EPS && Math.abs(d4 - d1) < EPS;
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		for (int i = 0; i < n; i++) {
			Point a = new Point(sc.nextDouble(), sc.nextDouble()), a1 = new Point(sc.nextInt(), sc.nextInt()),
					b = new Point(sc.nextDouble(), sc.nextDouble()), b1 = new Point(sc.nextInt(), sc.nextInt()),
					c = new Point(sc.nextDouble(), sc.nextDouble()), c1 = new Point(sc.nextInt(), sc.nextInt()),
					d = new Point(sc.nextDouble(), sc.nextDouble()), d1 = new Point(sc.nextInt(), sc.nextInt());
			int ans = oo;
			Point A = a, B = b, C = c, D = d;
			for (int r1 = 0; r1 < 4; r1++) {
				for (int r2 = 0; r2 < 4; r2++) {
					for (int r3 = 0; r3 < 4; r3++) {
						for (int r4 = 0; r4 < 4; r4++) {
							if (check(A, B, C, D)) {
								ans = Math.min(ans, r1 + r2 + r3 + r4);
							}
							D = d.rotate(d1, THETA * (r4 + 1));
						}
						C = c.rotate(c1, THETA * (r3 + 1));
					}
					B = b.rotate(b1, THETA * (r2 + 1));
				}
				A = a.rotate(a1, THETA * (r1 + 1));
			}
			out.println(ans == oo ? -1 : ans);
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

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		double nextDouble() throws IOException {
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
