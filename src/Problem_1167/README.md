# 트리의 지름 - 문제 번호 1167

## 문제 설명

이 문제는 주어진 트리에서 두 점 사이의 가장 긴 경로(트리의 지름)를 찾는 문제입니다. 트리는 사이클이 없는 그래프이며, 노드와 노드 간의 거리는 간선의 가중치로 정의됩니다. 트리의 지름은 가장 긴 경로의 길이를 의미합니다.

## 입력

- 첫 줄에 정수 `V` (트리의 노드 수, 2 ≤ V ≤ 10,000)가 주어집니다.
- 이어서 `V`개의 줄이 주어지며, 각 줄은 노드와 그 노드와 연결된 다른 노드 및 간선의 가중치를 설명합니다. 각 노드에 대한 입력은 해당 노드와 연결된 다른 노드와 가중치 정보를 포함하며, 마지막에는 `-1`로 끝납니다.

## 출력

- 두 점 사이의 가장 긴 경로의 길이(트리의 지름)를 출력합니다.

## 문제 해결 접근법

이 문제는 **DFS(깊이 우선 탐색)**를 두 번 사용하는 접근법으로 해결할 수 있습니다. 다음 단계에 따라 문제를 해결합니다:

### 주요 단계:

1. **첫 번째 DFS 탐색**:
   - 임의의 노드에서 시작하여 가장 먼 노드를 찾습니다. 이 노드를 `farthestNode`로 설정합니다.

2. **두 번째 DFS 탐색**:
   - `farthestNode`에서 다시 DFS를 수행하여 트리의 지름(가장 긴 경로의 길이)을 계산합니다.

3. **경로 길이 업데이트**:
   - 각 DFS 탐색에서 최대 거리를 업데이트하고 최종적으로 트리의 지름을 계산합니다.

## 코드 설명

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
    static class Node {
        int vertex, weight;
        Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }

    static ArrayList<Node>[] tree;
    static boolean[] visited;
    static int maxDistance, farthestNode;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int V = Integer.parseInt(br.readLine());
        tree = new ArrayList[V+1];
        for (int i = 1; i <= V; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < V; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            while (true) {
                int neighbor = Integer.parseInt(st.nextToken());
                if (neighbor == -1) break;
                int weight = Integer.parseInt(st.nextToken());
                tree[node].add(new Node(neighbor, weight));
            }
        }

        visited = new boolean[V + 1];
        dfs(1, 0);

        visited = new boolean[V + 1];
        dfs(farthestNode, 0);

        System.out.println(maxDistance);
    }
    
    // DFS 탐색 함수
    static void dfs(int node, int distance) {
        // 현재 거리보다 더 멀리 있는 노드를 찾으면 갱신
        if (distance > maxDistance) {
            maxDistance = distance;
            farthestNode = node;
        }

        visited[node] = true;
        
        // 모든 이웃 노드에 대해 DFS 탐색
        for (Node neighbor : tree[node]) {
            if (!visited[neighbor.vertex]) {
                dfs(neighbor.vertex, distance + neighbor.weight);
            }
        }
    }
}
