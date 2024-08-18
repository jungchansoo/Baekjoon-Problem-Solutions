package Problem_2887;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Edge implements Comparable<Edge> {
    int u, v, cost;

    public Edge(int u, int v, int cost) {
        this.u = u;
        this.v = v;
        this.cost = cost;
    }

    @Override
    public int compareTo(Edge o) {
        return this.cost - o.cost;
    }
}

public class Main {

    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int[][] planets = new int[n][4];
        ArrayList<Edge> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            planets[i][0] = Integer.parseInt(st.nextToken());
            planets[i][1] = Integer.parseInt(st.nextToken());
            planets[i][2] = Integer.parseInt(st.nextToken());
            planets[i][3] = i;
        }

        generateEdges(planets, edges, n, 0);
        generateEdges(planets, edges, n, 1);
        generateEdges(planets, edges, n, 2);

        Collections.sort(edges);

        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int result = 0;
        int edgeCount = 0;
        for (Edge edge : edges) {
            if (union(edge.u, edge.v)) {
                result += edge.cost;
                edgeCount++;
                if (edgeCount == n - 1) break;
            }
        }

        System.out.println(result);

    }

    private static void generateEdges(int[][] planets, ArrayList<Edge> edges, int n, int axis) {
        Arrays.sort(planets, Comparator.comparingInt(p -> p[axis]));

        for (int i = 1; i < n; i++) {
            int u = planets[i - 1][3];
            int v = planets[i][3];
            int cost = Math.abs(planets[i - 1][axis] - planets[i][axis]);
            edges.add(new Edge(u, v, cost));
        }
    }

    public static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    public static boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            parent[rootY] = rootX;
            return true;
        }
        return false;
    }

}
