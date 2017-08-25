package Topcoder;

/*-
 * @author Mazen Magdy
 *				-We try each possible seatings. Calculating how many possible arrangements of this
 *				 seating exists and the probability for each.
 */
public class SRM_249_D1_250_TableSeating {

	public static double getExpected(int numTables, int[] probs) {
		int n = probs.length;
		double[] dp = new double[1 << numTables];
		for (int table = (1 << numTables) - 1; table >= 0; table--) {
			double PNN = 0, PEE = 0;
			for (int i = 0; i < n; i++) {
				int size = i + 1;
				double PN = 0, PE = 0;
				for (int j = 0; j < numTables; j++) {
					int msk = ((1 << size) - 1) << j;
					if (msk < (1 << numTables) && (table & msk) == 0) {
						PE += size + dp[table | msk];
						PN++;
					}
				}
				if (PN > 0)
					PEE += (PE / PN) * probs[i];
				PNN += probs[i];
			}
			if (PNN > 0)
				dp[table] = PEE / PNN;
		}
		return dp[0];
	}

	public static void main(String[] args) {
		System.err.println(getExpected(5, new int[] { 0, 0, 0, 0, 0, 50, 50 }));
		System.err.println(getExpected(12, new int[] { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 10 }));
	}
}
