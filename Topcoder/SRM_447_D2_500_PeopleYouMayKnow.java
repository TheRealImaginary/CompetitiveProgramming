package Topcoder;

import java.util.ArrayList;
import java.util.Arrays;

/*-
 * @author Mazen Magdy
 *				-For Two nodes `a` and `b` to be at-least 3-friends, They would have a node in common
 *				 and/or have a path going from `a` to `b`. Any common node between them needs to be removed
 *				 so we filter the graph from such nodes. Then  we need to disconnect the two nodes removing
 *				 the minimal number of nodes. If We build a graph G' with the rest of the nodes such that
 *				 each node has a capacity of 1 and each edge has capacity 1, and source is `a`
 *				 and sink is `b`. The Mincut is the minimum number of nodes that needs to be removed
 *				 such that Source Component is disconnected from Sink Component. Hence we can either use
 *				 any MaxFlow Algorithm or Bipartite Matching to solve this problem.  
 */
public class SRM_447_D2_500_PeopleYouMayKnow {

	static ArrayList<Integer>[] g;
	static int[] match;
	static boolean[] vis;

	static int augment(int u) {
		if (vis[u])
			return 0;
		vis[u] = true;
		for (int v : g[u]) {
			if (match[v] == -1 || augment(match[v]) == 1) {
				match[v] = u;
				return 1;
			}
		}
		return 0;
	}

	public static int maximalScore(String[] friends, int a, int b) {
		int res = 0, n = friends.length;
		boolean[] done = new boolean[n];
		for (int i = 0; i < n; i++)
			if (friends[i].charAt(a) == friends[i].charAt(b) && friends[i].charAt(a) == 'Y') {
				done[i] = true;
				res++;
			}
		ArrayList<Integer> aa = new ArrayList<>(), bb = new ArrayList<>();
		for (int i = 0; i < n; i++)
			if (!done[i] && friends[i].charAt(a) == 'Y') {
				done[i] = true;
				aa.add(i);
			}
		for (int i = 0; i < n; i++)
			if (!done[i] && friends[i].charAt(b) == 'Y') {
				done[i] = true;
				bb.add(i);
			}
		g = new ArrayList[n];
		for (int i = 0; i < n; i++)
			g[i] = new ArrayList<>();
		int m = aa.size(), k = bb.size();
		for (int i = 0; i < m; i++)
			for (int j = 0; j < k; j++)
				if (friends[aa.get(i)].charAt(bb.get(j)) == 'Y')
					g[aa.get(i)].add(bb.get(j));
		match = new int[n];
		Arrays.fill(match, -1);
		int MCBM = 0;
		for (int i = 0; i < m; i++) {
			vis = new boolean[n];
			MCBM += augment(aa.get(i));
		}
		return res + MCBM;
	}

	public static void main(String[] args) {
		System.err.println(maximalScore(new String[] { "NYNN", "YNYN", "NYNY", "NNYN" }, 0, 3));
		System.err.println(maximalScore(
				new String[] { "NYNYYYN", "YNYNNYY", "NYNNNNY", "YNNNNNN", "YNNNNYN", "YYNNYNY", "NYYNNYN" }, 3, 2));
		System.err.println(maximalScore(new String[] { "NYYYYNNNN", "YNNNNYYYN", "YNNNNNNYN", "YNNNNNNYN", "YNNNNNNNY",
				"NYNNNNNNY", "NYNNNNNNY", "NYYYNNNNY", "NNNNYYYYN" }, 8, 0));
	}
}
