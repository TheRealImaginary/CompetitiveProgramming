package UVA;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class UVA_10679_ILoveStrings {

    private static final int MAX = 26001;

    private static class AhoCorasickTrie {

        private static final int ALPHA = 100;

        Node[] nodes;
        int root;
        int nodeCount;

        private static class Node {
            List<Integer> indices;
            boolean check;
            int[] next;
            int[] go;
            int link;
            int c;
            int p;

            public Node(int parent, int ch) {
                indices = new ArrayList<>();
                next = new int[ALPHA];
                go = new int[ALPHA];
                Arrays.fill(next, -1);
                Arrays.fill(go, -1);
                check = false;
                link = -1;
                c = ch;
                p = parent;
            }
        }

        public AhoCorasickTrie(int numberOfNodes) {
            nodes = new Node[numberOfNodes];
            nodes[root = 0] = new Node(0, -1);
            nodeCount = 1;
        }

        public void insert(String s, int index) {
            int node = root;
            for (int i = 0; i < s.length(); i++) {
                int c = s.charAt(i) - 'A';
                if (nodes[node].next[c] == -1) {
                    nodes[node].next[c] = nodeCount;
                    nodes[nodeCount++] = new Node(node, c);
                }
                node = nodes[node].next[c];
            }
            nodes[node].indices.add(index);
        }

        public int link(int node) {
            Node n = nodes[node];
            if (n.link == -1)
                n.link = n.p == root ? root : go(link(n.p), n.c);
            return n.link;
        }

        public int go(int node, int c) {
            Node n = nodes[node];
            if (n.go[c] == -1)
                n.go[c] = n.next[c] != -1 ? n.next[c] : node == root ? root : go(link(node), c);
            return n.go[c];
        }

        public boolean[] solve(String s, int n) {
            int node = root;
            for (int i = 0; i < s.length(); i++) {
                node = go(node, s.charAt(i) - 'A');
                nodes[node].check = true;
            }
            boolean[] res = new boolean[n];
            // Need to check suffix-links and update answer.
            // Case Like: 1 dabce 3 dbac abc bc
            for (node = root + 1; node < nodeCount; node++) {
                if (nodes[node].check) {
                    for (int idx : nodes[node].indices)
                        res[idx] = true;
                    int now = node;
                    while (!nodes[now = link(now)].check) {
                        nodes[now].check = true;
                        for (int idx : nodes[now].indices)
                            res[idx] = true;
                    }
                }
            }
            return res;
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner sc = new MyScanner(System.in);
        PrintWriter out = new PrintWriter(System.out);
        int TC = sc.nextInt();
        while (TC-- > 0) {
            AhoCorasickTrie trie = new AhoCorasickTrie(MAX);
            String s = sc.next();
            int q = sc.nextInt();
            for (int i = 0; i < q; i++)
                trie.insert(sc.next(), i);
            boolean[] ans = trie.solve(s, q);
            for (int i = 0; i < q; i++)
                out.println(ans[i] ? "y" : "n");
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
