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
 *				-We store the number of occurrences of a + b, and search we add to the answer
 *				 the number of occurrences of -(c + d)
 */
public class SUMFOUR {

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[] a = new int[n];
		int[] b = new int[n];
		int[] c = new int[n];
		int[] d = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = sc.nextInt();
			b[i] = sc.nextInt();
			c[i] = sc.nextInt();
			d[i] = sc.nextInt();
		}
		HashMap<Long, Integer> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			int x = a[i];
			for (int j = 0; j < n; j++) {
				long term = x * 1L + b[j];
				Integer y = map.get(term);
				y = y == null ? 1 : y + 1;
				map.put(term, y);
			}
		}
		long ans = 0;
		for (int i = 0; i < n; i++) {
			int x = c[i];
			for (int j = 0; j < n; j++) {
				long term = x * 1L + d[j];
				Integer y = map.get(-term);
				if(y == null)
					continue;
				ans += y;
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
