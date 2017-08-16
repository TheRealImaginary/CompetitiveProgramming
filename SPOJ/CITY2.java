package SPOJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*-
 * @author Mazen Magdy
 *				-A two buildings are the same iff its height is lower than all buildings between them
 *				 so we use a SegmentTree to find the minimum height between to buildings if the building's
 *				 height is lower than that value then it's lower than all other values and hence same building
 *				 otherwise new building.
 */
public class CITY2 {

	static final long oo = 1L << 40;

	static int[] a;

	static class SegmentTree {

		int N;
		long[] tree;

		int left(int p) {
			return p << 1;
		}

		int mid(int L, int R) {
			return (L + R) >> 1;
		}

		SegmentTree(int n) {
			N = n;
			tree = new long[N << 2];
			build(1, 0, N - 1);
		}

		void build(int p, int L, int R) {
			if (L == R) {
				tree[p] = a[L];
			} else {
				int mid = mid(L, R), left = left(p), right = left | 1;
				build(left, L, mid);
				build(right, mid + 1, R);
				tree[p] = Math.min(tree[left], tree[right]);
			}
		}

		long query(int i, int j) {
			return query(1, 0, N - 1, i, j);
		}

		long query(int p, int L, int R, int i, int j) {
			if (L > j || R < i)
				return oo;
			if (L >= i && R <= j)
				return tree[p];
			else {
				int mid = mid(L, R), left = left(p), right = left | 1;
				return Math.min(query(left, L, mid, i, j), query(right, mid + 1, R, i, j));
			}
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		for (int t = 1; sc.ready(); t++) {
			int n = sc.nextInt();
			a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = sc.nextInt();
			SegmentTree st = new SegmentTree(n);
			Map<Integer, Integer> map = new TreeMap<>();
			int ans = 0;
			for (int i = 0; i < n; i++) {
				if (a[i] == 0)
					continue;
				if (!map.containsKey(a[i])) {
					ans++;
					map.put(a[i], i);
				} else {
					long min = st.query(map.get(a[i]), i);
					if (min < a[i])
						ans++;
					map.put(a[i], i);
				}
			}
			out.printf("Case %d: %d\n", t, ans);
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
