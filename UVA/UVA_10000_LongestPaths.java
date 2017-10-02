package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class UVA_10000_LongestPaths {

	static final int oo = 1 << 28;

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		for (int t = 1; true; t++) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			ArrayList<Integer>[] g = new ArrayList[n + 1];
			for (int i = 1; i <= n; i++)
				g[i] = new ArrayList<>();
			int src = sc.nextInt();
			while (true) {
				int u = sc.nextInt(), v = sc.nextInt(); // one-based
				if (u + v == 0)
					break;
				g[u].add(v);
			}
			int[] dist = new int[n + 1];
			Arrays.fill(dist, oo);
			dist[src] = 0;
			for (int k = 0; k < n - 1; k++)
				for (int u = 1; u <= n; u++)
					for (int v : g[u])
						dist[v] = Math.min(dist[v], dist[u] - 1);
			int node = 1;
			for (int i = 1; i <= n; i++)
				if (dist[node] > dist[i])
					node = i;
			out.printf("Case %d: The longest path from %d has length %d, finishing at %d.\n", t, src, -dist[node],
					node);
			out.println();
		}
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
