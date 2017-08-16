package SPOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 * @see http://codeforces.com/gym/101055/problem/B
 * 				-We use a SegmentTree, in each node we save prefix sum, suffix sum, sum of the segment and
 * 				 the answer of that segment. Now to calculate these values for a node, its prefix sum is
 * 				 max(prefixLeft, sumLeft + prefixRight) similarly for the suffix. now the sum is leftSum + rightSum
 * 				 and the answer is max(suffixRight + prefixRight, answerLeft + answerRight).
 */
public class GSS3 {

	static class Data {
		int prefix, suffix, sum, ans;

		Data(int p, int suf, int s, int a) {
			prefix = p;
			suffix = suf;
			sum = s;
			ans = a;
		}

		@Override
		public String toString() {
			return prefix + " " + suffix + " " + sum + " " + ans;
		}
	}

	static final int oo = 1 << 28;
	static int[] a;

	static class SegmentTree {
		Data tree[];
		int N;

		private int left(int p) {
			return p << 1;
		}

		private int right(int p) {
			return (p << 1) + 1;
		}

		private Data merge(Data left, Data right) {
			if (left == null)
				return right;
			if (right == null)
				return left;
			return new Data(Math.max(left.prefix, left.sum + right.prefix),
					Math.max(right.suffix, right.sum + left.suffix), left.sum + right.sum,
					Math.max(left.suffix + right.prefix, Math.max(left.ans, right.ans)));
		}

		public SegmentTree(int n) {
			N = n;
			tree = new Data[4 * N];
			build(1, 0, N - 1);
		}

		private void build(int p, int L, int R) {
			if (L == R) {
				tree[p] = new Data(a[L], a[L], a[L], a[L]);
			} else {
				int mid = (L + R) >> 1, left = left(p), right = right(p);
				build(left, L, mid);
				build(right, mid + 1, R);
				tree[p] = merge(tree[left], tree[right]);
			}
		}

		public void update(int idx, int newV) {
			update(1, 0, N - 1, idx, newV);
		}

		private void update(int p, int L, int R, int idx, int newV) {
			if (L > idx || R < idx)
				return;
			if (L == idx && R == idx) {
				tree[p] = new Data(newV, newV, newV, newV);
			} else {
				int mid = (L + R) >> 1, left = left(p), right = right(p);
				if (idx <= mid)
					update(left, L, mid, idx, newV);
				else
					update(right, mid + 1, R, idx, newV);
				tree[p] = merge(tree[left], tree[right]);
			}
		}

		public int query(int i, int j) {
			return query(1, 0, N - 1, i, j).ans;
		}

		private Data query(int p, int L, int R, int i, int j) {
			if (i > R || j < L)
				return null;
			if (L >= i && R <= j)
				return tree[p];
			int mid = (L + R) >> 1, left = left(p), right = right(p);
			return merge(query(left, L, mid, i, j), query(right, mid + 1, R, i, j));
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		StringBuilder sb = new StringBuilder();
		int n = sc.nextInt();
		a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = sc.nextInt();
		SegmentTree st = new SegmentTree(n);
		// System.err.println(Arrays.toString(st.tree));
		int q = sc.nextInt();
		while (q-- > 0) {
			int type = sc.nextInt();
			if (type == 1) {
				sb.append(st.query(sc.nextInt() - 1, sc.nextInt() - 1)).append("\n");
			} else if (type == 0) {
				st.update(sc.nextInt() - 1, sc.nextInt());
				// System.err.println(Arrays.toString(st.tree));
			}
		}
		out.println(sb);
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
