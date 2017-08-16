package SPOJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				 -We use Dijkstra to find out the minimum time needed for an arrow to reach a position.
 */
public class GONDOR {

	static class Pair {
		int i;
		double time;

		Pair(int idx, double t) {
			i = idx;
			time = t;
		}

		@Override
		public String toString() {
			return i + " " + time;
		}
	}

	static final double oo = 1e9;
	static final double EPS = 1e-9;

	static int V;
	static int[] x, y, arrows, d[];

	static int sq(int x) {
		return x * x;
	}

	static double dist(int idx, int x2, int y2) {
		return Math.sqrt(sq(x[idx] - x2) + sq(y[idx] - y2));
	}

	static double[] dijkstra() {
		PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Double.compare(a.time, b.time));
		pq.add(new Pair(0, 0));
		double[] time = new double[V];
		Arrays.fill(time, oo);
		time[0] = 0;
		boolean[] lit = new boolean[V];
		while (!pq.isEmpty()) {
			Pair u = pq.poll();
			if (u.time > time[u.i] + EPS)
				continue;
			lit[u.i] = true;
			int shot = 0;
			for (int i = 0; i < V - 1; i++) {
				if (shot == Math.min(V - 1, arrows[u.i]))
					break;
				if (lit[d[u.i][i]])
					continue;
				double newD = u.time + dist(u.i, x[d[u.i][i]], y[d[u.i][i]]);
				if (newD + EPS < time[d[u.i][i]])
					pq.add(new Pair(d[u.i][i], time[d[u.i][i]] = newD));
				shot++;
			}
		}
		return time;
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		V = sc.nextInt();
		x = new int[V];
		y = new int[V];
		arrows = new int[V];
		d = new int[V][V - 1];
		for (int i = 0; i < V; i++) {
			x[i] = sc.nextInt();
			y[i] = sc.nextInt();
			arrows[i] = sc.nextInt();
			for (int j = 0; j < V - 1; j++)
				d[i][j] = sc.nextInt() - 1;
		}
		// System.err.println(Arrays.toString(x));
		// System.err.println(Arrays.toString(y));
		// System.err.println(Arrays.toString(arrows));
		// for (int i = 0; i < V; i++)
		// System.err.println(Arrays.toString(d[i]));
		// System.err.println(Arrays.toString(dijkstra()));
		double[] time = dijkstra();
		for (int i = 0; i < V; i++)
			out.println(time[i]);
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
