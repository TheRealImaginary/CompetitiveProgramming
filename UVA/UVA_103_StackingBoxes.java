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
import java.util.StringTokenizer;

public class UVA_103_StackingBoxes {

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			int n = sc.nextInt(), k = sc.nextInt();
			int[][] b = new int[n][k];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < k; j++)
					b[i][j] = sc.nextInt();
				Arrays.sort(b[i]);
			}
			ArrayList<Integer>[] g = new ArrayList[n + 1];
			int[] inDegree = new int[n];
			for (int i = 0; i <= n; i++)
				g[i] = new ArrayList<>();
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++) {
					boolean can = true;
					for (int l = 0; l < k; l++)
						if (b[i][l] <= b[j][l])
							can = false;
					if (can) {
						g[i].add(j);
						inDegree[j]++;
					}
				}
			for (int i = 0; i < n; i++)
				if (inDegree[i] == 0)
					g[n].add(i);
			int[] dist = new int[n + 1];
			Arrays.fill(dist, -1);
			dist[n] = 0;
			int[] p = new int[n + 1];
			Arrays.fill(p, -1);
			int ans = 0, node = -1;
			for (int j = 0; j < n; j++)
				for (int u = 0; u <= n; u++)
					for (int v : g[u])
						if (dist[v] < dist[u] + 1) {
							dist[v] = dist[u] + 1;
							p[v] = u;
							if (dist[v] > ans) {
								ans = dist[v];
								node = v;
							}
						}
			out.println(ans);
			while (node != -1 && node != n) {
				out.print(node + 1);
				node = p[node];
				if (node != -1 && node != n)
					out.print(" ");
			}
			out.println();
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

		boolean ready() throws IOException {
			return br.ready();
		}
	}
}
