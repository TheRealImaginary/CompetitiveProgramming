package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class UVA_11057_ExactSum {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st;
        StringTokenizer result = null;
        int[] books;
        int c = 0;
        while (br.ready()) {
            int b = Integer.parseInt(br.readLine());
            books = new int[b];
            st = new StringTokenizer(br.readLine());
            int min = 1000000000;
            for (int i = 0; st.hasMoreTokens(); i++) {
                books[i] = Integer.parseInt(st.nextToken());
            }
            Arrays.sort(books);
            int total = Integer.parseInt(br.readLine());
            for (int i = 0; i < books.length; i++) {
                int index = Arrays.binarySearch(books, total - books[i]);
                if (index > 0 && index != i && Math.abs(books[index] - books[i]) < min) {
                    min = Math.abs(books[index] - books[i]);
                    result = new StringTokenizer(books[i] + " " + books[index]);
                }
            }
            if (c != 0)
                pw.print("\n");
            pw.printf("Peter should buy books whose prices are %s and %s.\n", result.nextToken(), result.nextToken());
            br.readLine();  //Empty line in Question
            c = 1;
        }
        pw.println();
        pw.flush();
        pw.close();
    }

}
