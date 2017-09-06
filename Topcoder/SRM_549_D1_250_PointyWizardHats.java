package Topcoder;

import java.util.ArrayList;
import java.util.Arrays;

public class SRM_549_D1_250_PointyWizardHats {

	static int N, M;
	static ArrayList<Integer>[] g;
	static int[] match;
	static boolean[] vis;

	static int aug(int u) {
		if (vis[u])
			return 0;
		vis[u] = true;
		for (int v : g[u])
			if (match[v] == -1 || aug(match[v]) == 1) {
				match[v] = u;
				return 1;
			}
		return 0;
	}

	public static int getNumHats(int[] topHeight, int[] topRadius, int[] bottomHeight, int[] bottomRadius) {
		N = topHeight.length;
		M = bottomHeight.length;
		g = new ArrayList[N];
		for (int i = 0; i < N; i++)
			g[i] = new ArrayList<>();
		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++)
				if (bottomRadius[j] > topRadius[i] && bottomHeight[j] * topRadius[i] < bottomRadius[j] * topHeight[i])
					g[i].add(j);
		match = new int[M];
		Arrays.fill(match, -1);
		int MCBM = 0;
		for (int u = 0; u < N; u++) {
			vis = new boolean[N];
			MCBM += aug(u);
		}
		return MCBM;
	}

	public static void main(String[] args) {
		System.err.println(getNumHats(new int[] { 30 }, new int[] { 3 }, new int[] { 3 }, new int[] { 30 }));
		System.err.println(getNumHats(new int[] { 4, 4 }, new int[] { 4, 3 }, new int[] { 5, 12 }, new int[] { 5, 4 }));
	}
}
