package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We solve it using DP. Let f(r, c, s) be the number
 *				 of ways to walk from (r,c) to the last row making
 *				 sum `s`. with the base case f(n, c, 0) = 1 and the
 *				 the final result is summation of f(0, c, S) / 2 because
 *				 of the middle cell.
 */
public class UVA_10564_PathsThroughTheHourglass {

	static PrintWriter out;

	static int R;
	static int[] N, g[];
	static long[][][] dp;

	static long solve(int r, int c, int s) {
		if (r == R)
			return s == 0 ? 1 : 0;
		if (s < 0 || c >= N[r] || c < 0)
			return 0;
		if (dp[r][c][s] != -1)
			return dp[r][c][s];
		if (r < R >> 1)
			return dp[r][c][s] = solve(r + 1, c, s - g[r][c]) + solve(r + 1, c - 1, s - g[r][c]);
		return dp[r][c][s] = solve(r + 1, c, s - g[r][c]) + solve(r + 1, c + 1, s - g[r][c]);
	}

	static void print(int r, int c, int s) {
		if (r == R - 1)
			return;
		if (r < R >> 1) {
			// Left
			if (solve(r + 1, c - 1, s - g[r][c]) > 0) {
				out.print('L');
				print(r + 1, c - 1, s - g[r][c]);
			} else {
				out.print('R');
				print(r + 1, c, s - g[r][c]);
			}
		} else {
			// Left
			if (solve(r + 1, c, s - g[r][c]) > 0) {
				out.print('L');
				print(r + 1, c, s - g[r][c]);
			} else if (solve(r + 1, c + 1, s - g[r][c]) > 0) {
				out.print('R');
				print(r + 1, c + 1, s - g[r][c]);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		out = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt(), s = sc.nextInt();
			if (n == 0 && s == 0)
				break;
			R = (n << 1) - 1;
			N = new int[R];
			g = new int[R][];
			for (int i = 0; i < n; i++) {
				N[i] = n - i;
				g[i] = new int[n - i];
				for (int j = 0; j < n - i; j++)
					g[i][j] = sc.nextInt();
			}
			for (int i = n; i < R; i++) {
				N[i] = i - n + 2;
				g[i] = new int[i - n + 2];
				for (int j = 0; j < i - n + 2; j++)
					g[i][j] = sc.nextInt();
			}
			dp = new long[R][n][s + 1];
			for (int i = 0; i < R; i++)
				for (int j = 0; j < n; j++)
					Arrays.fill(dp[i][j], -1);
			long ans = 0;
			for (int col = 0; col < n; col++)
				ans += solve(0, col, s);
			out.println(ans >> 1);
			for (int col = 0; col < n; col++)
				if (solve(0, col, s) > 0) {
					out.printf("%d ", col);
					print(0, col, s);
					break;
				}
			out.println();
		}
		out.flush();
		out.close();
	}

	static class Scanner {
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream s) {
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
	}
}
