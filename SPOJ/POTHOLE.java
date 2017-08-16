package SPOJ;

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
 *				-We can use an edge from source or to sink no more than once,
 *				 so we have an edge capacity 1 otherwise a capacity of infinity. The Maximum Flow will be the answer.
 */
public class POTHOLE {

	static final int oo = 1 << 27;

	static int V, s, t;
	static ArrayList<Integer>[] g;
	static int[] res[], p;

	static void addEdge(int u, int v, int cost) {
		g[u].add(v);
		g[v].add(u);
		res[u][v] = cost;
	}

	static int edmondKarp() {
		int mf = 0;
		p = new int[V];
		while (true) {
			Queue<Integer> q = new LinkedList<>();
			q.add(0);
			Arrays.fill(p, -1);
			p[0] = 0;
			while (!q.isEmpty()) {
				int u = q.poll();
				if (u == t)
					break;
				for (int v : g[u])
					if (p[v] == -1 && res[u][v] > 0) {
						p[v] = u;
						q.add(v);
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
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = V = sc.nextInt();
			g = new ArrayList[n];
			for (int i = 0; i < n; i++)
				g[i] = new ArrayList<>();
			res = new int[n][n];
			for (int i = 0; i < n - 1; i++) {
				int k = sc.nextInt();
				for (int j = 0; j < k; j++) {
					int v = sc.nextInt() - 1;
					if (i == 0 || v == n - 1)
						addEdge(i, v, 1);
					else
						addEdge(i, v, oo);
				}
			}
			s = 0;
			t = n - 1;
			int ans = edmondKarp();
			out.println(ans >= oo ? 0 : ans);
		}
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
