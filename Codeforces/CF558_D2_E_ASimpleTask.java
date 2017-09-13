package Codeforces;

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
 *				-Imagine we sort a segment using counting sort. This gives a complexity of
 *				 O(nq) which is still slow. Now write the code for counting sort on a paper,
 *				 we would need to keep track of the number of letters in a segment and after
 *				 retrieving that sort the segment which in turns changes the occurrences in
 *				 the segment. So we use a Segment Tree where we update a range clearing it or
 *				 filling it with the occurrences of a letter in a range. We need 26 SegmnetTree
 *				 1 for each letter.
 */
public class CF558_D2_E_ASimpleTask {

	static class SegmentTree {
		int N;
		int[] tree, lazy;

		int left(int p) {
			return p << 1;
		}

		int mid(int L, int R) {
			return (L + R) >> 1;
		}

		void propagate(int p, int L, int R) {
			if (lazy[p] == 0 || L == R)
				return;
			int mid = mid(L, R), left = left(p), right = left | 1;
			if (lazy[p] == 1) {
				tree[left] = mid - L + 1;
				tree[right] = R - mid;
				lazy[left] = lazy[right] = 1;
			} else if (lazy[p] == 2) {
				tree[left] = tree[right] = 0;
				lazy[left] = lazy[right] = 2;
			}
			lazy[p] = 0;
		}

		SegmentTree(int n) {
			N = n;
			tree = new int[N << 2];
			lazy = new int[N << 2];
		}

		void update(int idx, int val) {
			update(1, 0, N - 1, idx, val);
		}

		void update(int p, int L, int R, int idx, int val) {
			if (L > idx && R < idx)
				return;
			if (L == R && L == idx)
				tree[p] += val;
			else {
				int mid = mid(L, R), left = left(p), right = left | 1;
				propagate(p, L, R);
				if (idx <= mid)
					update(left, L, mid, idx, val);
				else
					update(right, mid + 1, R, idx, val);
				tree[p] = tree[left] + tree[right];
			}
		}

		void update(int i, int j, int op) {
			update(1, 0, N - 1, i, j, op);
		}

		void update(int p, int L, int R, int i, int j, int op) {
			if (L > j || R < i)
				return;
			if (L >= i && R <= j) {
				if (op == 0) {
					tree[p] = 0;
					lazy[p] = 2;
				} else {
					tree[p] = R - L + 1;
					lazy[p] = 1;
				}
			} else {
				int mid = mid(L, R), left = left(p), right = left | 1;
				propagate(p, L, R);
				update(left, L, mid, i, j, op);
				update(right, mid + 1, R, i, j, op);
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
		int n = sc.nextInt(), q = sc.nextInt();
		SegmentTree[] st = new SegmentTree[26];
		char[] c = sc.next().toCharArray();
		for (int i = 0; i < 26; i++)
			st[i] = new SegmentTree(n);
		for (int i = 0; i < c.length; i++) {
			int idx = c[i] - 'a';
			st[idx].update(i, 1);
		}
		while (q-- > 0) {
			int i = sc.nextInt() - 1, j = sc.nextInt() - 1, k = sc.nextInt();
			int[] cnt = new int[26];
			for (int l = 0; l < 26; l++)
				cnt[l] = st[l].query(i, j);
			int idx = i;
			if (k == 0) {
				for (int l = 25; l >= 0; l--)
					if (cnt[l] != 0) {
						st[l].update(i, j, 0);
						st[l].update(idx, idx + cnt[l] - 1, 1);
						idx += cnt[l];
					}
			} else if (k == 1) {
				for (int l = 0; l < 26; l++) {
					if (cnt[l] != 0) {
						st[l].update(i, j, 0);
						st[l].update(idx, idx + cnt[l] - 1, 1);
						idx += cnt[l];
					}
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++)
			for (int j = 0; j < 26; j++)
				if (st[j].query(i, i) != 0) {
					sb.append((char) (j + 'a'));
					break;
				}
		out.println(sb);
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
