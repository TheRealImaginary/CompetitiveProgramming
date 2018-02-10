package Codeforces;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * For each circle we calculate the range of x values where it would be
 * covered by the large circle with radius R, then we treat each end in
 * these ranges as events and process the events from left to right, we break
 * ties using the type of the event, start events should always come before end ones.
 */
// TODO WA
public class CF101147_I_OnTheWayToThePark {

    private static final double EPS = 1e-15;

    private static class Event implements Comparable<Event> {

        public static final int START = 1;
        public static final int END = 2;

        double x;
        long r;
        int type;

        public Event(double x, long r, int type) {
            this.x = x;
            this.r = r;
            this.type = type;
        }

        @Override
        public int compareTo(Event e) {
            if (Math.abs(x - e.x) > EPS) return x > e.x + EPS ? 1 : -1;
            return type - e.type;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "x=" + x +
                    ", r=" + r +
                    ", type=" + type +
                    '}';
        }
    }

    private static long sq(long x) {
        return x * x;
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
//        MyScanner sc = new MyScanner("walk.in");
        PrintWriter out = new PrintWriter(System.out);
        int TC = sc.nextInt();
        while (TC-- > 0) {
            int N = sc.nextInt();
            long R = sc.nextLong();
            List<Event> events = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                long x = sc.nextLong(), y = sc.nextLong(), r = sc.nextLong();
                if (r > R || sq(y) > (R - r)) continue;
                double range = Math.sqrt(sq(R - r) - sq(y));
                events.add(new Event(x - range, r, Event.START));
                events.add(new Event(x + range, -r, Event.END));
            }
            Collections.sort(events);
            long ans = 0, sum = 0;
            for (Event e : events) {
                sum += e.r;
                ans = Math.max(ans, sum);
            }
            out.println(ans);
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
