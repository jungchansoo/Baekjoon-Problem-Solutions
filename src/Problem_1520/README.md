# 내리막 길 - 문제 번호 1520

## 문제 설명

이 문제는 주어진 지도의 시작점 `(0, 0)`에서 목표 지점 `(m-1, n-1)`까지 내리막길을 따라 이동할 수 있는 경로의 수를 구하는 문제입니다. 경로를 계산할 때, 항상 현재 위치보다 낮은 고도(높이)의 위치로만 이동할 수 있습니다. 가능한 모든 경로를 찾는 것이 목표입니다.

## 입력

- 첫 줄에 지도의 크기를 나타내는 두 개의 정수 `m`과 `n`이 주어집니다. 여기서 `m`은 행의 수, `n`은 열의 수를 의미합니다.
- 그 다음 `m`개의 줄에 각각 `n`개의 정수로 이루어진 지도가 주어집니다. 각 정수는 해당 위치의 고도를 나타냅니다.

## 출력

- 시작점 `(0, 0)`에서 목표 지점 `(m-1, n-1)`까지 도달할 수 있는 모든 가능한 경로의 수를 출력합니다.

## 문제 해결 접근법

이 문제는 **DFS(깊이 우선 탐색)**와 **DP(동적 프로그래밍)**을 결합하여 해결할 수 있습니다. 각각의 위치에서 출발해 목표 지점까지 도달할 수 있는 모든 경로의 수를 동적 프로그래밍 기법을 사용해 계산하고 저장합니다.

### 주요 단계:

1. **DFS 탐색**:
   - `(0, 0)`에서 출발하여, 내리막길을 따라 이동할 수 있는 모든 방향으로 DFS를 사용해 탐색합니다.
   - 목표 지점 `(m-1, n-1)`에 도달하면 경로 수 1을 반환합니다.

2. **DP 테이블 활용**:
   - 이미 계산된 경로 수는 `dp` 배열에 저장하여, 동일한 위치에 다시 도달했을 때 중복 계산을 방지합니다.
   - `dp[x][y]`는 위치 `(x, y)`에서 목표 지점까지 갈 수 있는 모든 경로의 수를 저장합니다.

3. **경로 수 누적**:
   - 네 가지 방향(상, 하, 좌, 우)으로 이동 가능한지 검사하고, 이동 가능한 경우 재귀적으로 DFS를 호출하여 경로 수를 누적합니다.

## 코드 설명

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Main {
    static int[][] map;
    static int[][] dp;
    static int m, n;
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        map = new int[m][n];
        dp = new int[m][n];

        // 지도의 높이를 입력받아 map 배열 초기화
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // DP 배열을 -1로 초기화하여 방문 여부 및 경로 수 초기화
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        // DFS를 이용하여 (0, 0)에서 목표 지점까지의 모든 경로 수 계산 및 출력
        System.out.println(dfs(0, 0));
    }

    public static int dfs(int x, int y) {
        // 목표 지점에 도달한 경우
        if (x == m - 1 && y == n - 1) {
            return 1;
        }

        // 이미 방문한 위치라면 저장된 경로 수 반환
        if (dp[x][y] != -1) {
            return dp[x][y];
        }

        dp[x][y] = 0;

        // 네 방향(상, 하, 좌, 우)으로 이동 가능 여부 확인 및 재귀 호출
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if ((nx >= 0 && nx < m) && (ny >= 0 && ny < n) && (map[nx][ny] < map[x][y])) {
                dp[x][y] += dfs(nx, ny);
            }
        }

        return dp[x][y];
    }
}
