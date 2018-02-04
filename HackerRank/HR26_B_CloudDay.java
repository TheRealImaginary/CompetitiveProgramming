package HackerRank;

import java.io.*;
import java.util.*;

/**
 * Since if a city is covered by more than 1 cloud it cannot see the sun, So We
 * process each event from left to right (careful when sorting) and check how many
 * clouds are covering a city.
 */
public class HR26_B_CloudDay {

    private static class Event {

        public static final int START = 1;
        public static final int END = 2;
        public static final int POINT = 3;

        long x;
        int type, idx;

        public Event(long x, int type, int idx) {
            this.x = x;
            this.type = type;
            this.idx = idx;
        }

        public long getX() {
            return x;
        }

        public int getIdx() {
            return idx;
        }
    }

    private static long maximumPeople(long[] p, long[] x, long[] y, long[] r) {
        int numberOfCities = p.length, numberOfClouds = y.length;
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < numberOfClouds; i++) {
            events.add(new Event(y[i] - r[i], Event.START, i));
            events.add(new Event(y[i] + r[i], Event.END, i));
        }
        for (int i = 0; i < numberOfCities; i++)
            events.add(new Event(x[i], Event.POINT, i));
        Collections.sort(events, (e1, e2) -> {
            if (e1.x != e2.x) return Long.compare(e1.x, e2.x);
            if (e1.type == Event.START) return e2.type == Event.START ? 0 : -1;
            if (e2.type == Event.START) return 1;
            if (e1.type == Event.END) return e2.type == Event.END ? 0 : 1;
            if (e2.type == Event.END) return -1;
            return Integer.compare(e1.type, e2.type);
        });
        long ans = 0;
        long[] ifRemove = new long[numberOfClouds];
        TreeSet<Event> set = new TreeSet<>(Comparator.comparingInt(Event::getIdx));
        for (Event e : events) {
            if (e.type == Event.START) {
                set.add(e);
            } else if (e.type == Event.END) {
                set.remove(e);
            } else if (e.type == Event.POINT) {
                if (set.size() == 0)
                    ans += p[e.idx];
                else if (set.size() == 1)
                    ifRemove[set.first().idx] += p[e.idx];
            }
        }
        long max = 0;
        for (int i = 0; i < numberOfClouds; i++)
            max = Math.max(max, ifRemove[i]);
        return ans + max;
    }

    public static void main(String[] args) throws IOException {
        MyScanner in = new MyScanner(System.in);
        int n = in.nextInt();
        long[] p = new long[n];
        for (int p_i = 0; p_i < n; p_i++) {
            p[p_i] = in.nextLong();
        }
        long[] x = new long[n];
        for (int x_i = 0; x_i < n; x_i++) {
            x[x_i] = in.nextLong();
        }
        int m = in.nextInt();
        long[] y = new long[m];
        for (int y_i = 0; y_i < m; y_i++) {
            y[y_i] = in.nextLong();
        }
        long[] r = new long[m];
        for (int r_i = 0; r_i < m; r_i++) {
            r[r_i] = in.nextLong();
        }
        long result = maximumPeople(p, x, y, r);
        System.out.println(result);
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
