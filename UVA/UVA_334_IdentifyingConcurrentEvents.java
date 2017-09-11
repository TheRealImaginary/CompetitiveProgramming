package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class UVA_334_IdentifyingConcurrentEvents {

	static class Pair implements Comparable<Pair> {
		int a, b;

		Pair(int x, int y) {
			a = x;
			b = y;
		}

		@Override
		public String toString() {
			return String.format("(%s,%s)", unmap[a], unmap[b]);
		}

		@Override
		public int compareTo(Pair p) {
			if (a != p.a)
				return a - p.a;
			if (b != p.b)
				return b - p.b;
			return 0;
		}
	}

	static String[] unmap;
	
	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		for (int t = 1; true; t++) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			Map<String, Integer> map = new TreeMap<>();
			unmap = new String[1000];
			int[][] c = new int[n][];
			int size = 0;
			for (int i = 0; i < n; i++) {
				int x = sc.nextInt();
				c[i] = new int[x];
				for (int j = 0; j < x; j++) {
					String s = sc.next();
					map.put(s, size);
					c[i][j] = size;
					unmap[size++] = s;
				}
			}
			int[][] g = new int[size][size];
			for (int i = 0; i < n; i++)
				for (int j = 0; j + 1 < c[i].length; j++)
					g[c[i][j]][c[i][j + 1]] = 1;
			int q = sc.nextInt();
			while (q-- > 0) {
				String a = sc.next(), b = sc.next();
				int u = map.get(a), v = map.get(b);
				g[u][v] = 1;
			}
			for (int k = 0; k < size; k++)
				for (int i = 0; i < size; i++)
					for (int j = 0; j < size; j++)
						g[i][j] |= (g[i][k] & g[k][j]);
			TreeSet<Pair> set = new TreeSet<>();
			for (int u = 0; u < size; u++)
				for (int v = 0; v < size; v++)
					if (u != v && g[u][v] == 0 && g[v][u] == 0)
						set.add(new Pair(u, v));
			if (set.isEmpty())
				out.printf("Case %d, no concurrent events.\n", t);
			else {
				out.printf("Case %d, %d concurrent events:\n", t, set.size() >> 1);
				if (set.size() == 2)
					out.printf("%s \n", set.first());
				else
					out.printf("%s %s \n", set.pollFirst(), set.pollFirst());
			}
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
