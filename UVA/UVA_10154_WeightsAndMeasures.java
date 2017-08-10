package UVA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-Let dp[i] be the minimum weight of turtles that can make up a stack
 *				 of size i, for every turtle j, we can put it at the bottom if it can
 *				 carry i turtles hence making a stack of size i + 1.
 */
public class UVA_10154_WeightsAndMeasures {

	static class Turtle {
		int w, s;

		public Turtle(int ww, int ss) {
			w = ww;
			s = ss;
		}

		@Override
		public String toString() {
			return w + " " + s;
		}
	}

	static final int oo = (int) 1e9;

	public static void main(String[] args) throws IOException {
		// MyScanner sc = new MyScanner(System.in);
		MyScanner sc = new MyScanner(new FileInputStream("in.txt"));
		ArrayList<Turtle> t = new ArrayList<>();
		while (sc.ready()) {
			int w = sc.nextInt(), s = sc.nextInt();
			t.add(new Turtle(w, s));
		}
		Collections.sort(t, new Comparator<Turtle>() {

			@Override
			public int compare(Turtle t1, Turtle t2) {
				return t1.s - t2.s;
			}

		});
		int[] dp = new int[t.size() + 1];
		int[] len = new int[t.size() + 1];
		Arrays.fill(dp, oo);
		dp[0] = 0;
		int ans = 0;
		for (int i = 0; i < t.size(); i++) {
			int w = t.get(i).w;
			int s = t.get(i).s;
			for (int j = t.size() - 1; j >= 0; j--) {
				if (s >= dp[j] + w && dp[j] + w < dp[j + 1]) {
					dp[j + 1] = dp[j] + w;
					ans = Math.max(ans, len[j + 1] = len[j] + 1);
				}
			}
		}
		System.out.println(ans);
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
