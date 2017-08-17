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

/*-
 * @author Mazen Magdy
 *				-We store the number of 3-tuples such that their result is a * b + c. Now we need to find
 *				 such 3-tuples d * (f + e) that's equal to the above 3-tuple, the answer for this tuple would be
 *				 the number of occurrences.
 */
public class ABCDEF {

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		long[] x = new long[n];
		for (int i = 0; i < n; i++)
			x[i] = sc.nextLong();
		HashMap<Long, Integer> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			long a = x[i];
			for (int j = 0; j < n; j++) {
				long b = x[j];
				for (int k = 0; k < n; k++) {
					long term = a * b + x[k];
					Integer y = map.get(term);
					y = y == null ? 1 : y + 1;
					map.put(term, y);
				}
			}
		}
		long ans = 0;
		for (int i = 0; i < n; i++) {
			long d = x[i];
			if (d == 0)
				continue;
			for (int j = 0; j < n; j++) {
				long f = x[j];
				for (int k = 0; k < n; k++) {
					long res = d * (f + x[k]);
					Integer y = map.get(res);
					if (y == null)
						continue;
					ans += y;
				}
			}
		}
		out.println(ans);
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
