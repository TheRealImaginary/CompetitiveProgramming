package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				 -We find the Minimum Spanning Tree (Subgraph). 
 */
public class UVA_10397_ConnectTheCampus {

	static class Edge implements Comparable<Edge> {

		int u, v;
		double cost;

		Edge(int a, int b, double c) {
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
			return Double.compare(cost, e.cost);
		}
	}

	static class DisjointSets {
		int[] rep, rank;

		public DisjointSets(int n) {
			rep = new int[n + 2];
			rank = new int[n + 2];
			for (int i = 0; i < n; i++)
				rep[i] = i;
		}

		void Union(int x, int y) {
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

		boolean isSameSet(int x, int y) {
			return findSet(x) == findSet(y);
		}

		int findSet(int x) {
			if (rep[x] == x)
				return x;
			return rep[x] = findSet(rep[x]);
		}
	}

	static Edge[] edgeList;
	static DisjointSets ds;

	static double dist(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.abs((x2 - x1) * (x2 - x1)) + Math.abs((y2 - y1) * (y2 - y1)));
	}

	static double Kruskal() {
		Arrays.sort(edgeList);
		double cost = 0.0;
		for (Edge e : edgeList) {
			if (!ds.isSameSet(e.u, e.v)) {
				ds.Union(e.u, e.v);
				cost += e.cost;
			}
		}
		return cost;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner(new FileInputStream("in.txt"));
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			if (sc.nextEmpty()) // Malformed input (Thanks To Sagheer)
				continue;
			int n = sc.nextInt();
			int[] x = new int[n];
			int[] y = new int[n];
			for (int i = 0; i < n; i++) {
				x[i] = sc.nextInt();
				y[i] = sc.nextInt();
			}
			ds = new DisjointSets(n);
			int m = sc.nextInt();
			for (int i = 0; i < m; i++) {
				int u = sc.nextInt() - 1, v = sc.nextInt() - 1;
				if (!ds.isSameSet(v, u))
					ds.Union(v, u);
			}
			edgeList = new Edge[n * (n - 1) >> 1];
			for (int i = 0, k = 0; i < n; i++)
				for (int j = i + 1; j < n; j++, k++)
					edgeList[k] = new Edge(i, j, dist(x[i], y[i], x[j], y[j]));
			out.println(new DecimalFormat("0.00").format(Kruskal()));
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

		boolean nextEmpty() throws IOException {
			String s = nextLine();
			st = new StringTokenizer(s);
			return s.isEmpty();
		}
	}
}
