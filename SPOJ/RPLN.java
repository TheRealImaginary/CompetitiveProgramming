package SPOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class RPLN {

	static int[] a;
	static final int oo = (int) 1e9;

	static class SegmentTree {
		int tree[];
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
			build(1, 0, N - 1);
		}

		private void build(int p, int L, int R) {
			if (L == R) {
				tree[p] = L;
			} else {
				int mid = (L + R) >> 1;
				build(left(p), L, mid);
				build(right(p), mid + 1, R);
				int left = left(p), right = right(p);
				tree[p] = a[tree[left]] <= a[tree[right]] ? tree[left] : tree[right];
			}
		}

		public int query(int i, int j) {
			return query(1, 0, N - 1, i, j);
		}

		private int query(int p, int L, int R, int i, int j) {
			if (i > R || j < L)
				return oo;
			if (L >= i && R <= j)
				return tree[p];
			int mid = (L + R) >> 1;
			int left = query(left(p), L, mid, i, j);
			int right = query(right(p), mid + 1, R, i, j);
			if (left == oo)
				return right;
			if (right == oo)
				return left;
			return a[left] <= a[right] ? left : right;
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		StringBuilder sb = new StringBuilder();
		int tc = sc.nextInt();
		for (int t = 1; t <= tc; t++) {
			int n = sc.nextInt();
			int q = sc.nextInt();
			a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = sc.nextInt();
			SegmentTree st = new SegmentTree(n);
			sb.append("Scenario #" + t + ":\n");
			while (q-- > 0) {
				int i = sc.nextInt() - 1;
				int j = sc.nextInt() - 1;
				sb.append(a[st.query(i, j)] + "\n");
			}
		}
		pw.print(sb);
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
