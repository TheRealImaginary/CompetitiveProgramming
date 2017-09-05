package SPOJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We insert all strings into a Trie then we dfs for each query
 */
public class DICT { // TLE

	static final int ALPHA = 26;

	static class Trie {

		static class Node {
			Node[] next;
			boolean end;

			Node() {
				next = new Node[ALPHA];
				end = false;
			}
		}

		Node root;
		boolean flag;

		Trie() {
			root = new Node();
		}

		void insert(String s) {
			Node node = root;
			int n = s.length();
			for (int i = 0; i < n; i++) {
				int idx = s.charAt(i) - 'a';
				if (node.next[idx] == null)
					node.next[idx] = new Node();
				node = node.next[idx];
			}
			node.end = true;
		}

		boolean search(String prefix, PrintWriter out) {
			flag = false;
			Node node = root;
			int n = prefix.length();
			for (int i = 0; i < n; i++) {
				int idx = prefix.charAt(i) - 'a';
				if (node.next[idx] == null)
					return false;
				node = node.next[idx];
			}
			dfs(node, prefix, new StringBuilder(prefix), out);
			return flag;
		}

		void dfs(Node node, String prefix, StringBuilder s, PrintWriter out) {
			if (node.end && !prefix.equals(s.toString()))
				out.println(s);
			for (int i = 0; i < ALPHA; i++)
				if (node.next[i] != null) {
					flag = true;
					dfs(node.next[i], prefix, new StringBuilder(s.toString()).append((char) (i + 'a')), out);
				}
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		Trie trie = new Trie();
		int n = sc.nextInt();
		while (n-- > 0)
			trie.insert(sc.next());
		int q = sc.nextInt();
		for (int t = 1; t <= q; t++) {
			out.printf("Case #%d:\n", t);
			if (!trie.search(sc.next(), out))
				out.println("No match.");
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
