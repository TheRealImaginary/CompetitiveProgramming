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
 *				-Since the order of unlocking is not important and because we can "Jump"
 *				 we can model the locks as vertices and connect them with edges weighted
 *				 with the cost needed to change the digits to the corresponding one, then
 *				 we can run Kruskal to find the Minimum Spanning Tree.
 */
public class UVA_1235_AntiBruteForceLock { // Correct

	static final int oo = (int) 1e9;
	static Edge[] edgeList;

	static class Edge implements Comparable<Edge> {
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

		@Override
		public int compareTo(Edge e) {
			return cost - e.cost;
		}
	}

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

	static int Kruskal(int V) {
		Arrays.sort(edgeList);
		DisjointUnionSet ds = new DisjointUnionSet(V + 10);
		int mst = 0;
		for (Edge e : edgeList) {
			if (!ds.isSameSet(e.u, e.v)) {
				ds.Union(e.u, e.v);
				mst += e.cost;
			}
		}
		return mst;
	}

	static int getCost(int x, int y) {
		int res = 0;
		for (int i = 0; i < 4; i++, x /= 10, y /= 10) {
			int c = Math.abs(x % 10 - y % 10);
			res += Math.min(c, 10 - c);
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt();
			int[] a = new int[n];
			for (int i = 0; i < n; i++) {
				a[i] = sc.nextInt();
			}
			edgeList = new Edge[n * (n - 1) >> 1];
			int intial_cost = oo;
			int idx = 0;
			for (int i = 0; i < a.length; i++) {
				intial_cost = Math.min(intial_cost, getCost(0, a[i]));
				for (int j = i + 1; j < a.length; j++)
					edgeList[idx++] = new Edge(i, j, getCost(a[i], a[j]));
			}
			pw.println(intial_cost + Kruskal(n));
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

		public double nextDouble() throws IOException {
			String x = next();
			StringBuilder sb = new StringBuilder("0");
			double res = 0, f = 1;
			boolean dec = false, neg = false;
			int start = 0;
			if (x.charAt(0) == '-') {
				neg = true;
				start++;
			}
			for (int i = start; i < x.length(); i++) {
				if (x.charAt(i) == '.') {
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				} else {
					sb.append(x.charAt(i));
					if (dec)
						f *= 10;
				}
			}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg ? -1 : 1);
		}

		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}
