package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 * 				-While we are connecting the vertices in Kruskal, if an edge results in a cycle
 * 				 we add it to the answer.
 */
public class UVA_11747_HeavyCycleEdges {

	static class Edge implements Comparable<Edge> {

		int node;
		double cost;

		Edge(int a, double b) {
			node = a;
			cost = b;
		}

		@Override
		public String toString() {
			return node + " " + cost;
		}

		@Override
		public int compareTo(Edge e) {
			if (e.cost != cost)
				return Double.compare(cost, e.cost);
			return node - e.node;
		}
	}

	static class Triple implements Comparable<Triple> {
		int u, v;
		long cost;

		Triple(int a, int b, long c) {
			u = a;
			v = b;
			cost = c;
		}

		@Override
		public String toString() {
			return u + " " + v + " " + cost;
		}

		@Override
		public int compareTo(Triple t) {
			if (cost != t.cost)
				return Long.compare(cost, t.cost);
			if (v != t.v)
				return v - t.v;
			return u - t.u;
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

	static Triple[] edgeList;

	static void Kruskal(PrintWriter pw, int v) {
		Arrays.sort(edgeList);
		DisjointUnionSet ds = new DisjointUnionSet(v);
		int e = edgeList.length;
		ArrayList<Long> ans = new ArrayList<>();
		for (int i = 0; i < e; i++) {
			if (!ds.isSameSet(edgeList[i].u, edgeList[i].v)) {
				ds.Union(edgeList[i].u, edgeList[i].v);
			} else
				ans.add(edgeList[i].cost);
		}
		if (ans.isEmpty())
			pw.print("forest");
		else {
			Collections.sort(ans);
			pw.print(ans.get(0));
			for (int i = 1; i < ans.size(); i++)
				pw.print(" " + ans.get(i));
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		while (true) {
			int v = sc.nextInt(), e = sc.nextInt();
			if (v == 0 && e == 0)
				break;
			edgeList = new Triple[e];
			for (int i = 0; i < e; i++)
				edgeList[i] = new Triple(sc.nextInt(), sc.nextInt(), sc.nextLong());
			Kruskal(pw, v);
			pw.println();
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
			for (int i = start; i < x.length(); i++)
				if (x.charAt(i) == '.') {
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				} else {
					sb.append(x.charAt(i));
					if (dec)
						f *= 10;
				}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg ? -1 : 1);
		}

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}
