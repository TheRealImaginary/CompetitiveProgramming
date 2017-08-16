package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We use a Regular Expression to check whether the CFG can generate the sentence or no.
 */
public class UVA_10058_JimmisRiddles {

	static final String VERB = "(hate|love|know|like)s*";
	static final String NOUN = "(tom|jerry|goofy|mickey|jimmy|dog|cat|mouse)";
	static final String ARTICLE = "(a|the)";
	static final String ACTOR = String.format("(%s|%s %s)", NOUN, ARTICLE, NOUN);
	static final String ACTIVE_LIST = String.format("(%s and )*%s", ACTOR, ACTOR);
	static final String ACTION = String.format("%s %s %s", ACTIVE_LIST, VERB, ACTIVE_LIST);
	static final String STATEMENT = String.format("%s( , %s)*", ACTION, ACTION);

	static final String YES = "YES I WILL";
	static final String NO = "NO I WON'T";

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		// MyScanner sc = new MyScanner("in.txt");
		PrintWriter out = new PrintWriter(System.out);
		// System.err.println(STATEMENT);
		// System.err.println(ACTION);
		// System.err.println(ACTIVE_LIST);
		// System.err.println(ACTOR);
		// System.err.println(ARTICLE);
		// System.err.println(NOUN);
		// System.err.println(VERB);
		while (sc.ready()) {
			String s = sc.nextLine();
			out.println(s.matches(STATEMENT) ? YES : NO);
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
