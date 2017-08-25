package Codeforces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class CF514_D2_D_R2D2AndDroidArmy {

	static int getSum(TreeSet<Integer>[] sets, int[][] a, int current) {
		int res = 0, m = sets.length;
		for (int i = 0; i < m; i++)
			res += Math.max(sets[i].isEmpty() ? 0 : sets[i].last(), a[i][current]);
		return res;
	}

	static void add(TreeSet<Integer>[] sets, Map<Integer, Integer>[] maps, int[][] a, int current) {
		int m = sets.length;
		for (int i = 0; i < m; i++) {
			Integer x = maps[i].get(a[i][current]);
			if (x == null) {
				x = 0;
				sets[i].add(a[i][current]);
			}
			maps[i].put(a[i][current], x + 1);
		}
	}

	static void remove(TreeSet<Integer>[] sets, Map<Integer, Integer>[] maps, int[][] a, int current) {
		int m = sets.length;
		for (int i = 0; i < m; i++) {
			Integer x = maps[i].get(a[i][current]);
			if (x == null)
				continue;
			--x;
			if (x == 0) {
				sets[i].remove(a[i][current]);
				maps[i].remove(a[i][current]);
				continue;
			}
			maps[i].put(a[i][current], x);
		}
	}

	static int[] getAns(TreeSet<Integer>[] sets) {
		int m = sets.length;
		int[] res = new int[m];
		for (int i = 0; i < m; i++)
			res[i] = sets[i].isEmpty() ? 0 : sets[i].last();
		return res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), m = sc.nextInt(), k = sc.nextInt();
		int[][] a = new int[m][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				a[j][i] = sc.nextInt();
		TreeSet<Integer>[] sets = new TreeSet[m];
		for (int i = 0; i < m; i++)
			sets[i] = new TreeSet<>();
		Map<Integer, Integer>[] maps = new Map[m];
		for (int i = 0; i < m; i++)
			maps[i] = new HashMap<>();
		int len = 0;
		int[] ans = new int[m];
		for (int i = 0, j = 0; i < n; i++) {
			while (j < n && getSum(sets, a, j) <= k) {
				add(sets, maps, a, j);
				j++;
			}
			if (j - i > len) {
				len = j - i;
				ans = getAns(sets);
			}
			remove(sets, maps, a, i);
			if (i == j)
				j++;
		}
		for (int i = 0; i < m; i++) {
			if (i > 0)
				out.print(" ");
			out.print(ans[i]);
		}
		out.println();
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
