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
 *				-We use a Segment Tree to flip a range and query the total number of 1s in this range.
 *				 So a node can carry the sum and for flipping the sum becomes N - sum, where N is the
 *				 length of the node's range.
 */
public class LITE {

	static class SegmentTree {

		int N;
		int[] tree;
		int[] lazy;

		int left(int p) {
			return p << 1;
		}

		int mid(int L, int R) {
			return (L + R) >> 1;
		}

		void propagate(int p, int L, int R) {
			if (L == R || lazy[p] == 0)
				return;
			int mid = mid(L, R), left = left(p), right = left | 1;
			tree[left] = (mid - L + 1) - tree[left];
			tree[right] = (R - mid) - tree[right];
			lazy[left] = (lazy[left] + lazy[p]) % 2;
			lazy[right] = (lazy[right] + lazy[p]) % 2;
			lazy[p] = 0;
		}

		SegmentTree(int n) {
			N = n;
			tree = new int[N << 2];
			lazy = new int[N << 2];
		}

		void flip(int i, int j) {
			flip(1, 0, N - 1, i, j);
		}

		void flip(int p, int L, int R, int i, int j) {
			if (L > j || R < i)
				return;
			if (L >= i && R <= j) {
				tree[p] = (R - L + 1) - tree[p];
				lazy[p] = (lazy[p] + 1) % 2;
			} else {
				int mid = mid(L, R), left = left(p), right = left | 1;
				propagate(p, L, R);
				flip(left, L, mid, i, j);
				flip(right, mid + 1, R, i, j);
				tree[p] = tree[left] + tree[right];
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
			int mid = mid(L, R), left = left(p), right = left | 1;
			propagate(p, L, R);
			return query(left, L, mid, i, j) + query(right, mid + 1, R, i, j);
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int N = sc.nextInt(), Q = sc.nextInt();
		StringBuilder sb = new StringBuilder();
		SegmentTree st = new SegmentTree(N);
		while (Q-- > 0) {
			int type = sc.nextInt(), l = sc.nextInt() - 1, r = sc.nextInt() - 1;
			if (type == 0)
				st.flip(l, r);
			else
				sb.append(st.query(l, r)).append('\n');
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
