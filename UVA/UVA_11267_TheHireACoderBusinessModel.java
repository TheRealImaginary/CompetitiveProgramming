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
 * 
 * @author Mazen Magdy
 *				-We find the Minimum Spanning Tree, but we take any edge with cost < 0, Moreover the graph
 *				 has to be bipartite.
 */
public class UVA_11267_TheHireACoderBusinessModel {

	static class Edge {
		int u, v;
		long cost;

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

	static class UnionFind {

		int[] rep, rank;

		UnionFind(int n) {
			rank = new int[n];
			rep = new int[n];
			for (int i = 0; i < n; i++)
				rep[i] = i;
		}

		boolean union(int x, int y) {
			if (sameSet(x, y))
				return false;
			x = findSet(x);
			y = findSet(y);
			if (rank[x] > rank[y])
				rep[y] = x;
			else {
				rep[x] = y;
				if (rank[x] == rank[y])
					rank[y]++;
			}
			return true;
		}

		boolean sameSet(int x, int y) {
			return findSet(x) == findSet(y);
		}

		int findSet(int x) {
			if (rep[x] == x)
				return x;
			return rep[x] = findSet(rep[x]);
		}
	}

	static int[] color;
	static ArrayList<Integer>[] g;

	static boolean dfs(int u) {
		for (int v : g[u]) {
			if (color[v] == -1) {
				color[v] = 1 ^ color[u];
				if (!dfs(v))
					return false;
			} else {
				if (color[u] == color[v])
					return false;
			}
		}
		return true;
	}

	static long Kruskal(Edge[] edgeList, int V) {
		Arrays.sort(edgeList, (a, b) -> Long.compare(a.cost, b.cost));
		UnionFind uf = new UnionFind(V);
		long mst = 0;
		for (Edge e : edgeList)
			if (uf.union(e.u, e.v) || e.cost < 0)
				mst += e.cost;
		return mst;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int V = sc.nextInt();
			if (V == 0)
				break;
			int E = sc.nextInt();
			Edge[] edgeList = new Edge[E];
			g = new ArrayList[V];
			for (int i = 0; i < V; i++)
				g[i] = new ArrayList<>();
			for (int i = 0; i < E; i++) {
				int u = sc.nextInt() - 1, v = sc.nextInt() - 1;
				g[u].add(v);
				g[v].add(u);
				edgeList[i] = new Edge(u, v, sc.nextLong());
			}
			color = new int[V];
			Arrays.fill(color, -1);
			if (!dfs(0))
				out.println("Invalid data, Idiot!");
			else
				out.println(Kruskal(edgeList, V));
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
