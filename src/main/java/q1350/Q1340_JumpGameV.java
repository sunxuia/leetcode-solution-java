package q1350;

import org.junit.runner.RunWith;
import q1700.Q1696_JumpGameVI;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1340. Jump Game V
 * https://leetcode.com/problems/jump-game-v/
 *
 * Given an array of integers arr and an integer d. In one step you can jump from index i to index:
 *
 * i + x where: i + x < arr.length and  0 < x <= d.
 * i - x where: i - x >= 0 and  0 < x <= d.
 *
 * In addition, you can only jump from index i to index j if arr[i] > arr[j] and arr[i] > arr[k] for all indices k
 * between i and j (More formally min(i, j) < k < max(i, j)).
 *
 * You can choose any index of the array and start jumping. Return the maximum number of indices you can visit.
 *
 * Notice that you can not jump outside of the array at any time.
 *
 * Example 1:
 * <img src="./Q1340_PIC.png">
 * Input: arr = [6,4,14,6,8,13,9,7,10,6,12], d = 2
 * Output: 4
 * Explanation: You can start at index 10. You can jump 10 --> 8 --> 6 --> 7 as shown.
 * Note that if you start at index 6 you can only jump to index 7. You cannot jump to index 5 because 13 > 9. You cannot
 * jump to index 4 because index 5 is between index 4 and 6 and 13 > 9.
 * Similarly You cannot jump from index 3 to index 2 or index 1.
 *
 * Example 2:
 *
 * Input: arr = [3,3,3,3,3], d = 3
 * Output: 1
 * Explanation: You can start at any index. You always cannot jump to any index.
 *
 * Example 3:
 *
 * Input: arr = [7,6,5,4,3,2,1], d = 1
 * Output: 7
 * Explanation: Start at index 0. You can visit all the indicies.
 *
 * Example 4:
 *
 * Input: arr = [7,1,7,1,7,1], d = 2
 * Output: 2
 *
 * Example 5:
 *
 * Input: arr = [66], d = 1
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= arr.length <= 1000
 * 1 <= arr[i] <= 10^5
 * 1 <= d <= arr.length
 *
 * 上一题 {@link Q1345_JumpGameIV}
 * 下一题 {@link Q1696_JumpGameVI}
 */
@RunWith(LeetCodeRunner.class)
public class Q1340_JumpGameV {

    @Answer
    public int maxJumps(int[] arr, int d) {
        int[] jumps = new int[arr.length];
        int res = 0;
        for (int i = 0; i < arr.length; i++) {
            res = Math.max(res, dfs(arr, d, jumps, i));
        }
        return res;
    }

    private int dfs(int[] arr, int d, int[] jumps, int curr) {
        if (jumps[curr] > 0) {
            return jumps[curr];
        }
        jumps[curr] = 1;
        // 向前跳
        int start = Math.max(0, curr - d);
        for (int i = curr - 1; i >= start && arr[curr] > arr[i]; i--) {
            jumps[curr] = Math.max(jumps[curr], 1 + dfs(arr, d, jumps, i));
        }
        // 向后跳
        int end = Math.min(arr.length - 1, curr + d);
        for (int i = curr + 1; i <= end && arr[curr] > arr[i]; i++) {
            jumps[curr] = Math.max(jumps[curr], 1 + dfs(arr, d, jumps, i));
        }
        return jumps[curr];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{6, 4, 14, 6, 8, 13, 9, 7, 10, 6, 12}, 2).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{3, 3, 3, 3, 3}, 3).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{7, 6, 5, 4, 3, 2, 1}, 1).expect(7);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(new int[]{7, 1, 7, 1, 7, 1}, 2).expect(2);

    @TestData
    public DataExpectation example5 = DataExpectation.createWith(new int[]{66}, 1).expect(1);

}
