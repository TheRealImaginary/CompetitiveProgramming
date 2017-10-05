package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class UVA_10449_Traffic { // RTE

	static final long oo = 1L << 57;

	static class Edge {
		int node;
		long cost;

		Edge(int a, long b) {
			node = a;
			cost = b;
		}

		@Override
		public String toString() {
			return node + " " + cost;
		}
	}

	static int V;
	static long[] busyness;
	static ArrayList<Edge>[] g;

	static long cube(long x) {
		return x * x * x;
	}

	static long getCost(long a, long b) {
		return cube(a - b);
	}

	static long[] bellmanFord() {
		long[] dist = new long[V];
		Arrays.fill(dist, oo + oo);
		dist[0] = 0;
		for (int k = 0; k < V - 1; k++)
			for (int u = 0; u < V; u++)
				for (Edge next : g[u])
					if (dist[next.node] > dist[u] + next.cost)
						dist[next.node] = dist[u] + next.cost;
		return dist;
	}

	static boolean[] bfs(long[] dist) {
		Queue<Integer> q = new LinkedList<>();
		boolean[] vis = new boolean[V];
		boolean[] inCycle = new boolean[V];
		for (int u = 0; u < V; u++)
			for (Edge next : g[u])
				if (dist[next.node] > dist[u] + next.cost) {
					q.add(next.node);
					vis[next.node] = true;
				}
		while (!q.isEmpty()) {
			int u = q.poll();
			inCycle[u] = true;
			for (Edge next : g[u])
				if (!vis[next.node]) {
					vis[next.node] = true;
					q.add(next.node);
				}
		}
		return inCycle;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		for (int t = 1; sc.ready(); t++) {
			if (sc.nextEmpty())
				continue;
			V = sc.nextInt();
			if (V == 0) {
				sc.nextInt();
				sc.nextInt();
				out.printf("Set #%d\n", t);
				continue;
			}
			busyness = new long[V];
			for (int i = 0; i < V; i++)
				busyness[i] = sc.nextInt();
			g = new ArrayList[V];
			for (int i = 0; i < V; i++)
				g[i] = new ArrayList<>();
			int m = sc.nextInt();
			while (m-- > 0) {
				int u = sc.nextInt() - 1, v = sc.nextInt() - 1;
				g[u].add(new Edge(v, getCost(busyness[v], busyness[u])));
			}
			// System.err.println(Arrays.toString(g));
			long[] dist = bellmanFord();
			boolean[] inCycle = bfs(dist);
			// System.err.println(Arrays.toString(dist));
			// System.err.println(Arrays.toString(inCycle));
			out.printf("Set #%d\n", t);
			int q = sc.nextInt();
			while (q-- > 0) {
				int v = sc.nextInt() - 1;
				out.println(inCycle[v] || dist[v] >= oo || dist[v] < 3 ? "?" : dist[v]);
			}
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
		
		public boolean nextEmpty() throws IOException {
			String s = nextLine();
			st = new StringTokenizer(s);
			return s.isEmpty();
		}
	}
}
