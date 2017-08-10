package UVA;

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
 *				-For each journey, We can either decrease its time if we can or no, we try them
 *				 all and take the minimum.
 */
public class UVA_12324_PhilipJFryProblem {

	static final int oo = 1 << 28;

	static int N;
	static int[] t, b, dp[];

	static int solve(int idx, int B) {
		B = Math.min(B, N);
		if (idx == N)
			return 0;
		if (dp[idx][B] != -1)
			return dp[idx][B];
		return dp[idx][B] = Math.min(B > 0 ? solve(idx + 1, B - 1 + b[idx]) + (t[idx] >> 1) : oo,
				solve(idx + 1, B + b[idx]) + t[idx]);
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			N = sc.nextInt();
			if (N == 0)
				break;
			t = new int[N];
			b = new int[N];
			for (int i = 0; i < N; i++) {
				t[i] = sc.nextInt();
				b[i] = sc.nextInt();
			}
			dp = new int[N][N + 1];
			for (int i = 0; i < N; i++)
				Arrays.fill(dp[i], -1);
			out.println(solve(0, 0));
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
