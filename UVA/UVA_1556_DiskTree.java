package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*-
 * @author Mazen Magdy
 *				-We use a Trie to to insert all directories and sub-directories then we DFS for the answer.
 */
public class UVA_1556_DiskTree { // RTE

	static class Trie {

		static class Node {
			TreeMap<String, Node> map;

			Node() {
				map = new TreeMap<>();
			}

			boolean contains(String s) {
				return map.containsKey(s);
			}

			void put(String s) {
				map.put(s, new Node());
			}

			Node get(String s) {
				return map.get(s);
			}

			Set<Entry<String, Node>> getEntrySet() {
				return map.entrySet();
			}
		}

		Node root;

		Trie() {
			root = new Node();
		}

		void insert(String s) {
			StringTokenizer st = new StringTokenizer(s);
			Node node = root;
			while (st.hasMoreTokens()) {
				String a = st.nextToken();
				if (!node.contains(a))
					node.put(a);
				node = node.get(a);
			}
		}

		void dfs(PrintWriter out) {
			dfs(root, 0, out);
		}

		void dfs(Node node, int prev, PrintWriter out) {
			if (node == null)
				return;
			for (Entry<String, Node> entry : node.getEntrySet()) {
				// if (prev != 0)
				// out.printf("%" + prev + "s%s\n", "", entry.getKey());
				// else
				// out.println(entry.getKey());
				int tmp = prev;
				while (tmp-- > 0)
					out.print(" ");
				out.println(entry.getKey());
				dfs(entry.getValue(), prev + 1, out);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		while (sc.ready()) {
			String line = sc.nextLine();
			if (line == null)
				break;
			sc.st = new StringTokenizer(line);
			int n = sc.nextInt();
			String[] in = new String[n];
			for (int i = 0; i < n; i++) {
				in[i] = sc.next().replaceAll("\\\\", " ");
			}
			// System.err.println(Arrays.toString(in));
			Trie trie = new Trie();
			for (int i = 0; i < n; i++)
				trie.insert(in[i]);
			trie.dfs(out);
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
