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
 *				-For each two possible games that the first person can win in
 *				 we calculate how many ball can be drawn such that the total
 *				 would be greater than the two balls.
 */
public class CF626_D12_D_JerrysProtest {

	static final int MAX = 5000;

	static int getSum(int[] a, int l, int r) {
		return a[Math.min(MAX, r)] - (l == 0 ? 0 : a[Math.min(l - 1, MAX)]);
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = sc.nextInt();
		Arrays.sort(a);
		int[] c = new int[MAX + 1];
		for (int i = 0; i < n; i++)
			for (int j = i + 1; j < n; j++)
				c[a[j] - a[i]]++;
		int[] d = new int[MAX + 1];
		d[0] = c[0];
		for (int i = 1; i <= MAX; i++)
			d[i] = d[i - 1] + c[i];
		long ans = 0, rest = 0;
		for (int i = 0; i <= MAX; i++)
			for (int j = 0; j <= MAX; j++)
				if (c[i] > 0 && c[j] > 0) {
					ans += c[i] * 1L * c[j] * 1L * getSum(d, i + j + 1, MAX);
					rest += c[i] * 1L * c[j] * 1L * getSum(d, 0, i + j);
				}
		out.println(ans * 1.0 / (ans + rest));
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

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws IOException {
			return Long.parseLong(next());
		}
	}
}
