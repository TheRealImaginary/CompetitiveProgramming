package PKU;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We build a graph where each vertex is a cow and is connected with +ve value to cows it likes
 *				 and -ve value with cows it hates. If a cycle exists in this graph it means we can keep increasing
 *				 the distance and if the first cow has a distance less than 0 then it's impossible to arrange the cows.
 *				 Otherwise, The Shortest Path to the last cow will be the answer to the problem.
 */
public class PKU_3169_Layout {

	static class Edge {
		int node, cost;

		Edge(int a, int b) {
			node = a;
			cost = b;
		}

		@Override
		public String toString() {
			return node + " " + cost;
		}
	}

	static final int oo = 1 << 28;
	static int V;
	static ArrayList<Edge>[] g;

	static int bellmanFord() {
		int[] dist = new int[V];
		Arrays.fill(dist, oo);
		dist[0] = 0;
		boolean modified = true;

		for (int k = 0; modified && k < V - 1; k++) {
			modified = false;
			for (int u = 0; u < V; u++)
				for (Edge next : g[u])
					if (dist[u] + next.cost < dist[next.node]) {
						dist[next.node] = dist[u] + next.cost;
						modified = true;
					}
		}
		if (dist[0] < 0)
			return -1;
		boolean has_negative_cycles = false;
		for (int u = 0; u < V; u++)
			for (Edge next : g[u]) {
				if (dist[u] + next.cost < dist[next.node])
					has_negative_cycles = true;
			}
		if (has_negative_cycles || dist[V - 1] >= oo)
			return -2;
		return dist[V - 1];
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), ml = sc.nextInt(), md = sc.nextInt();
		g = new ArrayList[n];
		for (int i = 0; i < n; i++)
			g[i] = new ArrayList<Edge>();
		for (int i = 0; i < ml; i++) {
			int u = sc.nextInt() - 1, v = sc.nextInt() - 1, cost = sc.nextInt();
			g[u].add(new Edge(v, cost));
		}
		for (int i = 0; i < md; i++) {
			int u = sc.nextInt() - 1, v = sc.nextInt() - 1, cost = sc.nextInt();
			g[v].add(new Edge(u, -cost));
		}
		V = n;
		out.println(bellmanFord());
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

		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}
