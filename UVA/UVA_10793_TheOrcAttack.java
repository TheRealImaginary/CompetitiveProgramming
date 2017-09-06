package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We find the APSP and then from all nodes reachable from with the same distance
 *				 from all sources we pick the one with the minimum maximum distance.
 */
public class UVA_10793_TheOrcAttack {

	static final int oo = 1 << 28;

	static int[][] adjMatrix;

	static void floyd(int V) {
		for (int k = 0; k < V; k++)
			for (int i = 0; i < V; i++)
				for (int j = 0; j < V; j++)
					adjMatrix[i][j] = Math.min(adjMatrix[i][j], adjMatrix[i][k] + adjMatrix[k][j]);
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			int n = sc.nextInt(), m = sc.nextInt();
			adjMatrix = new int[n][n];
			for (int i = 0; i < n; i++) {
				Arrays.fill(adjMatrix[i], oo);
				adjMatrix[i][i] = 0;
			}
			for (int i = 0; i < m; i++) {
				int u = sc.nextInt() - 1, v = sc.nextInt() - 1;
				adjMatrix[u][v] = adjMatrix[v][u] = Math.min(adjMatrix[u][v], sc.nextInt());
			}
			floyd(n);
			int res = oo;
			for (int place = 5; place < n; place++) {
				boolean canBe = true;
				for (int s = 1; s < 5; s++)
					if (adjMatrix[place][s] != adjMatrix[place][s - 1]) {
						canBe = false;
						break;
					}
				if (canBe) {
					int max = 0;
					for (int node = 0; node < n; node++)
						max = Math.max(max, adjMatrix[place][node]);
					res = Math.min(res, max);
				}
			}
			out.printf("Map %d: %d\n", t, res == oo ? -1 : res);
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
