package LiveArchive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class LiveArchive_5990_ArrayDiversity {

	static final long MOD = (long) 1e9 + 7;
	static final long oo = 1L << 58;

	static long modPow(long base, long exp) {
		long res = 1;
		while (exp > 0) {
			if ((exp & 1) != 0)
				res = mult(res, base);
			base = mult(base, base);
			exp >>= 1;
		}
		return res;
	}

	static long mult(long a, long b) {
		return (a * b) % MOD;
	}

	static long add(long a, long b) {
		return (a + b) % MOD;
	}

	static long f(int n) {
		return mult(mult(n, n + 1), modPow(2, MOD - 2));
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			int n = sc.nextInt();
			int[] a = new int[n];
			long min = oo, minCount = 0, max = -oo, maxCount = 0;
			for (int i = 0; i < n; i++) {
				int x = sc.nextInt();
				a[i] = x;
				if (x < min) {
					min = x;
					minCount = 1;
				} else if (x == min)
					minCount++;
				if (x > max) {
					max = x;
					maxCount = 1;
				} else if (x == max)
					maxCount++;
			}
			if (min == max) {
				out.printf("%d %d\n", f(n), modPow(2, n) - 1);
				continue;
			}
			long subsequences = mult(mult(modPow(2, minCount) - 1, modPow(2, maxCount) - 1),
					modPow(2, n - minCount - maxCount));
			int lastMin = -1, lastMax = -1;
			long subsegments = 0;
			for (int i = 0; i < n; i++)
				if (a[i] == min) {
					subsegments = add(subsegments, lastMax + 1);
					lastMin = i;
				} else if (a[i] == max) {
					subsegments = add(subsegments, lastMin + 1);
					lastMax = i;
				} else {
					subsegments = add(subsegments, Math.min(lastMin, lastMax) + 1);
				}
			out.printf("%d %d\n", subsegments, subsequences);
		}
		out.flush();
		out.close();
	}

	static class MyScanner {

		BufferedReader br;
		StringTokenizer st;

		MyScanner(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
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
