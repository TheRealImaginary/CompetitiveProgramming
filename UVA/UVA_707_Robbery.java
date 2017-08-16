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
 *				-We use DP to solve this problem. We know that at position (x, y) and time T
 *				 the Robber couldn't be here. So for each possible position and time we find-out
 *				 using legal moves if we can reach any of these cells, if we can then the Robber cannot be
 *				 at these positions otherwise he could have been there.
 */
public class UVA_707_Robbery {

	static final int FREE = 0, OCCUPIED = 1, UNKNOWN = -1;
	static final int[] dx = { 1, -1, 0, 0 };
	static final int[] dy = { 0, 0, 1, -1 };

	static int W, H, T;
	static int[][][] dp;

	static boolean isValid(int x, int y) {
		return x >= 1 && x <= W && y >= 1 && y <= H;
	}

	static int solve(int x, int y, int time) {
		if (dp[x][y][time] != UNKNOWN)
			return dp[x][y][time];
		if (time == 1)
			return dp[x][y][time] = FREE;
		int ans = solve(x, y, time - 1);
		for (int i = 0; i < 4; i++) {
			int r = x + dx[i], c = y + dy[i];
			if (isValid(r, c) && solve(r, c, time - 1) == FREE)
				ans = FREE;
		}
		return dp[x][y][time] = ans;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		for (int t = 1;; t++) {
			W = sc.nextInt();
			H = sc.nextInt();
			T = sc.nextInt();
			if (W + H + T == 0)
				break;
			dp = new int[W + 1][H + 1][T + 1];
			for (int i = 0; i <= W; i++)
				for (int j = 0; j <= H; j++)
					Arrays.fill(dp[i][j], UNKNOWN);
			int n = sc.nextInt();
			for (int i = 0; i < n; i++) {
				int time = sc.nextInt();
				int left = sc.nextInt(), top = sc.nextInt(), right = sc.nextInt(), bottom = sc.nextInt();
				for (int a = left; a <= right; a++)
					for (int b = top; b <= bottom; b++)
						dp[a][b][time] = OCCUPIED;
			}
			boolean stillHere = false;
			for (int x = 1; x <= W; x++)
				for (int y = 1; y <= H; y++)
					if (solve(x, y, T) == FREE)
						stillHere = true;
			out.printf("Robbery #%d:\n", t);
			if (!stillHere)
				out.println("The robber has escaped.");
			else {
				boolean f = false;
				for (int time = 1; time <= T; time++) {
					int ans = 0, i = 0, j = 0;
					for (int x = 1; x <= W; x++) {
						for (int y = 1; y <= H; y++) {
							if (dp[x][y][time] == FREE) {
								i = x;
								j = y;
								ans++;
							}
						}
					}
					if (ans == 1) {
						f = true;
						out.printf("Time step %d: The robber has been at %d,%d.\n", time, i, j);
					}
				}
				if (!f)
					out.println("Nothing known.");
			}
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
