package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.StringTokenizer;

public class UVA_10917_WalkThroughTheForest {

	static class Edge implements Comparable<Edge> {
		int node;
		long cost;

		Edge(int a, long c) {
			node = a;
			cost = c;
		}

		@Override
		public String toString() {
			return node + " " + cost;
		}

		@Override
		public int compareTo(Edge e) {
			return Long.compare(cost, e.cost);
		}
	}

	static final long oo = 1 << 57;
	static ArrayList<Edge>[] graph;
	static ArrayList<Edge>[] DAG;
	static long[] dist;
	static int V;
	static Stack<Integer> stack;
	static boolean[] vis;

	static void Dijkstra() {
		dist = new long[V];
		Arrays.fill(dist, oo);
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		pq.add(new Edge(1, 0));
		dist[1] = 0;
		while (!pq.isEmpty()) {
			Edge u = pq.poll();
			if (u.cost > dist[u.node])
				continue;
			for (Edge next : graph[u.node]) {
				long newD = next.cost + u.cost;
				if (newD < dist[next.node]) {
					dist[next.node] = newD;
					pq.add(new Edge(next.node, newD));
				}
			}
		}
	}

	static void topoSort(int u) {
		vis[u] = true;
		for (Edge v : DAG[u]) {
			if (!vis[v.node])
				topoSort(v.node);
		}
		stack.push(u);
	}

	static long count() {
		long[] dp = new long[V];
		dp[0] = 1;
		while (!stack.isEmpty()) {
			int u = stack.pop();
			for (Edge v : DAG[u])
				dp[v.node] += dp[u];
		}
		return dp[1];
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			V = sc.nextInt();
			if (V == 0)
				break;
			int m = sc.nextInt();
			graph = new ArrayList[V];
			for (int i = 0; i < V; i++)
				graph[i] = new ArrayList<>();
			for (int i = 0; i < m; i++) {
				int u = sc.nextInt() - 1, v = sc.nextInt() - 1;
				long cost = sc.nextLong();
				graph[u].add(new Edge(v, cost));
				graph[v].add(new Edge(u, cost));
			}
			Dijkstra();
			DAG = new ArrayList[V];
			for (int i = 0; i < V; i++)
				DAG[i] = new ArrayList<>();
			for (int u = 0; u < V; u++) {
				ArrayList<Edge> newE = new ArrayList<>();
				for (Edge v : graph[u]) {
					if (dist[v.node] < dist[u])
						newE.add(v);
				}
				DAG[u] = newE;
			}
			vis = new boolean[V];
			stack = new Stack<>();
			topoSort(0);
			out.println(count());
		}
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
	}
}
