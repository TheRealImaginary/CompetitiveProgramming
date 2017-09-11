package SPOJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We build a Graph where we nodes are the state of the lock, an edge
 *				 connects nodes `u`, `v` when we can type number at position v[length - 1] to
 *				 state `u` and get v.
 */
public class CODE {

	static class Edge {
		int node;
		boolean used;

		Edge(int a) {
			node = a;
			used = false;
		}

		@Override
		public String toString() {
			return node + " " + used;
		}
	}

	static ArrayList<Edge>[] g;
	static LinkedList<Integer> tour;

	static void findTour(int u) {
		for (Edge v : g[u]) {
			if (!v.used) {
				v.used = true;
				findTour(v.node);
			}
		}
		tour.add(u % 10);
	}

	static void sol(MyScanner sc, PrintWriter out) throws IOException {
		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			if (n == 1) {
				out.println("0123456789");
			} else {
				int x = (int) Math.pow(10, n - 1);
				g = new ArrayList[x];
				for (int i = 0; i < x; i++) {
					g[i] = new ArrayList<>();
					for (int j = 0; j <= 9; j++)
						g[i].add(new Edge((10 * (i % (x / 10))) + j));
				}
				tour = new LinkedList<>();
				findTour(0);
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < n - 2; i++)
					out.print(0);
				while (!tour.isEmpty())
					sb.append(tour.removeLast());
				out.println(sb);
			}
		}
		out.flush();
		out.close();
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		new Thread(null, new Runnable() {
			@Override
			public void run() {
				try {
					sol(sc, out);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "1", 1 << 27).start();
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
