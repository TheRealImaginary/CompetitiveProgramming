package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 * 
 * 		-Find SCC in the graph, and Create a DAG, the number of roots in this
 * 		 (nodes with zero in-degree) DAG is the answer.
 */
public class UVA_11504_Dominos {

	static int dfs_low[];
	static int dfs_num[];
	static int SCCSet[];
	static boolean inSCC[];
	static int counter;
	static int SCC;
	static ArrayList<Integer> graph[];
	// static Stack<Integer> components;
	static LinkedList<Integer> components;

	public static void tarjanSCC(int u) {
		dfs_low[u] = dfs_num[u] = ++counter;
		components.push(u);
		for (int v : graph[u]) {
			if (dfs_num[v] == 0)
				tarjanSCC(v);
			if (!inSCC[v])
				dfs_low[u] = Math.min(dfs_low[v], dfs_low[u]);
		}
		if (dfs_low[u] == dfs_num[u]) {

			while (true) {
				int v = components.pop();
				inSCC[v] = true;
				SCCSet[v] = SCC;
				if (v == u)
					break;
			}
			SCC++;
		}
	}

	public static void main(String[] args) throws IOException {
		PrintWriter pw = new PrintWriter(System.out);
		Scanner sc = new Scanner(System.in);

		int t = sc.nextInt();

		while (t-- > 0) {
			int n = sc.nextInt();
			int m = sc.nextInt();

			graph = new ArrayList[100001];
			for (int i = 0; i < graph.length; i++)
				graph[i] = new ArrayList<>();

			while (m-- > 0) {
				int u = sc.nextInt() - 1;
				int v = sc.nextInt() - 1;
				graph[u].add(v);
			}

			dfs_low = new int[100001];
			dfs_num = new int[100001];
			inSCC = new boolean[100001];
			SCCSet = new int[100001];
			// components = new Stack<>();
			components = new LinkedList<>();
			counter = 0;
			SCC = 0;

			for (int i = 0; i < n; i++) {
				if (dfs_num[i] == 0)
					tarjanSCC(i);
			}

			boolean best[] = new boolean[SCC + 1];
			Arrays.fill(best, true);
			for (int u = 0; u < graph.length; u++) {
				for (int v : graph[u]) {
					if (SCCSet[u] != SCCSet[v])
						best[SCCSet[v]] = false;
				}
			}
			int ans = 0;
			for (int i = 0; i < SCC; i++) {
				if (best[i])
					ans++;
			}
			pw.println(ans);
		}
		pw.close();
	}

	static class Scanner {
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream s) {
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
