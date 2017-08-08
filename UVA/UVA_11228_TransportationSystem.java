package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 * 				-We find states and connect them then find the Minimum Spanning Tree this will be
 * 				 the cost of roads, We do the same for finding rails. We can notice that if we find
 * 				 the MST for roads for each state, we will have a DisjointSet where each city is connected
 * 				 to another in the same state so we can run another MST with the same Set but on the graph we each
 * 				 city is connected to ones that exceed distance "r", which would lead to a neat and small solution.
 */
public class UVA_11228_TransportationSystem {

	static final long oo = 1L << 57;

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
			if (rank[x] >= rank[y]) {
				rep[y] = x;
				if (rank[x] == rank[y])
					rank[x]++;
			} else
				rep[x] = y;
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

	static long sq(long x) {
		return x * x;
	}

	static long d2(int x1, int y1, int x2, int y2) {
		return sq(x2 - x1) + sq(y2 - y1);
	}

	static double Kruskal(ArrayList<Edge> edgeList, int V) {
		Collections.sort(edgeList, (a, b) -> Long.compare(a.cost, b.cost));
		UnionFind uf = new UnionFind(V);
		double mst = 0;
		for (Edge e : edgeList)
			if (uf.union(e.u, e.v))
				mst += Math.sqrt(e.cost);
		return mst;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			int V = sc.nextInt();
			long r = sc.nextLong();
			r = sq(r);
			int[] x = new int[V];
			int[] y = new int[V];
			for (int i = 0; i < V; i++) {
				x[i] = sc.nextInt();
				y[i] = sc.nextInt();
			}
			UnionFind uf = new UnionFind(V);
			for (int i = 0; i < V; i++) {
				int x1 = x[i], y1 = y[i];
				for (int j = i + 1; j < V; j++) {
					if (uf.sameSet(i, j))
						continue;
					long cost = d2(x1, y1, x[j], y[j]);
					if (cost <= r)
						uf.union(i, j);
				}
			}
			ArrayList<Integer>[] state = new ArrayList[V];
			for (int i = 0; i < V; i++)
				state[i] = new ArrayList<>();
			for (int i = 0; i < V; i++)
				state[uf.findSet(i)].add(i);
			int numStates = uf.numSets;
			double roads = 0;
			ArrayList<Edge> edgeList = new ArrayList<>();
			for (int i = 0; i < V; i++) {
				ArrayList<Integer> a = state[i];
				if (a.isEmpty())
					continue;
				ArrayList<Edge> stateEdgeList = new ArrayList<>();
				for (int k = 0; k < a.size(); k++) {
					int kk = a.get(k);
					for (int l = k + 1; l < a.size(); l++)
						stateEdgeList.add(new Edge(kk, a.get(l), d2(x[kk], y[kk], x[a.get(l)], y[a.get(l)])));
				}
				roads += Kruskal(stateEdgeList, V);
				for (int j = i + 1; j < V; j++) {
					ArrayList<Integer> b = state[j];
					if (b.isEmpty())
						continue;
					long cost = oo;
					for (int k : a)
						for (int l : b) {
							cost = Math.min(cost, d2(x[k], y[k], x[l], y[l]));
						}
					edgeList.add(new Edge(i, j, cost));
				}
			}
			double rails = Kruskal(edgeList, V);
			out.printf("Case #%d: %d %.0f %.0f\n", t, numStates, roads, rails);
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
