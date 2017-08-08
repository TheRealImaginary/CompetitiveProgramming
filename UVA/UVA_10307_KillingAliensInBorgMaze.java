package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We build a graph using BFS starting from the source and then we find the
 *				 Minimum Spanning Tree.
 */
public class UVA_10307_KillingAliensInBorgMaze {

	static class Pair {
		int first, second;

		Pair(int f, int s) {
			first = f;
			second = s;
		}

		@Override
		public String toString() {
			return first + " " + second;
		}
	}

	static class Triple {
		Pair p;
		int cost;

		Triple(int a, int b, int c) {
			p = new Pair(a, b);
			cost = c;
		}

		@Override
		public String toString() {
			return p.toString() + " " + cost;
		}
	}

	static final int oo = (int) 1e9;
	static final int[] dx = { 1, 0, -1, 0 };
	static final int[] dy = { 0, 1, 0, -1 };
	static int R, C;
	static char[][] grid;
	static int[][] map;
	static ArrayList<Pair>[] g;
	static int V;

	static boolean isValid(int r, int c) {
		return r >= 0 && r < R && c >= 0 && c < C;
	}

	static void bfs(int x, int y, int s) {
		Queue<Triple> q = new LinkedList<>();
		q.add(new Triple(x, y, 0));
		boolean[][] vis = new boolean[R][C];
		vis[x][y] = true;
		while (!q.isEmpty()) {
			Triple u = q.poll();
			if (grid[u.p.first][u.p.second] != '#' && grid[u.p.first][u.p.second] != ' ') {
				g[s].add(new Pair(map[u.p.first][u.p.second], u.cost));
				g[map[u.p.first][u.p.second]].add(new Pair(s, u.cost));
			}
			for (int i = 0; i < 4; i++) {
				int newr = dx[i] + u.p.first, newc = u.p.second + dy[i];
				if (isValid(newr, newc) && grid[newr][newc] != '#' && !vis[newr][newc]) {
					q.add(new Triple(newr, newc, u.cost + 1));
					vis[newr][newc] = true;
				}
			}
		}
	}

	static int Prim() {
		PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
			@Override
			public int compare(Pair o1, Pair o2) {
				return o1.second - o2.second;
			}
		});
		pq.add(new Pair(0, 0));
		int mst = 0;
		boolean[] vis = new boolean[V];
		while (!pq.isEmpty()) {
			Pair u = pq.poll();
			if (vis[u.first])
				continue;
			vis[u.first] = true;
			mst += u.second;
			for (Pair v : g[u.first]) {
				if (!vis[v.first])
					pq.add(new Pair(v.first, v.second));
			}
		}
		return mst;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt(), m = sc.nextInt();
			R = m;
			C = n;
			grid = new char[R][C];
			int x = -1, y = -1, sid = -1;
			map = new int[R][C];
			ArrayList<Pair> sources = new ArrayList<>();
			V = 0;
			for (int i = 0; i < R; i++) {
				grid[i] = sc.nextLine().toCharArray();
				for (int j = 0; j < C; j++) {
					if (grid[i][j] == 'S') {
						x = i;
						y = j;
						sid = V;
						map[i][j] = V++;
					} else if (grid[i][j] == 'A') {
						sources.add(new Pair(i, j));
						map[i][j] = V++;
					}
				}
			}
			// System.err.println(sources + " " + sid + " " + x + " " + y);
			g = new ArrayList[V];
			for (int i = 0; i < V; i++)
				g[i] = new ArrayList<>();
			bfs(x, y, sid);
			for (Pair src : sources)
				bfs(src.first, src.second, map[src.first][src.second]);
			// System.err.println(Arrays.toString(g));
			out.println(Prim());
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
