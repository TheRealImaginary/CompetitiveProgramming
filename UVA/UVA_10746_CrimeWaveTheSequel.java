package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-Idea: Weighted Bipartite Matching.
 */
public class UVA_10746_CrimeWaveTheSequel {

	static final int oo = 1 << 28;
	static final double EPS = 1e-9;

	static class Edge {
		int node, cap;
		double cost;
		Edge rev;

		Edge(int a, int capacity, double price) {
			node = a;
			cap = capacity;
			cost = price;
		}

		@Override
		public String toString() {
			return node + " " + cap + " " + cost;
		}
	}

	static int V, s, t;
	static ArrayList<Edge>[] g;
	static double cost;
	static Edge[] P;

	static void addEdge(int u, int v, int cap, double cost) {
		Edge e1 = new Edge(v, cap, cost), e2 = new Edge(u, 0, -cost);
		e1.rev = e2;
		e2.rev = e1;
		g[u].add(e1);
		g[v].add(e2);
	}

	static double MCMF() {
		int mf = 0;
		while (true) {
			P = new Edge[V];
			double[] dist = new double[V];
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
			if (N + M == 0)
				break;
			V = N + M + 2;
			s = N + M;
			t = N + M + 1;
			g = new ArrayList[V];
			for (int i = 0; i < V; i++)
				g[i] = new ArrayList<>();
			for (int u = 0; u < N; u++) {
				addEdge(s, u, 1, 0);
				for (int v = 0; v < M; v++)
					addEdge(u, v + N, 1, sc.nextDouble());
			}
			for (int v = 0; v < M; v++)
				addEdge(v + N, t, 1, 0);
			cost = 0;
			MCMF();
			out.printf("%.2f\n", cost / N);
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

		public double nextDouble() throws IOException {
			String x = next();
			StringBuilder sb = new StringBuilder("0");
			double res = 0, f = 1;
			boolean dec = false, neg = false;
			int start = 0;
			if (x.charAt(0) == '-') {
				neg = true;
				start++;
			}
			for (int i = start; i < x.length(); i++) {
				if (x.charAt(i) == '.') {
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				} else {
					sb.append(x.charAt(i));
					if (dec)
						f *= 10;
				}
			}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg ? -1 : 1);
		}

		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}
