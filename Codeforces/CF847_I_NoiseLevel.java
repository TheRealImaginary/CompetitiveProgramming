package Codeforces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

// http://codeforces.com/contest/847/problem/I
/*-
 * @author Mazen Magdy
 *				
 *
 *				-Note: TLE with Built-in Queue.
 */
public class CF847_I_NoiseLevel {

	static final int MAX = 5000;

	static final int[] dx = { 1, 0, -1, 0 };
	static final int[] dy = { 0, 1, 0, -1 };

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), m = sc.nextInt(), Q = sc.nextInt(), P = sc.nextInt();
		char[][] g = new char[n][m];
		for (int i = 0; i < n; i++)
			g[i] = sc.next().toCharArray();
		long[][] noise = new long[n][m];
		for (int row = 0; row < n; row++)
			for (int col = 0; col < m; col++) {
				if (g[row][col] >= 'A' && g[row][col] <= 'Z') {
					boolean[][] vis = new boolean[n][m];
					vis[row][col] = true;
					int[] qx = new int[MAX];
					int[] qy = new int[MAX];
					int[] qnoise = new int[MAX];
					int b = 0, e = 1;
					qx[b] = row;
					qy[b] = col;
					qnoise[b] = (g[row][col] - 'A' + 1) * Q;
					while (b < e) {
						int currentRow = qx[b], currentCol = qy[b], currentNoise = qnoise[b++];
						noise[currentRow][currentCol] += currentNoise;
						if (currentNoise == 1)
							continue;
						for (int i = 0; i < 4; i++) {
							int r = currentRow + dx[i], c = currentCol + dy[i];
							if (r >= 0 && r < n && c >= 0 && c < m && g[r][c] != '*' && !vis[r][c]) {
								vis[r][c] = true;
								qx[e] = r;
								qy[e] = c;
								qnoise[e++] = currentNoise >> 1;
							}
						}
					}
				}
			}
		int ans = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				if (noise[i][j] > P)
					ans++;
		out.println(ans);
		out.flush();
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
	}
}
