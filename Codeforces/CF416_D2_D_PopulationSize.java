package Codeforces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class CF416_D2_D_PopulationSize {

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		long[] a = new long[n];
		for (int i = 0; i < n; i++)
			a[i] = sc.nextInt();
		int i = 0, ans = 0;
		while (i < n) {
			ans++;
			int j = i;
			while (j < n && a[j] == -1)
				j++;
			if(j == n)
				break;
		
			int k = j + 1;
			while (k < n && a[k] == -1)
				k++;
			if(k == n)
				break;
			
			long d = (a[k] - a[j]);
			if (d % (k - j) != 0) {
				i = k;
				continue;
			}
			d /= (k - j);
			if (d > 0 && a[j] - d * (j - i) <= 0) {
				i = k;
				continue;
			}
			j = k;
			k++;
			while (k < n) {
				long shouldBe = a[j] + d * (k - j);
				if (shouldBe <= 0)
					break;
				if (a[k] == -1 || a[k] == shouldBe) {
					k++;
					continue;
				}
				break;
			}
			i = k;
		}
		out.println(ans);
		out.flush();
		out.close();
	}

	static class MyScanner {
		StringTokenizer st;
		BufferedReader br;

		public MyScanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
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
