package UVA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class UVA_10645_Menu {

	static final double oo = 1 << 28;

	static class Item {
		int cost, value, index;

		Item(int c, int v, int i) {
			cost = c;
			value = v;
			index = i + 1;
		}

		@Override
		public String toString() {
			return cost + " " + value;
		}
	}

	static PrintWriter out;

	static int K, N, M;
	static double[][][][] dp;
	static Item[] items;

	static double solve(int use, int day, int last, int rem) {
		if (day == K)
			return 0;
		if (rem == 0)
			return -oo;
		if (dp[use][day][last][rem] != -1)
			return dp[use][day][last][rem];
		double res = -oo;
		for (int i = 0; i < N; i++) {
			if (rem < items[i].cost)
				continue;
			if (last == i && use == 1)
				res = Math.max(res, solve(use + 1, day + 1, i, rem - items[i].cost) + (items[i].value * 0.5));
			if (last == i && use == 2)
				res = Math.max(res, solve(use, day + 1, i, rem - items[i].cost));
			if (last != i)
				res = Math.max(res, solve(1, day + 1, i, rem - items[i].cost) + items[i].value);
		}
		return dp[use][day][last][rem] = res;
	}

	static void print(int use, int day, int last, int rem) {
		if (day == K)
			return;
		for (int i = 0; i < N; i++) {
			if (rem < items[i].cost)
				continue;
			if (last == i && use == 1)
				if (solve(use + 1, day + 1, i, rem - items[i].cost) + (items[i].value * 0.5) == solve(use, day, last,
						rem)) {
					if (day > 0)
						out.print(" ");
					out.print(items[i].index);
					print(use + 1, day + 1, i, rem - items[i].cost);
					return;
				}
			if (last == i && use == 2)
				if (solve(use, day + 1, i, rem - items[i].cost) == solve(use, day, last, rem)) {
					if (day > 0)
						out.print(" ");
					out.print(items[i].index);
					print(use, day + 1, i, rem - items[i].cost);
					return;
				}
			if (last != i)
				if (solve(1, day + 1, i, rem - items[i].cost) + items[i].value == solve(use, day, last, rem)) {
					if (day > 0)
						out.print(" ");
					out.print(items[i].index);
					print(1, day + 1, i, rem - items[i].cost);
					return;
				}
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		out = new PrintWriter(System.out);
		while (true) {
			K = sc.nextInt();
			N = sc.nextInt();
			M = sc.nextInt();
			if (K + N + M == 0)
				break;
			items = new Item[N];
			for (int i = 0; i < N; i++)
				items[i] = new Item(sc.nextInt(), sc.nextInt(), i);
			 Arrays.sort(items, (a, b) -> a.cost - b.cost);
			dp = new double[3][K][N + 1][M + 1];
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < K; j++)
					for (int k = 0; k <= N; k++)
						Arrays.fill(dp[i][j][k], -1);
			double ans = solve(0, 0, N, M);
			out.printf("%.1f\n", ans <= 0 ? 0 : ans);
			if (ans > 0) 
				print(0, 0, N, M);
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
