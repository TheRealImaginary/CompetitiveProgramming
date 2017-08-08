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
 *				-We find the Minimum Spanning Tree and print the edges.
 */
public class UVA_1208_Oreon {

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

	static ArrayList<String> Kruskal(ArrayList<Edge> edgeList, int V) {
		Collections.sort(edgeList, (a, b) -> Long.compare(a.cost, b.cost));
		UnionFind uf = new UnionFind(V);
		ArrayList<String> res = new ArrayList<>();
		for (Edge e : edgeList)
			if (uf.union(e.u, e.v))
				res.add(String.format("%c-%c %d", e.u + 'A', e.v + 'A', e.cost));
		return res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			int n = sc.nextInt();
			ArrayList<Edge> edgeList = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				sc.st = new StringTokenizer(sc.nextLine(), ", ");
				for (int j = 0; j < n && sc.st.hasMoreTokens(); j++) {
					int cost = sc.nextInt();
					if (cost != 0)
						edgeList.add(new Edge(i, j, cost));
				}
			}
			ArrayList<String> sol = Kruskal(edgeList, n);
			out.printf("Case %d:\n", t);
			for (String s : sol)
				out.println(s);
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
