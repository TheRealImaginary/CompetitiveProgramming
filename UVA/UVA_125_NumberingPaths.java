package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA_125_NumberingPaths {

	static final int MAXN = 31;

	static int V;
	static int[][] adjMatrix;

	static void floyd() {
		for (int k = 0; k <= V; k++)
			for (int i = 0; i <= V; i++)
				for (int j = 0; j <= V; j++)
					adjMatrix[i][j] = adjMatrix[i][j] + (adjMatrix[i][k] * adjMatrix[k][j]);
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		for (int t = 0; sc.ready(); t++) {
			int E = sc.nextInt();
			V = 0;
			adjMatrix = new int[MAXN][MAXN];
			while (E-- > 0) {
				int u = sc.nextInt(), v = sc.nextInt();
				adjMatrix[u][v] = 1;
				V = Math.max(V, Math.max(u, v));
			}
			floyd();
			for (int k = 0; k <= V; k++)
				if (adjMatrix[k][k] != 0)
					for (int i = 0; i <= V; i++)
						for (int j = 0; j <= V; j++)
							if (adjMatrix[i][k] != 0 && adjMatrix[k][j] != 0)
								adjMatrix[i][j] = -1;
			out.printf("matrix for city %d\n", t);
			for (int i = 0; i <= V; i++) {
				for (int j = 0; j <= V; j++) {
					out.print(adjMatrix[i][j]);
					if (j != V)
						out.print(" ");
				}
				out.println();
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

		String nextLine() throws IOException {
			return br.readLine();
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
