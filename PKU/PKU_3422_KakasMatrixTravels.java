package PKU;

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
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We build a graph where each node is split into 2 (in and out)
 *				 we connect in and out nodes with two edges, capacity k and cost 0
 *				 and capacity 1 and cost -a[i][j]. The first indicating that we can move into this node
 *				 k times and the second indicating that we can move into this node 1 time with cost -a[i][j].
 *				 The MCMF of this problem will be the answer multiplied by -1.
 */
// TLE with BellmanFord
public class PKU_3422_KakasMatrixTravels {

	static class Edge implements Comparable<Edge> {
		int node, cap, cost;
		Edge rev;

		Edge(int a, int c) {
			node = a;
			cost = c;
		}

		Edge(int a, int cap, int cost) {
			node = a;
			this.cap = cap;
			this.cost = cost;
		}

		@Override
		public String toString() {
			return node + " " + cap + " " + cost;
		}

		@Override
		public int compareTo(Edge e) {
			return cost - e.cost;
		}
	}

	static final int oo = 1 << 28;
	static final int[] dx = { 1, 0 };
	static final int[] dy = { 0, 1 };

	static int N, V, s, t;
	static ArrayList<Edge>[] g;
	static int[] p, pCost;
	static Edge[] pEdge;

	static int convert(int i, int j) {
		return i * N + j;
	}

	static boolean isValid(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}

	static int Vin(int u) {
		return u << 1;
	}

	static int Vout(int u) {
		return Vin(u) | 1;
	}

	static void addEdge(int u, int v, int cap, int cost) {
		Edge e1 = new Edge(v, cap, cost), e2 = new Edge(u, 0, -cost);
		e1.rev = e2;
		e2.rev = e1;
		g[u].add(e1);
		g[v].add(e2);
	}

	static int MCMF(int maxFlow) {
		int mf = 0, cost = 0;
		pCost = new int[V];
		pCost[s] = 0;
		while (mf < maxFlow) {
			p = new int[V];
			Arrays.fill(p, -1);
			p[s] = s;
			int[] dist = new int[V];
			Arrays.fill(dist, oo);
			dist[s] = 0;
			pEdge = new Edge[V];
			PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
			pq.add(new Edge(s, 0));
			while (!pq.isEmpty()) {
				Edge e = pq.poll();
				int u = e.node;
				if (e.cost > dist[u])
					continue;
				for (Edge next : g[u]) {
					// We need "pCost[u] - pCost[next.node]" to avoid cycle
					if (next.cap > 0 && dist[next.node] > e.cost + next.cost + pCost[u] - pCost[next.node]) {
						dist[next.node] = e.cost + next.cost + pCost[u] - pCost[next.node];
						p[next.node] = u;
						pEdge[next.node] = next;
						pq.add(new Edge(next.node, dist[next.node]));
					}
				}
			}
			if (p[t] == -1)
				break;

			for (int node = 0; node < V; node++)
				pCost[node] += dist[node];

			int flow = augment(t, oo);
			mf += flow;
			cost += (flow * pCost[t]);

			// System.err.println(flow + " " + pCost[t]);
		}
		return cost;
	}

	static int augment(int v, int flow) {
		if (v == s)
			return flow;

		Edge e = pEdge[v];
		flow = augment(p[v], Math.min(flow, e.cap));
		// System.err.printf("From %d %d (%d, %d) %d\n", p[v], v, (v >> 1) / N,
		// (v >> 1) % N, e.cap);

		e.cap -= flow;
		e.rev.cap += flow;

		return flow;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		N = sc.nextInt();
		int K = sc.nextInt();
		int[][] a = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				a[i][j] = sc.nextInt();
		V = ((N * N) << 1) + 2;
		s = V - 2;
		t = Vout(convert(N - 1, N - 1));
		g = new ArrayList[V];
		for (int i = 0; i < V; i++)
			g[i] = new ArrayList<Edge>();
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				int x = convert(i, j);
				addEdge(Vin(x), Vout(x), 1, -a[i][j]);
				addEdge(Vin(x), Vout(x), K, 0);
				for (int k = 0; k < 2; k++) {
					int r = i + dx[k], c = j + dy[k];
					int y = convert(r, c);
					if (isValid(r, c))
						addEdge(Vout(x), Vin(y), K, 0);
				}
			}
		addEdge(s, 0, K, 0);
		// addEdge(Vout(convert(N - 1, N - 1)), t, K, 0);
		out.println(-MCMF(K));
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
