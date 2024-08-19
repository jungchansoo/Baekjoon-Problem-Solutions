package Problem_14868;

import java.io.*;
import java.util.*;

public class Main {
    static int[] parent;
    static int[] rank;
    static Queue<int[]> queue = new LinkedList<>();
    static int N, K;
    static int[][] map;
    static int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        parent = new int[K + 1];
        rank = new int[K + 1];
        map = new int[N][N];

        for (int i = 1; i <= K; i++) {
            parent[i] = i;
        }

        for (int i = 1; i <= K; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            map[x][y] = i;
            queue.add(new int[]{x, y, i});
        }

        int years = 0;
        while (!queue.isEmpty()) {
            if (connected()) break;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int x = current[0];
                int y = current[1];
                int civ = current[2];

                for (int[] dir : directions) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];

                    if (nx >= 0 && ny >= 0 && nx < N && ny < N) {
                        if (map[nx][ny] == 0) {
                            map[nx][ny] = civ;
                            queue.add(new int[]{nx, ny, civ});
                        } else if (find(map[nx][ny]) != find(civ)) {
                            union(map[nx][ny], civ);
                        }
                    }
                }
            }
            years++;
        }

        System.out.println(years);
    }

    static boolean connected() {
        int root = find(1);
        for (int i = 2; i <= K; i++) {
            if (find(i) != root) return false;
        }
        return true;
    }

    static int find(int x) {
        if (x == parent[x]) return x;
        return parent[x] = find(parent[x]);
    }

    static void union(int x, int y) {
        x = find(x);
        y = find(y);

        if (x != y) {
            if (rank[x] > rank[y]) {
                parent[y] = x;
            } else {
                parent[x] = y;
                if (rank[x] == rank[y]) rank[y]++;
            }
        }
    }
}
