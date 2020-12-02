package q1350;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1345. Jump Game IV
 * https://leetcode.com/problems/jump-game-iv/
 *
 * Given an array of integers arr, you are initially positioned at the first index of the array.
 *
 * In one step you can jump from index i to index:
 *
 * i + 1 where: i + 1 < arr.length.
 * i - 1 where: i - 1 >= 0.
 * j where: arr[i] == arr[j] and i != j.
 *
 * Return the minimum number of steps to reach the last index of the array.
 *
 * Notice that you can not jump outside of the array at any time.
 *
 * Example 1:
 *
 * Input: arr = [100,-23,-23,404,100,23,23,23,3,404]
 * Output: 3
 * Explanation: You need three jumps from index 0 --> 4 --> 3 --> 9. Note that index 9 is the last index of the array.
 *
 * Example 2:
 *
 * Input: arr = [7]
 * Output: 0
 * Explanation: Start index is the last index. You don't need to jump.
 *
 * Example 3:
 *
 * Input: arr = [7,6,9,6,9,6,9,7]
 * Output: 1
 * Explanation: You can jump directly from index 0 to index 7 which is last index of the array.
 *
 * Example 4:
 *
 * Input: arr = [6,1,9]
 * Output: 2
 *
 * Example 5:
 *
 * Input: arr = [11,22,7,7,7,7,7,7,7,22,13]
 * Output: 3
 *
 * Constraints:
 *
 * 1 <= arr.length <= 5 * 10^4
 * -10^8 <= arr[i] <= 10^8
 *
 * 上一题 {@link Q1306_JumpGameIII}
 * 下一题 {@link Q1340_JumpGameV}
 */
@RunWith(LeetCodeRunner.class)
public class Q1345_JumpGameIV {

    /**
     * bfs
     */
    @Answer
    public int minJumps(int[] arr) {
        final int n = arr.length;
        int[] nexts = new int[n];
        int[] prevs = new int[n];
        Arrays.fill(nexts, n);
        Arrays.fill(prevs, -1);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            Integer prev = map.get(arr[i]);
            if (prev != null) {
                nexts[prev] = i;
                prevs[i] = prev;
            }
            map.put(arr[i], i);
        }

        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[n];
        queue.add(0);
        visited[0] = true;
        int res = 0;
        while (!queue.isEmpty()) {
            for (int len = queue.size(); len > 0; len--) {
                int pos = queue.poll();
                if (pos == n - 1) {
                    return res;
                }
                for (int i = prevs[pos]; i > 0 && !visited[i]; i = prevs[i]) {
                    visited[i] = true;
                    queue.add(i);
                }
                for (int i = nexts[pos]; i < n && !visited[i]; i = nexts[i]) {
                    visited[i] = true;
                    queue.add(i);
                }
                if (pos > 0 && !visited[pos - 1]) {
                    visited[pos - 1] = true;
                    queue.add(pos - 1);
                }
                if (!visited[pos + 1]) {
                    visited[pos + 1] = true;
                    queue.add(pos + 1);
                }
            }
            res++;
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{100, -23, -23, 404, 100, 23, 23, 23, 3, 404})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{7}).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{7, 6, 9, 6, 9, 6, 9, 7}).expect(1);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{6, 1, 9}).expect(2);

    @TestData
    public DataExpectation example5 = DataExpectation
            .create(new int[]{11, 22, 7, 7, 7, 7, 7, 7, 7, 22, 13})
            .expect(3);

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(TestDataFileHelper.read(testDataFile, 1, int[].class))
            .expect(30);

    @TestData
    public DataExpectation overMemory1 = DataExpectation
            .create(TestDataFileHelper.read(testDataFile, 2, int[].class))
            .expect(2);

    @TestData
    public DataExpectation overMemory2 = DataExpectation
            .create(TestDataFileHelper.read(testDataFile, 3, int[].class))
            .expect(30);

}
