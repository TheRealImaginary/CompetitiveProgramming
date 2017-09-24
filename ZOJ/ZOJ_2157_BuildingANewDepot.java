package ZOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ZOJ_2157_BuildingANewDepot {

	static class Point {
		int x, y;

		Point(int a, int b) {
			x = a;
			y = b;
		}

		@Override
		public String toString() {
			return x + " " + y;
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			Point[] points = new Point[n];
			for (int i = 0; i < n; i++)
				points[i] = new Point(sc.nextInt(), sc.nextInt());
			int ans = 0;
			Arrays.sort(points, (a, b) -> {
				if (a.x == b.x)
					return a.y - b.y;
				return a.x - b.x;
			});
			int i = 0;
			while (i < n) {
				if (points[i].x == points[i + 1].x) {
					ans += Math.abs(points[i].y - points[i + 1].y);
					i += 2;
				} else
					i++;
			}
			Arrays.sort(points, (a, b) -> {
				if (a.y == b.y)
					return a.x - b.x;
				return a.y - b.y;
			});
			i = 0;
			while (i < n) {
				if (points[i].y == points[i + 1].y) {
					ans += Math.abs(points[i].x - points[i + 1].x);
					i += 2;
				} else
					i++;
			}
			out.printf("The length of the fence will be %d units.\n", ans);
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
