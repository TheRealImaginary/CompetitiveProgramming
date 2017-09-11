package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-For each Word `w` that we can make using some of our letters and
 *				 the letters on the board (we need to account for each intersection/common letter)
 *				 we calculate its score and take the maximum.
 */
public class UVA_655_Scrabble {

	private static class Game {
		String letters, board;
		int position;
		int[] cnt;
		ArrayList<Integer>[] posOnBoard;

		Game(String line) {
			cnt = new int[ALPHA];
			posOnBoard = new ArrayList[ALPHA];
			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);
				if (Character.isDigit(c)) {
					letters = line.substring(0, i);
					board = line.substring(i + 1);
					position = c - '0';
					for (int j = 0; j < board.length(); j++) {
						if (posOnBoard[board.charAt(j) - 'A'] == null)
							posOnBoard[board.charAt(j) - 'A'] = new ArrayList<>();
						posOnBoard[board.charAt(j) - 'A'].add(j);
					}
					break;
				}
				cnt[c - 'A']++;
			}
		}

		@Override
		public String toString() {
			return Arrays.toString(cnt) + "\n" + letters + " " + board + " " + position + "\n";
		}

		public int canMake(Word w) {
			int need = 0, idx = -1;
			for (int i = 0; i < ALPHA; i++)
				if (cnt[i] < w.cnt[i]) {
					need += w.cnt[i] - cnt[i];
					idx = i;
				}
			if (need == 1)
				return idx;
			if (need == 0)
				return -1;
			return -2;
		}
	}

	static class Word {
		String word;
		int[] cnt;
		ArrayList<Integer>[] pos;

		Word(String s) {
			word = s;
			cnt = new int[ALPHA];
			pos = new ArrayList[ALPHA];
			for (int i = 0; i < s.length(); i++) {
				cnt[s.charAt(i) - 'A']++;
				if (pos[s.charAt(i) - 'A'] == null)
					pos[s.charAt(i) - 'A'] = new ArrayList<>();
				pos[s.charAt(i) - 'A'].add(i);
			}
		}

		@Override
		public String toString() {
			return word + "\n" + Arrays.toString(cnt) + "\n";
		}
	}

	private static final int ALPHA = 26;
	private static final char[][] BOARD = {
			{ '5', '-', '-', '2', '-', '-', '-', '5', '-', '-', '-', '2', '-', '-', '5' },
			{ '-', '4', '-', '-', '-', '3', '-', '-', '-', '3', '-', '-', '-', '4', '-' },
			{ '-', '-', '4', '-', '-', '-', '2', '-', '2', '-', '-', '-', '4', '-', '-' },
			{ '2', '-', '-', '4', '-', '-', '-', '2', '-', '-', '-', '4', '-', '-', '2' },
			{ '-', '-', '-', '-', '4', '-', '-', '-', '-', '-', '4', '-', '-', '-', '-' },
			{ '-', '3', '-', '-', '-', '3', '-', '-', '-', '3', '-', '-', '-', '3', '-' },
			{ '-', '-', '2', '-', '-', '-', '2', '-', '2', '-', '-', '-', '2', '-', '-' },
			{ '5', '-', '-', '2', '-', '-', '-', '4', '-', '-', '-', '2', '-', '-', '5' },
			{ '-', '-', '2', '-', '-', '-', '2', '-', '2', '-', '-', '-', '2', '-', '-' },
			{ '-', '3', '-', '-', '-', '3', '-', '-', '-', '3', '-', '-', '-', '3', '-' },
			{ '-', '-', '-', '-', '4', '-', '-', '-', '-', '-', '4', '-', '-', '-', '-' },
			{ '2', '-', '-', '4', '-', '-', '-', '2', '-', '-', '-', '4', '-', '-', '2' },
			{ '-', '-', '4', '-', '-', '-', '2', '-', '2', '-', '-', '-', '4', '-', '-' },
			{ '-', '4', '-', '-', '-', '3', '-', '-', '-', '3', '-', '-', '-', '4', '-' },
			{ '5', '-', '-', '2', '-', '-', '-', '5', '-', '-', '-', '2', '-', '-', '5' } };
	private static final int[] SCORES = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4,
			10 };

	private static int[] calculateScore(Game g, Word w, int idx, int i) {
		int n = w.word.length();
		int maxScore = 0, myPos = -1, hisPos = -1;
		if (g.posOnBoard[idx] != null && !g.posOnBoard[idx].isEmpty())
			for (int p : g.posOnBoard[idx]) {
				int Double = 0, Triple = 0, score = SCORES[idx];
				for (int j = 0; j < i; j++) {
					switch (BOARD[7 - (i - j)][7 + p - g.position + 1]) {
					case '-':
						score += SCORES[w.word.charAt(j) - 'A'];
						break;
					case '2':
					case '3':
						score += SCORES[w.word.charAt(j) - 'A'] * (BOARD[7 - (i - j)][7 + p - g.position + 1] - '0');
						break;
					case '4':
						Double++;
						score += SCORES[w.word.charAt(j) - 'A'];
						break;
					case '5':
						Triple++;
						score += SCORES[w.word.charAt(j) - 'A'];
						break;
					}
				}
				for (int j = i + 1; j < n; j++) {
					switch (BOARD[7 + (j - i)][7 + p - g.position + 1]) {
					case '-':
						score += SCORES[w.word.charAt(j) - 'A'];
						break;
					case '2':
					case '3':
						score += SCORES[w.word.charAt(j) - 'A'] * (BOARD[7 + (j - i)][7 + p - g.position + 1] - '0');
						break;
					case '4':
						score += SCORES[w.word.charAt(j) - 'A'];
						Double++;
						break;
					case '5':
						Triple++;
						score += SCORES[w.word.charAt(j) - 'A'];
						break;
					}
				}
				while (Double-- > 0)
					score <<= 1;
				while (Triple-- > 0)
					score *= 3;
				if (w.word.length() == 8)
					score += 50;
				if (maxScore < score) {
					maxScore = score;
					myPos = i;
					hisPos = p;
				}
			}
		return new int[] { maxScore, myPos + 1, hisPos + 1 };
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		ArrayList<Game> games = new ArrayList<>();
		String s;
		while ((s = sc.next()).length() > 8)
			games.add(new Game(s));
		ArrayList<Word> dictionary = new ArrayList<>();
		dictionary.add(new Word(s));
		while (sc.ready())
			dictionary.add(new Word(sc.next()));
		int t = 0;
		for (Game g : games) {
			int maxScore = 0, myPos = -1, hisPos = -1;
			Word chosen = null;
			// System.err.println(g);
			for (Word w : dictionary) {
				int can = g.canMake(w);
				if (can == -2)
					continue;
				int n = w.word.length();
				if (can == -1) {
					for (int i = 0; i < n; i++) {
						char c = w.word.charAt(i);
						if (g.posOnBoard[c - 'A'] == null || g.posOnBoard[c - 'A'].isEmpty())
							continue;
						int[] score = calculateScore(g, w, c - 'A', i);
						if (maxScore < score[0]) {
							maxScore = score[0];
							myPos = score[1];
							hisPos = score[2];
							chosen = w;
						}
					}
				} else {
					for (int p : w.pos[can]) {
						int[] score = calculateScore(g, w, can, p);
						if (maxScore < score[0]) {
							maxScore = score[0];
							myPos = score[1];
							hisPos = score[2];
							chosen = w;
						}
					}
				}
			}
			out.printf("%-8s%2d%2d%4d\n", chosen.word, hisPos, myPos, maxScore);
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
