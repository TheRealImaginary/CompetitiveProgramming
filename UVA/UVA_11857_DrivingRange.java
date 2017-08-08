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
 *				-We find the maximum weight of the edge included in the Minimum Spanning Tree.
 */
public class UVA_11857_DrivingRange { // Correct

	static class DisjointUnionSet {

		int[] rep, rank, setSize;

		public DisjointUnionSet(int n) {
			rep = new int[n];
			rank = new int[n];
			setSize = new int[n];
			for (int i = 0; i < rep.length; i++) {
				rep[i] = i;
				setSize[i] = 1;
			}
			Arrays.fill(rank, 1);
		}

		public void Union(int x, int y) {
			int x1 = findset(x);
			int y1 = findset(y);
			if (x1 == y1)
				return;
			if (rank[x1] > rank[y1]) {
				rep[y1] = x1;
				setSize[x1] += setSize[y1];
			} else {
				rep[x1] = y1;
				setSize[y1] += setSize[x1];
				if (rank[x1] == rank[y1])
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

	static class Edge implements Comparable<Edge> {
		int node, cost;

		Edge(int n, int c) {
			node = n;
			cost = c;
		}

		public String toString() {
			return node + " " + cost;
		}

		@Override
		public int compareTo(Edge e) {
			return cost - e.cost;
		}
	}

	static class Triple implements Comparable<Triple> {
		int u, v, c;

		Triple(int uu, int vv, int cc) {
			u = uu;
			v = vv;
			c = cc;
		}

		public String toString() {
			return u + " " + v + " " + c;
		}

		@Override
		public int compareTo(Triple t) {
			return c - t.c;
		}
	}

	static Triple[] edgeList;
	static ArrayList<Edge>[] graph;
	static ArrayList<Edge> treeEdge;
	static int edges;

	static void Kruskal(int u) {
		DisjointUnionSet ds = new DisjointUnionSet(graph.length);

		Arrays.sort(edgeList);

		for (Triple t : edgeList) {
			if (!ds.isSameSet(t.u, t.v)) {
				ds.Union(t.u, t.v);
				treeEdge.add(new Edge(t.v, t.c));
				treeEdge.add(new Edge(t.u, t.c));
				edges++;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt(), m = sc.nextInt();
			if (n == 0 && m == 0)
				break;
			if (m == 0) {
				pw.println("IMPOSSIBLE");
				continue;
			}
			graph = new ArrayList[n];
			edgeList = new Triple[m];
			for (int i = 0; i < n; i++)
				graph[i] = new ArrayList<>();
			int i = 0;
			while (m-- > 0) {
				int u = sc.nextInt(), v = sc.nextInt(), c = sc.nextInt();
				graph[u].add(new Edge(v, c));
				graph[v].add(new Edge(u, c));
				edgeList[i++] = new Triple(u, v, c);
			}
			treeEdge = new ArrayList<>();
			edges = 0;
			Kruskal(0);
			int ans = -1;
			if (edges != n - 1) {
				pw.println("IMPOSSIBLE");
				continue;
			}
			for (Edge e : treeEdge)
				ans = Math.max(ans, e.cost);
			pw.println(ans);
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
