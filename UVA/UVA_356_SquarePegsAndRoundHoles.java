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
 *				-We check the distance between the center and the squares for a quarter of the circle (n * n).
 *				 The answer is multiplied by four since it's symmetric.
 */
public class UVA_356_SquarePegsAndRoundHoles {

	static int sq(int x) {
		return x * x;
	}

	static double getDist(int x, int y) {
		return Math.sqrt(sq(x) + sq(y));
	}

	public static void main(String[] args) throws IOException {
		// MyScanner sc = new MyScanner(System.in);
		MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			int n = sc.nextInt();
			int all = 0, semi = 0;
			double r = n - 0.5;
			for (int x = 0; x < n; x++)
				for (int y = 0; y < n; y++) {
					double d1 = getDist(x, y), d2 = getDist(x + 1, y + 1);
					if (d2 <= r)
						all++;
					else if (d1 < r)
						semi++;
				}
			out.printf("In the case n = %d, %d cells contain segments of the circle.\n", n, semi << 2);
			out.printf("There are %d cells completely contained in the circle.\n", all << 2);
			if(sc.ready())
				out.println();
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

		public MyScanner(String filename) throws IOException {
			br = new BufferedReader(new FileReader(new File(filename)));
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
