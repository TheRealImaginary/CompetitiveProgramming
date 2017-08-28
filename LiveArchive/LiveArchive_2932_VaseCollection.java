package LiveArchive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.TreeSet;

/*-
 * @author Mazen Magdy
 *				-If we can pick `k` vases with `k * k` features, then we can pick `k - 1`  vases with
 *				 `(k - 1) * (k - 1)` features. Hence `k` is monotonic and we can use BinarySearch to
 *				 find the maximum `k` that works. To check whether we a specific `k` works or no, we use
 *				 f(idx, need, msk, k) meaning we picked a subset of vases till index `idx` and we still need `need`
 *				 vases such that all vases are having features in `msk` and the count of the features is `k`. Note
 *				 that the answer cannot be larger than `10` since we have 100 items.
 */
public class LiveArchive_2932_VaseCollection {

	static final int MAX = 37;

	static int N;
	static long[] decorations;
	static int[] vase;

	static boolean can(int idx, int need, long msk, int k) {
		if (need < k && Long.bitCount(msk) < k)
			return false;
		if (need == 0)
			return true;
		if (idx == N)
			return false;
		boolean f = false;
		if (need == k)
			f |= can(idx + 1, need - 1, decorations[vase[idx]], k);
		else
			f |= can(idx + 1, need - 1, msk & decorations[vase[idx]], k);
		return f || can(idx + 1, need, msk, k);
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t-- > 0) {
			N = sc.nextInt();
			TreeSet<Integer> set = new TreeSet<>();
			decorations = new long[MAX];
			for (int i = 0; i < N; i++) {
				int s = sc.nextInt(), d = sc.nextInt();
				decorations[s] |= (1L << d);
				set.add(s);
			}
			vase = new int[N];
			int idx = 0;
			for (Integer x : set)
				vase[idx++] = x;
			N = idx;
			int low = 0, high = 10, ans = 0;
			while (low <= high) {
				int mid = (low + high) >> 1;
				if (can(0, mid, 0, mid)) {
					ans = mid;
					low = mid + 1;
				} else
					high = mid - 1;
			}
			out.println(ans);
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
