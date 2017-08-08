package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*-
 * @author Mazen Magdy
 *				-We find the Maximum Spanning Tree, the answer is the sum of the weights
 *				 of the edges that were not taken by the MST.
 */

// Live-Archive 4110
public class UVA_1234_Racing {

	static class Edge implements Comparable<Edge> {
		int node, cost;

		public Edge(int node, int cost) {
			this.node = node;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge e) {
			return cost - e.cost;
		}
	}

	public static long Prim(int u) {

		long mst = 0;

		PriorityQueue<Edge> pq = new PriorityQueue<>(); // Descending Order

		pq.add(new Edge(u, 0));

		while (!pq.isEmpty()) {
			Edge temp = pq.poll();

			if (visited[temp.node])
				continue;
			visited[temp.node] = true;
			mst += Math.abs(temp.cost);

			for (Edge next : graph[temp.node]) {
				pq.add(new Edge(next.node, -next.cost));
			}
		}

		return mst;
	}

	static ArrayList<Edge> graph[];
	static boolean visited[];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("D:\\01-MY
		// Files\\Desktop\\in.txt"));
		PrintWriter pw = new PrintWriter(System.out);
		StringTokenizer st;

		int t = Integer.parseInt(new StringTokenizer(br.readLine()).nextToken());
		while (t-- > 0) {

			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());

			visited = new boolean[n];
			graph = new ArrayList[n];
			for (int i = 0; i < n; i++)
				graph[i] = new ArrayList<>();

			long total = 0;

			while (m-- > 0) {
				st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken()) - 1;
				int v = Integer.parseInt(st.nextToken()) - 1;
				int cost = Integer.parseInt(st.nextToken());

				total += cost;

				graph[u].add(new Edge(v, cost));
				graph[v].add(new Edge(u, cost));
			}

			pw.println(total - Prim(0));

		}

		pw.close();
	}

}
