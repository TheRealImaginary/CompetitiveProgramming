package SPOJ;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class CEPC08B {

    private static class Building {
        int height, index;

        public Building(int height, int index) {
            this.height = height;
            this.index = index;
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int TC = sc.nextInt();
        while (TC-- > 0) {
            int N = sc.nextInt(), D = sc.nextInt();
            PriorityQueue<Building> pq = new PriorityQueue<>((b1, b2) -> b1.height - b2.height);
            for (int i = 0; i < N; i++)
                pq.add(new Building(sc.nextInt(), i));
            int regions = 1;
            boolean[] underWater = new boolean[N];
            for (int i = 0; i < D; i++) {
                if (i > 0) out.print(" ");
                int level = sc.nextInt();
                while (!pq.isEmpty() && pq.peek().height <= level) {
                    Building b = pq.poll();
                    underWater[b.index] = true;
                    if ((b.index > 0 && !underWater[b.index - 1]) && (b.index + 1 < N && !underWater[b.index + 1]))
                        regions++;
                    else if ((b.index == 0 || underWater[b.index - 1])
                            && (b.index + 1 == N || underWater[b.index + 1]))
                        regions--;
                }
                out.print(regions);
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
