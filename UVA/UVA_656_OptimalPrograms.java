package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We try possible valid operations for 1 input and for each one
 *				 we check if it works for the rest.
 */
public class UVA_656_OptimalPrograms {

	static final String EMPTY = "Empty sequence";
	static final String IMPOSSIBLE = "Impossible";
	static final String[] OPERATIONS = { "ADD", "DIV", "DUP", "MUL", "SUB" };
	static final int MAX = 30000;

	static class State {
		Stack<Integer> stack;
		int steps;
		ArrayList<Integer> moves;

		State() {
			stack = new Stack<>();
			moves = new ArrayList<>();
			steps = 0;
		}

		State(int x) {
			this();
			stack.push(x);
		}

		State(State s) {
			this();
			Stack<Integer> temp = new Stack<>();
			while (!s.isEmpty())
				temp.push(s.pop());
			while (!temp.isEmpty()) {
				this.push(temp.peek());
				s.push(temp.pop());
			}
			steps = s.steps;
			for (int move : s.moves)
				moves.add(move);
		}

		int peek() {
			return stack.peek();
		}

		int pop() {
			return stack.pop();
		}

		int size() {
			return stack.size();
		}

		boolean isEmpty() {
			return stack.isEmpty();
		}

		void push(int x) {
			stack.push(x);
		}

		State execute(int operation) {
			State state = new State(this);
			if (operation == 2) {
				state.push(state.peek());
			} else if (operation == 1) {
				int a = state.pop(), b = state.pop();
				state.push(b / a);
			} else if (operation == 3) {
				state.push(state.pop() * state.pop());
			} else if (operation == 4) {
				int a = state.pop(), b = state.pop();
				state.push(b - a);
			} else if (operation == 0) {
				state.push(state.pop() + state.pop());
			}
			state.steps++;
			state.moves.add(operation);
			return state;
		}

		@Override
		public String toString() {
			return stack + " " + steps;
		}
	}

	static boolean worksForRest(State state, int[] x, int[] y) {
		int n = x.length;
		for (int i = 1; i < n; i++) {
			State current = new State(x[i]);
			for (int j = 0; j < state.moves.size(); j++) {
				int operation = state.moves.get(j);
				if ((operation == 1 && current.peek() == 0) || Math.abs(current.peek()) > MAX)
					return false;
				current = current.execute(operation);
			}
			if (current.size() != 1 || current.peek() != y[i])
				return false;
		}
		return true;
	}

	static String go(int[] x, int[] y) {
		StringBuilder sb = new StringBuilder();
		Queue<State> q = new LinkedList<>();
		q.add(new State(x[0]));
		while (!q.isEmpty()) {
			State current = q.poll();
			if (current.size() == 1 && current.peek() == y[0] && worksForRest(current, x, y)) {
				for (int i = 0; i < current.moves.size(); i++) {
					if (i > 0)
						sb.append(" ");
					sb.append(OPERATIONS[current.moves.get(i)]);
				}
				return sb.toString();
			}
			if (current.steps == 10)
				continue;
			for (int operation = 0; operation < 5; operation++) {
				if ((current.size() == 1 && operation != 2) || (current.peek() == 0 && operation == 1))
					continue;
				State nextState = current.execute(operation);
				if (Math.abs(nextState.peek()) > MAX)
					continue;
				q.add(nextState);
			}
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		for (int t = 1; true; t++) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			int[] x = new int[n];
			for (int i = 0; i < n; i++)
				x[i] = sc.nextInt();
			int[] y = new int[n];
			for (int i = 0; i < n; i++)
				y[i] = sc.nextInt();
			boolean empty = true;
			for (int i = 0; i < n; i++)
				if (x[i] != y[i]) {
					empty = false;
					break;
				}
			out.printf("Program %d\n", t);
			if (empty)
				out.println(EMPTY);
			else {
				String sol = go(x, y);
				out.println(sol == null ? IMPOSSIBLE : sol);
			}
			out.println();
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

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
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
