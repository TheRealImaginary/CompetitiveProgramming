package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-Since there are 5 symbols we try each possibility for each one
 *				 all of them must make the function true hence the function being a tautology.
 */
public class UVA_11108_Tautology {

	static final int MAX = 5;

	static int getBit(char c) {
		switch (c) {
		case 'p':
			return 0;
		case 'q':
			return 1;
		case 'r':
			return 2;
		case 's':
			return 3;
		case 't':
			return 4;
		}
		return -1;
	}

	// c is in prefix notation
	static int solve(char[] c, int msk) {
		int n = c.length;
		Stack<Integer> stack = new Stack<>();
		for (int i = n - 1; i >= 0; i--) {
			int bit = getBit(c[i]);
			if (bit != -1) {
				stack.push((msk & (1 << bit)) != 0 ? 1 : 0);
			} else {
				switch (c[i]) {
				case 'K':
					stack.push(stack.pop() & stack.pop());
					break;
				case 'A':
					stack.push(stack.pop() | stack.pop());
					break;
				case 'N':
					stack.push(stack.pop() ^ 1);
					break;
				case 'C':
					stack.push((stack.pop() ^ 1) | stack.pop());
					break;
				case 'E':
					stack.push(stack.pop() == stack.pop() ? 1 : 0);
					break;
				}
			}
		}
		return stack.peek();
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			char[] c = sc.nextLine().toCharArray();
			if (c[0] == '0')
				break;
			// Must be true for all 2^5 possibilities
			int f = 1;
			for (int msk = 0; msk < 1 << MAX && f == 1; msk++)
				f &= solve(c, msk);
			out.println(f == 1 ? "tautology" : "not");
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
