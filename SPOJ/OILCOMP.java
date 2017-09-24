package SPOJ;

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
 * 
 * @see UVA - 10349 - Antenna Placement 
 */
public class OILCOMP {

	static final int oo = 1 << 28;
	static final int[] dx = { 1, 0, -1, 0 };
	static final int[] dy = { 0, 1, 0, -1 };

	static int V, R, C, s, t;
	static ArrayList<Integer>[] g;
	static int[][] res;
	static int[] p;

	static boolean isValid(int r, int c) {
		return r >= 0 && r < R && c >= 0 && c < C;
	}

	static void addEdge(int u, int v, int cap) {
		g[u].add(v);
		g[v].add(u);
		res[u][v] = cap;
	}

	static int edmondKarp() {
		int mf = 0;
		while (true) {
			p = new int[V];
			Arrays.fill(p, -1);
			p[s] = s;
			Queue<Integer> q = new LinkedList<>();
			q.add(s);
			while (!q.isEmpty()) {
				int u = q.poll();
				for (int v : g[u])
					if (res[u][v] > 0 && p[v] == -1) {
						p[v] = u;
						q.add(v);
					}
			}
			if (p[t] == -1)
				break;
			mf += augment(t, oo);
		}
		return mf;
	}

	static int augment(int v, int flow) {
		if (v == s)
			return flow;

		flow = augment(p[v], Math.min(flow, res[p[v]][v]));

		res[p[v]][v] -= flow;
		res[v][p[v]] += flow;

		return flow;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int TC = sc.nextInt();
		for (int tc = 1; tc <= TC; tc++) {
			R = sc.nextInt();
			C = sc.nextInt();
			R ^= C;
			C ^= R;
			R ^= C;
			// System.err.println(R + " " + C);
			int[][] grid = new int[R][C];
			int[][] map = new int[R][C];
			int set1 = 0, set2 = 0, total = 0;
			for (int i = 0; i < R; i++)
				for (int j = 0; j < C; j++) {
					int x = sc.nextInt();
					grid[i][j] = x;
					if (((i + j) & 1) == 0)
						map[i][j] = set1++;
					else
						map[i][j] = set2++;
					total += x;
				}
			V = set1 + set2 + 2;
			s = set1 + set2;
			t = set1 + set2 + 1;
			g = new ArrayList[V];
			for (int i = 0; i < V; i++)
				g[i] = new ArrayList<>();
			res = new int[V][V];
			for (int i = 0; i < R; i++)
				for (int j = 0; j < C; j++) {
					if (((i + j) & 1) != 0)
						continue;
					addEdge(s, map[i][j], grid[i][j]);
					for (int k = 0; k < 4; k++) {
						int row = i + dx[k], col = j + dy[k];
						if (isValid(row, col) && ((row + col) & 1) != 0)
							addEdge(map[i][j], set1 + map[row][col], oo);
					}
				}
			for (int i = 0; i < R; i++)
				for (int j = 0; j < C; j++)
					if (((i + j) & 1) != 0)
						addEdge(set1 + map[i][j], t, grid[i][j]);
			// for (int i = 0; i < R; i++)
			// System.err.println(Arrays.toString(map[i]));
			// System.err.println(Arrays.toString(g));
			out.printf("Case %d: %d\n", tc, total - edmondKarp());
		}
		out.flush();
		out.close();
	}

	static class Scanner {
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream s) {
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
	}
}
