package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We find Palindrome in s$r and r$s , where `r` is the reverse of `s`
 *				 Then for each `i`, 1<=i<n We check their existance in s$r and r$s
 *				 respectively, meaning we have a palindrome of length `i` and `n - i`.
 */
public class UVA_11888_Abnormal89s {

	static boolean[] go(String s) {
		int n = s.length();
		int[] pi = new int[n];
		for (int i = 1, j = 0; i < n; i++) {
			while (j > 0 && s.charAt(j) != s.charAt(i))
				j = pi[j - 1];
			if (s.charAt(i) == s.charAt(j))
				j++;
			pi[i] = j;
		}
		// System.err.println(Arrays.toString(pi));
		boolean[] res = new boolean[n + 1];
		int j = n - 1;
		while (j > 0) {
			res[pi[j]] = true;
			j = pi[j - 1];
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			String s = sc.next();
			String r = new StringBuilder(s).reverse().toString();
			String sr = new StringBuilder(s).append("$").append(r).toString();
			String rs = new StringBuilder(r).append("$").append(s).toString();
			boolean[] a = go(sr), b = go(rs);
			// System.err.println(Arrays.toString(a));
			// System.err.println(Arrays.toString(b));
			int n = s.length();
			boolean alindrome = false;
			for (int i = 1; i < n; i++)
				if (a[i] && b[n - i]) {
					alindrome = true;
					break;
				}
			out.println(alindrome ? "alindrome" : a[n] ? "palindrome" : "simple");
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

		boolean ready() throws IOException {
			return br.ready();
		}
	}
}
