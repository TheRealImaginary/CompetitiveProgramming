package Codeforces;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class CF101243_D_WeatherStation {

    private static final long MOD = (long) 1e9 + 7;

    private static int N;
    private static char[] c;
    private static long[][] dp;

    private static int get(char c) {
        return c == 'N' ? 0 : c == 'E' ? 1 : c == 'S' ? 2 : 3;
    }

    private static long add(long a, long b) {
        return (a + b) % MOD;
    }

    // 0 N, 1 E, 2 S, 3 W, 4 null
    private static long solve(int idx, int last) {
        if (idx == N)
            return 1;
        if (dp[last][idx] != -1)
            return dp[last][idx];
        int id = get(c[idx]);
        long res = solve(idx + 1, id) % MOD;
        if ((last == 0 && (id == 1 || id == 3)) || (last == 2 && (id == 1 || id == 3)))
            res = add(res, solve(idx + 1, 4));
        return dp[last][idx] = res;
    }

    public static void main(String[] args) throws IOException {
//        Scanner sc = new Scanner(new File("input.txt"));
//        PrintWriter out = new PrintWriter("output.txt");
        Scanner sc = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        c = sc.next().toCharArray();
        N = c.length;
        dp = new long[5][N];
        for (int i = 0; i < 5; i++)
            Arrays.fill(dp[i], -1);
        out.println(solve(0, 4));
        out.flush();
        out.close();
    }
}