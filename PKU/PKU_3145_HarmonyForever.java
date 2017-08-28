package PKU;

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
 *				-
 */
public class PKU_3145_HarmonyForever { // TLE

	static final int oo = 1 << 28;
	static final int MAX = 500000;

	static class SegmentTree {
		int N;
		int[] tree;

		int left(int p) {
			return p << 1;
		}

		int mid(int L, int R) {
			return (L + R) >> 1;
		}

		SegmentTree(int n) {
			N = n;
			tree = new int[N << 2];
			Arrays.fill(tree, oo);
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
				int mid = mid(L, R), left = left(p), right = left | 1;
				if (idx <= mid)
					update(left, L, mid, idx, val);
				else
					update(right, mid + 1, R, idx, val);
				tree[p] = Math.min(tree[left], tree[right]);
			}
		}

		int query(int i, int j) {
			return query(1, 0, N - 1, i, j);
		}

		int query(int p, int L, int R, int i, int j) {
			if (L > j || R < i)
				return oo;
			if (L >= i && R <= j)
				return tree[p];
			int mid = mid(L, R), left = left(p), right = left | 1;
			return Math.min(query(left, L, mid, i, j), query(right, mid + 1, R, i, j));
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		for (int tc = 1; true; tc++) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			SegmentTree st = new SegmentTree(MAX + 1);
			int[] time = new int[MAX + 1];
			int t = 1;
			if (tc > 1)
				out.println();
			out.printf("Case %d:\n", tc);
			while (n-- > 0) {
				char op = sc.next().charAt(0);
				if (op == 'A') {
					int y = sc.nextInt();
					int res = oo, minMod = oo;
					for (int k = 0; true; k++) {
						int left = k * y, right = Math.min(MAX, ((k + 1) * y) - 1);
						if (left > right)
							break;
						int min = st.query(left, right);
						if (min < oo) {
							int r = min % y;
							if (r < minMod || (r == minMod && time[min] > time[res])) {
								minMod = min % y;
								res = min;
							}
						}
					}
					if (res >= oo)
						out.println(-1);
					else
						out.println(time[res]);
				} else if (op == 'B') {
					int x = sc.nextInt();
					time[x] = Math.max(time[x], t++);
					st.update(x, x);
				}
			}
		}
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
