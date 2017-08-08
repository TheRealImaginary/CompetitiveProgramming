package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We find the min edge in the Maximum Spanning Tree.
 */
public class UVA_10842_TrafficFlow {

	static class Edge implements Comparable<Edge> {
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

		@Override
		public int compareTo(Edge e) {
			if (cost != e.cost)
				return Long.compare(cost, e.cost);
			if (v != e.v)
				return v - e.v;
			return u - e.u;
		}
	}

	static class DisjointSets {
		int[] rep, rank;

		public DisjointSets(int n) {
			rep = new int[n];
			for (int i = 0; i < n; i++)
				rep[i] = i;
			rank = new int[n];
		}

		void union(int x, int y) {
			if (isSameSet(x, y))
				return;
			x = findSet(x);
			y = findSet(y);
			if (rank[x] > rank[y])
				rep[y] = x;
			else {
				rep[x] = y;
				if (rank[x] == rank[y])
					rank[y]++;
			}
		}

		int findSet(int x) {
			if (rep[x] == x)
				return x;
			return rep[x] = findSet(rep[x]);
		}

		boolean isSameSet(int x, int y) {
			return findSet(x) == findSet(y);
		}
	}

	static Edge[] edgeList;
	static int V;

	static long Kruskal() {
		Arrays.sort(edgeList);
		DisjointSets ds = new DisjointSets(V);
		long min = Integer.MAX_VALUE;
		for (int i = edgeList.length - 1; i >= 0; i--) {
			Edge e = edgeList[i];
			if (!ds.isSameSet(e.u, e.v)) {
				ds.union(e.u, e.v);
				min = Math.min(min, e.cost);
			}
		}
		return min;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int t = sc.nextInt(), Case = 1;
		while (t-- > 0) {
			V = sc.nextInt();
			int m = sc.nextInt();
			edgeList = new Edge[m];
			for (int i = 0; i < m; i++)
				edgeList[i] = new Edge(sc.nextInt(), sc.nextInt(), sc.nextLong());
			out.printf("Case #%d: %d\n", Case++, Kruskal());
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
