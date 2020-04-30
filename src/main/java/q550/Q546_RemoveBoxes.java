package q550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/remove-boxes/
 *
 * Given several boxes with different colors represented by different positive numbers.
 * You may experience several rounds to remove boxes until there is no box left. Each time you can choose some
 * continuous boxes with the same color (composed of k boxes, k >= 1), remove them and get k*k points.
 * Find the maximum points you can get.
 *
 * Example 1:
 * Input:
 *
 * [1, 3, 2, 2, 2, 3, 4, 3, 1]
 *
 * Output:
 *
 * 23
 *
 * Explanation:
 *
 * [1, 3, 2, 2, 2, 3, 4, 3, 1]
 * ----> [1, 3, 3, 4, 3, 1] (3*3=9 points)
 * ----> [1, 3, 3, 3, 1] (1*1=1 points)
 * ----> [1, 1] (3*3=9 points)
 * ----> [] (2*2=4 points)
 *
 * Note: The number of boxes n would not exceed 100.
 */
@RunWith(LeetCodeRunner.class)
public class Q546_RemoveBoxes {

    /**
     * 参考 https://www.cnblogs.com/grandyang/p/6850657.html
     * 暴力dfs 的方式会超时, 带缓存还是超时, 链接中提供了一种改进的缓存方式.
     */
    @Answer
    public int removeBoxes(int[] boxes) {
        final int n = boxes.length;
        // dp[start][end][len] 表示在boxes 的区间 [start, end] 内,
        // 有len 个元素和boxes[start] 相等, 此时可以得到的分数
        int[][][] dp = new int[n][n][n];
        return dfs(boxes, 0, n - 1, 0, dp);
    }

    private int dfs(int[] boxes, int start, int end, int len, int[][][] dp) {
        if (start > end) {
            return 0;
        }
        if (dp[start][end][len] > 0) {
            return dp[start][end][len];
        }
        // 本来就相邻的就直接加上去
        while (start + 1 <= end && boxes[start] == boxes[start + 1]) {
            start++;
            len++;
        }
        // 本身就和boxes[i] 相邻的结果
        int res = (1 + len) * (1 + len) + dfs(boxes, start + 1, end, 0, dp);
        // 如果 boxes[start] == boxes[i], 那么就除掉两者之间的空隙, 让不相邻的相同元素相邻合并.
        for (int i = start + 1; i <= end; i++) {
            if (boxes[i] == boxes[start]) {
                res = Math.max(res, dfs(boxes, start + 1, i - 1, 0, dp)
                        + dfs(boxes, i, end, len + 1, dp));
            }
        }
        return dp[start][end][len] = res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{1, 3, 2, 2, 2, 3, 4, 3, 1}).expect(23);

    @TestData
    public DataExpectation overTime = DataExpectation.create(new int[]{
            3, 8, 8, 5, 5, 3, 9, 2, 4, 4, 6, 5, 8, 4, 8, 6, 9, 6, 2, 8, 6, 4, 1, 9, 5, 3,
            10, 5, 3, 3, 9, 8, 8, 6, 5, 3, 7, 4, 9, 6, 3, 9, 4, 3, 5, 10, 7, 6, 10, 7})
            .expect(136);

}
