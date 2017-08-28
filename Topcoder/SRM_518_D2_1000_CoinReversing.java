package Topcoder;

/*-
 * @author Mazen Magdy
 *				-The probability of choosing a[i] coins from N is p = a[i] / N.
 *				 At first we start with N heads then we choose a[i] coins and flip them
 *				 so we change Heads and Tails accordingly. 
 */
public class SRM_518_D2_1000_CoinReversing {

	public static double expectedHeads(int N, int[] a) {
		double heads = N, tails = 0;
		for (int i = 0; i < a.length; i++) {
			double p = a[i] * 1.0 / N;
			double newHeads = heads - (p * heads) + (p * tails);
			double newTails = tails - (p * tails) + (p * heads);
			heads = newHeads;
			tails = newTails;
		}
		return heads;
	}

	public static void main(String[] args) {
		System.err.println(expectedHeads(3, new int[] { 2, 2 }));
		System.err.println(expectedHeads(1000, new int[] { 916, 153, 357, 729, 183, 848, 61, 672, 295, 936 }));
	}
}
