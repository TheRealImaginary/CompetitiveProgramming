package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*-
 * @author Mazen Magdy
 *				-For each word we try each possible insertion, change, deletion.
 */
public class UVA_10029_EditStepLadders {

	static int tryInsert(String a, Map<String, Integer> dp) {
		int n = a.length();
		Integer max = dp.get(a);
		max = max == null ? 1 : max;
		for (int i = 0; i < n; i++) {
			for (char c = 'a'; c <= 'z'; c++) {
				String newString = a.substring(0, i) + c + a.substring(i);
				Integer x = dp.get(newString);
				x = x == null ? 0 : x;
				max = Math.max(max, x + 1);
			}
		}
		return max;
	}

	static int tryChange(String a, Map<String, Integer> dp) {
		int n = a.length();
		Integer max = dp.get(a);
		max = max == null ? 1 : max;
		for (int i = 0; i < n; i++) {
			for (char c = 'a'; c <= 'z'; c++) {
				String newString = a.substring(0, i) + c + a.substring(i + 1);
				Integer x = dp.get(newString);
				x = x == null ? 0 : x;
				max = Math.max(max, x + 1);
			}
		}
		return max;
	}

	static int tryDelete(String a, Map<String, Integer> dp) {
		int n = a.length();
		Integer max = dp.get(a);
		max = max == null ? 1 : max;
		for (int i = 0; i < n; i++) {
			String newString = a.substring(0, i) + a.substring(i + 1);
			Integer x = dp.get(newString);
			x = x == null ? 0 : x;
			max = Math.max(max, x + 1);
		}
		return max;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		ArrayList<String> s = new ArrayList<>();
		while (sc.ready())
			s.add(sc.next());
		Map<String, Integer> dp = new TreeMap<>();
		// Map<String, Integer> dp = new HashMap<>(); // 1 sec difference
		int N = s.size();
		for (int i = 0; i < N; i++) {
			String a = s.get(i);
			dp.put(a, Math.max(tryInsert(a, dp), Math.max(tryChange(a, dp), tryDelete(a, dp))));
		}
		int ans = 0;
		for (Integer x : dp.values())
			ans = Math.max(ans, x);
		out.println(ans);
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
