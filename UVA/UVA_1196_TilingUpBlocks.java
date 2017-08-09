package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-The solution is similar to LIS, we sort on one dimension, and use a Segment Tree
 *				 (could also use Binary Search) on the other dimension to 
 *				 find the maximum answer for a block with higher dimension.
 */
public class UVA_1196_TilingUpBlocks { // Correct

	static class Block implements Comparable<Block> {
		int l, m;

		Block(int a, int b) {
			l = a;
			m = b;
		}

		@Override
		public String toString() {
			return l + " " + m;
		}

		@Override
		public int compareTo(Block p) {
			if (l != p.l)
				return l - p.l;
			return m - p.m;
		}
	}

	static class SegmentTree {

		int N;
		int[] tree;

		SegmentTree(int n) {
			N = n;
			tree = new int[N << 2];
		}

		void update(int idx, int val) {
			update(1, 0, N - 1, idx, val);
		}

		void update(int p, int L, int R, int idx, int val) {
			if (L > idx || R < idx)
				return;
			if (L == R && L == idx)
				tree[p] = val;
			else {
				int mid = (L + R) >> 1, left = p << 1, right = left | 1;
				if (idx <= mid)
					update(left, L, mid, idx, val);
				else
					update(right, mid + 1, R, idx, val);
				tree[p] = Math.max(tree[left], tree[right]);
			}
		}

		int query(int i, int j) {
			return query(1, 0, N - 1, i, j);
		}

		int query(int p, int L, int R, int i, int j) {
			if (L > j || R < i)
				return 0;
			if (L >= i && R <= j)
				return tree[p];
			int mid = (L + R) >> 1, left = p << 1, right = left | 1;
			return Math.max(query(left, L, mid, i, j), query(right, mid + 1, R, i, j));
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			Block[] b = new Block[n];
			for (int i = 0; i < n; i++)
				b[i] = new Block(sc.nextInt(), sc.nextInt());
			Arrays.sort(b);
			int MAX = 101;
			SegmentTree st = new SegmentTree(MAX);
			int ans = 1;
			for (int i = n - 1; i >= 0; i--) {
				int x = st.query(b[i].m, MAX) + 1;
				ans = Math.max(ans, x);
				st.update(b[i].m, x);
			}
			out.println(ans);
		}
		out.println('*');
		out.flush();
		out.close();
	}

	static class MyScanner {

		BufferedReader br;
		StringTokenizer st;

		MyScanner(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
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

		boolean ready() throws IOException {
			return br.ready();
		}
	}
}
