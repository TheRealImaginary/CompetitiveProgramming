package Codeforces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.StringTokenizer;

public class CF670_D2_E_CorrectBracketSequenceEditor {

	// We don't need index, could use char itself
	static class Node {
		Node left, right, pairedWith;
		char c;
		int idx;

		Node(char c, int i) {
			this.c = c;
			idx = i;
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = sc.nextInt(), m = sc.nextInt(), k = sc.nextInt();
		char[] c = sc.next().toCharArray();
		Stack<Node> stack = new Stack<>();
		Node cursor = null, prev = null;
		for (int i = 0; i < n; i++) {
			if (prev == null) {
				prev = new Node(c[i], i);
				cursor = prev;
				stack.push(prev);
			} else {
				Node node = new Node(c[i], i);
				node.left = prev;
				prev.right = node;
				prev = node;
				// There must be something in stack
				if (c[i] == ')') {
					Node pair = stack.pop();
					pair.pairedWith = node;
					node.pairedWith = pair;
				} else {
					stack.push(node);
				}
			}
		}
		while (--k > 0)
			cursor = cursor.right;
		// System.err.println(cursor);
		char[] ins = sc.next().toCharArray();
		for (int i = 0; i < m; i++) {
			if (ins[i] == 'L')
				cursor = cursor.left;
			else if (ins[i] == 'R')
				cursor = cursor.right;
			else {
				Node pair = cursor.pairedWith;
				if (pair.idx < cursor.idx) {
					Node temp = pair;
					pair = cursor;
					cursor = temp;
					temp = null;
				}
				// cursor is earlier
				Node leftCursor = cursor.left, rightPair = pair.right;
				if (leftCursor != null)
					leftCursor.right = rightPair;
				if (rightPair != null)
					rightPair.left = leftCursor;
				if (rightPair != null)
					cursor = rightPair;
				else
					cursor = leftCursor;
			}
			// System.err.println(cursor);
		}
		while (cursor.left != null)
			cursor = cursor.left;
		StringBuilder sb = new StringBuilder();
		while (cursor != null) {
			sb.append(cursor.c);
			cursor = cursor.right;
		}
		out.println(sb);
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
