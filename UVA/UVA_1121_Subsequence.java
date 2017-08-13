package UVA;

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
 *				-We can either use Sliding Window or BinarySearch.
 */
public class UVA_1121_Subsequence {

	static long getSum(long[] a, int s, int e) {
		return a[e] - (s == 0 ? 0 : a[s - 1]);
	}

	// O(nlogn)
	static void sol(MyScanner sc, PrintWriter out) throws IOException {
		int n = sc.nextInt(), s = sc.nextInt();
		long[] a = new long[n];
		for (int i = 0; i < n; i++) {
			a[i] = sc.nextLong();
			if (i > 0)
				a[i] += a[i - 1];
		}
		int len = n + 1;
		for (int i = 0; i < n; i++) {
			int low = i, high = n - 1, ans = -1;
			while (low <= high) {
				int mid = (low + high) >> 1;
				if (getSum(a, i, mid) >= s) {
					ans = mid;
					high = mid - 1;
				} else
					low = mid + 1;
			}
			if (ans != -1)
				len = Math.min(len, ans - i + 1);
		}
		out.println(len == n + 1 ? 0 : len);
	}

	// O(n)
	static void sol2(MyScanner sc, PrintWriter out) throws IOException {
		int n = sc.nextInt(), s = sc.nextInt();
		long[] a = new long[n];
		for (int i = 0; i < n; i++)
			a[i] = sc.nextLong();
		int len = n + 1;
		for (int i = 0, j = 0, sum = 0; i < n; i++) {
			while (j < n && sum < s)
				sum += a[j++];
			if (sum >= s)
				len = Math.min(len, j - i);
			sum -= a[i];
		}
		out.println(len == n + 1 ? 0 : len);
	}

	public static void main(String[] args) throws IOException {
		// MyScanner sc = new MyScanner(System.in);
		MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			// sol(sc, out);
			sol2(sc, out);
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
