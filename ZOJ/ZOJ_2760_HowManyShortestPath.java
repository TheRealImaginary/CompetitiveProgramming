package ZOJ;

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
 *				-We find the shortest path and then we build a shortest path DAG where each 
 *				 node has vertex capacity 1, and max-flow which will be the answer.
 *				 (Number of edge-disjoint paths).
 */
public class ZOJ_2760_HowManyShortestPath {

	static final int oo = 1 << 28;

	static int V, s, t;
	static ArrayList<Integer>[] g;
	static int[][] res;
	static int[] p;

	static void addEdge(int u, int v, int cap) {
		g[u].add(v);
		g[v].add(u);
		res[u][v] = cap;
	}

	static int[][] floyd(int[][] matrix) {
		int[][] adjMatrix = new int[V][V];
		for (int i = 0; i < V; i++)
			adjMatrix[i] = Arrays.copyOf(matrix[i], V);
		for (int k = 0; k < V; k++)
			for (int i = 0; i < V; i++)
				for (int j = 0; j < V; j++)
					adjMatrix[i][j] = Math.min(adjMatrix[i][j], adjMatrix[i][k] + adjMatrix[k][j]);
		return adjMatrix;
	}

	static int edmondKarp() {
		int mf = 0;
		p = new int[V];
		while (true) {
			Queue<Integer> q = new LinkedList<>();
			Arrays.fill(p, -1);
			p[s] = s;
			q.add(s);
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
//		MyScanner sc = new MyScanner(System.in);
		MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			V = sc.nextInt();
			int[][] matrix = new int[V][V];
			for (int i = 0; i < V; i++)
				for (int j = 0; j < V; j++) {
					int x = sc.nextInt();
					matrix[i][j] = x == -1 ? oo : x;
				}
			s = sc.nextInt();
			t = sc.nextInt();
			int[][] adjMatrix = floyd(matrix);
			if (adjMatrix[s][t] == oo)
				out.println(0);
			else {
				g = new ArrayList[V];
				for (int i = 0; i < V; i++)
					g[i] = new ArrayList<Integer>();
				res = new int[V][V];
				for (int u = 0; u < V; u++)
					for (int v = 0; v < V; v++)
						if (u != v)
							if (adjMatrix[s][u] + matrix[u][v] == adjMatrix[s][v])
								addEdge(u, v, 1);
				out.println(edmondKarp());
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

		MyScanner(String filename) throws IOException {
			br = new BufferedReader(new FileReader(new File(filename)));
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
