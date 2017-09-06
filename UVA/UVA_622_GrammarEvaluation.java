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
 *				-We change the infix expression to post-fix then we evaluate it.
 */
public class UVA_622_GrammarEvaluation {

	static boolean isNumeric(String s) {
		int n = s.length();
		for (int i = 0; i < n; i++) {
			if (!Character.isDigit(s.charAt(i)))
				return false;
		}
		return true;
	}

	static int priority(String s) {
		if (s.equals("*"))
			return 3;
		if (s.equals("+") || s.equals("-"))
			return 2;
		return -1;
	}

	static String toPostFix(String[] infix) {
		Stack<String> operators = new Stack<>();
		int n = infix.length;
		StringBuilder sb = new StringBuilder();
		boolean bad = false;
		for (int i = 0; i < n; i++) {
			if (isNumeric(infix[i])) {
				sb.append(infix[i]).append(" ");
			} else {
				if (!infix[i].equals("(") && !infix[i].equals(")") && !infix[i].equals("*") && !infix[i].equals("+")) {
					bad = true;
					break;
				}
				if (infix[i].equals("(")) {
					operators.push(infix[i]);
				} else if (infix[i].equals(")")) {
					while (!operators.isEmpty() && !operators.peek().equals("("))
						sb.append(operators.pop()).append(" ");
					if (operators.isEmpty()) {
						bad = true;
						break;
					}
					operators.pop();
				} else {
					while (!operators.isEmpty()) {
						if (operators.peek().equals("("))
							break;
						int pTop = priority(operators.peek()), pCurrent = priority(infix[i]);
						if (pTop == -1 || pCurrent == -1) {
							bad = true;
							break;
						}
						if (pTop >= pCurrent)
							sb.append(operators.pop()).append(" ");
						else
							break;
					}
					operators.push(infix[i]);
				}
			}
		}
		if (bad)
			return "ERROR";
		while (!operators.isEmpty())
			sb.append(operators.pop()).append(" ");
		return sb.toString();
	}

	static String solve(String postfix) {
		Stack<Integer> operands = new Stack<>();
		StringTokenizer st = new StringTokenizer(postfix);
		boolean bad = false;
		while (st.hasMoreTokens()) {
			String currentToken = st.nextToken();
			if (isNumeric(currentToken))
				operands.push(Integer.parseInt(currentToken));
			else {
				if (operands.size() < 2) {
					bad = true;
					break;
				}
				if (currentToken.equals("*"))
					operands.push(operands.pop() * operands.pop());
				else if (currentToken.equals("+"))
					operands.push(operands.pop() + operands.pop());
				else {
					// Should not happen
					bad = true;
					break;
				}
			}
		}
		if (bad || operands.size() != 1)
			return "ERROR";
		return Integer.toString(operands.peek());
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t-- > 0) {
			String line = sc.nextLine();
			String postfix = toPostFix(line.split("(?<=[()+*])|(?=[()+*])"));
			// System.err.println(postfix);
			if (postfix.equals("ERROR"))
				out.println("ERROR");
			else
				out.println(solve(postfix));
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
