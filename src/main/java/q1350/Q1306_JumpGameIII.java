package q1350;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import q050.Q045_JumpGameII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1306. Jump Game III
 * https://leetcode.com/problems/jump-game-iii/
 *
 * Given an array of non-negative integers arr, you are initially positioned at start index of the array. When you are
 * at index i, you can jump to i + arr[i] or i - arr[i], check if you can reach to any index with value 0.
 *
 * Notice that you can not jump outside of the array at any time.
 *
 * Example 1:
 *
 * Input: arr = [4,2,3,0,3,1,2], start = 5
 * Output: true
 * Explanation:
 * All possible ways to reach at index 3 with value 0 are:
 * index 5 -> index 4 -> index 1 -> index 3
 * index 5 -> index 6 -> index 4 -> index 1 -> index 3
 *
 * Example 2:
 *
 * Input: arr = [4,2,3,0,3,1,2], start = 0
 * Output: true
 * Explanation:
 * One possible way to reach at index 3 with value 0 is:
 * index 0 -> index 4 -> index 1 -> index 3
 *
 * Example 3:
 *
 * Input: arr = [3,0,2,1,2], start = 2
 * Output: false
 * Explanation: There is no way to reach at index 1 with value 0.
 *
 * Constraints:
 *
 * 1 <= arr.length <= 5 * 10^4
 * 0 <= arr[i] < arr.length
 * 0 <= start < arr.length
 *
 * 上一题 {@link Q045_JumpGameII}
 * 下一题 {@link Q1345_JumpGameIV}
 */
@RunWith(LeetCodeRunner.class)
public class Q1306_JumpGameIII {

    @Answer
    public boolean canReach(int[] arr, int start) {
        final int n = arr.length;
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            int pos = queue.poll();
            if (pos < 0 || n <= pos || visited[pos]) {
                continue;
            }
            if (arr[pos] == 0) {
                return true;
            }
            visited[pos] = true;
            queue.add(pos - arr[pos]);
            queue.add(pos + arr[pos]);
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{4, 2, 3, 0, 3, 1, 2}, 5).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{4, 2, 3, 0, 3, 1, 2}, 0).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{3, 0, 2, 1, 2}, 2).expect(false);

}
