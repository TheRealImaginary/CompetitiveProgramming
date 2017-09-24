package Topcoder;

import java.util.Arrays;

public class SRM_372_D1_1000_RainyDay {

	static final int oo = 1 << 28;

	static int N;
	static char[] c, f;
	static int[][] dp;

	private static int isWet(int idx, int time) {
		if (idx < 0 || idx == N)
			return 1;
		if (c[idx] != '.')
			return 0;
		if (f[(idx + time) % N] == 'R')
			return 1;
		return 0;
	}

	private static int solve(int idx, int time) {
		if (time > N * N || idx < 0 || idx == N)
			return oo;
		if (c[idx] == 'H')
			return 0;
		if (dp[idx][time] != -1)
			return dp[idx][time];
		return dp[idx][time] = Math.min(solve(idx, time + 1) + isWet(idx, time) + isWet(idx, time + 1),
				Math.min(solve(idx + 1, time + 1) + isWet(idx + 1, time) + isWet(idx + 1, time + 1),
						solve(idx - 1, time + 1) + isWet(idx - 1, time) + isWet(idx - 1, time + 1)));
	}

	private static int minWet(int time) {
		time *= 2;
		int ret = oo;
		for (int i = 0; i < f.length; i++) {
			int counter = 0;
			for (int t = 0; t < time; t++)
				if (f[(i + t) % f.length] == 'R')
					counter++;
			ret = Math.min(ret, counter);
		}
		return ret;
	}

	// Greedy Solution
	private static int sol(String path, String forecast) {
		int y = path.indexOf('Y');
		int h = path.indexOf('H');
		if (y < h)
		{
			int ret = 0;
			int openCount = 0;
			for (int i = y; i <= h; i++) {
				if (path.charAt(i) == '.')
					openCount++;
				else {
					ret += minWet(openCount);
					openCount = 0;
				}
			}
			return ret;
		} else {
			int wetCount = minWet(1);
			int ret = 0;
			for (int i = y; i >= h; i--)
				if (path.charAt(i) == '.')
					ret += wetCount;
			return ret;
		}

	}

	public static int minimumRainTime(String path, String forecast) {
		c = path.toCharArray();
		f = forecast.toCharArray();
		N = path.length();
		dp = new int[N][N * N + 1];
		for (int i = 0; i < N; i++)
			Arrays.fill(dp[i], -1);
		return sol(path, forecast);
		// return solve(path.indexOf('Y'), 0);
	}

	public static void main(String[] args) {
		System.err.println(minimumRainTime("Y..H", "R.RR"));
		System.err.println(minimumRainTime("Y.C.H", "RRRR."));
		System.err.println(minimumRainTime("Y..C.H", "RRR.R."));
	}
}
