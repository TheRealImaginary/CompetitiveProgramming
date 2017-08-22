package SPOJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We use a SegmentTree, in each node We store how many numbers are in each
 *				 Congruence class of 3 (0, 1, 2), when we increment, we are shifting the amount
 *				 between the 3.
 */
public class MULTQ3 {

	static class Data {
		int zero, one, two;

		Data(int a, int b, int c) {
			zero = a;
			one = b;
			two = c;
		}

		void flip1() {
			two ^= one;
			one ^= two;
			two ^= one;

			one ^= zero;
			zero ^= one;
			one ^= zero;
		}

		void flip2() {
			two ^= one;
			one ^= two;
			two ^= one;

			zero ^= two;
			two ^= zero;
			zero ^= two;
		}

		@Override
		public String toString() {
			return zero + " " + one + " " + two;
		}
	}

	static class SegmentTree {

		int N;
		Data[] tree;
		int[] lazy;

		int left(int p) {
			return p << 1;
		}

		int mid(int L, int R) {
			return (L + R) >> 1;
		}

		Data merge(Data left, Data right) {
			if (left == null)
				return right;
			if (right == null)
				return left;
			return new Data(left.zero + right.zero, left.one + right.one, left.two + right.two);
		}

		void propagate(int p, int L, int R) {
			if (L == R || lazy[p] == 0)
				return;
			int left = left(p), right = left | 1;
			if (lazy[p] == 1) {
				tree[left].flip1();
				tree[right].flip1();
			} else if (lazy[p] == 2) {
				tree[left].flip2();
				tree[right].flip2();
			}
			lazy[left] = (lazy[left] + lazy[p]) % 3;
			lazy[right] = (lazy[right] + lazy[p]) % 3;
			lazy[p] = 0;
		}

		SegmentTree(int n) {
			N = n;
			tree = new Data[N << 2];
			lazy = new int[N << 2];
			build(1, 0, N - 1);
		}

		void build(int p, int L, int R) {
			if (L == R) {
				tree[p] = new Data(1, 0, 0);
			} else {
				int mid = mid(L, R), left = left(p), right = left | 1;
				build(left, L, mid);
				build(right, mid + 1, R);
				tree[p] = merge(tree[left], tree[right]);
			}
		}

		void increment(int i, int j) {
			increment(1, 0, N - 1, i, j);
		}

		void increment(int p, int L, int R, int i, int j) {
			if (L > j || R < i)
				return;
			if (L >= i && R <= j) {
				tree[p].flip1();
				lazy[p] = (lazy[p] + 1) % 3;
			} else {
				int mid = mid(L, R), left = left(p), right = left | 1;
				propagate(p, L, R);
				increment(left, L, mid, i, j);
				increment(right, mid + 1, R, i, j);
				tree[p] = merge(tree[left], tree[right]);
			}
		}

		int query(int i, int j) {
			return query(1, 0, N - 1, i, j).zero;
		}

		Data query(int p, int L, int R, int i, int j) {
			if (L > j || R < i)
				return null;
			if (L >= i && R <= j)
				return tree[p];
			int mid = mid(L, R), left = left(p), right = left | 1;
			propagate(p, L, R);
			return merge(query(left, L, mid, i, j), query(right, mid + 1, R, i, j));
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		StringBuilder sb = new StringBuilder();
		int N = sc.nextInt(), Q = sc.nextInt();
		SegmentTree st = new SegmentTree(N);
		while (Q-- > 0) {
			int type = sc.nextInt(), l = sc.nextInt(), r = sc.nextInt();
			if (type == 0)
				st.increment(l, r);
			else
				sb.append(st.query(l, r)).append("\n");
		}
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

		public MyScanner(String filename) throws IOException {
			br = new BufferedReader(new FileReader(new File(filename)));
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
