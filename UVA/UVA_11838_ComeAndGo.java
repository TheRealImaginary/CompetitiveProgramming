package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

/*-
 * 
 * @author Mazen Magdy 
 * 		  -For each possible pair of vertices to be able to visit
 *         each other there must exist only 1 Strongly Connected Component
 *
 */
public class UVA_11838_ComeAndGo {

	static int c, numSCC;
	static int[] dfs_num, dfs_low;
	static boolean[] inSCC;
	static ArrayList<Integer>[] g;
	static Stack<Integer> stack;

	static void tarjanSCC(int u) {
		stack.push(u);
		dfs_num[u] = dfs_low[u] = ++c;
		for (int v : g[u]) {
			if (dfs_num[v] == 0)
				tarjanSCC(v);
			if (!inSCC[v])
				dfs_low[u] = Math.min(dfs_low[u], dfs_low[v]);
		}
		if (dfs_num[u] == dfs_low[u]) {
			while (true) {
				int v = stack.pop();
				inSCC[v] = true;
				if (u == v)
					break;
			}
			numSCC++;
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt(), m = sc.nextInt();
			if (n == 0 && m == 0)
				break;
			g = new ArrayList[n];
			for (int i = 0; i < n; i++)
				g[i] = new ArrayList<>();
			for (int i = 0; i < m; i++) {
				int u = sc.nextInt() - 1, v = sc.nextInt() - 1, p = sc.nextInt();
				g[u].add(v);
				if (p == 2)
					g[v].add(u);
			}
			c = numSCC = 0;
			stack = new Stack<>();
			dfs_num = new int[n];
			dfs_low = new int[n];
			inSCC = new boolean[n];
			for (int node = 0; node < n; node++)
				if (dfs_num[node] == 0)
					tarjanSCC(node);
			out.println(numSCC == 1 ? 1 : 0);
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
	}
}
