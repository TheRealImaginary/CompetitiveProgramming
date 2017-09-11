package Topcoder;

public class SRM_315_D2_1000_KidsGame {

	public static int kthKid(int n, int m, int k) {
		k--;
		int currentPos = 0;
		int counter = 1;
		while (true) {
			int remove = (currentPos + m - 1) % n;
			if (remove == k)
				return counter;
			counter++;
			if (remove < k)
				k--; // shift our guy
			n--;
			currentPos = remove;
		}
	}

	public static void main(String[] args) {
		System.err.println(kthKid(5, 2, 3));
		System.err.println(kthKid(1, 10, 1));
		System.err.println(kthKid(99, 100, 99));
		System.err.println(kthKid(19999, 7, 5));
		System.err.println(kthKid(99999, 11111, 3));
	}
}
