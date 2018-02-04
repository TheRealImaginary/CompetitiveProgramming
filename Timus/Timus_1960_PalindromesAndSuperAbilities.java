package Timus;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * The number of nodes in a Palindromic Tree is the number of distinct
 * palindromes.
 */
public class Timus_1960_PalindromesAndSuperAbilities {

    private static class PalindromicTree {

        private static final int ALPHA = 26;

        private static class Node {
            int[] next;
            int link;
            int len;

            private Node(int length) {
                this(length, -1);
            }

            private Node(int length, int suffixLink) {
                len = length;
                next = new int[ALPHA];
                Arrays.fill(next, -1);
                link = suffixLink;
            }
        }

        Node[] nodes;
        int nodeCount;
        int evenRoot;
        int oddRoot;
        int last;
        private char[] c;

        int[] ans;

        public PalindromicTree(String s) {
            int maxNodes = s.length() + 2;
            nodes = new Node[maxNodes];
            evenRoot = 0;
            oddRoot = 1;
            nodes[evenRoot] = new Node(0, oddRoot);
            nodes[oddRoot] = new Node(-1, oddRoot);
            nodeCount = 2;
            last = evenRoot;
            c = s.toCharArray();
            ans = new int[c.length];
            for (int i = 0; i < s.length(); i++)
                addLetter(i);
        }

        public void addLetter(int pos) {
            int ch = c[pos] - 'a';
            while (true) {
                Node node = nodes[last];
                if (pos - 1 - node.len >= 0 && c[pos - 1 - node.len] == c[pos])
                    break;
                last = node.link;
            }

            if (nodes[last].next[ch] != -1)
                last = nodes[last].next[ch];
            else {
                int newNode = nodeCount++;
                Node node = nodes[newNode] = new Node(nodes[last].len + 2);
                nodes[last].next[ch] = newNode;

                if (node.len == 1)
                    node.link = evenRoot;
                else {
                    while (true) {
                        last = nodes[last].link;
                        node = nodes[last];
                        if (pos - 1 - node.len >= 0 && c[pos - 1 - node.len] == c[pos]) {
                            nodes[newNode].link = nodes[last].next[ch];
                            break;
                        }
                    }
                }
                last = newNode;
            }
            ans[pos] = nodeCount - 2;
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        PalindromicTree tree = new PalindromicTree(sc.next());
        for (int i = 0; i < tree.ans.length; i++) {
            if (i > 0)
                out.print(" ");
            out.print(tree.ans[i]);
        }
        out.println();
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
