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
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*-
 * @author Mazen Magdy 
 * 			-To find the number of divisors of a number we multiply
 *          the powers of the prime factors, so this is what we need to here, To
 *          get the prime factors of N! for each prime less than N we add N / p^k
 *          for all k >= 1, now we prime factorize D and for each prime if it's
 *          not in D we multiply the answer by it's power + 1, else if it's in D
 *          and the power is greater than that in D we multiply the answer by the
 *          power - D's power + 1 otherwise the answer is 0
 */
public class UVA_10484_DivisibilityOfFactors {

	static final int MAX = 50001;
	static ArrayList<Long> primes;

	static void sieve() {
		boolean[] isPrime = new boolean[MAX];
		Arrays.fill(isPrime, true);
		isPrime[0] = isPrime[1] = false;
		primes = new ArrayList<>();
		for (int i = 2; i < MAX; i++)
			if (isPrime[i]) {
				if (i * 1L * i < MAX)
					for (int j = i * i; j < MAX; j += i)
						isPrime[j] = false;
				primes.add(i * 1L);
			}
	}

	static Map<Long, Integer> solve(int n) {
		Map<Long, Integer> res = new TreeMap<>();
		for (long p : primes) {
			if (p > n)
				break;
			int power = 0;
			for (long pp = p; pp <= n; pp *= p)
				power += n / pp;
			res.put(p, power);
		}
		return res;
	}

	static Map<Long, Integer> factorize(long n) {
		Map<Long, Integer> res = new TreeMap<>();
		int pf_idx = 0;
		long p = primes.get(pf_idx);
		while (p * p <= n) {
			int pow = 0;
			while (n % p == 0) {
				pow++;
				n /= p;
			}
			if (pow > 0)
				res.put(p, pow);
			p = primes.get(++pf_idx);
		}
		if (n != 1)
			res.put(n, 1);
		return res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);

		sieve();

		while (true) {
			int n = sc.nextInt();
			long m = Math.abs(sc.nextLong());
			if (n == 0 && m == 0)
				break;
			if (n == 0 || n == 1) {
				out.println(m == 1 ? 1 : 0);
				continue;
			}
			Map<Long, Integer> powers = solve(n);
			Map<Long, Integer> map = factorize(m);
			boolean f = true;
			long ans = 1;
			for (Entry<Long, Integer> entry : powers.entrySet()) {
				int power = entry.getValue();
				long prime = entry.getKey();
				if (map.containsKey(prime))
					continue;
				ans *= (power + 1);
			}
			if (!f) {
				out.println(0);
				continue;
			}
			for (Entry<Long, Integer> entry : map.entrySet()) {
				Integer power = powers.get(entry.getKey());
				if (power == null || entry.getValue() > power) {
					f = false;
					break;
				}
				ans *= (power - entry.getValue() + 1);
			}
			out.println(f ? ans : 0);
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
