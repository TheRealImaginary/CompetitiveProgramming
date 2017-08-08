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
 *				-We find the maximum edge weight in the Minimum Spanning Forest.
 */
public class UVA_1216_TheBugSensorProblem {

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
		int numSets;

		UnionFind(int n) {
			rank = new int[n];
			rep = new int[n];
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

	static long sq(long x) {
		return x * x;
	}

	static long d2(int x1, int y1, int x2, int y2) {
		return sq(x2 - x1) + sq(y2 - y1);
	}

	static double Kruskal(Edge[] edgeList, int V, int K) {
		Arrays.sort(edgeList, (a, b) -> Long.compare(a.cost, b.cost));
		UnionFind uf = new UnionFind(V);
		long res = 0;
		for (Edge e : edgeList) {
			if (uf.numSets == K)
				break;
			if (uf.union(e.u, e.v)) {
				res = Math.max(res, e.cost);
			}
		}
		return Math.ceil(Math.sqrt(res));
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int K = sc.nextInt();
			int V = 0;
			ArrayList<Integer> x = new ArrayList<>();
			ArrayList<Integer> y = new ArrayList<>();
			while (true) {
				int xx = sc.nextInt();
				if (xx == -1)
					break;
				V++;
				int yy = sc.nextInt();
				x.add(xx);
				y.add(yy);
			}
			Edge[] edgeList = new Edge[(V * (V - 1)) >> 1];
			for (int i = 0, k = 0; i < V; i++) {
				int x1 = x.get(i), y1 = y.get(i);
				for (int j = i + 1; j < V; j++, k++)
					edgeList[k] = new Edge(i, j, d2(x1, y1, x.get(j), y.get(j)));
			}
			out.printf("%.0f\n", Kruskal(edgeList, V, K));
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
