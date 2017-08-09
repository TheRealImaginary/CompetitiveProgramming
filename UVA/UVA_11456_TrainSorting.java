package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-The LDS starting at i can be put in the beginning and LIS
 *				 ending at i can be put at the end. 
 */
public class UVA_11456_TrainSorting {

	// LIS starting at i, LDS ending at i
	static int[] LIS, LDS;

	public static int LDS(int[] a) {
		int n = a.length;
		int lds = 0;
		for (int i = n - 1; i >= 0; i--) {
			int curL = 1;
			for (int j = i + 1; j < n; j++) {
				if (a[j] < a[i])
					curL = Math.max(curL, 1 + LDS[j]);
			}
			LDS[i] = curL;
			lds = Math.max(lds, curL);
		}
		return lds;
	}

	public static int LIS(int[] a) {
		int n = a.length;
		int lis = 0;
		for (int i = n - 1; i >= 0; i--) {
			int curL = 1;
			for (int j = i + 1; j < n; j++) {
				if (a[j] > a[i])
					curL = Math.max(curL, 1 + LIS[j]);
			}
			LIS[i] = curL;
			lis = Math.max(lis, curL);
		}
		return lis;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt();
			int[] a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = sc.nextInt();
			LIS = new int[n];
			LDS = new int[n];
			LIS(a);
			LDS(a);
			int ans = 0;
			for (int i = 0; i < n; i++)
				ans = Math.max(ans, LIS[i] + LDS[i] - 1);
			pw.println(ans);
		}
		pw.close();
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
