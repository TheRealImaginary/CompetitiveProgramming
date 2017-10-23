package Codeforces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class CF397_D2_C_OnNumberOfDecompositionsIntoMultipliers { // Correct

	static final long MOD = (long) 1e9 + 7;
	static final int MAXP = (int) 31700;
	static final int MAXN = 50001;

	static ArrayList<Integer> primes;
	static long[] fact, invf;
	static TreeMap<Integer, Integer> map;

	static void preprocess() {
		sieve();
		fact();
	}

	static void sieve() {
		primes = new ArrayList<>();
		boolean[] isPrime = new boolean[MAXP];
		Arrays.fill(isPrime, true);
		isPrime[0] = isPrime[1] = false;
		for (int i = 2; i < MAXP; i++)
			if (isPrime[i]) {
				if (i * 1L * i < MAXP)
					for (int j = i * i; j < MAXP; j += i)
						isPrime[j] = false;
				primes.add(i);
			}
	}

	static void fact() {
		fact = new long[MAXN];
		invf = new long[MAXN];
		fact[0] = fact[1] = invf[0] = invf[1] = 1;
		for (int i = 2; i < MAXN; i++) {
			fact[i] = (i * fact[i - 1]) % MOD;
			invf[i] = pow(fact[i], MOD - 2);
		}
	}

	static long pow(long base, long exp) {
		long res = 1;
		while (exp > 0) {
			if ((exp & 1) != 0)
				res = (res * base) % MOD;
			exp >>= 1;
			base = (base * base) % MOD;
		}
		return res % MOD;
	}

	static void factorize(int x) {
		int idx = 0, pf = primes.get(idx++);
		while (pf * pf <= x) {
			int pow = 0;
			while (x % pf == 0) {
				x /= pf;
				pow++;
			}
			Integer val = map.get(pf);
			val = val == null ? 0 : val;
			map.put(pf, val + pow);
			pf = primes.get(idx++);
		}
		if (x != 1) {
			Integer val = map.get(x);
			val = val == null ? 0 : val;
			map.put(x, val + 1);
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);

		// Sigh ...
		preprocess();
		int n = sc.nextInt();
		map = new TreeMap<>();
		for (int i = 0; i < n; i++) {
			int a = sc.nextInt();
			factorize(a);
		}
		long ans = 1;
		for (Integer val : map.values())
			if (val > 0)
				ans = (ans * ((((fact[val + n - 1] * invf[val]) % MOD) * invf[n - 1]) % MOD)) % MOD;
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

		public MyScanner(String file) throws IOException {
			br = new BufferedReader(new FileReader(new File(file)));
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
