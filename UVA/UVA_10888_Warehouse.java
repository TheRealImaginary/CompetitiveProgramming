package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We use BFS to get the Shortest Path from each Box to each Destination. Then we use
 *				 DP (idx, msk) which is the minimum distance to get boxes to a destination given that
 *				 we are at a idx-th box and we are already done with boxes that have their indices set in msk.
 */
public class UVA_10888_Warehouse {

	static class Cell {
		int r, c, dist;

		Cell(int a, int b) {
			r = a;
			c = b;
		}

		Cell(int a, int b, int x) {
			r = a;
			c = b;
			dist = x;
		}

		@Override
		public String toString() {
			return r + " " + c + " " + dist;
		}
	}

	static final int oo = 1 << 28;
	static final int MAX = 15;
	static final int[] dx = { 1, 0, -1, 0 };
	static final int[] dy = { 0, 1, 0, -1 };
	static int R, C;
	static char[][] grid;
	static int[][] dp, dist, map;

	static boolean isValid(int r, int c) {
		return r >= 0 && r < R && c >= 0 && c < C;
	}

	static int[] bfs(int r, int c) {
		Queue<Cell> q = new LinkedList<>();
		q.add(new Cell(r, c, 0));
		boolean[][] vis = new boolean[R][C];
		vis[r][c] = true;
		int[] dist = new int[MAX];
		Arrays.fill(dist, oo);
		while (!q.isEmpty()) {
			Cell u = q.poll();
			if (grid[u.r][u.c] == 'X')
				dist[map[u.r][u.c]] = u.dist;
			for (int i = 0; i < 4; i++) {
				int newr = u.r + dx[i], newc = u.c + dy[i];
				if (isValid(newr, newc) && !vis[newr][newc] && grid[newr][newc] != '#') {
					vis[newr][newc] = true;
					q.add(new Cell(newr, newc, u.dist + 1));
				}
			}
		}
		return dist;
	}

	static int solve(int idx, int msk) {
		if (msk == (1 << MAX) - 1)
			return 0;
		if (dp[idx][msk] != -1)
			return dp[idx][msk];
		int res = oo;
		for (int i = 0; i < MAX; i++)
			if ((msk & (1 << i)) == 0)
				res = Math.min(res, solve(idx + 1, msk | (1 << i)) + dist[idx][i]);
		return dp[idx][msk] = res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			R = sc.nextInt();
			C = sc.nextInt();
			ArrayList<Cell> sources = new ArrayList<>();
			grid = new char[R][C];
			map = new int[R][C];
			int idx = 0;
			for (int i = 0; i < R; i++) {
				grid[i] = sc.next().toCharArray();
				for (int j = 0; j < C; j++)
					if (grid[i][j] == 'B')
						sources.add(new Cell(i, j));
					else if (grid[i][j] == 'X')
						map[i][j] = idx++;
			}
			dist = new int[MAX][MAX];
			for (int i = 0; i < sources.size(); i++) {
				Cell s = sources.get(i);
				dist[i] = bfs(s.r, s.c);
			}
			dp = new int[MAX][1 << MAX];
			for (int i = 0; i < MAX; i++)
				Arrays.fill(dp[i], -1);
			out.println(solve(0, 0));
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
