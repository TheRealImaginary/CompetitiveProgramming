package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * 
 * @author Mazen Magdy
 * 
 *         Idea - Pruning and Heuristic
 * 
 *         -The Naive approach would be to try out all possible numbers in each
 *         	position increasingly and then take the minimum length at the end we
 *         	would need a nested loop O(n^2) work per recursive call to check that
 *         	a[i] + a[j] exists.
 *         -We can make the naive approach faster by doing 2 things:
 * 				-Once we chose a number to put in position i, we increment numbers in the form of a[i] + a[j]
 * 				where j < i by 1, indicating that we can use numbers in this form later on.
 * 		   -We prune any state where the shortest path from this state to state "N" is greater than the current answer.
 */
public class UVA_529_AdditionChains {

	static final int MAX = 10001;
	static final int oo = 1 << 27;

	static int[] a, b, c;
	static int N;
	static int ans;
	static int[] exist;

	static void solve(int len, int last) {
		if (len + c[last] >= ans)
			return;
		if (last > N)
			return;
		if (last == N) {
			if (ans > len) {
				ans = len;
				for (int i = 0; i < len; i++)
					b[i] = a[i];
			}
			return;
		}
		for (int num = Math.min(last << 1, N); num > last; num--) {
			if (exist[num] == 0)
				continue;
			a[len] = num;
			for (int i = 0; i <= len; i++)
				exist[a[i] + num]++;
			solve(len + 1, num);
			for (int i = 0; i <= len; i++)
				exist[a[i] + num]--;
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			N = sc.nextInt();
			if (N == 0)
				break;
			ans = oo;
			a = new int[MAX << 1];
			b = new int[MAX << 1];
			c = new int[(N + 1) << 1];
			for (int i = N - 1; i > 0; i--)
				c[i] = c[i << 1] + 1;
			// System.err.println(Arrays.toString(c));
			a[0] = 1;
			exist = new int[MAX << 1];
			exist[1] = exist[2] = 1;
			solve(1, 1);
			// System.err.println(ans);
			for (int i = 0; i < ans; i++) {
				if (i > 0)
					out.print(" ");
				out.print(b[i]);
			}
			out.println();
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
