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
 *				-We find the Second Best Minimum Spanning Tree.
 */
public class UVA_10462_IsThereASecondWayLeft { // Correct

	static class DisjointSets {

		int[] rep, rank;

		public DisjointSets(int n) {
			rep = new int[n];
			rank = new int[n];
			for (int i = 0; i < rep.length; i++)
				rep[i] = i;
			Arrays.fill(rank, 1);
		}

		public void Union(int x, int y) {
			int x1 = findset(x);
			int y1 = findset(y);
			if (rank[x1] > rank[y1])
				rep[y1] = x1;
			else if (rank[x1] < rank[y1])
				rep[x1] = y1;
			else {
				rep[x1] = y1;
				rank[y1]++;
			}
		}

		public int findset(int x) {
			if (x == rep[x])
				return x;
			return rep[x] = findset(rep[x]);
		}

		public boolean isSameSet(int x, int y) {
			return findset(x) == findset(y);
		}

	}

	static class Edge {
		int node, cost;

		Edge(int n, int c) {
			node = n;
			cost = c;
		}

	}

	static class Triple implements Comparable<Triple> {

		int u, v, cost, id;

		Triple(int a, int b, int c, int d) {
			u = a;
			v = b;
			cost = c;
			id = d;
		}

		@Override
		public int compareTo(Triple t) {
			if (cost != t.cost)
				return cost - t.cost;
			else if (v != t.v)
				return v - t.v;
			return u - t.u;
		}

		// added Id in case there are two edges with same attributes
		public boolean equals(Object o) {
			Triple t = (Triple) o;

			return u == t.u && v == t.v && cost == t.cost && id == t.id;
		}
	}

	static ArrayList<Edge> graph[];
	static Triple[] edgeList;
	static int nodes;
	static boolean visited[];
	static ArrayList<Triple> Tree;
	static final int oo = (int) 1e9;

	public static void dfs(int u) {

		visited[u] = true;

		for (Edge v : graph[u]) {
			if (!visited[v.node])
				dfs(v.node);
		}
	}

	public static int Kruskal(int V, boolean f, Triple edge) {

		int mst = 0;
		DisjointSets ds = new DisjointSets(V);

		for (Triple t : edgeList) {

			if (edge != null) {
				if (edge.equals(t))
					continue;
			}

			if (!ds.isSameSet(t.u, t.v)) {
				ds.Union(t.u, t.v);
				nodes++;
				mst += t.cost;
				if (f)
					Tree.add(t);
			}
		}

		return mst;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int t = sc.nextInt();
		int Case = 1;
		while (t-- > 0) {
			int n = sc.nextInt();
			int e = sc.nextInt();

			visited = new boolean[n];
			graph = new ArrayList[n];
			for (int i = 0; i < n; i++)
				graph[i] = new ArrayList<>();
			edgeList = new Triple[e];
			int i = 0;
			while (e-- > 0) {
				int u = sc.nextInt() - 1;
				int v = sc.nextInt() - 1;
				int cost = sc.nextInt();
				graph[u].add(new Edge(v, cost));
				graph[v].add(new Edge(u, cost));
				edgeList[i++] = new Triple(u, v, cost, i);
			}

			int cc = 0;
			for (int j = 0; j < n; j++) {
				if (!visited[j]) {
					dfs(j);
					cc++;
				}
			}
			if (cc > 1)
				pw.printf("Case #%d : No way\n", Case++);
			else {
				Arrays.sort(edgeList);
				Tree = new ArrayList<>();
				int mst = Kruskal(n, true, null);
				int smst = oo;

				for (Triple tr : Tree) {
					nodes = 0;
					int x = Kruskal(n, false, tr);
					if (nodes == n - 1)
						smst = Math.min(smst, x);
				}
				if (smst != oo)
					pw.printf("Case #%d : %d\n", Case++, smst);
				else
					pw.printf("Case #%d : No second way\n", Case++);
			}
		}

		pw.close();
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

		public boolean ready() throws IOException {
			return br.ready();
		}

	}

}
