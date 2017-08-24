package SPOJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We BFS from both ends looking for a common node, Unless they already can reach each
 *				 other in less than half the limit.
 */
public class ANARC08A {

	static final int oo = 1 << 28;
	static final int[][] moves = { { 0, 3, 4, 1 }, { 1, 4, 5, 2 }, { 3, 6, 7, 4 }, { 4, 7, 8, 5 }, { 0, 1, 4, 3 },
			{ 1, 2, 5, 4 }, { 3, 4, 7, 6 }, { 4, 5, 8, 7 } };
	static final String START = "123456789";
	static final String END = "0000000000";

	static int bfs(int limit, String s, String t, Map<String, Integer> map) {
		Queue<String> q = new LinkedList<>();
		q.add(s);
		map.put(s, 0);
		while (!q.isEmpty()) {
			String u = q.poll();
			Integer x = map.get(u);
			if (u.equals(t))
				return x <= limit ? x : -1;
			if (x + 1 > limit)
				continue;
			for (int i = 0; i < 8; i++) {
				char[] next = u.toCharArray();
				int j;
				char y;
				for (j = 1, y = next[moves[i][0]]; j < 4; j++)
					next[moves[i][j - 1]] = next[moves[i][j]];
				next[moves[i][3]] = y;
				String nextState = new String(next);
				Integer z = map.get(nextState);
				if (z == null) {
					map.put(nextState, x + 1);
					q.add(nextState);
				}
			}
		}
		return -1;
	}

	static int go(String s, int limit) {
		// System.err.println(limit);
		Map<String, Integer> sourceMap = new HashMap<>();
		int sourceLimit = limit >> 1;
		int sourceResult = bfs(sourceLimit, s, START, sourceMap);
		if (sourceResult >= 0)
			return sourceResult;

		int sinkLimit = limit - sourceLimit;
		Map<String, Integer> sinkMap = new HashMap<>();
		int sinkResult = bfs(sinkLimit, START, s, sinkMap);
		if (sinkResult >= 0)
			return sinkResult;

		int res = oo;
		for (Entry<String, Integer> e : sourceMap.entrySet()) {
			Integer x = sinkMap.get(e.getKey());
			if (x != null)
				res = Math.min(res, e.getValue() + x);
		}
		return res == oo || res > limit ? -1 : res;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		// bfs(); BFS from 123456789 to all nodes (TLE)
		for (int t = 1; true; t++) {
			String s = sc.nextLine();
			if (s.equals(END))
				break;
			out.printf("%d. %d\n", t, go(s.substring(1), s.charAt(0) - '0'));
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
