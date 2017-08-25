package Topcoder;

import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-If cost of all operations are 1, this becomes a very simple DP Problem. Now since cost
 *				 of an operation is a function. To find the minimum cost of changing some to another which is
 *				 tricky we need to consider all possible sequences, for example for two letters `a`, `b`; For `a`
 *				 to become `b`, we use a change operation from a to b or even change a to some letters then finally
 *				 to `b`. However, it could be better to use some other operations other than "change". We can erase `a`
 *				 insert another letter let's call it `c` and change `c` to be, this could have an optimal cost. So we need
 *				 to consider these possibilities when calculating the minCost. Afterwards we can calculate matchCost[i][j]
 *				 which is the minimum cost of either change i to j or j to i.
 */
public class SRM_509_D1_500_Palindromization {

	private static final long oo = 1L << 57;
	private static final String ADD = "add";
	private static final String ERASE = "erase";
	private static final String CHANGE = "change";
	private static final int EMPTY = 26;
	private static final int ALPHA = 27;

	public static int getMinimumCost(String word, String[] operations) {
		long[][] minCost = new long[ALPHA][ALPHA];
		for (int i = 0; i < ALPHA; i++) {
			Arrays.fill(minCost[i], oo);
			minCost[i][i] = 0;
		}
		int m = operations.length;
		StringTokenizer st;
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(operations[i]);
			String op = st.nextToken();
			if (op.equals(ADD)) {
				char c = st.nextToken().charAt(0);
				int cost = Integer.parseInt(st.nextToken());
				minCost[EMPTY][c - 'a'] = cost;
			} else if (op.equals(ERASE)) {
				char c = st.nextToken().charAt(0);
				int cost = Integer.parseInt(st.nextToken());
				minCost[c - 'a'][EMPTY] = cost;
			} else if (op.equals(CHANGE)) {
				char a = st.nextToken().charAt(0), b = st.nextToken().charAt(0);
				int cost = Integer.parseInt(st.nextToken());
				minCost[a - 'a'][b - 'a'] = cost;
			}
		}
		for (int k = 0; k < ALPHA; k++)
			for (int i = 0; i < ALPHA; i++)
				for (int j = 0; j < ALPHA; j++)
					minCost[i][j] = Math.min(minCost[i][j], minCost[i][k] + minCost[k][j]);
		long[][] matchCost = new long[ALPHA][ALPHA];
		for (int i = 0; i < ALPHA; i++)
			for (int j = 0; j < ALPHA; j++) {
				matchCost[i][j] = oo;
				for (int k = 0; k < ALPHA; k++)
					matchCost[i][j] = Math.min(matchCost[i][j], minCost[i][k] + minCost[j][k]);
			}
		int n = word.length();
		long[][] dp = new long[n][n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(dp[i], oo);
			dp[i][i] = 0;
		}
		for (int len = 2; len <= n; len++)
			for (int i = 0; i < n - len + 1; i++) {
				int j = i + len - 1, a = word.charAt(i) - 'a', b = word.charAt(j) - 'a';
				if (len == 2)
					dp[i][j] = a == b ? 0
							: Math.min(matchCost[a][b], Math.min(matchCost[EMPTY][a], matchCost[EMPTY][b]));
				else
					dp[i][j] = Math.min(matchCost[a][b] + dp[i + 1][j - 1],
							Math.min(matchCost[EMPTY][a] + dp[i + 1][j], matchCost[EMPTY][b] + dp[i][j - 1]));

			}
		return (int) (dp[0][n - 1] >= oo ? -1 : dp[0][n - 1]);
	}

	public static void main(String[] args) {
		System.err.println(getMinimumCost("topcoder", new String[] { "erase t 1", "erase o 1", "erase p 1", "erase c 1",
				"erase d 1", "erase e 1", "erase r 1" }));
		System.err.println(getMinimumCost("topcoder", new String[] { "erase t 10", "erase o 1", "erase p 1",
				"erase c 1", "erase d 1", "erase e 1", "erase r 1" }));
		System.err.println(getMinimumCost("caaaaaab", new String[] { "change b a 100000", "change c a 100000",
				"change c d 50000", "change b e 50000", "erase d 50000", "erase e 49999" }));
		System.err.println(getMinimumCost("moon", new String[] { "erase o 5", "add u 7", "change d p 3",
				"change m s 12", "change n d 6", "change s l 1" }));
	}
}
