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
 *				-We reverse the costs and during traversing we need to make sure
 *				 cost is lower than 0 (we have energy). Moreover we check if our node
 *				 is in a negative cycle.
 */
public class UVA_10557_XYZZY {

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
	static ArrayList<Edge>[] g;
	static long[] energy;

	static long[] bellmanFord() {
		long[] dist = new long[V];
		Arrays.fill(dist, oo);
		dist[0] = -100;
		for (int k = 0; k < V - 1; k++)
			for (int u = 0; u < V; u++)
				for (Edge next : g[u])
					if (dist[next.node] > dist[u] + next.cost && dist[u] + next.cost < 0)
						dist[next.node] = dist[u] + next.cost;
		return dist;
	}

	static boolean bfs(int node) {
		Queue<Integer> q = new LinkedList<>();
		q.add(node);
		boolean[] vis = new boolean[V];
		vis[node] = true;
		while (!q.isEmpty()) {
			int u = q.poll();
			if (u == V - 1)
				return true;
			for (Edge next : g[u])
				if (!vis[next.node]) {
					vis[next.node] = true;
					q.add(next.node);
				}
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			V = sc.nextInt();
			if (V == -1)
				break;
			ArrayList<Integer>[] to = new ArrayList[V];
			for (int i = 0; i < V; i++)
				to[i] = new ArrayList<>();
			energy = new long[V];
			for (int u = 0; u < V; u++) {
				energy[u] = sc.nextLong();
				int k = sc.nextInt();
				for (int i = 0; i < k; i++)
					to[u].add(sc.nextInt() - 1);
			}
			g = new ArrayList[V];
			for (int i = 0; i < V; i++)
				g[i] = new ArrayList<>();
			for (int u = 0; u < V; u++)
				for (int v : to[u])
					g[u].add(new Edge(v, energy[v] * -1));
			// System.err.println(Arrays.toString(g));
			long[] dist = bellmanFord();
			// System.err.println(Arrays.toString(dist));
			boolean canWithACycle = false;
			for (int u = 0; u < V; u++)
				for (Edge next : g[u])
					if (dist[next.node] > dist[u] + next.cost && dist[u] + next.cost < 0)
						if (bfs(next.node)) {
							canWithACycle = true;
							break;
						}
			// System.err.println(dist[V - 1] + " " + canWithACycle);
			out.println(dist[V - 1] <= 0 || canWithACycle ? "winnable" : "hopeless");
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
