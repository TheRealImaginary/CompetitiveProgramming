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
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class UVA_10171_MeetingProfMiguel {

	static class Edge implements Comparable<Edge> {
		int v, cost;
		boolean type;

		public Edge(int node, int energy, boolean young) {
			v = node;
			cost = energy;
			type = young;
		}

		@Override
		public String toString() {
			return v + " " + cost + " " + (type ? 'Y' : 'M');
		}

		@Override
		public int compareTo(Edge o) {
			return cost - o.cost;
		}
	}

	static final int MAX = 26;
	static final int oo = 1 << 27;
	static ArrayList<Edge>[] g;

	static void Dijkstra(int source, int[] dist, boolean type) {
		Arrays.fill(dist, oo);
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		dist[source] = 0;
		pq.add(new Edge(source, 0, type));
		while (!pq.isEmpty()) {
			Edge u = pq.poll();
			if (u.cost > dist[u.v])
				continue;
			for (Edge next : g[u.v]) {
				if (u.cost + next.cost < dist[next.v] && next.type == type)
					pq.add(new Edge(next.v, dist[next.v] = u.cost + next.cost, type));
			}
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		while (true) {
			int n = sc.nextInt();
			if (n == 0)
				break;
			g = new ArrayList[MAX];
			for (int i = 0; i < MAX; i++)
				g[i] = new ArrayList<>();
			while (n-- > 0) {
				boolean young = sc.next().charAt(0) == 'Y';
				boolean uni = sc.next().charAt(0) == 'U';
				int u = sc.next().charAt(0) - 'A', v = sc.next().charAt(0) - 'A';
				int energy = sc.nextInt();
				g[u].add(new Edge(v, energy, young));
				if (!uni)
					g[v].add(new Edge(u, energy, young));
			}
			int s1 = sc.next().charAt(0) - 'A', s2 = sc.next().charAt(0) - 'A';
			int[] a = new int[MAX];
			Dijkstra(s1, a, true);
			int[] b = new int[MAX];
			Dijkstra(s2, b, false);
			Edge ans = new Edge(-1, oo, false);
			ArrayList<Character> dst = new ArrayList<>();
			for (int i = 0; i < MAX; i++)
				if (ans.cost > a[i] + b[i]) {
					ans = new Edge(i, a[i] + b[i], false);
					dst = new ArrayList<>();
					dst.add((char) (i + 'A'));
				} else if (ans.cost < oo && ans.cost == a[i] + b[i]) {
					dst.add((char) (i + 'A'));
				}
			out.print(ans.cost == oo ? "You will never meet." : ans.cost);
			for (char c : dst)
				out.print(" " + c);
			out.println();
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

		public MyScanner(String file) throws IOException {
			br = new BufferedReader(new FileReader(new File(file)));
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
