package ZOJ;

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
 *				-We use exclusion-inclusion principle, the number of numbers divisible by x <= m
 *				 are m / x but then we need to exclude the numbers divisible by x and y using m / lcm(x, y).
 *				 We use a mask to define our subset and exclude or include the result depending on the size of
 *				 the subset.
 */
public class ZOJ_2836_NumberPuzzle {

	static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	static int lcm(int a, int b) {
		return a * (b / gcd(a, b));
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			int n = sc.nextInt();
			long m = sc.nextLong();
			int[] a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = sc.nextInt();
			long res = 0;
			for (int msk = 1; msk < 1 << n; msk++) {
				int lcm = 1;
				for (int i = 0; i < n; i++) {
					if ((msk & (1 << i)) != 0)
						lcm = lcm(lcm, a[i]);
				}
				long x = m / lcm;
				if ((Integer.bitCount(msk) & 1) != 0)
					res += x;
				else
					res -= x;
			}
			out.println(res);
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
