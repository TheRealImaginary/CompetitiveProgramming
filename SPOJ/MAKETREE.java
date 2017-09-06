package SPOJ;

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
 * @author Mazen Magdy
 *				-The topological sort of the graph is the answer.
 */
public class MAKETREE { // TLE

	static ArrayList<Integer>[] g;
	static Stack<Integer> stack;
	static boolean[] vis;

	static void topoSort(int u) {
		vis[u] = true;
		for (int v : g[u])
			if (!vis[v])
				topoSort(v);
		stack.push(u);
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), k = sc.nextInt();
		g = new ArrayList[n];
		for (int i = 0; i < n; i++)
			g[i] = new ArrayList<>();
		for (int node = 0; node < k; node++) {
			int x = sc.nextInt();
			for (int i = 0; i < x; i++) {
				int v = sc.nextInt() - 1;
				g[node].add(v);
			}
		}
		stack = new Stack<>();
		vis = new boolean[n];
		for (int node = 0; node < n; node++)
			if (!vis[node])
				topoSort(node);
		int[] p = new int[n];
		int parent = -1;
		while (!stack.isEmpty()) {
			int u = stack.pop();
			p[u] = parent + 1;
			parent = u;
		}
		for (int i = 0; i < n; i++)
			out.println(p[i]);
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
