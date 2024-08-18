# 최소값 찾기 - 문제 번호 11003

## 문제 설명

이 문제는 주어진 `N`개의 숫자 배열에서 길이 `L`인 슬라이딩 윈도우를 사용하여 각 윈도우 내의 최소값을 구하는 문제입니다. 배열의 각 위치에서 슬라이딩 윈도우 내의 최소값을 출력해야 합니다.

## 입력

- 첫 줄에 `N`(1 ≤ `N` ≤ 5,000,000)과 `L`(1 ≤ `L` ≤ `N`)이 주어집니다.
- 다음 줄에 `N`개의 정수가 공백으로 구분되어 주어집니다.

## 출력

- 각 슬라이딩 윈도우 내의 최소값을 공백으로 구분하여 출력합니다.

## 문제 해결 접근법

이 문제는 **덱(Deque)** 자료구조를 사용하여 효율적으로 해결할 수 있습니다. Deque를 사용하여 윈도우 내의 최소값을 유지하면서 배열을 순회합니다. 이를 통해 시간 복잡도 `O(N)`으로 문제를 해결할 수 있습니다.

### 주요 단계:

1. **입력 처리**:
   - `N`과 `L`을 입력받고, 배열에 데이터를 저장합니다.

2. **슬라이딩 윈도우 적용**:
   - 배열을 순회하면서 현재 윈도우에 해당하는 최소값을 구합니다.
   - Deque는 현재 윈도우 내에서 최소값의 인덱스를 관리합니다.

3. **결과 출력**:
   - 각 윈도우의 최소값을 `StringBuilder`에 저장하고, 최종적으로 출력합니다.

### 코드 설명

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        int[] arr = new int[N];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        StringBuilder sb = new StringBuilder();
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            // 윈도우 범위를 벗어난 요소 제거
            if (!deque.isEmpty() && deque.peekFirst() < i - L + 1) {
                deque.pollFirst();
            }

            // Deque의 마지막 요소가 현재 요소보다 크면 제거
            while (!deque.isEmpty() && arr[deque.peekLast()] > arr[i]) {
                deque.pollLast();
            }

            // 현재 인덱스를 Deque에 추가
            deque.offerLast(i);
            // Deque의 첫 번째 요소는 현재 윈도우 내의 최소값의 인덱스
            sb.append(arr[deque.peekFirst()]).append(" ");
        }

        // 결과 출력
        System.out.println(sb.toString().trim());
    }
}
