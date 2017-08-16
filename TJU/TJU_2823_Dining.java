package TJU;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We need to "match" the maximum amount of cows to food and drinks. So we can build a graph where
 *				 each drink, food, cow is a vertex. Food is connected to a cow with capacity 1, and a cow is connected to drink
 *				 with capacity 1. Moreover a cow has vertex capacity 1. The Maximum Flow in this graph is the answer.
 */
public class TJU_2823_Dining {

	static final int oo = 1 << 27;

	static int V, s, t;
	static ArrayList<Integer>[] g;
	static int[][] res;
	static int[] p;

	static void addEdge(int u, int v, int cap) {
		g[u].add(v);
		g[v].add(u);
		res[u][v] = cap;
	}

	static int edmondKarp() {
		int mf = 0;
		p = new int[V];
		while (true) {
			Queue<Integer> q = new LinkedList<Integer>();
			q.add(s);
			Arrays.fill(p, -1);
			p[s] = s;
			while (!q.isEmpty()) {
				int u = q.poll();
				// System.err.println(u);
				if (u == t)
					break;
				for (int v : g[u]) {
					// System.err.println(u + " " + v + " " + res[u][v]);
					if (p[v] == -1 && res[u][v] > 0) {
						p[v] = u;
						q.add(v);
					}
				}
			}
			if (p[t] == -1)
				break;

			mf += augment(t, oo);
		}
		return mf;
	}

	static int augment(int v, int flow) {
		if (v == s)
			return flow;
		flow = augment(p[v], Math.min(flow, res[p[v]][v]));

		res[p[v]][v] -= flow;
		res[v][p[v]] += flow;

		return flow;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int N = sc.nextInt(), F = sc.nextInt(), D = sc.nextInt();
		V = (N << 1) + F + D + 2;
		s = V - 2;
		t = V - 1;
		g = new ArrayList[V];
		for (int i = 0; i < V; i++)
			g[i] = new ArrayList<Integer>();
		res = new int[V][V];
		for (int cow = 0; cow < N; cow++) {
			int f = sc.nextInt();
			int d = sc.nextInt();
			for (int j = 0; j < f; j++) {
				int v = sc.nextInt() - 1;
				addEdge(v, F + cow, 1);
			}

			addEdge(F + cow, F + N + cow, 1);

			for (int j = 0; j < d; j++) {
				int v = sc.nextInt() - 1;
				addEdge(F + N + cow, v + F + (N << 1), 1);
			}
		}
		for (int i = 0; i < F; i++)
			addEdge(s, i, 1);
		for (int i = F + (N << 1); i < F + (N << 1) + D; i++)
			addEdge(i, t, 1);
		// System.err.println(Arrays.toString(g));
		out.println(edmondKarp());
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
