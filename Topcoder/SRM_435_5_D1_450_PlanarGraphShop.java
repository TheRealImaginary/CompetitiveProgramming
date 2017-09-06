package Topcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

// SRM 435.5 Div 1 450
public class SRM_435_5_D1_450_PlanarGraphShop {

	static final int oo = 1 << 27;
	static final int MAX = 50;

	static int sq(int x) {
		return x * x;
	}

	static int cube(int x) {
		return sq(x) * x;
	}

	public static int bestCount(int n) {
		Set<Integer> set = new TreeSet<>();
		set.add(1);
		set.add(8);
		set.add(9);
		for (int v = 3; v <= MAX; v++)
			for (int e = 0; e <= 3 * v - 6; e++)
				set.add(cube(v) + sq(e));
		ArrayList<Integer> a = new ArrayList<>(set);
		int[] dp = new int[n + 1];
		Arrays.fill(dp, oo);
		dp[0] = 0;
		for (int j = 0; j < a.size(); j++)
			for (int i = 1; i <= n; i++)
				if (i >= a.get(j))
					dp[i] = Math.min(dp[i], dp[i - a.get(j)] + 1);
		return dp[n];
	}

	public static void main(String[] args) {
		System.err.println(bestCount(1));
		System.err.println(bestCount(36));
		System.err.println(bestCount(7));
	}
}
