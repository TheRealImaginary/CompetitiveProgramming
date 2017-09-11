package Codeforces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-k should be no less than the maximum Distance from a node to a cycle.
 *				 Moreover, k must be divisible by LCM of all cycle lengths.
 */
public class CF542_D1_C_IdempotentFunctions {

	static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	static long lcm(long a, long b) {
		return a * (b / gcd(a, b));
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt();
		int[] f = new int[n];
		for (int i = 0; i < n; ++i) {
			f[i] = sc.nextInt() - 1;
		}
		int maxDistanceToCycle = 0;
		long lcm = 1;
		for (int i = 0; i < n; ++i) {
			int[] time = new int[n];
			int current = i;
			int currentTime = 1;
			while (time[current] == 0) {
				time[current] = currentTime++;
				current = f[current];
			}
			int cycleLength = currentTime - time[current];
			int distToCycle = time[current] - 1;
			maxDistanceToCycle = Math.max(maxDistanceToCycle, distToCycle);
			lcm = lcm(lcm, cycleLength);
		}
		long ans = lcm;
		while (ans < maxDistanceToCycle)
			ans += lcm;
		out.println(ans);
		out.flush();
		out.close();
	}

	static class MyScanner {
		StringTokenizer st;
		BufferedReader br;

		public MyScanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
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
