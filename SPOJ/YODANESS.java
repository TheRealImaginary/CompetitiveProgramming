package SPOJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * @author Mazen Magdy
 * We want to count the number of pairs of words that appear out of order,
 * and so this problem just needs us to report the Inversion Index, There are many ways
 * of doing so (Segment Tree, Fenwick Tree, Merge Sort), I chose Merge Sort.
 */
public class YODANESS {

	static final int oo = 1 << 27;

	static long inversionIndex;

	static void mergeSort(int[] a, int L, int R) {
		if (L == R) {
			return;
		} else {
			int mid = (L + R) >> 1;
			mergeSort(a, L, mid);
			mergeSort(a, mid + 1, R);
			merge(a, L, mid, R);
		}
	}

	static void merge(int[] a, int L, int mid, int R) {
		int n1 = mid - L + 1;
		int n2 = R - mid;

		int[] l = new int[n1 + 1];
		int[] r = new int[n2 + 1];
		for (int i = 0; i < n1; i++)
			l[i] = a[L + i];
		for (int i = 0; i < n2; i++)
			r[i] = a[mid + i + 1];
		l[n1] = r[n2] = oo;

		for (int k = L, i = 0, j = 0; k <= R; k++) {
			if (l[i] <= r[j])
				a[k] = l[i++];
			else {
				inversionIndex += n1 - i;
				a[k] = r[j++];
			}
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt();
			String[] s = new String[n];
			for (int i = 0; i < n; i++)
				s[i] = sc.next();
			HashMap<String, Integer> map = new HashMap<>();
			int idx = 0;
			for (int i = 0; i < n; i++) {
				String a = sc.next();
				if (!map.containsKey(a))
					map.put(a, idx++);
			}
			int[] a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = map.get(s[i]);
			inversionIndex = 0;
			mergeSort(a, 0, n - 1);
			out.println(inversionIndex);
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

		public MyScanner(String file) throws IOException {
			br = new BufferedReader(new FileReader(new File(file)));
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
