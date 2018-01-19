package UVA;

import java.io.*;
import java.util.*;

public class UVA_105_TheSkylineProblem {

    private static final int START = 1;
    private static final int END = 2;
    private static final int MAX = 10001;

    private static class Event {
        int h, type;

        public Event(int h, int type) {
            this.h = h;
            this.type = type;
        }

        @Override
        public String toString() {
            return h + " " + (type == START ? "START" : "END");
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
//        MyScanner sc = new MyScanner("in.txt");
        PrintWriter out = new PrintWriter(System.out);
        List<Event>[] events = new List[MAX];
        for (int i = 0; i < MAX; i++)
            events[i] = new ArrayList<>();
        while (sc.ready()) {
            int l = sc.nextInt(), h = sc.nextInt(), r = sc.nextInt();
            events[l].add(new Event(h, START));
            events[r].add(new Event(h, END));
        }
        TreeMap<Integer, Integer> map = new TreeMap<>();
        TreeSet<Integer> set = new TreeSet<>();
        List<Integer> sol = new ArrayList<>();
        int currentHeight = 0;
        for (int i = 0; i < MAX; i++) {
            int maxHeight = currentHeight;
            for (Event e : events[i]) {
                if (e.type == START) {
                    maxHeight = Math.max(maxHeight, e.h);
                    set.add(e.h);
                    map.put(e.h, map.getOrDefault(e.h, 0) + 1);
                } else {
                    map.put(e.h, map.get(e.h) - 1);
                    if (map.get(e.h) == 0)
                        set.remove(e.h);
                    maxHeight = set.isEmpty() ? 0 : set.last();
                }
            }
            if (maxHeight != currentHeight) {
                sol.add(i);
                sol.add(maxHeight);
            }
            currentHeight = maxHeight;
        }
        for (int i = 0; i < sol.size(); i++) {
            if (i > 0)
                out.print(" ");
            out.print(sol.get(i));
        }
        out.println();
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
