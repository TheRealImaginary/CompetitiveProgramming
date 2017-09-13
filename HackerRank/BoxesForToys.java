package HackerRank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-Idea: Calculate the answer for index j using values calculated at index i, such
 *				 that j > i (Could also be done in reverse).
 *				-Lets process the first box, we only have its area in the answer, now the second box we
 *				 will call this box `b`, we can have two cases, either b has a dimension greater than the 
 *				 previous one, or it's less than `b`. Let's check dimension `a`, If this dimension is greater
 *				 than for box `b` then we need to adjust the volume and add it to the answer but also accounting
 *				 for both boxes (sub-array of size 1 and 2), if the volume of the previous box was in an array
 *				 [v1, 1, 1, 1, ..] let's call it `v`, then we need to update the volume at index 0 and 1 using the new
 *				 dimension, meaning that box `b`'s dimension can cover itself or both boxes, so we get an array
 *				 [v3, v2, 1, 1, ...], now for the second case if the dimension is smaller we need to update index 1
 *				 only and add the sum of the array to the answer, meaning we have sub-array of size 1 and subarray of
 *				 size 2, covered by box `b` and previous box respectively.
 */
// 101 Hack 50
public class BoxesForToys {

	static final long MOD = (long) 1e9 + 7;

	static class Box {
		int[] x;

		Box(int a, int b, int c) {
			x = new int[] { a, b, c };
			Arrays.sort(x);
		}

		@Override
		public String toString() {
			return Arrays.toString(x);
		}
	}

	static class SegmentTree {
		int N;
		long[] tree, lazy;

		int left(int p) {
			return p << 1;
		}

		int mid(int L, int R) {
			return (L + R) >> 1;
		}

		void propagate(int p, int L, int R) {
			if (lazy[p] == 1 || L == R)
				return;
			int left = left(p), right = left | 1;
			tree[left] = mult(tree[left], lazy[p]);
			tree[right] = mult(tree[right], lazy[p]);
			lazy[left] = mult(lazy[left], lazy[p]);
			lazy[right] = mult(lazy[right], lazy[p]);
			lazy[p] = 1;
		}

		SegmentTree(int n) {
			N = n;
			tree = new long[N << 2];
			lazy = new long[N << 2];
			build(1, 0, N - 1);
		}

		void build(int p, int L, int R) {
			lazy[p] = 1;
			if (L == R) {
				tree[p] = 1;
			} else {
				int mid = mid(L, R), left = left(p), right = left | 1;
				build(left, L, mid);
				build(right, mid + 1, R);
				tree[p] = add(tree[left], tree[right]);
			}
		}

		void update(int i, int j, long val) {
			update(1, 0, N - 1, i, j, val);
		}

		void update(int p, int L, int R, int i, int j, long val) {
			if (L > j || R < i)
				return;
			if (L >= i && R <= j) {
				tree[p] = mult(tree[p], val);
				lazy[p] = mult(lazy[p], val);
			} else {
				int mid = mid(L, R), left = left(p), right = left | 1;
				propagate(p, L, R);
				update(left, L, mid, i, j, val);
				update(right, mid + 1, R, i, j, val);
				tree[p] = add(tree[left], tree[right]);
			}
		}

		long query(int i, int j) {
			return query(1, 0, N - 1, i, j);
		}

		long query(int p, int L, int R, int i, int j) {
			if (L > j || R < i)
				return 0;
			if (L >= i && R <= j)
				return tree[p];
			int mid = mid(L, R), left = left(p), right = left | 1;
			propagate(p, L, R);
			return add(query(left, L, mid, i, j), query(right, mid + 1, R, i, j));
		}
	}

	static long mult(long a, long b) {
		return ((a % MOD) * (b % MOD)) % MOD;
	}

	static long add(long a, long b) {
		return ((a % MOD) + (b % MOD)) % MOD;
	}

	static long modPow(long base, long exp) {
		long res = 1;
		while (exp > 0) {
			if ((exp & 1) != 0)
				res = mult(res, base);
			base = mult(base, base);
			exp >>= 1;
		}
		return res;
	}

	static long f(int n) {
		return mult(mult(n, n + 1), modPow(2, MOD - 2));
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		Box[] b = new Box[n];
		for (int i = 0; i < n; i++)
			b[i] = new Box(sc.nextInt(), sc.nextInt(), sc.nextInt());
		Stack<Integer>[] stacks = new Stack[3];
		for (int i = 0; i < 3; i++) {
			stacks[i] = new Stack<>();
			stacks[i].push(-1);
		}
		long ans = 0;
		SegmentTree st = new SegmentTree(n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 3; j++) {
				// >= would work too
				while (stacks[j].size() >= 2 && b[i].x[j] > b[stacks[j].peek()].x[j]) {
					int x = stacks[j].pop();
					st.update(stacks[j].peek() + 1, x, modPow(b[x].x[j], MOD - 2));
				}
				st.update(stacks[j].peek() + 1, i, b[i].x[j]);
				stacks[j].push(i);
			}
			ans = add(ans, st.query(0, i));
		}
		out.println(mult(ans, modPow(f(n), MOD - 2)));
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
