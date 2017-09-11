package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class UVA_11047_TheScroogeCoProblem {

	static final int oo = 1 << 28;

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		// PrintWriter out = new PrintWriter("out.txt");
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt();
			int[][] g = new int[n][n];
			Map<String, Integer> map = new TreeMap<>();
			String[] unmap = new String[n];
			for (int i = 0; i < n; i++) {
				String s = sc.next();
				map.put(s, i);
				unmap[i] = s;
			}
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++) {
					int x = sc.nextInt();
					g[i][j] = x == -1 ? oo : x;
				}
			int[][] p = new int[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					p[i][j] = i;
					p[j][i] = j;
				}
				p[i][i] = i;
			}
			for (int k = 0; k < n; k++)
				for (int i = 0; i < n; i++)
					for (int j = 0; j < n; j++) {
						if (g[i][j] > g[i][k] + g[k][j]) {
							g[i][j] = g[i][k] + g[k][j];
							p[i][j] = p[k][j];
						}
					}
			int q = sc.nextInt();
			for (int i = 0; i < q; i++) {
				String name = sc.next(), a = sc.next(), b = sc.next();
				int u = map.get(a), v = map.get(b);
				if (g[u][v] == oo) {
					out.printf("Sorry Mr %s you can not go from %s to %s\n", name, a, b);
				} else {
					out.printf("Mr %s to go from %s to %s, you will receive %d euros\n", name, a, b, g[u][v]);
					out.print("Path:");
					Stack<Integer> stack = new Stack<>();
					while (true) {
						stack.push(v);
						v = p[u][v];
						if (v == u)
							break;
					}
					stack.push(u);
					while (!stack.isEmpty()) {
						if (stack.size() == 1)
							out.printf("%s", unmap[stack.pop()]);
						else
							out.printf("%s ", unmap[stack.pop()]);
					}
					out.println();
				}
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
