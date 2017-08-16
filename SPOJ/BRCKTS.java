package SPOJ;

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
 *				-We use a Segment Tree than keeps track of unmatched opened and closed brackets.
 *				 If the root has no unmatched brackets then it's a valid sequence, otherwise no.
 */
public class BRCKTS {

	static class Pair {
		int open, close;

		Pair(int a, int b) {
			open = a;
			close = b;
		}

		void negate() {
			open ^= close;
			close ^= open;
			open ^= close;
		}

		@Override
		public String toString() {
			return open + " " + close;
		}
	}

	static char[] c;

	static class SegmentTree {
		int N;
		Pair[] tree;

		int left(int p) {
			return p << 1;
		}

		int mid(int L, int R) {
			return (L + R) >> 1;
		}

		Pair merge(Pair left, Pair right) {
			int min = Math.min(left.open, right.close);
			return new Pair(left.open + right.open - min, right.close + left.close - min);
		}

		SegmentTree(int n) {
			N = n;
			tree = new Pair[N << 2];
			build(1, 0, N - 1);
		}

		void build(int p, int L, int R) {
			if (L == R) {
				if (c[L] == '(')
					tree[p] = new Pair(1, 0);
				else
					tree[p] = new Pair(0, 1);
			} else {
				int mid = mid(L, R), left = left(p), right = left | 1;
				build(left, L, mid);
				build(right, mid + 1, R);
				tree[p] = merge(tree[left], tree[right]);
			}
		}

		void update(int idx) {
			update(1, 0, N - 1, idx);
		}

		void update(int p, int L, int R, int idx) {
			if (L > idx || R < idx)
				return;
			if (L == R && L == idx)
				tree[p].negate();
			else {
				int mid = mid(L, R), left = left(p), right = left | 1;
				if (idx <= mid)
					update(left, L, mid, idx);
				else
					update(right, mid + 1, R, idx);
				tree[p] = merge(tree[left], tree[right]);
			}
		}

		Pair query() {
			return tree[1];
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		for (int t = 1; sc.ready(); t++) {
			int n = sc.nextInt();
			c = sc.next().toCharArray();
			SegmentTree st = new SegmentTree(n);
			int q = sc.nextInt();
			out.printf("Test %d:\n", t);
			while (q-- > 0) {
				int x = sc.nextInt() - 1;
				if (x < 0) {
					System.err.println(st.query());
					Pair p = st.query();
					out.println(p.open == p.close && p.open == 0 ? "YES" : "NO");
				} else {
					st.update(x);
					System.err.println(Arrays.toString(st.tree));
				}
			}
		}
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
