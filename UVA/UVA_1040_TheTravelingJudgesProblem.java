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
 * 				-Since input constraints are small we try taking or
 *         		leaving a vertex find the Minimum Spanning Tree of the Graph, and
 *         		minimize the cost.
 */
public class UVA_1040_TheTravelingJudgesProblem {

	static class Edge {
		int u, v;
		long cost;
		boolean vis;

		Edge(int a, long c) {
			u = a;
			cost = c;
			vis = false;
		}

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

	static final long oo = 1L << 57;

	static ArrayList<Integer>[] tree;
	static int[] P;

	static long Kruskal(Edge[] edgeList, int V, int msk, boolean build) {
		long cost = 0;
		UnionFind uf = new UnionFind(V);
		int edges = 0;
		for (Edge e : edgeList)
			if ((msk & (1 << e.u)) != 0 && (msk & (1 << e.v)) != 0 && uf.union(e.u, e.v)) {
				cost += e.cost;
				edges++;
				if (build) {
					tree[e.u].add(e.v);
					tree[e.v].add(e.u);
				}
			}
		return edges == V - 1 ? cost : oo;
	}

	static void dfs(int u, int p) {
		for (int v : tree[u])
			if (v != p) {
				P[v] = u;
				dfs(v, u);
			}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		for (int t = 1; true; t++) {
			int V = sc.nextInt();
			if (V == -1)
				break;
			int T = sc.nextInt() - 1;
			int E = sc.nextInt();
			Edge[] edgeList = new Edge[E];
			for (int i = 0; i < E; i++)
				edgeList[i] = new Edge(sc.nextInt() - 1, sc.nextInt() - 1, sc.nextLong());
			Arrays.sort(edgeList, (a, b) -> Long.compare(a.cost, b.cost));
			int K = sc.nextInt();
			int[] judges = new int[K];
			int need = 1 << T;
			for (int i = 0; i < K; i++)
				need |= (1 << (judges[i] = sc.nextInt() - 1));
			long min = oo;
			int m = 0x0FFFF;
			for (int msk = 0; msk < 1 << V; msk++) {
				if ((msk & need) != need)
					continue;
				long cost = Kruskal(edgeList, V, msk, false);
				if (cost < min || (cost == min && Integer.bitCount(msk) < Integer.bitCount(m))) {
					min = cost;
					m = msk;
				}
			}
			// System.err.println(min + " " + Integer.toBinaryString(m));
			tree = new ArrayList[V];
			for (int i = 0; i < V; i++)
				tree[i] = new ArrayList<>();
			Kruskal(edgeList, V, m, true);
			out.printf("Case %d: distance = %d\n", t, min);
			P = new int[V];
			Arrays.fill(P, -1);
			dfs(T, -1);
			for (int node : judges) {
				int x = node;
				StringBuilder sb = new StringBuilder();
				while (x != -1) {
					if (sb.length() == 0)
						sb.append("   ");
					else
						sb.append("-");
					sb.append(x + 1);
					x = P[x];
				}
				out.println(sb);
			}
			out.println();
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
