package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *
 *		   Idea : Reference Manipulation
 *
 *		   -Each element is a Node that has references to both the element after and the element before it
 *		    for swapping we simply swap the data in the nodes, for the other two operation we manipulate the reference
 *			of Node A, B and their neighbors accordingly, for reverse we only need to keep track of its parity and adjust
 *			the operations accordingly. For each if reverse is odd and we need to move element A to B's left then we actually
 *			move A to B's right.
 */
public class UVA_12657_BoxesInALine {

	static class Node {
		Node left, right;
		int data;

		Node(int x) {
			data = x;
			left = right = null;
		}

		@Override
		public String toString() {
			return data + " ";
		}
	}

	static Node head, tail;
	static HashMap<Integer, Node> map;

	private static void debug() {
		Node node = head;
		while (node != null) {
			System.err.print("Left is " + node.left);
			System.err.print(" This node is " + node);
			System.err.println(" Right node is " + node.right);
			node = node.right;
		}
	}

	static void putLeft(int x, int y) {
		Node a = map.get(x), b = map.get(y);
		if (a.right == b && b.left == a)
			return;
		Node leftA = a.left, rightA = a.right, leftB = b.left;
		if (leftA != null)
			leftA.right = rightA;
		if (rightA != null)
			rightA.left = leftA;
		a.right = b;
		a.left = leftB;
		if (leftB != null)
			leftB.right = a;
		b.left = a;
		if (head == a)
			head = rightA;
		else if (head == b)
			head = a;
		if (tail == a)
			tail = leftA;
	}

	static void putRight(int x, int y) {
		Node a = map.get(x), b = map.get(y);
		if (a.left == b && b.right == a)
			return;
		Node leftA = a.left, rightA = a.right, rightB = b.right;
		if (leftA != null)
			leftA.right = rightA;
		if (rightA != null)
			rightA.left = leftA;
		a.left = b;
		a.right = b.right;
		if (rightB != null)
			rightB.left = a;
		b.right = a;
		if (head == a)
			head = rightA;
		if (tail == b)
			tail = a;
		else if (tail == a)
			tail = leftA;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		int Case = 1;
		while (sc.ready()) {
			int n = sc.nextInt(), q = sc.nextInt();
			map = new HashMap<>();
			head = new Node(1);
			map.put(1, head);
			Node prev = head;
			for (int i = 1; i < n; i++) {
				Node node = new Node(i + 1);
				prev.right = node;
				node.left = prev;
				prev = node;
				map.put(i + 1, node);
			}
			tail = prev;
			int reverse = 0;
			while (q-- > 0) {
				int type = sc.nextInt();
				if (type == 1) {
					if ((reverse & 1) == 0)
						putLeft(sc.nextInt(), sc.nextInt());
					else
						putRight(sc.nextInt(), sc.nextInt());
				} else if (type == 2) {
					if ((reverse & 1) == 0)
						putRight(sc.nextInt(), sc.nextInt());
					else
						putLeft(sc.nextInt(), sc.nextInt());
				} else if (type == 3) {
					int x = sc.nextInt(), y = sc.nextInt();
					Node a = map.get(x), b = map.get(y);
					a.data ^= b.data;
					b.data ^= a.data;
					a.data ^= b.data;
					map.put(a.data, a);
					map.put(b.data, b);
				} else if (type == 4) {
					reverse++;
				}
			}
			long res = 0;
			int idx = 1;
			Node node = null;
			if ((reverse & 1) == 0) {
				node = head;
				while (node != null) {
					if ((idx & 1) != 0)
						res += node.data;
					node = node.right;
					idx++;
				}
			} else {
				node = tail;
				while (node != null) {
					if ((idx & 1) != 0)
						res += node.data;
					node = node.left;
					idx++;
				}
			}
			out.printf("Case %d: %d\n", Case++, res);
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
