import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeMap;

// TODO MLE
public class CF100531_K_KebabHouse {

    private static final int MOD = (int) 1e9 + 7;
    private static final int MAXN = 1000;
    private static final int MAXQ = 250;
    private static final int OFFSET = 100;

    private static class Triple implements Comparable<Triple> {
        int a, b, c;

        public Triple(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public int compareTo(Triple triple) {
            if (a != triple.a) return a - triple.a;
            if (b != triple.b) return b - triple.b;
            return c - triple.c;
        }
    }

    private static int N, T;
    private static int[] q, x;
    private static long[][][] dp;
    private static TreeMap<Triple, Integer> map;

    private static int add(int a, int b) {
        return (a + b) % MOD;
    }

    private static int solve(int idx, int need, int put) {
        Triple state = new Triple(put, need, idx);
        int res = map.getOrDefault(state, -1);
        if (res != -1) return res;
        if (need < 0) {
            if (put < x[idx]) return 0;
            if (idx == N - 1) return 1;
            res = solve(idx + 1, need + q[idx + 1], q[idx + 1] - (need + q[idx + 1]));
            map.put(state, res);
            return res;
        }
        if (need == 0) {
            if (put < x[idx]) return 0;
            if (idx == N - 1) return 1;
            res = solve(idx + 1, q[idx + 1], 0);
            map.put(state, res);
            return res;
        } else res = add(solve(idx, need - 1, put + 1), solve(idx, need - T - 1, put
                + (need - T - 1 >= 0 ? T : need - 1)));
        map.put(state, res);
        return res;
    }

//    private static long solve(int idx, int need, int put) {
//        if (dp[put][need + OFFSET][idx] != -1) return dp[put][need + OFFSET][idx];
//        if (need < 0)
//            return put >= x[idx] ? idx == N - 1 ? 1 :
//                    (dp[put][need + OFFSET][idx] =
//                            solve(idx + 1, need + q[idx + 1], q[idx + 1] - (need + q[idx + 1]))) : 0;
//        if (need == 0) return put >= x[idx] ? idx == N - 1 ? 1 :
//                (dp[put][need + OFFSET][idx] = solve(idx + 1, q[idx + 1], 0)) : 0;
//        return dp[put][need + OFFSET][idx] = add(solve(idx, need - 1, put + 1), solve(idx, need - T - 1, put
//                + (need - T - 1 >= 0 ? T : need - 1)));
//    }

//    private static long solve(int idx, int time, int sleep, int put) {
//        int need = q[idx] - sleep - put;
//        if (idx == N - 1 && need == 0) return put >= x[idx] ? 1 : 0;
//        if (need == 0) return put >= x[idx] ? solve(idx + 1, time, 0, 0) : 0;
//        if (time == 0)
//            return add(solve(idx, time, sleep, put + 1), solve(idx, -T, sleep + 1, put));
//        return solve(idx, time + 1, sleep, put + 1);
//    }

//    private static long solve(int idx, int time, int need, int put) {
//        if (idx == N - 1 && need == 0) return put >= x[idx] ? 1 : 0;
//        if (need == 0) return put >= x[idx] ? solve(idx + 1, time, q[idx + 1], 0) : 0;
//        if (time == 0)
//            return add(solve(idx, time, need - 1, put + 1), solve(idx, -T, need - 1, put));
//        return solve(idx, time + 1, need - 1, put + 1);
//    }

    public static void main(String[] args) throws IOException {
        new Thread(null, new Runnable() {
            @Override
            public void run() {
                try {
                    MyScanner sc = new MyScanner(System.in);
                    PrintWriter out = new PrintWriter(System.out);
//                    MyScanner sc = new MyScanner("kebab.in");
//                    PrintWriter out = new PrintWriter("kebab.out");
                    N = sc.nextInt();
                    T = sc.nextInt();
                    q = new int[N];
                    x = new int[N];
                    for (int i = 0; i < N; i++) {
                        q[i] = sc.nextInt();
                        x[i] = sc.nextInt();
                    }
//                    dp = new long[MAXQ + 1][MAXQ + OFFSET + 1][MAXN + 1];
//                    for (int i = 0; i <= MAXQ; i++)
//                        for (int j = 0; j <= MAXQ + OFFSET; j++)
//                            Arrays.fill(dp[i][j], -1);
                    // out.println(solve(0, 0, 0, 0));
                    map = new TreeMap<>();
                    out.println(solve(0, q[0], 0));
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "", 1 << 27).start();
    }

    private static class MyScanner {
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
