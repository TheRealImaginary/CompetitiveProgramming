package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*-
 * @author Mazen Magdy
 *				-We find the Transitive Closure and include dependencies `a` for some `c` 
 *				 iff there doesn't exist another dependency `b` such that `b` depends
 *				 on `a` and `c` depends on `b`.
 */
public class UVA_925_NoMorePrerequisitesPlease {

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int V = sc.nextInt();
			String[] s = new String[V];
			for (int i = 0; i < V; i++)
				s[i] = sc.next();
			Arrays.sort(s);
			Map<String, Integer> map = new TreeMap<>();
			String[] unmap = new String[V];
			for (int i = 0; i < V; i++) {
				map.put(s[i], i);
				unmap[i] = s[i];
			}
			int[][] g = new int[V][V];
			for (int i = 0; i < V; i++)
				g[i][i] = 1;
			int m = sc.nextInt();
			while (m-- > 0) {
				int v = map.get(sc.next());
				int k = sc.nextInt();
				while (k-- > 0) {
					int u = map.get(sc.next());
					g[u][v] = 1;
				}
			}
			for (int k = 0; k < V; k++)
				for (int i = 0; i < V; i++)
					for (int j = 0; j < V; j++)
						g[i][j] |= (g[i][k] & g[k][j]);
			ArrayList<Integer>[] sol = new ArrayList[V];
			for (int i = 0; i < V; i++)
				sol[i] = new ArrayList<>();
			for (int u = 0; u < V; u++)
				for (int v = 0; v < V; v++) {
					if (u == v || g[u][v] == 0)
						continue;
					boolean ok = true;
					for (int k = 0; k < V; k++) {
						if (k == u || k == v)
							continue;
						if (g[u][k] == 1 && g[k][v] == 1)
							ok = false;
					}
					if (ok)
						sol[v].add(u);
				}
			for (int i = 0; i < V; i++) {
				if (sol[i].isEmpty())
					continue;
				out.printf("%s %d", unmap[i], sol[i].size());
				for (int dependency : sol[i])
					out.printf(" %s", unmap[dependency]);
				out.println();
			}
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
