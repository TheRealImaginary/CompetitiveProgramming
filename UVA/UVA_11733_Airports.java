package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author Mazen Magdy
 *				 -We find the Minimum Spanning Tree and check the edge weight with
 *				 the airport cost.
 */
public class UVA_11733_Airports { // TLE

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
		int[] rep;
		int[] rank;
		int numSets;

		UnionFind(int n) {
			rep = new int[n];
			rank = new int[n];
			for (int i = 0; i < n; i++)
				rep[i] = i;
			numSets = n;
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
			numSets--;
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

	static long[] Kruskal(Edge[] edgeList, int V, long cost) {
		Arrays.sort(edgeList, (a, b) -> Long.compare(a.cost, b.cost));
		UnionFind uf = new UnionFind(V);
		long mst = 0;
		for (Edge e : edgeList)
			if (!uf.sameSet(e.u, e.v)) {
				if (e.cost >= cost)
					return new long[] { mst, uf.numSets };
				uf.union(e.u, e.v);
				mst += e.cost;
			}
		return new long[] { mst, uf.numSets };
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			int V = sc.nextInt(), E = sc.nextInt();
			long cost = sc.nextLong();
			Edge[] edgeList = new Edge[E];
			for (int i = 0; i < E; i++)
				edgeList[i] = new Edge(sc.nextInt() - 1, sc.nextInt() - 1, sc.nextLong());
			long[] a = Kruskal(edgeList, V, cost);
			out.printf("Case #%d: %d %d\n", t, a[0] + (a[1] * cost), a[1]);
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

		public boolean ready() throws IOException {
			return br.ready();
		}
	}

}
