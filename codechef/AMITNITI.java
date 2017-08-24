package codechef;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We split the array into two parts, each of size 16, and calculate the sum of each
 *				 possible subset for each array (2^16 + 2^16). Then for each number we simply check
 *				 if there are two subsets to make up the number with them.
 */
public class AMITNITI {

	static final int N = 33;
	static final int M = 16;
	static final int K = 16;

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		long[] f = new long[N + 1];
		f[1] = 2;
		f[2] = 7;
		for (int i = 3; i <= N; i++)
			f[i] = ((i & 1) == 0) ? f[i - 1] + 7 : f[i - 1] + 3 * f[i - 2];
		ArrayList<Long> a = new ArrayList<>();
		for (int msk = 0; msk < 1 << M; msk++) {
			long sum = 0;
			for (int i = 0; i < M; i++)
				if ((msk & (1 << i)) != 0)
					sum += f[i + 1];
			a.add(sum);
		}
		ArrayList<Long> b = new ArrayList<>();
		for (int msk = 0; msk < 1 << K; msk++) {
			long sum = 0;
			for (int i = 0; i < K; i++)
				if ((msk & (1 << i)) != 0)
					sum += f[M + i + 1];
			b.add(sum);
		}
		Collections.sort(b);
		int t = sc.nextInt();
		while (t-- > 0) {
			long n = sc.nextLong();
			boolean flag = false;
			for (long x : a) {
				int idx = Collections.binarySearch(b, n - x);
				if (idx >= 0) {
					flag = true;
					break;
				}
			}
			out.println(flag ? "YES" : "NO");
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
