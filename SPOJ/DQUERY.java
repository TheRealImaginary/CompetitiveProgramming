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
 *				-We can use a Segment Tree or a Fenwick Tree, When we encounter a element
 *				 we put it in the BIT/SegmentTree, if we encounter it again we remove it
 *				 and insert it in the new index and answer each query.
 */
public class DQUERY {

	static final int MAX = 1 << 20;

	static class Query implements Comparable<Query> {
		int l, r, idx;

		Query(int a, int b, int i) {
			l = a;
			r = b;
			idx = i;
		}

		@Override
		public String toString() {
			return l + " " + r;
		}

		@Override
		public int compareTo(Query q) {
			if(r != q.r)
				return r - q.r;
			return l - q.l;
		}
	}

	static class FenwickTree {
		int[] tree;
		int N;

		FenwickTree(int n) {
			N = n;
			tree = new int[n];
		}

		void update(int idx, int val) {
			while (idx <= N) {
				tree[idx] += val;
				idx += (idx & -idx);
			}
		}

		int sum(int idx) {
			int sum = 0;
			while (idx > 0) {
				sum += tree[idx];
				idx -= (idx & -idx);
			}
			return sum;
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[] a = new int[n + 1];
		for (int i = 1; i <= n; i++)
			a[i] = sc.nextInt();
		int m = sc.nextInt();
		Query[] q = new Query[m];
		for (int i = 0; i < m; i++)
			q[i] = new Query(sc.nextInt(), sc.nextInt(), i);
		Arrays.sort(q);
		int[] before = new int[MAX];
		Arrays.fill(before, -1);
		int[] ans = new int[m];
		FenwickTree ft = new FenwickTree(n + 2);
		for (int i = 1, j = 0; i <= n; i++) {
			if (before[a[i]] != -1)
				ft.update(before[a[i]], -1);
			before[a[i]] = i;
			ft.update(i, 1);
			while (j < m && q[j].r == i) {
				ans[q[j].idx] = ft.sum(q[j].r) - ft.sum(q[j].l - 1);
				j++;
			}
		}
		for (int i = 0; i < m; i++)
			out.println(ans[i]);
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
