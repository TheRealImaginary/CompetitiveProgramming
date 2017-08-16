package SPOJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 * @see	   http://www.spoj.com/problems/NHAY/
 *				-We use KMP to calculate the Fail Function for each index. Now for each index
 *				 We simply check whether the value is equal to the length of the pattern or no.
 */
public class NAJPF {

	static int[] KMP(char[] c) {
		int n = c.length;
		int[] f = new int[n];
		for (int i = 1, j = 0; i < n; i++) {
			while (j > 0 && c[i] != c[j])
				j = f[j - 1];
			if (c[i] == c[j])
				j++;
			f[i] = j;
		}
		return f;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			String s = sc.next(), p = sc.next();
			StringBuilder sb = new StringBuilder(p);
			sb.append("#").append(s);
			int[] f = KMP(sb.toString().toCharArray());
			int n = f.length, m = p.length();
			ArrayList<Integer> ans = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				if (f[i] == m) {
					ans.add(i - m - 1 - m + 2);
				}
			}
			if (ans.size() > 0) {
				out.println(ans.size());
				for (int i = 0; i < ans.size(); i++) {
					out.print(ans.get(i));
					if (i != ans.size() - 1)
						out.print(" ");
				}
				out.println();
			} else
				out.println("Not Found");
			if (tc > 0)
				out.println();
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
