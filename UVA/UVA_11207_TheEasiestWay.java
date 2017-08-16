package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We either cut the piece into 4 parts or into 2 parts and then cut the 2.
 *				 so we pick the smallest side and divide it by 2 this will be the side of the square
 *				 and it will be possible to cut each to into another 2 to get four piece. We also pick
 *				 the largest side and cut it into 4 pieces.
 */
public class UVA_11207_TheEasiestWay { 
	
	// Either 1 * 4 or 2 * 2
	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			double max = -1;
			int idx = -1;
			for (int i = 1; i <= n; i++) {
				int x = sc.nextInt(), y = sc.nextInt();
				if (x < y) {
					int tmp = x;
					x = y;
					y = tmp;
				}
				double tmp = Math.max(y / 2.0, Math.min(y, x / 4.0));
				if (max < tmp) {
					max = tmp;
					idx = i;
				}
			}
			pw.println(idx);
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
