package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2106. Maximum Fruits Harvested After at Most K Steps</h3>
 * <a href="https://leetcode.com/problems/maximum-fruits-harvested-after-at-most-k-steps/">
 * https://leetcode.com/problems/maximum-fruits-harvested-after-at-most-k-steps/
 * </a><br/>
 *
 * <p>Fruits are available at some positions on an infinite x-axis. You are given a 2D integer array <code>fruits</code>
 * where <code>fruits[i] = [position<sub>i</sub>, amount<sub>i</sub>]</code> depicts <code>amount<sub>i</sub></code>
 * fruits at the position <code>position<sub>i</sub></code>. <code>fruits</code> is already <strong>sorted</strong> by
 * <code>position<sub>i</sub></code> in <strong>ascending order</strong>, and each <code>position<sub>i</sub></code> is
 * <strong>unique</strong>.</p>
 *
 * <p>You are also given an integer <code>startPos</code> and an integer <code>k</code>. Initially, you are at the
 * position <code>startPos</code>. From any position, you can either walk to the <strong>left or right</strong>. It
 * takes <strong>one step</strong> to move <strong>one unit</strong> on the x-axis, and you can walk <strong>at
 * most</strong> <code>k</code> steps in total. For every position you reach, you harvest all the fruits at that
 * position, and the fruits will disappear from that position.</p>
 *
 * <p>Return <em>the <strong>maximum total number</strong> of fruits you can harvest</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/11/21/1.png" style="width: 472px; height: 115px;" />
 * <pre>
 * <strong>Input:</strong> fruits = [[2,8],[6,3],[8,6]], startPos = 5, k = 4
 * <strong>Output:</strong> 9
 * <strong>Explanation:</strong>
 * The optimal way is to:
 * - Move right to position 6 and harvest 3 fruits
 * - Move right to position 8 and harvest 6 fruits
 * You moved 3 steps and harvested 3 + 6 = 9 fruits in total.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/11/21/2.png" style="width: 512px; height: 129px;" />
 * <pre>
 * <strong>Input:</strong> fruits = [[0,9],[4,1],[5,7],[6,2],[7,4],[10,9]], startPos = 5, k = 4
 * <strong>Output:</strong> 14
 * <strong>Explanation:</strong>
 * You can move at most k = 4 steps, so you cannot reach position 0 nor 10.
 * The optimal way is to:
 * - Harvest the 7 fruits at the starting position 5
 * - Move left to position 4 and harvest 1 fruit
 * - Move right to position 6 and harvest 2 fruits
 * - Move right to position 7 and harvest 4 fruits
 * You moved 1 + 3 = 4 steps and harvested 7 + 1 + 2 + 4 = 14 fruits in total.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/11/21/3.png" style="width: 476px; height: 100px;" />
 * <pre>
 * <strong>Input:</strong> fruits = [[0,3],[6,4],[8,5]], startPos = 3, k = 2
 * <strong>Output:</strong> 0
 * <strong>Explanation:</strong>
 * You can move at most k = 2 steps and cannot reach any position with fruits.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= fruits.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>fruits[i].length == 2</code></li>
 * 	<li><code>0 &lt;= startPos, position<sub>i</sub> &lt;= 2 * 10<sup>5</sup></code></li>
 * 	<li><code>position<sub>i-1</sub> &lt; position<sub>i</sub></code> for any <code>i &gt; 0</code>&nbsp;
 * 	(<strong>0-indexed</strong>)</li>
 * 	<li><code>1 &lt;= amount<sub>i</sub> &lt;= 10<sup>4</sup></code></li>
 * 	<li><code>0 &lt;= k &lt;= 2 * 10<sup>5</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2106_MaximumFruitsHarvestedAfterAtMostKSteps {

    @Answer
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        final int n = fruits.length;
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + fruits[i][1];
        }

        int res = 0;
        int start = lessOrEquals(fruits, startPos);
        for (int i = start; i >= 0 && startPos - fruits[i][0] <= k; i--) {
            int rightPos = fruits[i][0] + k - (startPos - fruits[i][0]);
            int right = startPos < rightPos ? lessOrEquals(fruits, rightPos) : start;
            int harvest = sums[right + 1] - sums[i];
            res = Math.max(res, harvest);
        }

        for (int i = start + 1; i < n && fruits[i][0] - startPos <= k; i++) {
            int leftPos = fruits[i][0] - k + (fruits[i][0] - startPos);
            int left = leftPos < startPos ? lessOrEquals(fruits, leftPos - 1) : start;
            int harvest = sums[i + 1] - sums[left + 1];
            res = Math.max(res, harvest);
        }
        return res;
    }

    // 找出 fruits 中 <= pos 的最大下标
    private int lessOrEquals(int[][] fruits, int pos) {
        int start = -1, end = fruits.length - 1;
        while (start < end) {
            int mid = (start + end + 1) / 2;
            if (fruits[mid][0] <= pos) {
                start = mid;
            } else {
                end = mid - 1;
            }
        }
        return start;
    }

    // 参考 leetcode 上比较快的思路, 时间复杂度 O(N)
    @Answer
    public int maxTotalFruits2(int[][] fruits, int startPos, int k) {
        final int n = fruits.length;
        // 边界情况
        if (startPos <= fruits[0][0]) {
            // 起始点在最左边
            int res = 0;
            for (int i = 0; i < n && fruits[i][0] - startPos <= k; i++) {
                res += fruits[i][1];
            }
            return res;
        }
        if (fruits[n - 1][0] <= startPos) {
            // 起始点在最右边
            int res = 0;
            for (int i = n - 1; i >= 0 && startPos - fruits[i][0] <= k; i--) {
                res += fruits[i][1];
            }
            return res;
        }

        // 找到最左可走到的位置
        int left = 0;
        while (startPos - fruits[left][0] > k) {
            left++;
        }
        // 将right 设置为 startPos 的右边一位 ( >= startPos)
        int right = left;
        int sum = 0;
        while (fruits[right][0] <= startPos) {
            sum += fruits[right][1];
            right++;
        }

        int res = sum;
        // 先往左再折返往右
        while (fruits[left][0] <= startPos && right < n) {
            int rightPos = fruits[right][0];
            int leftPos = fruits[left][0];
            int dist = (rightPos - leftPos) + (startPos - leftPos);
            if (dist > k) {
                sum -= fruits[left][1];
                left++;
            } else {
                sum += fruits[right][1];
                right++;
            }
            res = Math.max(res, sum);
        }

        // left 和 right 设置到只往左走的位置
        // left 跑到 startPos 左边 ( 跑到 sum 覆盖的区域外 )
        left--;
        // right 走到可达的最右边
        while (right < n && fruits[right][0] <= startPos + k) {
            sum += fruits[right][1];
            right++;
        }
        right--;
        res = Math.max(res, sum);

        // 先往右再折返往左
        while (fruits[right][0] >= startPos && left >= 0) {
            int rightPos = fruits[right][0];
            int leftPos = fruits[left][0];
            int dist = (rightPos - leftPos) + (rightPos - startPos);
            if (dist > k) {
                sum -= fruits[right][1];
                right--;
            } else {
                sum += fruits[left][1];
                left--;
            }
            res = Math.max(res, sum);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{2, 8}, {6, 3}, {8, 6}}, 5, 4)
            .expect(9);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{0, 9}, {4, 1}, {5, 7}, {6, 2}, {7, 4}, {10, 9}}, 5, 4)
            .expect(14);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[][]{{0, 3}, {6, 4}, {8, 5}}, 3, 2)
            .expect(0);

}
