package SPOJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class FOXLINGS {

	static class Query {
		int a, b;

		Query(int x, int y) {
			a = x;
			b = y;
		}

		@Override
		public String toString() {
			return a + " " + b;
		}
	}

	static class UnionFind {
		int[] rep, rank;
		int numSets;

		UnionFind(int n) {
			rep = new int[n];
			rank = new int[n];
			for (int i = 0; i < n; i++)
				rep[i] = i;
			numSets = n;
		}

		void union(int x, int y) {
			if (sameSet(x, y))
				return;
			numSets--;
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

		boolean sameSet(int x, int y) {
			return findSet(x) == findSet(y);
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), m = sc.nextInt();
		ArrayList<Integer> a = new ArrayList<>();
		Query[] q = new Query[m];
		for (int i = 0; i < m; i++) {
			int x = sc.nextInt() - 1, y = sc.nextInt() - 1;
			a.add(x);
			a.add(y);
			q[i] = new Query(x, y);
		}
		Collections.sort(a);
		TreeMap<Integer, Integer> map = new TreeMap<>();
		int id = 0;
		for (int i = 0; i < a.size(); i++)
			if (!map.containsKey(a.get(i)))
				map.put(a.get(i), id++);
		// System.err.println(map);
		UnionFind uf = new UnionFind(Math.min(n, map.size()));
		for (int i = 0; i < m; i++) {
			Query query = q[i];
			uf.union(map.get(query.a), map.get(query.b));
		}
		out.println(uf.numSets + Math.max(0, n - map.size()));
		out.flush();
		out.close();
	}

	static class MyScanner {
		StringTokenizer st;
		BufferedReader br;

		public MyScanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}

		public MyScanner(String filename) throws IOException {
			br = new BufferedReader(new FileReader(new File(filename)));
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
