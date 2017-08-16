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
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We use Dijkstra to calculate the minimum cost and keep track of the path taken
 *				 to print it.
 */
public class UVA_523_MinimumTransportCost {

	static class Edge {
		int node;
		long cost;

		Edge(int v, long c) {
			node = v;
			cost = c;
		}

		@Override
		public String toString() {
			return node + " " + cost;
		}
	}

	static final long oo = 1L << 31;
	static final String TO = "-->";

	static int V;
	static ArrayList<Edge>[] g;
	static long[] b;
	static int[] p;

	static long dijkstra(int s, int t) {
		p = new int[V];
		long[] dist = new long[V];
		Arrays.fill(dist, oo);
		Arrays.fill(p, -1);
		dist[s] = 0;
		PriorityQueue<Edge> pq = new PriorityQueue<>((x, y) -> Long.compare(x.cost, y.cost));
		pq.add(new Edge(s, 0));
		while (!pq.isEmpty()) {
			Edge u = pq.poll();
			if (u.cost > dist[u.node])
				continue;
			for (Edge next : g[u.node]) {
				long extra = next.node != s && next.node != t ? b[next.node] : 0;
				if (next.cost + u.cost + extra < dist[next.node]) {
					pq.add(new Edge(next.node, dist[next.node] = next.cost + u.cost + extra));
					p[next.node] = u.node;
				}
			}
		}
		return dist[t];
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			String line = sc.nextLine();
			while (line != null && line.isEmpty())
				line = sc.nextLine();
			StringTokenizer st = new StringTokenizer(line);
			V = st.countTokens();
			g = new ArrayList[V];
			for (int i = 0; i < V; i++)
				g[i] = new ArrayList<>();
			for (int u = 0; u < V; u++) {
				for (int v = 0; st.hasMoreTokens(); v++) {
					if (u == v) {
						st.nextToken();
						continue;
					}
					long cost = Long.parseLong(st.nextToken());
					if (cost != -1)
						g[u].add(new Edge(v, cost));
				}
				if (u != V - 1)
					st = new StringTokenizer(sc.nextLine());
			}
			b = new long[V];
			for (int i = 0; i < V; i++)
				b[i] = sc.nextLong();
			boolean f = true;
			while (true) {
				line = sc.nextLine();
				if (line == null || line.isEmpty())
					break;
				if (f)
					f = false;
				else
					out.println();
				st = new StringTokenizer(line);
				int s = Integer.parseInt(st.nextToken()) - 1, t = Integer.parseInt(st.nextToken()) - 1;
				long ans = dijkstra(s, t);
				Stack<Integer> stack = new Stack<>();
				int node = t;
				while (node != -1) {
					stack.push(node + 1);
					node = p[node];
				}
				StringBuilder sb = new StringBuilder();
				while (!stack.isEmpty()) {
					sb.append(stack.pop());
					if (!stack.isEmpty())
						sb.append(TO);
				}
				out.printf("From %d to %d :\n", s + 1, t + 1);
				out.printf("Path: %s\n", sb);
				out.printf("Total cost : %d\n", ans);
			}
			if (tc > 0)
				out.println();
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
