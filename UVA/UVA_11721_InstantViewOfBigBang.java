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
 *				-If we work on the Reverse of the Graph, any node in a negative cycle will remain in a negative
 *				 cycle.
 */
public class UVA_11721_InstantViewOfBigBang {

	static final long oo = 1L << 58;

	static class Edge {
		int u, v;
		long cost;

		Edge(int a, long c) {
			this(-1, a, c);
		}

		Edge(int a, int b, long c) {
			u = a;
			v = b;
			cost = c;
		}

		@Override
		public String toString() {
			return u + " " + v + " " + cost;
		}
	}

	static int N, M;
	static ArrayList<Integer>[] g;
	static boolean[] inCycle;

	static void dfs(int u) {
		inCycle[u] = true;
		for (int v : g[u])
			if (!inCycle[v])
				dfs(v);
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int TC = sc.nextInt();
		for (int tc = 1; tc <= TC; tc++) {
			N = sc.nextInt();
			M = sc.nextInt();
			g = new ArrayList[N];
			for (int i = 0; i < N; i++)
				g[i] = new ArrayList<>();
			Edge[] edgeList = new Edge[M];
			for (int i = 0; i < M; i++) {
				int u = sc.nextInt(), v = sc.nextInt();
				long cost = sc.nextLong();
				g[v].add(u); // Reverse Edge.
				edgeList[i] = new Edge(v, u, cost);
			}
			long[] dist = new long[N];
			Arrays.fill(dist, oo);
			dist[0] = 0;
			for (int k = 0; k < N - 1; k++)
				for (Edge e : edgeList)
					if (dist[e.v] > dist[e.u] + e.cost)
						dist[e.v] = dist[e.u] + e.cost;
			inCycle = new boolean[N];
			boolean cycle = false;
			for (Edge e : edgeList)
				if (dist[e.v] > dist[e.u] + e.cost) {
					dfs(e.v);
					cycle = true;
				}
			out.printf("Case %d:", tc);
			for (int u = 0; u < N; u++)
				if (inCycle[u])
					out.printf(" %d", u);
			out.println(!cycle ? " impossible" : "");
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
