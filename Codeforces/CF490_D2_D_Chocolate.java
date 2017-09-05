package Codeforces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-A solution exists iff the area is a product of 2^x * 3^y. We decrease the piece
 *				 with the greater area to the smaller one.
 */
public class CF490_D2_D_Chocolate {

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		long a1 = sc.nextLong(), b1 = sc.nextLong();
		long a2 = sc.nextLong(), b2 = sc.nextLong();
		long s1 = a1 * b1, s2 = a2 * b2;
		long twoFirst = 0, twoSecond = 0, threeFirst = 0, threeSecond = 0;
		while (s1 % 2 == 0) {
			s1 /= 2;
			twoFirst++;
		}
		while (s1 % 3 == 0) {
			s1 /= 3;
			threeFirst++;
		}
		while (s2 % 2 == 0) {
			s2 /= 2;
			twoSecond++;
		}
		while (s2 % 3 == 0) {
			s2 /= 3;
			threeSecond++;
		}
		if (s1 != s2) {
			System.out.println(-1);
			return;
		}
		int ans = 0;
		while (threeFirst > threeSecond && a1 % 3 == 0) {
			a1 /= 3;
			a1 *= 2;
			ans++;
			twoFirst++;
			threeFirst--;
		}
		while (threeFirst > threeSecond && b1 % 3 == 0) {
			b1 /= 3;
			b1 *= 2;
			ans++;
			twoFirst++;
			threeFirst--;
		}
		while (threeFirst < threeSecond && a2 % 3 == 0) {
			a2 /= 3;
			a2 *= 2;
			ans++;
			twoSecond++;
			threeSecond--;
		}
		while (threeFirst < threeSecond && b2 % 3 == 0) {
			b2 /= 3;
			b2 *= 2;
			ans++;
			twoSecond++;
			threeSecond--;
		}

		while (twoFirst > twoSecond && a1 % 2 == 0) {
			a1 /= 2;
			ans++;
			twoFirst--;
		}
		while (twoFirst > twoSecond && b1 % 2 == 0) {
			b1 /= 2;
			ans++;
			twoFirst--;
		}
		while (twoFirst < twoSecond && a2 % 2 == 0) {
			a2 /= 2;
			ans++;
			twoSecond--;
		}
		while (twoFirst < twoSecond && b2 % 2 == 0) {
			b2 /= 2;
			ans++;
			twoSecond--;
		}

		System.out.println(ans);
		System.out.printf("%d %d\n", a1, b1);
		System.out.printf("%d %d\n", a2, b2);
	}

	static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		public MyScanner(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
		}

		public MyScanner(String filename) throws FileNotFoundException {
			br = new BufferedReader(new FileReader(new File(filename)));
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public boolean ready() throws IOException {
			return br.ready();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}
	}
}
