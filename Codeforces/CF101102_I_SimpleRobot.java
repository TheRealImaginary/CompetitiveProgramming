package Codeforces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class CF101102_I_SimpleRobot {

	static int R, C;
	static String row, col;

	static int solveCol(int c) {
		int res = 0, currentCol = c;
		for (int i = 0; i < col.length(); i++) {
			if (col.charAt(i) == '>') {
				if (currentCol == C)
					res++;
				else
					currentCol++;
			} else if (col.charAt(i) == '<') {
				if (currentCol == 1)
					res++;
				else
					currentCol--;
			}
		}
		return res;
	}

	static int solveRow(int r) {
		int res = 0, currentRow = r;
		for (int i = 0; i < row.length(); i++) {
			if (row.charAt(i) == 'v') {
				if (currentRow == R)
					res++;
				else
					currentRow++;
			} else if (row.charAt(i) == '^') {
				if (currentRow == 1)
					res++;
				else
					currentRow--;
			}
		}
		return res;
	}

	static int TernarySearch(int left, int right, boolean row) {
		while (right - left > 2) {
			int l1 = left + (right - left) / 3, l2 = right - (right - left) / 3;
			if (row) {
				if (solveRow(l1) < solveRow(l2))
					right = l2;
				else
					left = l1;
			} else {
				if (solveCol(l1) < solveCol(l2))
					right = l2;
				else
					left = l1;
			}
		}
		return row ? Math.min(solveRow(left), Math.min(solveRow(right), solveRow((left + right) / 2)))
				: Math.min(solveCol(left), Math.min(solveCol(right), solveCol((left + right) / 2)));
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int tc = sc.nextInt();
		while (tc-- > 0) {
			R = sc.nextInt();
			C = sc.nextInt();
			String commands = sc.next();
			StringBuilder rowCommands = new StringBuilder(), colCommands = new StringBuilder();
			for (int i = 0; i < commands.length(); i++) {
				char c = commands.charAt(i);
				if (c == '>' || c == '<')
					colCommands.append(c);
				else
					rowCommands.append(c);
			}
			row = rowCommands.toString();
			col = colCommands.toString();
			out.println(TernarySearch(1, R, true) + TernarySearch(1, C, false));
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
