package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*-
 * 
 * @author Mazen Magdy
 *		-The number of SCCs is the answer.
 */
public class UVA_11709_TrustGroups {

	static int dfs_low[], dfs_num[];
	static boolean inSCC[];
	static Stack<Integer> components;
	static int SCC, counter;
	static ArrayList<Integer> graph[];

	public static void tarjanSCC(int u) {

		dfs_num[u] = dfs_low[u] = ++counter;
		components.push(u);
		// visited[u] = true;

		for (int v : graph[u]) {
			if (dfs_num[v] == 0) {
				tarjanSCC(v);
			}
			if (!inSCC[v])
				dfs_low[u] = Math.min(dfs_low[u], dfs_low[v]);
		}

		if (dfs_num[u] == dfs_low[u]) {

			while (true) {
				int v = components.pop();
				inSCC[v] = true;

				if (v == u)
					break;
			}
			SCC++;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(System.out);
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int t = Integer.parseInt(st.nextToken());

		while (n != 0 || t != 0) {

			TreeMap<String, Integer> map = new TreeMap<>();
			graph = new ArrayList[n];
			dfs_low = new int[n];
			dfs_num = new int[n];
			components = new Stack<>();
			inSCC = new boolean[n];

			for (int i = 0; i < n; i++) {
				graph[i] = new ArrayList<>();
				String s = br.readLine();
				map.put(s, i);
			}
			while (t-- > 0) {
				int u = map.get(br.readLine());
				int v = map.get(br.readLine());
				graph[u].add(v);
			}
			SCC = 0;
			counter = 0;
			for (int i = 0; i < graph.length; i++) {
				if (dfs_num[i] == 0)
					tarjanSCC(i);
			}
			
			pw.println(SCC);

			st = new StringTokenizer(br.readLine());
			n = Integer.parseInt(st.nextToken());
			t = Integer.parseInt(st.nextToken());
		}

		pw.flush();
		pw.close();

	}

}
