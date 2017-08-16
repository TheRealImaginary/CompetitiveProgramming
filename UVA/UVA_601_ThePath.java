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
 *				-We use DFS (can also use BFS) to check if either has a winning move.
 *				 If no then for each empty cell we check if we can use it to win in one move. If
 *				 still neither can win then no-one can win.
 */
public class UVA_601_ThePath {

	static final int[] dx = { 1, 0, -1, 0 };
	static final int[] dy = { 0, 1, 0, -1 };

	static int N;
	static char[][] g;
	static boolean[][] vis;

	static boolean isValid(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}

	static boolean dfs(int r, int c, char type) {
		vis[r][c] = true;
		if (type == 'W' && c == N - 1)
			return true;
		if (type == 'B' && r == N - 1)
			return true;
		boolean res = false;
		for (int i = 0; i < 4; i++) {
			int x = dx[i] + r, y = dy[i] + c;
			if (isValid(x, y) && !vis[x][y] && g[x][y] == type)
				res |= dfs(x, y, type);
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			N = sc.nextInt();
			if (N == 0)
				break;
			g = new char[N][N];
			for (int i = 0; i < N; i++)
				g[i] = sc.next().toCharArray();
			boolean w = false, b = false;
			vis = new boolean[N][N];
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++) {
					if (g[i][0] == 'W')
						w |= dfs(i, 0, 'W');
					if (g[0][i] == 'B')
						b |= dfs(0, i, 'B');
				}
			if (w) {
				out.println("White has a winning path.");
				continue;
			} else if (b) {
				out.println("Black has a winning path.");
				continue;
			}
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (g[i][j] == 'U') {
						g[i][j] = 'W';
						vis = new boolean[N][N];
						for (int k = 0; k < N; k++)
							if (g[k][0] == 'W')
								w |= dfs(k, 0, 'W');
						vis[i][j] = false;
						g[i][j] = 'B';
						for (int k = 0; k < N; k++)
							if (g[0][k] == 'B')
								b |= dfs(0, k, 'B');
						g[i][j] = 'U';
					}
				}
			}
			if (w)
				out.println("White can win in one move.");
			else if (b)
				out.println("Black can win in one move.");
			else
				out.println("There is no winning path.");
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
