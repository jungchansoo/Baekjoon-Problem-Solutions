# 행성 터널 - 문제 번호 2887

## 문제 설명

이 문제는 주어진 `N`개의 행성을 최소 비용으로 모두 연결하는 최소 스패닝 트리(MST, Minimum Spanning Tree)를 찾는 문제입니다. 각 행성은 3차원 공간의 좌표 `(x, y, z)`로 주어지며, 두 행성을 연결하는 비용은 해당 좌표 중 하나의 차이로 정의됩니다.

목표는 **모든 행성을 최소 비용으로 연결하는** 것입니다. 이를 위해 Kruskal 알고리즘과 Union-Find 자료구조를 사용하여 MST를 구성합니다.

## 입력

- 첫 줄에 행성의 수 `n`이 주어집니다. (`1 ≤ n ≤ 100,000`)
- 다음 `n`개의 줄에 각 행성의 `x`, `y`, `z` 좌표가 주어집니다.

## 출력

- 모든 행성을 최소 비용으로 연결하는 간선들의 비용 합을 출력합니다.

## 문제 해결 접근법

이 문제는 **Kruskal 알고리즘**과 **Union-Find 자료구조**를 사용하여 해결할 수 있습니다. 각 행성 간의 간선을 계산하여 최소 스패닝 트리를 구성하고, 그 비용의 합을 구합니다.

### 주요 단계:

1. **간선 생성**:
    - 각 축(`x`, `y`, `z`)을 기준으로 행성들을 정렬한 뒤, 인접한 행성들 간의 간선을 생성합니다. 이 간선은 두 행성을 연결하는 데 필요한 최소 비용을 나타냅니다.

2. **간선 정렬**:
    - 생성된 모든 간선을 비용 기준으로 오름차순 정렬합니다.

3. **Kruskal 알고리즘 적용**:
    - 정렬된 간선들 중에서 사이클을 형성하지 않는 간선을 선택하여 MST를 구성합니다.
    - 이를 위해 Union-Find 자료구조를 사용하여 각 노드가 속한 집합을 관리합니다.

### 코드 설명

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// Edge 클래스는 행성 간의 간선을 나타냅니다. 간선은 두 행성(u, v)과 그 연결 비용(cost)을 가집니다.
class Edge implements Comparable<Edge> {
    int u, v, cost;

    // 생성자: 간선을 초기화합니다.
    public Edge(int u, int v, int cost) {
        this.u = u;
        this.v = v;
        this.cost = cost;
    }

    // 간선의 비용을 기준으로 비교합니다. 이는 간선 리스트를 정렬할 때 사용됩니다.
    @Override
    public int compareTo(Edge o) {
        return this.cost - o.cost;
    }
}

public class Main {

    // 부모 노드를 저장하는 배열, Union-Find 자료구조에서 사용됩니다.
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());  // 행성의 수

        int[][] planets = new int[n][4];  // 행성의 좌표(x, y, z)와 행성 번호를 저장하는 배열
        ArrayList<Edge> edges = new ArrayList<>();  // 간선을 저장하는 리스트

        // 행성의 좌표 입력 및 초기화
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            planets[i][0] = Integer.parseInt(st.nextToken());  // x 좌표
            planets[i][1] = Integer.parseInt(st.nextToken());  // y 좌표
            planets[i][2] = Integer.parseInt(st.nextToken());  // z 좌표
            planets[i][3] = i;  // 행성 번호
        }

        // 각 축을 기준으로 간선 생성
        generateEdges(planets, edges, n, 0);  // x 축 기준
        generateEdges(planets, edges, n, 1);  // y 축 기준
        generateEdges(planets, edges, n, 2);  // z 축 기준

        // 모든 간선을 비용 기준으로 정렬
        Collections.sort(edges);

        // Union-Find 자료구조 초기화
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int result = 0;  // 최소 스패닝 트리의 총 비용
        int edgeCount = 0;  // 선택된 간선의 수

        // 간선을 하나씩 선택하면서 MST를 구성
        for (Edge edge : edges) {
            if (union(edge.u, edge.v)) {  // 두 노드가 다른 집합에 속해 있으면 연결
                result += edge.cost;  // 총 비용에 간선 비용 추가
                edgeCount++;
                if (edgeCount == n - 1) break;  // 모든 노드가 연결되면 종료
            }
        }

        System.out.println(result);  // 최종 비용 출력
    }

    // generateEdges 메서드는 각 축을 기준으로 행성을 정렬한 후, 인접한 행성들 간의 간선을 생성합니다.
    private static void generateEdges(int[][] planets, ArrayList<Edge> edges, int n, int axis) {
        // 주어진 축(axis)에 따라 행성을 정렬
        Arrays.sort(planets, Comparator.comparingInt(p -> p[axis]));

        // 정렬된 행성 간에 인접한 간선을 생성하여 리스트에 추가
        for (int i = 1; i < n; i++) {
            int u = planets[i - 1][3];
            int v = planets[i][3];
            int cost = Math.abs(planets[i - 1][axis] - planets[i][axis]);
            edges.add(new Edge(u, v, cost));
        }
    }

    // find 메서드는 노드 x가 속한 집합의 루트를 찾습니다.
    public static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);  // 경로 압축 최적화를 통해 루트 노드를 찾습니다.
    }

    // union 메서드는 두 노드 x와 y의 집합을 병합합니다.
    public static boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        // 두 노드가 다른 집합에 속해 있다면 하나로 병합
        if (rootX != rootY) {
            parent[rootY] = rootX;  // y의 집합을 x의 집합에 병합
            return true;
        }
        return false;  // 이미 같은 집합에 속해 있는 경우
    }

}
