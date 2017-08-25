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
 */
public class GSS1 {

	static int[] a;

	static class Data {
		int prefix, suffix, sum, ans;

		Data(int pre, int suff, int s, int a) {
			prefix = pre;
			suffix = suff;
			sum = s;
			ans = a;
		}

		@Override
		public String toString() {
			return prefix + " " + suffix + " " + sum + " " + ans;
		}
	}

	static class SegmentTree {
		int N;
		Data tree[];

		private int left(int p) {
			return p << 1;
		}

		private Data merge(Data left, Data right) {
			if (left == null)
				return right;
			if (right == null)
				return left;
			return new Data(Math.max(left.prefix, left.sum + right.prefix),
					Math.max(right.suffix, left.suffix + right.sum), left.sum + right.sum,
					Math.max(Math.max(left.ans, right.ans), left.suffix + right.prefix));
		}

		public SegmentTree(int n) {
			N = n;
			tree = new Data[N << 2];
			build(1, 0, n - 1);
		}

		public void build(int p, int L, int R) {
			if (L == R) {
				tree[p] = new Data(a[L], a[L], a[L], a[L]);
			} else {
				int mid = (L + R) >> 1, left = left(p), right = left | 1;
				build(left, L, mid);
				build(right, mid + 1, R);
				tree[p] = merge(tree[left], tree[right]);
			}
		}

		public int query(int i, int j) {
			return queury(1, 0, N - 1, i, j).ans;
		}

		private Data queury(int p, int L, int R, int i, int j) {
			if (i > R || j < L)
				return null;
			if (L >= i && R <= j)
				return tree[p];
			int mid = (L + R) >> 1, left = left(p), right = left | 1;
			return merge(queury(left, L, mid, i, j), queury(right, mid + 1, R, i, j));
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int N = sc.nextInt();
		a = new int[N];
		for (int i = 0; i < N; i++)
			a[i] = sc.nextInt();
		SegmentTree st = new SegmentTree(N);
		int Q = sc.nextInt();
		StringBuilder sb = new StringBuilder();
		while (Q-- > 0)
			sb.append(st.query(sc.nextInt() - 1, sc.nextInt() - 1)).append('\n');
		out.print(sb);
		out.flush();
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
