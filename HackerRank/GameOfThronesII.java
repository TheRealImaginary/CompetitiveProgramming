package HackerRank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class GameOfThronesII {

	static final long MOD = (long) 1e9 + 7;
	static final int ALPHA = 26;

	static long modPow(long base, long exp) {
		long res = 1;
		while (exp > 0) {
			if ((exp & 1) != 0)
				res = (res * base) % MOD;
			base = (base * base) % MOD;
			exp >>= 1;
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		char[] c = sc.next().toCharArray();
		int n = c.length;
		int[] cnt = new int[ALPHA];
		for (int i = 0; i < c.length; i++)
			cnt[c[i] - 'a']++;
		long[] fact = new long[n + 1];
		long[] invfact = new long[n + 1];
		fact[0] = fact[1] = invfact[0] = invfact[1] = 1;
		for (int i = 2; i <= n; i++) {
			fact[i] = (i * fact[i - 1]) % MOD;
			invfact[i] = modPow(fact[i], MOD - 2);
		}
		long ans = fact[n >> 1];
		for (int i = 0; i < 26; i++)
			if (cnt[i] != 0)
				ans = (ans * invfact[cnt[i] >> 1]) % MOD;
		out.println(ans % MOD);
		out.flush();
		out.close();
	}

	static class MyScanner {

		BufferedReader br;
		StringTokenizer st;

		MyScanner(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
		}

		MyScanner(String filename) throws IOException {
			br = new BufferedReader(new FileReader(new File(filename)));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		String nextLine() throws IOException {
			return br.readLine();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		boolean ready() throws IOException {
			return br.ready();
		}
	}
}
