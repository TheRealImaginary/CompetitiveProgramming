package UVA;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class UVA_11680_Dragster { // WA Not Sure.


    private static class Pair {
        int racer;
        double p;

        public Pair(int racer, double p) {
            this.racer = racer;
            this.p = p;
        }

        @Override
        public String toString() {
            return racer + " " + p;
        }
    }

    private static int N;
    private static double[][] P;
    private static int[] L, R;

    private static List<Pair> solve(int race) {
        if (race <= N) {
            List<Pair> res = new ArrayList<>();
            res.add(new Pair(race, 1));
            return res;
        }
        List<Pair> left = solve(L[race]), right = solve(R[race]), res = new ArrayList<>();
        int n = left.size(), m = right.size();
        for (int i = 0; i < n; i++) {
            Pair now = left.get(i);
            double p = 0;
            for (int j = 0; j < m; j++)
                p += now.p * right.get(j).p * P[now.racer - 1][right.get(j).racer - 1];
            res.add(new Pair(now.racer, p));
        }
        for (int i = 0; i < m; i++) {
            Pair now = right.get(i);
            double p = 0;
            for (int j = 0; j < n; j++)
                p += now.p * left.get(j).p * P[now.racer - 1][left.get(j).racer - 1];
            res.add(new Pair(now.racer, p));
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        while (true) {
            N = sc.nextInt();
            if (N == 0)
                break;
            P = new double[N][N];
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    P[i][j] = sc.nextDouble();
            boolean[] last = new boolean[N << 1];
            Arrays.fill(last, true);
            L = new int[N << 1];
            R = new int[N << 1];
            for (int i = N + 1; i < N << 1; i++) {
                L[i] = sc.nextInt();
                R[i] = sc.nextInt();
                last[L[i]] = last[R[i]] = false;
            }
            List<Pair> res = null;
            for (int start = 1; start < N << 1; start++)
                if (last[start]) {
                    res = solve(start);
                    break;
                }
            if (res != null) {
                for (int i = 0; i < res.size(); i++)
                    if (res.get(i).racer == 1) {
                        out.println(new DecimalFormat("0.000000").format(res.get(i).p));
                        break;
                    }
            } else
                out.println("0.000000");
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

        public double nextDouble() throws IOException {
            String x = next();
            StringBuilder sb = new StringBuilder("0");
            double res = 0, f = 1;
            boolean dec = false, neg = false;
            int start = 0;
            if (x.charAt(0) == '-') {
                neg = true;
                start++;
            }
            for (int i = start; i < x.length(); i++)
                if (x.charAt(i) == '.') {
                    res = Long.parseLong(sb.toString());
                    sb = new StringBuilder("0");
                    dec = true;
                } else {
                    sb.append(x.charAt(i));
                    if (dec)
                        f *= 10;
                }
            res += Long.parseLong(sb.toString()) / f;
            return res * (neg ? -1 : 1);
        }

        public boolean ready() throws IOException {
            return br.ready();
        }
    }
}
