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
 *				-We build a graph where people who might vote yes are connected to a source node
 *				 and people who will say no are connected to a sink node, Moreover each of the friend
 *				 are connected to each other. All edges will have capacity 1. The Minimum Cost to disconnect
 *				 this graph into two disjoint sets is the answer (min-cut). As all nodes connected to the source
 *				 are people voting yes and nodes connected to the sink are people saying no. edges in between them
 *				 are the disagreements.
 */
public class COCONUTS {

	static final int oo = 1 << 28;

	static int V, s, t;
	static ArrayList<Integer>[] g;
	static int[] p;
	static int[][] res;

	static void addEdge(int u, int v, int cap) {
		g[u].add(v);
		g[v].add(u);
		res[u][v] = cap;
	}

	static int edmondKarp() {
		int mf = 0;
		while (true) {
			Queue<Integer> q = new LinkedList<>();
			q.add(s);
			p = new int[V];
			Arrays.fill(p, -1);
			while (!q.isEmpty()) {
				int u = q.poll();
				for (int v : g[u])
					if (res[u][v] > 0 && p[v] == -1) {
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
		while (true) {
			int n = sc.nextInt(), m = sc.nextInt();
			if (n == 0 && m == 0)
				break;
			V = n + 2;
			s = n;
			t = n + 1;
			g = new ArrayList[V];
			for (int i = 0; i < V; i++)
				g[i] = new ArrayList<>();
			res = new int[V][V];
			for (int i = 0; i < n; i++) {
				int x = sc.nextInt();
				if (x == 0)
					addEdge(i, t, 1);
				else
					addEdge(s, i, 1);
			}
			for (int i = 0; i < m; i++) {
				int u = sc.nextInt() - 1, v = sc.nextInt() - 1;
				addEdge(u, v, 1);
				addEdge(v, u, 1);
			}
			out.println(edmondKarp());
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

		MyScanner(String filename) throws IOException {
			br = new BufferedReader(new FileReader(new File(filename)));
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
