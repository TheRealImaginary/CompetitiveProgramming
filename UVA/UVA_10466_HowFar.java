package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We calculate the position in radians for each body
 *				 and then calculate its x and y axis and its distance.
 */
public class UVA_10466_HowFar {

	static final double DEG_TO_RAD(double theta) {
		return Math.PI * theta / 180.0;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner(new FileInputStream("in.txt"));
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			int n = sc.nextInt();
			double T = sc.nextDouble();
			double x = 0, y = 0;
			ArrayList<Double> ans = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				double r = sc.nextDouble(), t = sc.nextDouble();
				// DEG_TO_RAD(2 * Math.PI * T / t);
				double theta = 2 * Math.PI * T / t;
				x += r * Math.cos(theta);
				y += r * Math.sin(theta);
				ans.add(Math.sqrt(x * x + y * y));
			}
			for (int i = 0; i < n; i++) {
				out.printf("%.4f", ans.get(i));
				if (i != n - 1)
					out.print(" ");
			}
			out.println();
		}
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

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}
