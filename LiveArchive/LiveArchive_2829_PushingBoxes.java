package LiveArchive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We move the Boxes accordingly sorting them to speed-up our solution. We need find out
 *				 the number of boxes in a row/column when moving up/down, left/right respectively and
 *				 take the minimum between that number and units needed to be moved.
 */
public class LiveArchive_2829_PushingBoxes { // WA

	static class Box {
		int row, col;

		Box(int a, int b) {
			row = a;
			col = b;
		}

		@Override
		public String toString() {
			return row + " " + col;
		}
	}

	static int H, W;
	static int N;
	static Box[] boxes;

	static void moveDown(int units) {
		Arrays.sort(boxes, (a, b) -> a.col == b.col ? a.row - b.row : a.col - b.col);
		int count = 1, x = 1;
		for (int i = 1; i < N; i++)
			if (boxes[i].col == boxes[i - 1].col)
				count = Math.max(count, x = x + 1);
			else
				x = 1;
		int left = H - count;
		if (left < units)
			units = left;
		for (int i = 0; i < N; i++)
			if (boxes[i].row < units)
				boxes[i].row = units;
		for (int i = 1; i < N; i++) {
			while (i < N && boxes[i].col == boxes[i - 1].col) {
				boxes[i].row = boxes[i].row > boxes[i - 1].row ? boxes[i].row : boxes[i - 1].row + 1;
				i++;
			}
		}
	}

	static void moveUp(int units) {
		Arrays.sort(boxes, (a, b) -> a.row == b.row ? b.row - a.row : a.col - b.col);
		int count = 1, x = 1;
		for (int i = 1; i < N; i++)
			if (boxes[i].col == boxes[i - 1].col)
				count = Math.max(count, x = x + 1);
			else
				x = 1;
		int left = H - count;
		if (left < units)
			units = left;
		for (int i = 0; i < N; i++)
			if (boxes[i].row >= H - units)
				boxes[i].row = H - units - 1;
		for (int i = 1; i < N; i++) {
			while (i < N && boxes[i].col == boxes[i - 1].col) {
				boxes[i].row = boxes[i].row < boxes[i - 1].row ? boxes[i].row : boxes[i - 1].row - 1;
				i++;
			}
		}
	}

	static void moveRight(int units) {
		Arrays.sort(boxes, (a, b) -> a.col == b.col ? a.col - b.col : a.row - b.row);
		int count = 1, y = 1;
		for (int i = 1; i < N; i++)
			if (boxes[i].row == boxes[i - 1].row)
				count = Math.max(count, y = y + 1);
			else
				y = 1;
		int left = W - count;
		if (left < units)
			units = left;
		for (int i = 0; i < N; i++)
			if (boxes[i].col < units)
				boxes[i].col = units;
		for (int i = 1; i < N; i++)
			while (i < N && boxes[i].row == boxes[i - 1].row) {
				boxes[i].col = boxes[i].col > boxes[i - 1].col ? boxes[i].col : boxes[i - 1].col + 1;
				i++;
			}
	}

	static void moveLeft(int units) {
		Arrays.sort(boxes, (a, b) -> a.row == b.row ? b.col - a.col : a.row - b.row);
		int count = 1, y = 1;
		for (int i = 1; i < N; i++)
			if (boxes[i].row == boxes[i - 1].row)
				count = Math.max(count, y = y + 1);
			else
				y = 1;
		int left = W - count;
		if (left < units)
			units = left;
		for (int i = 0; i < N; i++)
			if (boxes[i].col >= W - units)
				boxes[i].col = W - units - 1;
		for (int i = 1; i < N; i++) {
			while (i < N && boxes[i].row == boxes[i - 1].row) {
				boxes[i].col = boxes[i].col < boxes[i - 1].col ? boxes[i].col : boxes[i - 1].col - 1;
				i++;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		for (int t = 1; true; t++) {
			H = sc.nextInt();
			W = sc.nextInt();
			if (H == 0 && W == 0)
				break;
			N = sc.nextInt();
			boxes = new Box[N];
			for (int i = 0; i < N; i++)
				boxes[i] = new Box(sc.nextInt(), sc.nextInt());
			while (true) {
				String operation = sc.next();
				if (operation.equals("done"))
					break;
				int units = sc.nextInt();
				if (operation.equals("left"))
					moveLeft(units);
				else if (operation.equals("right"))
					moveRight(units);
				else if (operation.equals("up"))
					moveUp(units);
				else if (operation.equals("down"))
					moveDown(units);
				// System.err.println(Arrays.toString(boxes));
			}
			Arrays.sort(boxes, (a, b) -> a.row != b.row ? a.row - b.row : a.col - b.col);
			out.printf("Data set %d ends with boxes at locations", t);
			for (int i = 0; i < N; i++)
				out.printf(" (%d,%d)", boxes[i].row, boxes[i].col);
			out.println(".");
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
