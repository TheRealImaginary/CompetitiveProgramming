package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @author Mazen Magdy
 *				-We find the Minimum Spanning Tree, if it has less than V - 1 edges
 *				 where V is the number of vertices, then it is impossible, otherwise
 *				 we print the cost.
 */
public class UVA_11710_ExpensiveSubway {

	static class Edge {
		int u, v, cost;

		Edge(int a, int b, int c) {
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

		UnionFind(int n) {
			rep = new int[n];
			rank = new int[n];
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

	static int[] Kruskal(Edge[] edgeList, int N) {
		Arrays.sort(edgeList, (a, b) -> a.cost - b.cost);
		int V = 0, mst = 0;
		UnionFind uf = new UnionFind(N);
		for (Edge e : edgeList) {
			if (uf.union(e.u, e.v)) {
				V++;
				mst += e.cost;
			}
		}
		return new int[] { V, mst };
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int V = sc.nextInt(), E = sc.nextInt();
			if (V == 0 && E == 0)
				break;
			Map<String, Integer> map = new TreeMap<>();
			int id = 0;
			for (int i = 0; i < V; i++)
				map.put(sc.next(), id++);
			Edge[] edgeList = new Edge[E];
			for (int i = 0; i < E; i++)
				edgeList[i] = new Edge(map.get(sc.next()), map.get(sc.next()), sc.nextInt());
			sc.next();
			int[] a = Kruskal(edgeList, V);
			if (a[0] != V - 1) {
				out.println("Impossible");
			} else {
				out.println(a[1]);
			}
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
