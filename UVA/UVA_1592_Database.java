package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeSet;

/*-
 * @author Mazen Magdy
 *				-Number of Columns are low so we BruteForce on them and keep track of data in the rows in these
 *				 columns.
 */
public class UVA_1592_Database {

	static class Data implements Comparable<Data> {
		String a, b;
		int r;

		Data(String x, String y, int row) {
			a = x;
			b = y;
			r = row;
		}

		@Override
		public String toString() {
			return a + " " + b + " " + r;
		}

		@Override
		public int compareTo(Data d) {
			int f = a.compareTo(d.a);
			if (f != 0)
				return f;
			return b.compareTo(d.b);
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			int n = sc.nextInt(), m = sc.nextInt();
			String[][] s = new String[n][m];
			for (int i = 0; i < n; i++) {
				String a = sc.nextLine();
				StringTokenizer st = new StringTokenizer(a, ",");
				for (int j = 0; st.hasMoreTokens(); j++)
					s[i][j] = st.nextToken();
			}
			TreeSet<Data> set = new TreeSet<>();
			boolean f = false;
			int r1 = -1, r2 = -1, c1 = -1, c2 = -1;
			for (int i = 0; i < m && !f; i++)
				for (int j = i + 1; j < m && !f; j++) {
					set = new TreeSet<>();
					for (int k = 0; k < n && !f; k++) {
						if (!set.add(new Data(s[k][i], s[k][j], k))) {
							f = true;
							r1 = set.ceiling(new Data(s[k][i], s[k][j], 0)).r + 1;
							r2 = k + 1;
							c1 = i + 1;
							c2 = j + 1;
						}
					}
				}
			out.println(f ? "NO" : "YES");
			if (f)
				out.printf("%d %d\n%d %d\n", r1, r2, c1, c2);
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

		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}
