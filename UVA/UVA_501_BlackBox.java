package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

// Can be solved using 2 Priority Queues
public class UVA_501_BlackBox {

	static class SegmentTree {
		int N;
		int[] tree;

		int left(int p) {
			return p << 1;
		}

		int right(int p) {
			return left(p) | 1;
		}

		int mid(int L, int R) {
			return (L + R) >> 1;
		}

		SegmentTree(int n) {
			N = n;
			tree = new int[N << 2];
		}

		void update(int i) {
			update(1, 0, N - 1, i);
		}

		void update(int p, int L, int R, int idx) {
			if (L > idx || R < idx)
				return;
			if (L == R && R == idx)
				tree[p]++;
			else {
				int mid = mid(L, R), left = left(p), right = right(p);
				if (idx <= mid)
					update(left, L, mid, idx);
				else
					update(right, mid + 1, R, idx);
				tree[p] = tree[left] + tree[right];
			}
		}

		int query(int k) {
			return query(1, 0, N - 1, k);
		}

		int query(int p, int L, int R, int k) {
			int mid = mid(L, R), left = left(p), right = right(p);
			if (L > R)
				return -1;
			if (L == R)
				return L;
			if (tree[left] >= k)
				return query(left, L, mid, k);
			return query(right, mid + 1, R, k - tree[left]);
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt(), m = sc.nextInt();
			int[] a = new int[n];
			ArrayList<Integer> b = new ArrayList<>(n);
			for (int i = 0; i < n; i++)
				b.add(a[i] = sc.nextInt());
			Collections.sort(b);
			Map<Integer, Integer> map = new HashMap<>();
			Map<Integer, Integer> unmap = new HashMap<>();
			int id = 0;
			for (int i = 0; i < n; i++)
				if (!map.containsKey(b.get(i))) {
					map.put(b.get(i), id);
					unmap.put(id++, b.get(i));
				}
			int[] q = new int[m];
			for (int i = 0; i < m; i++)
				q[i] = sc.nextInt();
			int k = 0;
			SegmentTree st = new SegmentTree(n);
			for (int i = 0, j = 0; i < n; i++) {
				st.update(map.get(a[i]));
				while (j < m && q[j] == i + 1) {
					k++;
					out.println(unmap.get(st.query(k)));
					j++;
				}
			}
			if (tc > 0)
				out.println();
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
