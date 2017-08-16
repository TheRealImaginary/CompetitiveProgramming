package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We use Floyd-Warshall to solve this problem, and we keep the path taken to print it.
 */
public class UVA_186_TripRouting {

	static final int oo = 1 << 27;
	static final int MAX = 101;

	static int V;
	static int[][] adjMatrix, P;
	static String[][] route;

	static void floyd() {
		for (int k = 0; k < V; k++)
			for (int i = 0; i < V; i++)
				for (int j = 0; j < V; j++)
					if (adjMatrix[i][j] > adjMatrix[i][k] + adjMatrix[k][j]) {
						adjMatrix[i][j] = adjMatrix[i][k] + adjMatrix[k][j];
						P[i][j] = P[k][j];
					}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		route = new String[MAX][MAX];
		adjMatrix = new int[MAX][MAX];
		P = new int[MAX][MAX];
		for (int i = 0; i < MAX; i++) {
			Arrays.fill(adjMatrix[i], oo);
			adjMatrix[i][i] = 0;
			Arrays.fill(route[i], "");
			// We could reverse here and print path would be easier
			// We would also need to change in floyd to P[i][j] = P[i][k]
			// We would then be going down the path not up
			for (int j = 0; j < MAX; j++) {
				P[i][j] = i;
				P[j][i] = j;
			}
			P[i][i] = i;
		}
		int id = 0;
		Map<String, Integer> map = new HashMap<>();
		ArrayList<String> unmap = new ArrayList<>();
		while (true) {
			String s = sc.nextLine();
			if (s == null || s.isEmpty())
				break;
			// System.err.println(s);
			StringTokenizer st = new StringTokenizer(s, ",");
			String a = st.nextToken(), b = st.nextToken();
			if (!map.containsKey(a)) {
				map.put(a, id++);
				unmap.add(a);
			}
			if (!map.containsKey(b)) {
				map.put(b, id++);
				unmap.add(b);
			}
			int u = map.get(a), v = map.get(b);
			String name = st.nextToken();
			int cost = Integer.parseInt(st.nextToken());
			if (adjMatrix[u][v] > cost) {
				adjMatrix[u][v] = adjMatrix[v][u] = cost;
				route[u][v] = route[v][u] = name;
			}
		}
		V = id;
		floyd();
		while (true) {
			String s = sc.nextLine();
			if (s == null || s.isEmpty())
				break;
			out.print("\n\n");
			StringTokenizer st = new StringTokenizer(s, ",");
			int u = map.get(st.nextToken()), v = map.get(st.nextToken());
			int x = u, y = v;
			Stack<Integer> stack = new Stack<>();
			while (true) {
				stack.push(y);
				int temp = P[u][y];
				if (temp == u)
					break;
				// x = y;
				y = temp;
			}
			out.println("From                 To                   Route      Miles");
			out.println("-------------------- -------------------- ---------- -----");
			y = stack.pop();
			while (true) {
				String from = unmap.get(x), to = unmap.get(y);
				out.printf("%s", from);
				out.printf("%1$" + (21 - from.length() + to.length()) + "s", to);
				out.printf("%1$" + (21 - to.length() + route[x][y].length()) + "s", route[x][y]);
				out.printf("%" + (16 - route[x][y].length()) + "d\n", adjMatrix[x][y]);
				x = y;
				if (stack.isEmpty())
					break;
				y = stack.pop();
			}
			out.println("                                                     -----");
			out.print("                                          Total      ");
			out.printf("%5d\n", adjMatrix[u][v]);
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
