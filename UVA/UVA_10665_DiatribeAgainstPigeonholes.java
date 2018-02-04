package UVA;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * Sort the Pigeonholes decreasingly either way, break ties Lexicographically smaller
 * from the left and Lexicographically larger from the right. Put Lexicographically
 * smaller items first amongst heavy ones.
 */
public class UVA_10665_DiatribeAgainstPigeonholes {

    private static final int ALPHA = 26;

    private static class Pigeonhole {
        int alpha, weight;

        public Pigeonhole(int alpha) {
            this.alpha = alpha;
            this.weight = 0;
        }

        @Override
        public String toString() {
            return (char) (alpha + 'A') + " " + weight;
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int TC = sc.nextInt();
        while (TC-- > 0) {
            int N = sc.nextInt();
            char[] c = sc.next().toCharArray();
            Pigeonhole[] pigeonholes = new Pigeonhole[ALPHA];
            for (int i = 0; i < ALPHA; i++)
                pigeonholes[i] = new Pigeonhole(i);
            for (int i = 0; i < c.length - 1; i++)
                pigeonholes[c[i] - 'A'].weight++;
            int L = 0, R = N - 1;
            PriorityQueue<Pigeonhole> left = new PriorityQueue<>((a, b) -> a.weight != b.weight ?
                    b.weight - a.weight : a.alpha - b.alpha);
            PriorityQueue<Pigeonhole> right = new PriorityQueue<>((a, b) -> a.weight != b.weight ?
                    b.weight - a.weight : b.alpha - a.alpha);
            for (int i = 0; i < N; i++)
                left.add(pigeonholes[i]);
            Pigeonhole[] ans = new Pigeonhole[ALPHA];
            while (left.size() > 1) {
                Pigeonhole a = left.poll(), b = left.peek();
                if (a.weight == b.weight) {
                    ans[L++] = a;
                    if (L > R) break;
                    right.clear();
                    right.addAll(left);
                    ans[R--] = right.poll();
                    left.clear();
                    left.addAll(right);
                } else {
                    // Same Distance Make It Lexicographically Smaller
                    if (a.alpha > b.alpha) {
                        ans[L++] = left.poll();
                        if (L > R) break;
                        left.add(a);
                        right.clear();
                        right.addAll(left);
                        ans[R--] = right.poll();
                        left.clear();
                        left.addAll(right);
                    } else {
                        ans[L++] = a;
                        if (L > R) break;
                        right.clear();
                        right.addAll(left);
                        ans[R--] = right.poll();
                        left.clear();
                        left.addAll(right);
                    }
                }
            }
            if (left.size() == 1)
                ans[L] = left.poll();
            for (int i = 0; i < N; i++) {
                if (i > 0) out.print(" ");
                out.print((char) (ans[i].alpha + 'A'));
            }
            out.println();
            for (int i = 0; i < N; i++) {
                if (i > 0) out.print(" ");
                out.print(ans[i].weight);
            }
            out.println();
        }
        out.flush();
        out.close();
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
