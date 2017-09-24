package TJU;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class TJU_1636_GoingHome { // TLE (Check Hungarian (Kuhn-Munkres’s) algorithm)

	static class Coord {
		int x, y;

		Coord(int a, int b) {
			x = a;
			y = b;
		}

		@Override
		public String toString() {
			return x + " " + y;
		}
	}

	static class Edge {
		int node, cap, cost;
		Edge rev;

		Edge(int a, int b, int c) {
			node = a;
			cap = b;
			cost = c;
		}

		@Override
		public String toString() {
			return node + " " + cap + " " + cost;
		}
	}

	static final int oo = 1 << 28;

	static int V, s, t, cost;
	static ArrayList<Edge>[] g;
	static Edge[] P;

	static void addEdge(int u, int v, int cap, int cost) {
		Edge e1 = new Edge(v, cap, cost), e2 = new Edge(u, 0, -cost);
		e1.rev = e2;
		e2.rev = e1;
		g[u].add(e1);
		g[v].add(e2);
	}

	static int getCost(Coord a, Coord b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}

	static int MCMF() {
		int mf = 0;
		while (true) {
			P = new Edge[V];
			int[] dist = new int[V];
			Arrays.fill(dist, oo);
			dist[s] = 0;
			for (int k = 0; k < V - 1; k++)
				for (int u = 0; u < V; u++)
					for (Edge next : g[u])
						if (next.cap > 0 && dist[next.node] > dist[u] + next.cost) {
							dist[next.node] = dist[u] + next.cost;
							P[next.node] = next;
						}
			if (P[t] == null)
				break;
			mf += augment(t, oo);
		}
		return mf;
	}

	static int augment(int v, int flow) {
		if (v == s)
			return flow;

		Edge e = P[v];
		flow = augment(e.rev.node, Math.min(flow, e.cap));

		e.cap -= flow;
		e.rev.cap += flow;

		cost += flow * e.cost;

		return flow;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int N = sc.nextInt(), M = sc.nextInt();
			if (N == 0 && M == 0)
				break;
			ArrayList<Coord> a = new ArrayList<Coord>();
			ArrayList<Coord> b = new ArrayList<Coord>();
			char[][] grid = new char[N][M];
			for (int i = 0; i < N; i++) {
				grid[i] = sc.next().toCharArray();
				for (int j = 0; j < M; j++)
					if (grid[i][j] == 'm')
						a.add(new Coord(i, j));
					else if (grid[i][j] == 'H')
						b.add(new Coord(i, j));
			}
			// System.err.println(a);
			// System.err.println(b);
			int n = a.size(), m = b.size();
			V = n + m + 2;
			s = n + m;
			t = n + m + 1;
			g = new ArrayList[V];
			for (int i = 0; i < V; i++)
				g[i] = new ArrayList<Edge>();
			for (int u = 0; u < n; u++) {
				addEdge(s, u, 1, 0);
				for (int v = 0; v < m; v++)
					addEdge(u, v + n, 1, getCost(a.get(u), b.get(v)));
			}
			for (int v = 0; v < m; v++)
				addEdge(v + n, t, 1, 0);
			// System.err.println(Arrays.toString(g));
			cost = 0;
			MCMF();
			out.println(cost);
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
