package PKU;

import java.io.*;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

// TODO WA
public class PKU_1823_Hotel {

    private static class Range implements Comparable<Range> {
        int left, right, amount;

        public Range(int left, int right) {
            this.left = left;
            this.right = right;
            amount = right - left + 1;
        }

        @Override
        public String toString() {
            return left + " " + right + " " + amount;
        }

        @Override
        public int compareTo(Range r) {
            if (amount != r.amount)
                return amount - r.amount;
            if (left != r.left)
                return left - r.left;
            return right - r.right;
        }
    }

    private static class LeftSorter implements Comparator<Range> {
        @Override
        public int compare(Range r1, Range r2) {
            return r1.left - r2.left;
        }
    }

    private static class RightSorter implements Comparator<Range> {
        @Override
        public int compare(Range r1, Range r2) {
            return r1.right - r2.right;
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int N = sc.nextInt(), P = sc.nextInt();
        TreeSet<Range> availableRooms = new TreeSet<Range>(), left = new TreeSet<Range>(new LeftSorter()),
                right = new TreeSet<Range>(new RightSorter());
        Range range = new Range(1, N);
        availableRooms.add(range);
        left.add(range);
        right.add(range);
        while (P-- > 0) {
            int type = sc.nextInt();
            if (type == 1 || type == 2) {
                int L = sc.nextInt(), R = L + sc.nextInt() - 1;
                Range rooms = new Range(L, R);
                if (type == 1) {
                    Range inRange = left.floor(rooms);

                    availableRooms.remove(inRange);
                    left.remove(inRange);
                    right.remove(inRange);

                    Range r1 = new Range(inRange.left, L - 1), r2 = new Range(R + 1, inRange.right);
                    if (r1.left <= r1.right) {
                        availableRooms.add(r1);
                        left.add(r1);
                        right.add(r1);
                    }
                    if (r2.left <= r2.right) {
                        availableRooms.add(r2);
                        left.add(r2);
                        right.add(r2);
                    }
                } else {
                    Range reversed = new Range(R, L);
                    Range leastLeft = left.higher(reversed), maxRight = right.lower(reversed);
                    if (leastLeft == null && maxRight == null) {
                        availableRooms.add(range);
                        left.add(range);
                        right.add(range);
                    } else if (leastLeft == null) {
                        if (maxRight.right + 1 == L) {
                            availableRooms.remove(maxRight);
                            left.remove(maxRight);
                            right.remove(maxRight);

                            Range put = new Range(maxRight.left, R);
                            availableRooms.add(put);
                            left.add(put);
                            right.add(put);
                        } else {
                            availableRooms.add(range);
                            left.add(range);
                            right.add(range);
                        }
                    } else if (maxRight == null) {
                        if (leastLeft.left - 1 == R) {
                            availableRooms.remove(leastLeft);
                            left.remove(leastLeft);
                            right.remove(leastLeft);

                            Range put = new Range(L, leastLeft.right);
                            availableRooms.add(put);
                            left.add(put);
                            right.add(put);
                        } else {
                            availableRooms.add(range);
                            left.add(range);
                            right.add(range);
                        }
                    } else {
                        if (maxRight.right + 1 == L && leastLeft.left - 1 == R) {
                            availableRooms.remove(leastLeft);
                            left.remove(leastLeft);
                            right.remove(leastLeft);

                            availableRooms.remove(maxRight);
                            left.remove(maxRight);
                            right.remove(maxRight);

                            Range put = new Range(maxRight.left, leastLeft.right);
                            availableRooms.add(put);
                            left.add(put);
                            right.add(put);
                        } else if (maxRight.right + 1 == L) {
                            availableRooms.remove(maxRight);
                            left.remove(maxRight);
                            right.remove(maxRight);

                            Range put = new Range(maxRight.left, R);
                            availableRooms.add(put);
                            left.add(put);
                            right.add(put);
                        } else if (leastLeft.left - 1 == R) {
                            availableRooms.remove(leastLeft);
                            left.remove(leastLeft);
                            right.remove(leastLeft);

                            Range put = new Range(L, leastLeft.right);
                            availableRooms.add(put);
                            left.add(put);
                            right.add(put);
                        }
                    }
                }
            } else if (type == 3) {
                out.println(availableRooms.last().amount);
            }
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
