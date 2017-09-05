package Codeforces;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

/*-
 * @author Mazen Magdy
 *				-We try all possible squares after placing a joker if needed.
 */
public class CF71_D2_D_Solitaire {

	static int N, M;
	static String[][] b;
	static TreeSet<String> cards;

	static boolean check(int i, int j) {
		HashSet<Character> suit = new HashSet<>(), rank = new HashSet<>();
		for (int r = i; r <= i + 2; r++)
			for (int c = j; c <= j + 2; c++) {
				suit.add(b[r][c].charAt(1));
				rank.add(b[r][c].charAt(0));
			}
		return suit.size() == 1 || rank.size() == 9;
	}

	static boolean isValid(int i, int j) {
		return i >= 0 && i < N && j >= 0 && j < M;
	}

	static boolean notIntersect(int i, int j, int r, int c) {
		return r > i + 2 || i > r + 2 || c > j + 2 || j > c + 2;
	}

	static int[] check() {
		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++) {
				if (isValid(i + 2, j + 2) && check(i, j))
					for (int r = 0; r < N; r++)
						for (int c = 0; c < M; c++)
							if (isValid(r + 2, c + 2) && notIntersect(i, j, r, c) && check(r, c)) {
								return new int[] { i + 1, j + 1, r + 1, c + 1 };
							}
			}
		return null;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		cards = new TreeSet<>();
		String[] suits = { "C", "D", "H", "S" },
				ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A" };
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 13; j++)
				cards.add(ranks[j] + suits[i]);
		System.err.println(cards);
		b = new String[N][M];
		int jokers = 0;
		int[] jokerPos = new int[4];
		boolean J1 = false;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < M; j++) {
				String s = sc.next();
				b[i][j] = s;
				if (s.equals("J1")) {
					jokerPos[0] = i;
					jokerPos[1] = j;
					J1 = true;
					jokers++;
					continue;
				}
				if (s.equals("J2")) {
					jokerPos[2] = i;
					jokerPos[3] = j;
					jokers++;
					continue;
				}
				cards.remove(s);
			}
		if (jokers == 0) {
			int[] result = check();
			if (result != null) {
				System.out.printf(
						"Solution exists.\nThere are no jokers.\nPut the first square to (%d, %d).\nPut the second square to (%d, %d).",
						result[0], result[1], result[2], result[3]);
			} else
				System.out.println("No solution.");
		} else if (jokers == 1) {
			for (String card : cards) {
				b[jokerPos[0]][jokerPos[1]] = card;
				int[] result = check();
				if (result != null) {
					System.out.println("Solution exists.");
					System.out.printf("Replace %s with %s.\n", J1 ? "J1" : "J2", card);
					System.out.printf("Put the first square to (%d, %d).\n", result[0], result[1]);
					System.out.printf("Put the second square to (%d, %d).\n", result[2], result[3]);
					return;
				}
			}
			System.out.println("No solution.");
		} else if (jokers == 2) {
			for (String c1 : cards) {
				for (String c2 : cards) {
					if (c1.equals(c2))
						continue;
					b[jokerPos[0]][jokerPos[1]] = c1;
					b[jokerPos[2]][jokerPos[3]] = c2;
					int[] result = check();
					if (result != null) {
						System.out.println("Solution exists.");
						System.out.printf("Replace J1 with %s and J2 with %s.\n", c1, c2);
						System.out.printf("Put the first square to (%d, %d).\n", result[0], result[1]);
						System.out.printf("Put the second square to (%d, %d).\n", result[2], result[3]);
						return;
					}
				}
			}
			System.out.println("No solution.");
		}
	}

	static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		public MyScanner(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
		}

		public MyScanner(String filename) throws FileNotFoundException {
			br = new BufferedReader(new FileReader(new File(filename)));
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public boolean ready() throws IOException {
			return br.ready();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}
	}
}
