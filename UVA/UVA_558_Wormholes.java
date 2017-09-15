package UVA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class UVA_558_Wormholes {

	static int distance[];
	static ArrayList<Edge> graph[];
	static int V;
	static final int oo = (int) 1e9;

	static class Edge {
		int cost, node;

		public Edge(int node, int cost) {
			this.node = node;
			this.cost = cost;
		}
	}

	public static boolean BellmanFord(int s) {
		distance[s] = 0;
		boolean modified = true;
		Arrays.fill(distance, oo);
		for (int k = 0; modified && k < V - 1; k++) {
			modified = false;
			for (int u = 0; u < V; u++) {
				for (Edge next : graph[u]) {
					if (distance[u] + next.cost < distance[next.node]) {
						distance[next.node] = distance[u] + next.cost;
						modified = true;
					}
				}
			}
		}
		boolean has_negative_cycles = false;
		for (int u = 0; u < V; u++) {
			for (Edge next : graph[u]) {
				if (distance[u] + next.cost < distance[next.node])
					has_negative_cycles = true;
			}
		}
		return has_negative_cycles;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(System.out);
		StringTokenizer st;

		int t = Integer.parseInt(br.readLine());

		while (t-- > 0) {
			st = new StringTokenizer(br.readLine());
			V = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());

			graph = new ArrayList[V];
			distance = new int[V];
			for (int i = 0; i < graph.length; i++)
				graph[i] = new ArrayList<>();
			// Arrays.fill(distance, oo);
			while (m-- > 0) {
				st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				int cost = Integer.parseInt(st.nextToken());
				graph[u].add(new Edge(v, cost));
			}
			if (BellmanFord(0))
				pw.println("possible");
			else
				pw.println("not possible");
		}

		pw.flush();
		pw.close();
	}

}
