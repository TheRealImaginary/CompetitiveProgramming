package Topcoder;

import java.util.TreeMap;

public class SRM_407_D1_500_PointsGame {

	static class Triple implements Comparable<Triple> {

		int a, b, c;

		Triple(int x, int y, int z) {
			a = x;
			b = y;
			c = y;
		}

		@Override
		public String toString() {
			return a + " " + b + " " + c;
		}

		@Override
		public int compareTo(Triple t) {
			if (a != t.a)
				return a - t.a;
			if (b != t.b)
				return b - t.b;
			return c - t.c;
		}
	}

	static final double oo = 1e9;

	static int N;
	static int[] x, y;
	static TreeMap<Triple, Double> map;

	static double dist(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	static double solve(int white, int blue, int player) {
		if (Integer.bitCount(white) + Integer.bitCount(blue) == N)
			return 0;
		Triple t = new Triple(white, blue, player);
		if (map.containsKey(t))
			return map.get(t);
		double res = player == 0 ? 0 : oo;
		for (int i = 0; i < N; i++)
			if ((white & (1 << i)) == 0 && (blue & (1 << i)) == 0) {
				double sum = 0;
				if (player == 0) {
					for (int j = 0; j < N; j++)
						if ((blue & (1 << j)) != 0)
							sum += dist(x[i], y[i], x[j], y[j]);
					res = Math.max(res, solve(white | (1 << i), blue, player ^ 1) + sum);
				} else {
					for (int j = 0; j < N; j++)
						if ((white & (1 << j)) != 0)
							sum += dist(x[i], y[i], x[j], y[j]);
					res = Math.min(res, solve(white, blue | (1 << i), player ^ 1) + sum);
				}
			}
		map.put(t, res);
		return res;
	}

	public static double gameValue(int[] X, int[] Y) {
		N = X.length;
		x = X;
		y = Y;
		map = new TreeMap<>();
		return solve(0, 0, 0);
	}

	public static void main(String[] args) {
		System.err.println(gameValue(new int[] { 0, 0 }, new int[] { 0, 1 }));
		System.err.println(gameValue(new int[] { 0, 0, 1, 1 }, new int[] { 0, 5, 0, 5 }));
		System.err.println(gameValue(new int[] { 0, 0, 0, 0 }, new int[] { 0, 1, 5, 6 }));
		System.err.println(gameValue(new int[] { 0, 1, 2, 3 }, new int[] { 0, 0, 0, 0 }));
	}
}
