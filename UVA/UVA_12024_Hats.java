package UVA;

import java.io.*;
import java.util.StringTokenizer;

/**
 * @author Mazen Magdy
 *              -Number of Derangements.
 */
public class UVA_12024_Hats {

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int[] fact = new int[13];
        fact[1] = 1;
        for (int i = 2; i <= 12; i++)
            fact[i] = i * fact[i - 1];
        int[] f = new int[13];
        f[0] = 1;
        f[1] = 0;
        for (int i = 2; i <= 12; i++)
            f[i] = (i - 1) * (f[i - 1] + f[i - 2]);

        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            out.printf("%d/%d\n", f[n], fact[n]);
        }
        out.flush();
        out.close();
    }

    static class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        MyScanner(InputStream in) {
            br = new BufferedReader(new InputStreamReader(in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
