package SPOJ;

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

public class SCITIES { // RTE

	static class Edge {
		int node, cap, cost;
		Edge rev;

		Edge(int a, int capacity, int price) {
			node = a;
			cap = capacity;
			cost = price;
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
		Edge e1 = new Edge(v, cap, -cost), e2 = new Edge(u, 0, cost);
		e1.rev = e2;
		e2.rev = e1;
		g[u].add(e1);
		g[v].add(e2);
	}

	static int MCMF() {
		int flow = 0;
		while (true) {
			P = new Edge[V];
			int[] dist = new int[V];
			Arrays.fill(dist, oo);
			dist[s] = 0;
			for (int i = 0; i < V - 1; i++)
				for (int u = 0; u < V; u++) {
					for (Edge next : g[u])
						if (next.cap > 0 && dist[u] + next.cost < dist[next.node]) {
							dist[next.node] = dist[u] + next.cost;
							P[next.node] = next;
						}
				}
			if (P[t] == null)
				break;
			flow += augment(t, oo);
		}
		return flow;
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
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt(), m = sc.nextInt();
			V = n + m + 10;
			g = new ArrayList[V];
			for (int i = 0; i < V; i++)
				g[i] = new ArrayList<>();
			while (true) {
				int u = sc.nextInt(), v = sc.nextInt(), cost = sc.nextInt();
				if (u == 0 && v == 0 && cost == 0)
					break;
				addEdge(u, v + n, 1, cost);
			}
			s = 0;
			t = n + m + 1;
			for (int node = 1; node <= n; node++)
				addEdge(s, node, 1, 0);
			for (int node = n + 1; node <= n + m; node++)
				addEdge(node, t, 1, 0);
			cost = 0;
			MCMF();
			out.println(-cost);
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
