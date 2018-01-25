package SPOJ;

import java.io.*;
import java.util.*;

/**
 * Left and Right sides are events, We use Line Sweep and insert when we encounter a left event
 * and remove when we encounter a right event, we use a Segment Tree to get the length of the Line
 * Sweep cut by the rectangles.
 */
public class NKMARS {

    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int MAX = 30001;

    private static class Event {
        int a;
        int type;
        int idx;

        public Event(int a, int type, int idx) {
            this.a = a;
            this.type = type;
            this.idx = idx;
        }

        public int getA() {
            return a;
        }

        @Override
        public String toString() {
            return a + " " + (type == LEFT ? "Left" : "Right") + " " + idx;
        }
    }

    private static class Rectangle {
        int y1;
        int y2;

        public Rectangle(int y1, int y2) {
            this.y1 = y1;
            this.y2 = y2;
        }

        @Override
        public String toString() {
            return y1 + " " + y2;
        }
    }

    private static class SegmentTree {
        int[] tree;
        boolean[] lazy;
        int N;

        public SegmentTree(int n) {
            N = n;
            tree = new int[N << 2];
            lazy = new boolean[N << 2];
        }

        int left(int p) {
            return p << 1;
        }

        int mid(int L, int R) {
            return (L + R) >> 1;
        }

        void propagate(int p, int L, int R) {
            if (lazy[p]) {
                int mid = mid(L, R), left = left(p), right = left | 1;
                tree[left] = mid - L + 1;
                tree[right] = R - mid;
                lazy[left] = lazy[right] = true;
                lazy[p] = false;
            }
        }

        void set(int i, int j) {
            set(1, 0, N - 1, i, j);
        }

        void set(int p, int L, int R, int i, int j) {
            if (L > j || R < i)
                return;
            if (L >= i && R <= j) {
                tree[p] = R - L + 1;
                lazy[p] = true;
            } else {
                int mid = mid(L, R), left = left(p), right = left | 1;
                propagate(p, L, R);
                set(left, L, mid, i, j);
                set(right, mid + 1, R, i, j);
                tree[p] = tree[left] + tree[right];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt();
        List<Event> horizontal = new ArrayList<>();
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x1 = sc.nextInt(), y1 = sc.nextInt(), x2 = sc.nextInt(), y2 = sc.nextInt();
            horizontal.add(new Event(x1, LEFT, i));
            horizontal.add(new Event(x2, RIGHT, i));
            rectangles.add(new Rectangle(y1, y2));
        }
        Collections.sort(horizontal, Comparator.comparingInt(Event::getA));
//        System.err.println(horizontal);
        long area = 0;
        Set<Integer> active = new TreeSet<>();
        SegmentTree st;
        active.add(horizontal.get(0).idx);
        for (int i = 1; i < horizontal.size(); i++) {
            st = new SegmentTree(MAX);
            Event horizontalEvent = horizontal.get(i);
            int deltaX = horizontalEvent.a - horizontal.get(i - 1).a;
            if (deltaX == 0) {
                if (horizontalEvent.type == RIGHT)
                    active.remove(horizontalEvent.idx);
                else
                    active.add(horizontalEvent.idx);
                continue;
            }
            for (int idx : active)
                st.set(rectangles.get(idx).y1, rectangles.get(idx).y2 - 1);
            area += deltaX * st.tree[1];
            if (horizontalEvent.type == LEFT)
                active.add(horizontalEvent.idx);
            else
                active.remove(horizontalEvent.idx);
        }
        out.println(area);
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
