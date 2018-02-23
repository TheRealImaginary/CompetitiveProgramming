package Codeforces;

import java.io.*;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * For each element we find the element `e` such that it satisfies the inequality.
 * Now the number of pairs is the number of ways we can expand the index `i` of e and
 * `j`. Moreover we need to maintain the last left we included in our answer to avoid over
 * counting.
 */
public class CF100459_C_Intervals {

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int N = sc.nextInt();
        long D = sc.nextLong();
        long[] a = new long[N];
        for (int i = 0; i < N; i++) a[i] = sc.nextInt();
        Map<Long, Integer> map = new TreeMap<>();
        long ans = 0;
        for (int i = 0, left = 0; i < N; i++) {
            int max = Math.max(map.getOrDefault(a[i] - D, -1),
                    map.getOrDefault(a[i] + D, -1));
            if (max != -1 && max >= left) {
                ans += (max - left + 1) * 1L * (N - i);
                left = max + 1;
            }
            map.put(a[i], i);
        }
        out.println(ans);
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

        public boolean ready() throws IOException {
            return br.ready();
        }
    }
}
