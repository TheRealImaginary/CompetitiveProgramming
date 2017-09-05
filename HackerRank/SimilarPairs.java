package HackerRank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

// Use Value as Nodes as ranges for query
public class SimilarPairs {

	static class SegmentTree {
		int[] tree;
		int N;

		private int left(int p) {
			return p << 1;
		}

		private int right(int p) {
			return (p << 1) + 1;
		}

		public SegmentTree(int n) {
			N = n;
			tree = new int[n << 2];
		}

		public int query(int i, int j) {
			return query(1, 0, N - 1, i, j);
		}

		private int query(int p, int L, int R, int i, int j) {
			if (i > R || L > j)
				return 0;
			if (L >= i && R <= j)
				return tree[p];
			int mid = (L + R) >> 1, left = left(p), right = right(p);
			int ll = query(left, L, mid, i, j);
			int rr = query(right, mid + 1, R, i, j);
			return ll + rr;
		}

		// toggles a point
		public void update(int i) {
			update(1, 0, N - 1, i);
		}

		private void update(int p, int L, int R, int idx) {
			if (idx > R || idx < L)
				return;
			if (idx == L && idx == R) {
				tree[p] ^= 1;
			} else {
				int mid = (L + R) >> 1, left = left(p), right = right(p);
				update(left, L, mid, idx);
				update(right, mid + 1, R, idx);
				tree[p] = tree[left] + tree[right];
			}
		}
	}

	static ArrayList<Integer>[] tree;
	static long ans;
	static SegmentTree st;
	static int t;
	static int n;

	static void dfs(int u, int p) {
		ans += st.query(u - t < 0 ? 0 : u - t, u + t > n ? n : u + t);
		st.update(u);
		for (int v : tree[u]) {
			if (v != p)
				dfs(v, u);
		}
		st.update(u);
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		n = sc.nextInt();
		t = sc.nextInt();

		tree = new ArrayList[n + 1];
		for (int i = 0; i <= n; i++)
			tree[i] = new ArrayList<>();
		boolean[] root = new boolean[n + 1];
		Arrays.fill(root, true);
		for (int i = 0; i < n - 1; i++) {
			int u = sc.nextInt(), v = sc.nextInt();
			tree[u].add(v);
			tree[v].add(u);
			root[v] = false;
		}
		int f = -1;
		for (int i = 1; i <= n; i++)
			if (root[i]) {
				f = i;
				break;
			}
		ans = 0;
		st = new SegmentTree(n + 1);
		dfs(f, -1);
		pw.println(ans);
		pw.close();
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
