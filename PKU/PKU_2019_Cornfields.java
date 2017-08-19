package PKU;

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
 *				-Since limits are small and there are no updates, We can use a 2D SparseTable
 *				 to store minimum and maximum.
 */
public class PKU_2019_Cornfields {

	static class SparseTable {

		short[][][][] max, min;

		SparseTable(int n, int m, short[][] a) {
			int k1 = (int) Math.floor(Math.log(n) / Math.log(2)) + 1,
					k2 = (int) Math.floor(Math.log(m) / Math.log(2)) + 1;
			max = new short[n][k1][m][k2];
			min = new short[n][k1][m][k2];
			for (int r = 0; r < n; r++) {
				for (int c = 0; c < m; c++) {
					max[r][0][c][0] = a[r][c];
					min[r][0][c][0] = a[r][c];
				}

				for (int j2 = 1; (1 << j2) <= m; j2++)
					for (int c = 0; c + (1 << j2) - 1 < m; c++) {
						max[r][0][c][j2] = (short)Math.max(max[r][0][c][j2 - 1], max[r][0][c + (1 << (j2 - 1))][j2 - 1]);
						min[r][0][c][j2] = (short)Math.min(min[r][0][c][j2 - 1], min[r][0][c + (1 << (j2 - 1))][j2 - 1]);
					}
			}
			for (int j1 = 1; (1 << j1) <= n; j1++)
				for (int r = 0; r + (1 << j1) - 1 < n; r++)
					for (int j2 = 0; (1 << j2) <= m; j2++)
						for (int c = 0; c + (1 << j2) - 1 < m; c++) {
							max[r][j1][c][j2] = (short)Math.max(max[r][j1 - 1][c][j2],
									max[r + (1 << (j1 - 1))][j1 - 1][c][j2]);
							min[r][j1][c][j2] = (short)Math.min(min[r][j1 - 1][c][j2],
									min[r + (1 << (j1 - 1))][j1 - 1][c][j2]);
						}
		}

		int query(int x1, int y1, int x2, int y2) {
			int k1 = (int) Math.floor(Math.log(x2 - x1 + 1) / Math.log(2)),
					k2 = (int) Math.floor(Math.log(y2 - y1 + 1) / Math.log(2));

			int a = Math.max(max[x1][k1][y1][k2], max[x1][k1][y2 - (1 << k2) + 1][k2]), b = Math
					.max(max[x2 - (1 << k1) + 1][k1][y1][k2], max[x2 - (1 << k1) + 1][k1][y2 - (1 << k2) + 1][k2]);

			int c = Math.min(min[x1][k1][y1][k2], min[x1][k1][y2 - (1 << k2) + 1][k2]), d = Math
					.min(min[x2 - (1 << k1) + 1][k1][y1][k2], min[x2 - (1 << k1) + 1][k1][y2 - (1 << k2) + 1][k2]);

			return Math.max(a, b) - Math.min(c, d);
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), b = sc.nextInt(), k = sc.nextInt();
		short[][] a = new short[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				a[i][j] = sc.nextShort();
		SparseTable spT = new SparseTable(n, n, a);
		for (int i = 0; i < k; i++) {
			int x1 = sc.nextInt() - 1, y1 = sc.nextInt() - 1, x2 = x1 + b - 1, y2 = y1 + b - 1;
			out.println(spT.query(x1, y1, x2, y2));
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

		String nextLine() throws IOException {
			return br.readLine();
		}

		short nextShort() throws IOException {
			return Short.parseShort(next());
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
