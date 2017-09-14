package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class UVA_1243_PolynomialtimeRedutions {

	static ArrayList<Integer>[] graph;
	static int[][] adjMatrix;
	static int V;
	static int[] dfs_low, dfs_num, SCCSet, SCCSize;
	static Stack<Integer> components;
	static int counter, SCC;

	// transitive closure
	static void floyd(int Size) {
		for (int k = 0; k < Size; k++)
			for (int i = 0; i < Size; i++)
				for (int j = 0; j < Size; j++)
					adjMatrix[i][j] |= (adjMatrix[i][k] & adjMatrix[k][j]);
	}

	public static void tarjanSCC(int u) {
		dfs_num[u] = dfs_low[u] = ++counter;
		components.push(u);

		for (int v : graph[u]) {
			if (dfs_num[v] == 0)
				tarjanSCC(v);
			if (SCCSet[v] == -1)
				dfs_low[u] = Math.min(dfs_low[u], dfs_low[v]);
		}

		if (dfs_num[u] == dfs_low[u]) {
			int size = 0;
			while (true) {
				size++;
				int v = components.pop();
				SCCSet[v] = SCC;
				if (v == u)
					break;
			}
			SCCSize[SCC] = size;
			SCC++;
		}
	}

	static int prepareAndSolve() {
		dfs_num = new int[V];
		dfs_low = new int[V];
		SCCSet = new int[V];
		SCCSize = new int[V];
		Arrays.fill(SCCSet, -1);
		components = new Stack<>();
		counter = SCC = 0;
		int ans = 0;
		for (int i = 0; i < V; i++)
			if (dfs_num[i] == 0)
				tarjanSCC(i);
		ArrayList<Integer>[] DAG = new ArrayList[SCC];
		for (int i = 0; i < SCC; i++)
			DAG[i] = new ArrayList<>();
		for (int i = 0; i < V; i++) {
			for (int v : graph[i]) {
				if (SCCSet[i] != SCCSet[v]) {
					DAG[SCCSet[i]].add(SCCSet[v]);
				}
			}
		}
		for (int i = 0; i < SCC; i++)
			if(SCCSize[i] != 1)
				ans += SCCSize[i];
		// prepare transitive closure for DAG
		adjMatrix = new int[SCC][SCC];
		for (int i = 0; i < SCC; i++) {
			for (int v : DAG[i])
				adjMatrix[i][v] = 1;
		}
		floyd(SCC);
		for (int u = 0; u < SCC; u++) {
			for (int v = 0; v < SCC; v++) {
				if (adjMatrix[u][v] == 1) {
					boolean f = true;
					for (int w : DAG[u]) {
						if (w != v && adjMatrix[w][v] == 1)
							f = false;
					}
					if (f)
						ans++;
				}
			}
		}
		return ans;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int Case = 1;
		while (true) {
			int n = sc.nextInt(), m = sc.nextInt();
			if (n + m == 0)
				break;
			V = n;
			graph = new ArrayList[V];
			for (int i = 0; i < V; i++)
				graph[i] = new ArrayList<>();
			for (int i = 0; i < m; i++) {
				int u = sc.nextInt() - 1, v = sc.nextInt() - 1;
				graph[u].add(v);
			}
			out.printf("Case %d: %d\n", Case++, prepareAndSolve());
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
