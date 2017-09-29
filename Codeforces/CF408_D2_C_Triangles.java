package Codeforces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class CF408_D2_C_Triangles {

	static final int MAX = 1000001;

	static class Vector {
		int x, y;

		Vector(int a, int b) {
			x = a;
			y = b;
		}

		int dot(Vector v) {
			return (x * v.x) + (y * v.y);
		}

		@Override
		public String toString() {
			return x + " " + y;
		}
	}

	public static void main(String[] args) throws IOException {
		MyScanner sc = new MyScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int a = sc.nextInt(), b = sc.nextInt();
		ArrayList<Vector> vectors = new ArrayList<>();
		for (int x = -a; x < a; x++)
			for (int y = -a; y < a; y++)
				if (x * x + y * y == a * a)
					vectors.add(new Vector(x, y));
		int[] sqrt = new int[MAX];
		Arrays.fill(sqrt, -1);
		for (int i = 1; i < b; i++)
			sqrt[i * i] = i;
		for (int x = -b; x < b; x++) {
			if (sqrt[b * b - x * x] == -1)
				continue;
			int y = sqrt[b * b - x * x];
			Vector now = new Vector(x, y);
			for (Vector v : vectors)
				if (v.dot(now) == 0 && now.y != v.y && now.x != v.x) {
					out.println("YES");
					out.println("0 0");
					out.printf("%d %d\n", v.x, v.y);
					out.printf("%d %d\n", now.x, now.y);
					out.flush();
					out.close();
					return;
				}
		}
		out.println("NO");
		out.flush();
		out.close();
	}

	static class MyScanner {
		BufferedReader br;
		StringTokenizer st;

		MyScanner(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}
}
