package Codeforces;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * For each permutation of the sequence of strings, we place the first 3
 * horizontally and second 3 vertically, then we just check if characters match
 * and strings can be put in grid with dimensions N by M, where N = |s[4]| and
 * M = |s[1]| (0-based)
 */
public class CF47_C_Crossword {

    private static final int MAXN = 6;

    private static int[] pos;
    private static String[] s;
    private static char[][] answer;
    private static boolean[] done;

    // 0, 1, 2 H | 3, 4, 5 V
    private static void solve(int idx) {
        if (idx == MAXN) {
//            System.err.println(Arrays.toString(pos));
            char[][] mightBe = new char[MAXN][];
            for (int i = 0; i < MAXN; i++) {
                mightBe[i] = new char[s[pos[i]].length()];
                for (int j = 0; j < mightBe[i].length; j++)
                    mightBe[i][j] = s[pos[i]].charAt(j);
            }
            char[][] grid = new char[mightBe[4].length][mightBe[1].length];
            for (int i = 0; i < mightBe[4].length; i++)
                Arrays.fill(grid[i], '.');
            for (int i = 0; i < MAXN; i++)
                for (int j = 0; j < mightBe[i].length; j++) {
                    int row = -1, col = -1;
                    if (i == 0) {
                        row = 0;
                        col = j;
                    } else if (i == 1) {
                        row = mightBe[3].length - 1;
                        col = j;
                    } else if (i == 2) {
                        row = mightBe[4].length - 1;
                        col = j + mightBe[0].length - 1;
                    } else if (i == 3) {
                        row = j;
                        col = 0;
                    } else if (i == 4) {
                        row = j;
                        col = mightBe[0].length - 1;
                    } else {
                        row = j + mightBe[3].length - 1;
                        col = mightBe[1].length - 1;
                    }
                    if (row < 0 || row >= grid.length || col < 0 || col >= grid[row].length) return;
                    if (grid[row][col] == '.') grid[row][col] = mightBe[i][j];
                    else if (grid[row][col] != mightBe[i][j]) return;
                }
            if (answer == null || smaller(grid, answer)) answer = grid;
        } else {
            for (int i = 0; i < MAXN; i++) {
                if (done[i]) continue;
                pos[idx] = i;
                done[i] = true;
                solve(idx + 1);
                done[i] = false;
            }
        }
    }

    private static boolean smaller(char[][] a, char[][] b) {
        int n = Math.min(a.length, b.length);
        for (int i = 0; i < n; i++) {
            int x = new String(a[i]).compareTo(new String(b[i]));
            if (x == 0) continue;
            return x < 0;
        }
        return a.length <= b.length;
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        s = new String[MAXN];
        for (int i = 0; i < MAXN; i++)
            s[i] = sc.next();
        pos = new int[MAXN];
        done = new boolean[MAXN];
        solve(0);
        if (answer == null) out.println("Impossible");
        else for (int i = 0; i < answer.length; i++) out.println(new String(answer[i]));
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
