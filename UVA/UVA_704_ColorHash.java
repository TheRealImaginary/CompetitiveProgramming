package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*-
 * @author Mazen Magdy
 * @see http://www.spoj.com/problems/ANARC08A/
 */
// WA Different Path not sure why, although correct lengths
// I also tried using BFS and gave the same verdict
public class UVA_704_ColorHash { // TODO

	static final int oo = 1 << 28;
	static final int N = 24;
	static final int[] FINAL_STATE = new int[] { 0, 3, 4, 3, 0, 5, 6, 5, 0, 1, 2, 1, 0, 7, 8, 7, 0, 9, 10, 9, 0, 1, 2,
			1 };
	static final int LIMIT = 16;
	static final String DONE = "PUZZLE ALREADY SOLVED";
	static final String NO_SOLUTION = "NO SOLUTION WAS FOUND IN 16 STEPS";

	static TreeMap<Board, String> path;
	static String sol;

	static class Board implements Comparable<Board> {
		int[] board;
		int move;

		Board(int[] a) {
			this(a, -1);
		}

		Board(int[] a, int m) {
			board = a;
			move = m;
		}

		Board rotateClockwise() {
			int size = 12;
			int[] a = board.clone();
			for (int i = 0; i < size; i++)
				a[(i + 2) % size] = board[i];
			a[21] = a[9];
			a[22] = a[10];
			a[23] = a[11];
			return new Board(a);
		}

		Board rotateRightHalfClockwise() {
			int size = 24, mod = 12;
			int[] a = board.clone();
			for (int i = 12; i < size; i++)
				a[((((i - 14) % mod) + mod) % mod) + mod] = board[i];
			a[9] = a[21];
			a[10] = a[22];
			a[11] = a[23];
			return new Board(a);
		}

		Board rotateCounterClockwise() {
			int size = 12;
			int[] a = board.clone();
			for (int i = 0; i < size; i++)
				a[(((i - 2) % size) + size) % size] = board[i];
			a[21] = a[9];
			a[22] = a[10];
			a[23] = a[11];
			return new Board(a);
		}

		Board rotateRightHalfCounterClockwise() {
			int size = 24, mod = 12;
			int[] a = board.clone();
			for (int i = 12; i < size; i++)
				a[((i + 2) % mod) + mod] = board[i];
			a[9] = a[21];
			a[10] = a[22];
			a[11] = a[23];
			return new Board(a);
		}

		boolean isFinalState() {
			return compareTo(new Board(FINAL_STATE)) == 0;
		}

		@Override
		public String toString() {
			return Arrays.toString(board) + " " + move;
		}

		@Override
		public int compareTo(Board b) {
			for (int i = 0; i < N; i++)
				if (board[i] < b.board[i])
					return -1;
				else if (board[i] > b.board[i])
					return 1;
			return 0;
		}
	}

	static void dfs(int prev, int depth, Board current, String p) {
		if (depth > LIMIT >> 1)
			return;

		String mightBePath = path.get(current);
		if (mightBePath == null || mightBePath.length() > p.length()
				|| (mightBePath.length() == p.length() && mightBePath.compareTo(p) > 0))
			path.put(current, p);

		if (prev != 3)
			dfs(1, depth + 1, current.rotateClockwise(), 3 + p);
		if (prev != 1)
			dfs(3, depth + 1, current.rotateCounterClockwise(), 1 + p);
		if (prev != 4)
			dfs(2, depth + 1, current.rotateRightHalfClockwise(), 4 + p);
		if (prev != 2)
			dfs(4, depth + 1, current.rotateRightHalfCounterClockwise(), 2 + p);
	}

	static void solve(int prev, int depth, Board current, String p) {
		if (depth > LIMIT >> 1)
			return;

		String restOfPath = path.get(current);
		if (restOfPath != null)
			if (sol.length() == 0 || restOfPath.length() + p.length() < sol.length()) {
				sol = p + restOfPath;
			}

		if (prev != 3)
			solve(1, depth + 1, current.rotateClockwise(), p + 1);
		if (prev != 1)
			solve(3, depth + 1, current.rotateCounterClockwise(), p + 3);
		if (prev != 4)
			solve(2, depth + 1, current.rotateRightHalfClockwise(), p + 2);
		if (prev != 2)
			solve(4, depth + 1, current.rotateRightHalfCounterClockwise(), p + 4);
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);

		path = new TreeMap<>();

		final Board FINAL = new Board(FINAL_STATE);
		dfs(0, 0, FINAL, "");

		int t = sc.nextInt();
		while (t-- > 0) {
			int[] board = new int[N];
			for (int i = 0; i < N; i++)
				board[i] = sc.nextInt();
			Board start = new Board(board);
			if (start.isFinalState()) {
				out.println("DONE");
			} else {
				sol = "";
				solve(0, 0, start, "");
				out.println(sol.length() == 0 ? NO_SOLUTION : sol);
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
