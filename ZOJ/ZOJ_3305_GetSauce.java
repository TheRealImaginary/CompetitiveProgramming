package ZOJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

/*-
 * @author Mazen Magdy
 *				-
 */
public class ZOJ_3305_GetSauce {

	static int N, M;
	static int[] a;
	static int[] dp;

	static int solve(int msk) {
		if (msk == (1 << N) - 1)
			return 0;
		if (dp[msk] != -1)
			return dp[msk];
		int res = 0;
		for (int i = 0; i < M; i++)
			if ((msk & a[i]) == 0)
				res = Math.max(res, solve(msk | a[i]) + 1);
		return dp[msk] = res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			if (sc.nextEmpty())
				continue;
			N = sc.nextInt();
			M = sc.nextInt();
			TreeSet<Integer> set = new TreeSet<Integer>();
			for (int i = 0; i < M; i++) {
				int k = sc.nextInt();
				int msk = 0;
				for (int j = 0; j < k; j++)
					msk |= (1 << (sc.nextInt() - 1));
				set.add(msk);
			}
			a = new int[M = set.size()];
			for (int i = 0; !set.isEmpty(); i++)
				a[i] = set.pollFirst();
			// System.err.println(M);
			// System.err.println(Arrays.toString(a));
			dp = new int[1 << N];
			Arrays.fill(dp, -1);
			out.println(solve(0));
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

		boolean nextEmpty() throws IOException {
			String s = nextLine();
			st = new StringTokenizer(s);
			return s.isEmpty();
		}
	}
}
