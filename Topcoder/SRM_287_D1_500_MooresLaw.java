package Topcoder;

/*-
 * @author Mazen Magdy
 *				-Let f(t) be the time needed to finish the task after we waited `t` years,
 *				 This function is unimodal and we need to find the minima. We use Ternary
 *				 Search. f(t) can be calculate like years/2^(wait / 1.5) + wait.
 */
public class SRM_287_D1_500_MooresLaw {

	static final double EPS = 1e-9;

	private static double f(double years, double wait) {
		return wait + years / Math.pow(2, wait / 1.5);
	}

	public static double shortestComputationTime(int years) {
		double left = 0, right = years;
		while (Math.abs(left - right) > EPS) {
			double l1 = left + (right - left) / 3, l2 = right - (right - left) / 3;
			if (f(years, l1) < f(years, l2))
				right = l2;
			else
				left = l1;
		}
		return f(years, (left + right) / 2);
	}

	public static void main(String[] args) {
		System.err.println(shortestComputationTime(14));
		System.err.println(shortestComputationTime(3));
		System.err.println(shortestComputationTime(47));
	}
}
