package Codeforces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CF30_D2_C_ShootingGallery {

	static class Point {
		int x, y;

		Point(int a, int b) {
			x = a;
			y = b;
		}

		double dist(Point p) {
			return sq(x - p.x) + sq(y - p.y);
		}

		@Override
		public String toString() {
			return x + " " + y;
		}
	}

	static class Target {
		Point point;
		int t;
		double p;

		Target(Point a, int x, double probability) {
			point = a;
			t = x;
			p = probability;
		}

		double dist(Target t) {
			return point.dist(t.point);
		}
	}

	static final double EPS = 1e-9;

	static int N;
	static Target[] t;
	static double[][] dp;

	static double sq(double x) {
		return x * x;
	}

	static double solve(int idx, int last) {
		if (idx == N)
			return 0;
		if (dp[idx][last] != -1)
			return dp[idx][last];
		double res = solve(idx + 1, last);
		if (last == N || t[idx].dist(t[last]) <= sq(t[idx].t - t[last].t) + EPS)
			res = Math.max(res, t[idx].p + solve(idx + 1, idx));
		return dp[idx][last] = res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		N = sc.nextInt();
		t = new Target[N];
		for (int i = 0; i < N; i++)
			t[i] = new Target(new Point(sc.nextInt(), sc.nextInt()), sc.nextInt(), sc.nextDouble());
		Arrays.sort(t, (a, b) -> a.t - b.t);
		dp = new double[N][N + 1];
		for (int i = 0; i < N; i++)
			Arrays.fill(dp[i], -1);
		out.println(solve(0, t.length));
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

		boolean ready() throws IOException {
			return br.ready();
		}
	}
}
