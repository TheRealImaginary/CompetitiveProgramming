package LiveArchive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-Both Traversals need to be the same, which is similar to Tree Isomorphism. We loop
 *				 on the String and maintaining a level counter, once it reaches 0 meaning we returned to
 *				 a root of a subtree we recurse to this subtree get the level spectrum. If both Trees have
 *				 the same level spectrum then they are the same.
 */
// Tree isomorphism
public class LiveArchive_2935_SubwayTreeSystems {

	static int cnt;
	static HashMap<ArrayList<Integer>, Integer> map;

	static int dfs(char[] c, int l, int r) {
		ArrayList<Integer> a = new ArrayList<>();
		a.add(0);

		int depth = 0;
		for (int i = l, x = l; i <= r; i++) {
			if (c[i] == '0')
				depth++;
			else if (c[i] == '1')
				depth--;
			if (depth == 0) {
				// Go to this subtree, can be of size 1
				a.add(dfs(c, x + 1, i - 1));
				x = i + 1;
			}
		}

		Collections.sort(a);
		a.add(1);

		if (!map.containsKey(a))
			map.put(a, cnt++);

		return map.get(a);
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			char[] a = sc.nextLine().toCharArray(), b = sc.nextLine().toCharArray();
			if (a.length != b.length)
				out.println("different");
			else {
				int N = a.length;

				map = new HashMap<>();

				int x = dfs(a, 0, N - 1);

				System.err.println(map);

				int y = dfs(b, 0, N - 1);

				System.err.println(map);

				// System.err.println(x + " " + y);
				out.println(x == y ? "same" : "different");
			}
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
