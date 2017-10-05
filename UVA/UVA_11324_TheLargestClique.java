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

/*-
 * 
 * @author Mazen Magdy
 * 		-T(G) is a Graph where each node is connected to any other node it can reach in the original graph.
 * 		-The Answer is the size of the largest Set of vertices such that between any pair of vertices, there
 * 		 exists an edge either from u to v or v to u.
 * 		-If we find all SCC in the Original Graph and Create a DAG, let node u and v be nodes in the DAG,
 * 		 If u can reach v then this means u can reach all vertices in v's component, so the answer is the maximum
 * 		 number of nodes we can pass through from any node.
 */
public class UVA_11324_TheLargestClique {

	static ArrayList<Integer>[] graph;
	static ArrayList<Integer>[] DAG;
	static int[] dfs_low, dfs_num, SCC_ID, SCC_Size;
	static int c, scc, V;
	static Stack<Integer> stack;
	static int[] dp;

	static void tarjanSCC(int u) {
		dfs_num[u] = dfs_low[u] = ++c;
		stack.push(u);
		for (int v : graph[u]) {
			if (dfs_num[v] == 0)
				tarjanSCC(v);
			if (SCC_ID[v] == -1)
				dfs_low[u] = Math.min(dfs_low[v], dfs_low[u]);
		}

		if (dfs_num[u] == dfs_low[u]) {
			int size = 0;
			while (true) {
				int v = stack.pop();
				SCC_ID[v] = scc;
				size++;
				if (v == u)
					break;
			}
			SCC_Size[scc++] = size;
		}
	}

	static void connect() {
		DAG = new ArrayList[V];
		for (int i = 0; i < V; i++)
			DAG[i] = new ArrayList<>();
		for (int u = 0; u < V; u++) {
			int uid = SCC_ID[u];
			for (int v : graph[u]) {
				int vid = SCC_ID[v];
				if (uid != vid)
					DAG[uid].add(vid);
			}
		}
	}

	static int solve(int u) {
		if (dp[u] != 0)
			return dp[u];
		int ans = 0;
		for (int v : DAG[u])
			ans = Math.max(ans, solve(v));
		return dp[u] = ans + SCC_Size[u];
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt(), m = sc.nextInt();
			graph = new ArrayList[n];
			for (int i = 0; i < n; i++)
				graph[i] = new ArrayList<>();
			for (int i = 0; i < m; i++) {
				int u = sc.nextInt() - 1, v = sc.nextInt() - 1;
				graph[u].add(v);
			}
			scc = 0;
			c = 0;
			dfs_num = new int[n];
			dfs_low = new int[n];
			SCC_ID = new int[n];
			Arrays.fill(SCC_ID, -1);
			SCC_Size = new int[n];
			stack = new Stack<>();
			V = n;
			for (int i = 0; i < V; i++)
				if (dfs_num[i] == 0)
					tarjanSCC(i);
			connect();
			// System.err.println(Arrays.toString(SCC_Size));
			// System.err.println(Arrays.toString(SCC_ID));
			// System.err.println(Arrays.toString(DAG));
			dp = new int[scc];
			int ans = 0;
			for (int i = 0; i < scc; i++)
				ans = Math.max(ans, solve(i));
			out.println(ans);
		}
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
	}
}
