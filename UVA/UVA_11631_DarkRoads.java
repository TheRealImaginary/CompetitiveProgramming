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
 *				-We find the cost of the Minimum Spanning Tree, The answer would be the sum of
 *				 weights of the edges not taken in the MST. 
 */
public class UVA_11631_DarkRoads {

	static ArrayList<Edge> graph[];
	static int V;

	static class Edge implements Comparable<Edge> {

		int node;
		int cost;

		public Edge(int node, int cost) {
			this.node = node;
			this.cost = cost;
		}

		public String toString() {
			return String.format("%d %d", node, cost);
		}

		@Override
		public int compareTo(Edge e) {
			if (cost != e.cost)
				return cost - e.cost;
			return node - e.node;
		}
	}

	public static long Prim(int u) {
		long mst = 0;

		boolean visited[] = new boolean[V];
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		pq.add(new Edge(u, 0));

		while (!pq.isEmpty()) {

			Edge current = pq.poll();

			if (visited[current.node])
				continue;

			visited[current.node] = true;
			mst += current.cost;

			for (Edge next : graph[current.node]) {
				if (!visited[next.node])
					pq.add(next);
			}
		}

		return mst;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("D:\\01-MY
		// Files\\Desktop\\in.txt"));
		PrintWriter pw = new PrintWriter(System.out);
		StringTokenizer st;

		while (true) {

			st = new StringTokenizer(br.readLine());

			V = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());

			if (V == 0 && E == 0)
				break;

			graph = new ArrayList[V];
			for (int i = 0; i < V; i++)
				graph[i] = new ArrayList<>();

			long total = 0;

			while (E-- > 0) {
				st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				int cost = Integer.parseInt(st.nextToken());
				total += cost;

				graph[u].add(new Edge(v, cost));
				graph[v].add(new Edge(u, cost));
			}

			pw.println(total - Prim(0));
		}

		pw.flush();
		pw.close();
	}

}
