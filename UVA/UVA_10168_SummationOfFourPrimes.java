package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-An even numbers greater than 2 can be written as a summation of two primes.
 *				 So if we can decompose the number into summation of two even numbers greater than 2
 *				 we can further decompose of these number into a summation of two primes.
 */
public class UVA_10168_SummationOfFourPrimes {
	
	static final int MAX = (int) 1e7 + 1;

	static ArrayList<Integer> primes;

	static void sieve() {
		primes = new ArrayList<>();
		boolean[] isPrime = new boolean[MAX];
		Arrays.fill(isPrime, true);
		isPrime[0] = isPrime[1] = false;
		for (int i = 2; i < MAX; i++)
			if (isPrime[i]) {
				if (i * 1L * i < MAX)
					for (int j = i * i; j < MAX; j += i)
						isPrime[j] = false;
				primes.add(i);
			}
	}

	static int[] solve(int n) {
		if ((n & 1) != 0) {
			int idx = Collections.binarySearch(primes, n - 2);
			if (idx >= 0)
				return new int[] { 2, primes.get(idx) };
		} else {
			for (int i = 0; i < primes.size(); i++) {
				int idx = Collections.binarySearch(primes, n - primes.get(i));
				if (idx >= 0) {
					return new int[] { primes.get(i), primes.get(idx) };
				}
			}
		}
		return null; // Should not happen unless n is odd
	}

	public static void main(String[] args) throws IOException {
		// MyScanner sc = new MyScanner(System.in);
		MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);

		sieve();

		while (sc.ready()) {
			int n = sc.nextInt();
			if (n < 8)
				out.println("Impossible.");
			else {
				if ((n & 1) == 0) {
					out.print("2 2");
					int[] ans = solve(n - 4);
					out.printf(" %d %d\n", ans[0], ans[1]);
				} else {
					int[] res = null;
					int[] ans = null;
					for (int even = 4;; even += 2) {
						res = solve(even);
						ans = solve(n - even);
						if (ans != null) {
							break;
						}
					}
					if (ans != null)
						out.printf("%d %d %d %d\n", res[0], res[1], ans[0], ans[1]);
					else
						out.println("Impossible.");
				}
			}
		}
		out.flush();
		out.close();
	}

	static class MyScanner {
		StringTokenizer st;
		BufferedReader br;

		public MyScanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}

		public MyScanner(String filename) throws IOException {
			br = new BufferedReader(new FileReader(new File(filename)));
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public boolean ready() throws IOException {
			return br.ready();
		}
	}
}
