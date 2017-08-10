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
 *				-Let S be the total, we need to pick N numbers with sum A, answer is A * (S - A)
 *				 We need to know whether we can make A using N numbers from N + M, and choose the one
 *				 the leads to min and max.	
 */
public class UVA_10690_ExpressionAgain {

	static final int oo = 1 << 28;
	static final int OFFSET = 2500;
	static final int MAX = 5000;

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			if (sc.nextEmpty())
				continue;
			int N = sc.nextInt(), M = sc.nextInt();
			int total = 0;
			int[] a = new int[N + M];
			for (int i = 0; i < N + M; i++)
				total += a[i] = sc.nextInt();
			boolean[][] dp = new boolean[N + 1][MAX + 1];
			dp[0][OFFSET] = true;
			for (int i = 0; i < N + M; i++)
				for (int j = N - 1; j >= 0; j--)
					for (int k = 0; k <= MAX; k++)
						if (dp[j][k])
							dp[j + 1][k + a[i]] = true;
			int min = oo, max = -oo;
			for (int term = MAX; term >= 0; term--) {
				if (dp[N][term]) {
					min = Math.min(min, (term - OFFSET) * (total - term + OFFSET));
					max = Math.max(max, (term - OFFSET) * (total - term + OFFSET));
				}
			}
			out.printf("%d %d\n", max, min);
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

		public boolean nextEmpty() throws IOException {
			String s = nextLine();
			st = new StringTokenizer(s);
			return s.isEmpty();
		}
	}
}
