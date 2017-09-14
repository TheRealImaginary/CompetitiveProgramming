package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-Can also be solved using APSP.
 */
public class UVA_439_KnightMoves {

	static class Triple {
		int row, col, res;

		Triple(int r, int c, int a) {
			row = r;
			col = c;
			res = a;
		}

		public String toString() {
			return row + " " + col + " " + res;
		}
	}

	static final int[] dx = { 2, 2, -2, -2, 1, -1, 1, -1 };
	static final int[] dy = { 1, -1, 1, -1, 2, 2, -2, -2 };
	static final int MAXN = 8;
	static boolean[][] vis;

	static final boolean isValid(int r, int c) {
		return r >= 0 && r < MAXN && c >= 0 && c < MAXN;
	}

	static int bfs(int sr, int sc, int er, int ec) {
		Queue<Triple> q = new LinkedList<>();
		q.add(new Triple(sr, sc, 0));
		vis[sr][sc] = true;
		while (!q.isEmpty()) {
			Triple u = q.poll();
			int row = u.row, col = u.col, res = u.res;
			if (row == er && col == ec)
				return u.res;
			for (int i = 0; i < 8; i++) {
				int newr = row + dx[i];
				int newc = col + dy[i];
				if (isValid(newr, newc) && !vis[newr][newc]) {
					q.add(new Triple(newr, newc, res + 1));
					vis[newr][newc] = true;
				}
			}
		}
		return -1;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner(new FileInputStream("in.txt"));
		PrintWriter pw = new PrintWriter(System.out);
		int[] AlphaToNum = { 0, 1, 2, 3, 4, 5, 6, 7 };
		while (sc.ready()) {
			String from = sc.next(), to = sc.next();
			int srow = AlphaToNum[from.charAt(0) - 'a'], scol = 8 - (from.charAt(1) - '0');
			int erow = AlphaToNum[to.charAt(0) - 'a'], ecol = 8 - (to.charAt(1) - '0');
			vis = new boolean[MAXN][MAXN];
			pw.printf("To get from %s to %s takes %d knight moves.\n", from, to, bfs(srow, scol, erow, ecol));
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

		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}
