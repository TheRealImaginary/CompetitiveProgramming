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
 *				-We explore all possible boards using a mask with 16-bits.
 */
public class UVA_10536_GameOfEuler {

	static final int LOSE = 0, WIN = 1, WOOT = -1;
	static final int N = 4;

	static int[] dp;

	static int convert(int i, int j) {
		return i * N + j;
	}

	static int solve(int msk) {
		if (msk == (1 << (N * N)) - 1)
			return WIN;
		if (dp[msk] != WOOT)
			return dp[msk];
		int can = LOSE;
		// Through
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if ((msk & (1 << convert(i, j))) == 0)
					can |= (1 ^ solve(msk | (1 << convert(i, j))));
		if (can == WIN)
			return dp[msk] = can;
		// Rows
		for (int c = 0; c < 16; c += 4) {
			int c1 = c + 1, c2 = c1 + 1, c3 = c2 + 1;
			if ((msk & (1 << c)) == 0)
				if ((msk & (1 << c1)) == 0) {
					can |= (1 ^ solve(msk | (1 << c) | (1 << c1)));
					if (can == WIN)
						return dp[msk] = can;
					if ((msk & (1 << c2)) == 0)
						can |= (1 ^ solve(msk | (1 << c) | (1 << c1) | (1 << c2)));
				}
			if (can == WIN)
				return dp[msk] = can;
			if ((msk & (1 << c3)) == 0)
				if ((msk & (1 << c2)) == 0) {
					can |= (1 ^ solve(msk | (1 << c3) | (1 << c2)));
					if (can == WIN)
						return dp[msk] = can;
					if ((msk & (1 << c1)) == 0)
						can |= (1 ^ solve(msk | (1 << c3) | (1 << c2) | (1 << c1)));
				}
		}
		if (can == WIN)
			return dp[msk] = can;
		// Columns
		for (int r = 0; r < 4; r++) {
			int r1 = r + 4, r2 = r1 + 4, r3 = r2 + 4;
			if ((msk & (1 << r)) == 0)
				if ((msk & (1 << r1)) == 0) {
					can |= (1 ^ solve(msk | (1 << r) | (1 << r1)));
					if (can == WIN)
						return dp[msk] = can;
					if ((msk & (1 << r2)) == 0) {
						can |= (1 ^ solve(msk | (1 << r) | (1 << r1) | (1 << r2)));
					}
				}
			if (can == WIN)
				return dp[msk] = can;
			if ((msk & (1 << r3)) == 0)
				if ((msk & (1 << r2)) == 0) {
					can |= (1 ^ solve(msk | (1 << r3) | (1 << r2)));
					if (can == WIN)
						return dp[msk] = can;
					if ((msk & (1 << r1)) == 0)
						can |= (1 ^ solve(msk | (1 << r3) | (1 << r2) | (1 << r1)));
				}
		}
		return dp[msk] = can;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		// PrintWriter out = new PrintWriter("out.txt");
		dp = new int[1 << (N * N)];
		Arrays.fill(dp, -1);
		solve(0);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int msk = 0;
			for (int i = 0; i < N; i++) {
				String s = sc.next();
				for (int j = 0; j < N; j++)
					if (s.charAt(j) == 'X')
						msk |= (1 << convert(i, j));
			}
			out.println(solve(msk) == WIN ? "WINNING" : "LOSING");
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
