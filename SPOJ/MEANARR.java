package SPOJ;

import java.io.*;
import java.util.*;

/**
 * @author Mazen Magdy
 *              -For a subarray to have mean >= `k` the subarray should
 *               satisfy `sum - nk >= 0` where `sum` is the sum of of all
 *               elements in the subarray and `n` is the number of elements
 *               in the subarray and `k` given in the input. This is concluded
 *               from ((Sum(a) from i to j) / n) >= k. Hence the problem now
 *               is to count the number of subarrays with their sum >= 0 when
 *               we subtract k from each element.
 */
public class MEANARR {

    static class SegmentTree {
        int N;
        int[] tree;

        int left(int p) {
            return p << 1;
        }

        int mid(int L, int R) {
            return (L + R) >> 1;
        }

        public SegmentTree(int n) {
            N = n;
            tree = new int[N << 2];
        }

        void set(int idx) {
            set(1, 0, N - 1, idx);
        }

        void set(int p, int L, int R, int idx) {
            if (L > idx || R < idx)
                return;
            if (L == R && L == idx)
                tree[p]++;
            else {
                int mid = mid(L, R), left = left(p), right = left | 1;
                if (idx <= mid)
                    set(left, L, mid, idx);
                else
                    set(right, mid + 1, R, idx);
                tree[p] = tree[left] + tree[right];
            }
        }

        int query(int i, int j) {
            return query(1, 0, N - 1, i, j);
        }

        int query(int p, int L, int R, int i, int j) {
            if (L > j || R < i)
                return 0;
            if (L >= i && R <= j)
                return tree[p];
            int mid = mid(L, R), left = left(p), right = left | 1;
            return query(left, L, mid, i, j) + query(right, mid + 1, R, i, j);
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int n = sc.nextInt();
        long k = sc.nextLong();
        long[] a = new long[n];
        for (int i = 0; i < n; i++)
            a[i] = sc.nextLong() - k;
        List<Long> b = new ArrayList<>();
        if (n != 0)
            b.add(a[0]);
        for (int i = 1; i < n; i++) {
            a[i] += a[i - 1];
            b.add(a[i]);
        }
        Map<Long, Integer> map = new HashMap<>();
        Collections.sort(b);
        int id = 0;
        for (int i = 0; i < b.size(); i++) {
            long x = b.get(i);
            if (!map.containsKey(x))
                map.put(x, id++);
        }
        long ans = 0;
        SegmentTree st = new SegmentTree(id);
        for (int i = n - 1; i >= 0; i--) {
            ans += st.query(map.get(a[i]), id);
            st.set(map.get(a[i]));
            if (a[i] >= 0)
                ans++;
        }
        out.println(ans);
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
