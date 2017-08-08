package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				 -We find the Minimum Spanning Tree then the maximum edge on the path.
 *				 (minimax). Can also be solved using FloydWarshall
 */
public class UVA_534_Frogger {

	static class Edge implements Comparable<Edge> {
		int node;
		double cost;

		public Edge(int n, double c) {
			node = n;
			cost = c;
		}

		public String toString() {
			return String.format("%d %f", node, cost);
		}

		@Override
		public int compareTo(Edge e) {
			if (cost != e.cost)
				return Double.compare(cost, e.cost);
			return node - e.node;
		}

	}

	static class Triple implements Comparable<Triple> {
		int u, v;
		double cost;

		public Triple(int a, int b, double c) {
			u = a;
			v = b;
			cost = c;
		}

		public String toString() {
			return String.format("%d %d %f", u, v, cost);
		}

		@Override
		public int compareTo(Triple t) {
			if (cost != t.cost)
				return Double.compare(cost, t.cost);
			return v - t.v;
		}

	}

	static class DisjointSets {

		int[] rep, rank;

		public DisjointSets(int n) {
			rep = new int[n];
			rank = new int[n];
			for (int i = 0; i < rep.length; i++)
				rep[i] = i;
			Arrays.fill(rank, 1);
		}

		public void Union(int x, int y) {
			int x1 = findset(x);
			int y1 = findset(y);
			if (rank[x1] > rank[y1])
				rep[y1] = x1;
			else if (rank[x1] < rank[y1])
				rep[x1] = y1;
			else {
				rep[x1] = y1;
				rank[y1]++;
			}
		}

		public int findset(int x) {
			if (x == rep[x])
				return x;
			return rep[x] = findset(rep[x]);
		}
	}

	static ArrayList<Edge> graph[];
	static Triple edgeList[];
	static ArrayList<Edge> Tree[];
	static boolean vis[];
	static final int _oo = (int) -1e9;

	public static double square(double l) {
		return l * l;
	}

	public static double calc(double x, double y, double xx, double yy) {
		return Math.sqrt(square(x - xx) + square(y - yy));
	}

	public static void Kruskal(int V) {

		DisjointSets ds = new DisjointSets(V);

		Arrays.sort(edgeList);

		for (Triple t : edgeList) {

			if (ds.findset(t.u) != ds.findset(t.v)) {
				ds.Union(t.u, t.v);

				Tree[t.u].add(new Edge(t.v, t.cost));
				Tree[t.v].add(new Edge(t.u, t.cost));
			}
		}
	}

	public static double dfs(int u) {

		if (u == 1)
			return 0;
		for (Edge next : Tree[u]) {
			if (!vis[next.node]) {
				vis[next.node] = true;
				double distance = dfs(next.node);
				if (distance > _oo)
					return Math.max(distance, next.cost);
			}
		}

		return _oo;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int s = 1;
		while (true) {

			int n = sc.nextInt();
			if (n == 0)
				break;
			double x[] = new double[n];
			double y[] = new double[n];
			for (int i = 0; i < n; ++i) {
				x[i] = sc.nextInt();
				y[i] = sc.nextInt();
			}

			vis = new boolean[n];
			edgeList = new Triple[(n * (n - 1)) >> 1];
			graph = new ArrayList[n];
			for (int i = 0; i < n; i++)
				graph[i] = new ArrayList<>();
			Tree = new ArrayList[n];
			for (int i = 0; i < n; i++)
				Tree[i] = new ArrayList<>();

			int ii = 0;
			for (int i = 0; i < n; i++) {
				double xx = x[i];
				double yy = y[i];
				for (int j = i + 1; j < n; j++) {
					double dist = calc(xx, yy, x[j], y[j]);
					graph[i].add(new Edge(j, dist));
					graph[j].add(new Edge(i, dist));
					edgeList[ii++] = new Triple(i, j, dist);
				}
			}
			Kruskal(n);
			double ans = 0;
			ans = dfs(0);
			pw.printf("Scenario #%d\n", s++);
			pw.printf("Frog Distance = %s\n", new DecimalFormat("0.000").format(ans));
			pw.println();
			sc.nextLine();
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
	}
}
