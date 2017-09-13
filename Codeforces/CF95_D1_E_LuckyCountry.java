package Codeforces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-One could solve it using the recurrence (idx, S) as a knapsack problem
 *				 as if we want to choose some coins (components) to make sum `S` which is lucky.
 *				 This would need N^2 Time. We could count the number of occurrences of a coin
 *				 (component sizes) and break them into powers of two 1, 2, 4, ...(N - x) where
 *				 N is the largest power of two smaller than x. Any combination of the power of twos
 *				 can generate any number from 0 to x. Now we perform a Knapsack on this representation
 *				 yielding an N * S * log(N) solution.
 */
public class CF95_D1_E_LuckyCountry {

	static class UnionFind {
		int[] rank, rep, setSize;

		UnionFind(int n) {
			rank = new int[n];
			rep = new int[n];
			setSize = new int[n];
			for (int i = 0; i < n; i++) {
				rep[i] = i;
				setSize[i] = 1;
			}
		}

		void union(int x, int y) {
			if (sameSet(x, y))
				return;
			x = findSet(x);
			y = findSet(y);
			if (rank[x] > rank[y]) {
				rep[y] = x;
				setSize[x] += setSize[y];
			} else {
				rep[x] = y;
				setSize[y] += setSize[x];
				if (rank[x] == rank[y])
					rank[y]++;
			}
		}

		int findSet(int x) {
			return rep[x] == x ? x : (rep[x] = findSet(rep[x]));
		}

		boolean sameSet(int x, int y) {
			return findSet(x) == findSet(y);
		}
	}

	static final int oo = 1 << 28;

	static ArrayList<Integer> generateLucky(int n) {
		ArrayList<Integer> res = new ArrayList<>();
		Queue<Integer> q = new LinkedList<>();
		q.add(4);
		q.add(7);
		while (!q.isEmpty()) {
			int u = q.poll();
			if (u > n)
				continue;
			res.add(u);
			q.add(u * 10 + 4);
			q.add(u * 10 + 7);
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), m = sc.nextInt();
		UnionFind uf = new UnionFind(n);
		for (int i = 0; i < m; i++)
			uf.union(sc.nextInt() - 1, sc.nextInt() - 1);
		boolean[] done = new boolean[n];
		int[] cnt = new int[n + 1];
		for (int i = 0; i < n; i++) {
			if (done[uf.findSet(i)])
				continue;
			done[uf.findSet(i)] = true;
			cnt[uf.setSize[uf.findSet(i)]]++;
		}
		// System.err.println(Arrays.toString(cnt));
		ArrayList<Integer> w = new ArrayList<>();
		ArrayList<Integer> c = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			int N = 1, x = cnt[i];
			while (N < x) {
				w.add(N * i);
				c.add(N - 1);
				x -= N;
				N <<= 1;
			}
			if (x != 0) {
				w.add(x * i);
				c.add(x - 1);
			}
		}
		// System.err.println(w);
		// System.err.println(c);
		int[] dp = new int[n + 1];
		Arrays.fill(dp, oo);
		dp[0] = 0;
		for (int i = w.size() - 1; i >= 0; i--)
			for (int s = n; s >= 0; s--)
				if (s + w.get(i) <= n)
					dp[s + w.get(i)] = Math.min(dp[s + w.get(i)], dp[s] + c.get(i) + 1);
		// System.err.println(Arrays.toString(dp));
		int ans = oo;
		ArrayList<Integer> lucky = generateLucky(n);
		// System.err.println(lucky);
		for (int l : lucky)
			ans = Math.min(ans, dp[l]);
		out.println(ans == oo ? -1 : ans - 1);
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
