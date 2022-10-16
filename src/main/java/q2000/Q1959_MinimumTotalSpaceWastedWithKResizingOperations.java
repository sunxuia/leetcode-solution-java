package q2000;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1959. Minimum Total Space Wasted With K Resizing Operations
 * https://leetcode.com/problems/minimum-total-space-wasted-with-k-resizing-operations/
 *
 * You are currently designing a dynamic array. You are given a 0-indexed integer array nums, where nums[i] is the
 * number of elements that will be in the array at time i. In addition, you are given an integer k, the maximum number
 * of times you can resize the array (to any size).
 *
 * The size of the array at time t, sizet, must be at least nums[t] because there needs to be enough space in the array
 * to hold all the elements. The space wasted at time t is defined as sizet - nums[t], and the total space wasted is the
 * sum of the space wasted across every time t where 0 <= t < nums.length.
 *
 * Return the minimum total space wasted if you can resize the array at most k times.
 *
 * Note: The array can have any size at the start and does not count towards the number of resizing operations.
 *
 * Example 1:
 *
 * Input: nums = [10,20], k = 0
 * Output: 10
 * Explanation: size = [20,20].
 * We can set the initial size to be 20.
 * The total wasted space is (20 - 10) + (20 - 20) = 10.
 *
 * Example 2:
 *
 * Input: nums = [10,20,30], k = 1
 * Output: 10
 * Explanation: size = [20,20,30].
 * We can set the initial size to be 20 and resize to 30 at time 2.
 * The total wasted space is (20 - 10) + (20 - 20) + (30 - 30) = 10.
 *
 * Example 3:
 *
 * Input: nums = [10,20,15,30,20], k = 2
 * Output: 15
 * Explanation: size = [10,20,20,30,30].
 * We can set the initial size to 10, resize to 20 at time 1, and resize to 30 at time 3.
 * The total wasted space is (10 - 10) + (20 - 20) + (20 - 15) + (30 - 30) + (30 - 20) = 15.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 200
 * 1 <= nums[i] <= 10^6
 * 0 <= k <= nums.length - 1
 *
 * 题解: 题目意思比较怪, 翻译过来就是:
 * 初始选择1个数字 v, 然后从前往后遍历 nums, 遍历过程必须满足 v >= nums[i], 过程中还有k 次机会调整v 的大小.
 * 现在要求这个过程中 ∑(v-nums[i]) 的最小值.
 */
@RunWith(LeetCodeRunner.class)
public class Q1959_MinimumTotalSpaceWastedWithKResizingOperations {

    @Answer
    public int minSpaceWastedKResizing(int[] nums, int k) {
        int[] levels = Arrays.stream(nums).distinct().sorted().toArray();
        final int len = levels.length;
        final int MAX = Integer.MAX_VALUE / 2;
        // dp[kr][lv] 表示遍历到进行到 nums[i] 的时候, 已经变换kr 次, 当前值 v=lv, 后的 ∑(v-nums[i]) 的最小值.
        int[][] dp = new int[k + 1][len];
        for (int i = 0; i <= k; i++) {
            Arrays.fill(dp[i], MAX);
        }
        // 初始化 nums[0], 可以自由决定lv, 变换次数为0
        for (int lv = 0; lv < len; lv++) {
            if (levels[lv] >= nums[0]) {
                dp[0][lv] = levels[lv] - nums[0];
            }
        }

        for (int i = 1; i < nums.length; i++) {
            for (int kr = k; kr > 0; kr--) {
                // kr-1 次变换的最小值
                int minWasted = MAX;
                for (int lv = 0; lv < len; lv++) {
                    minWasted = Math.min(minWasted, dp[kr - 1][lv]);
                }

                for (int lv = 0; lv < len; lv++) {
                    if (levels[lv] < nums[i]) {
                        dp[kr][lv] = MAX;
                    } else {
                        // lv 不变, 或者由上一次变换变为lv
                        dp[kr][lv] = Math.min(dp[kr][lv], minWasted) + levels[lv] - nums[i];
                    }
                }
            }
            // kr==0
            for (int lv = 0; lv < len; lv++) {
                if (levels[lv] < nums[i]) {
                    dp[0][lv] = MAX;
                } else {
                    // 0 次只能不变
                    dp[0][lv] = dp[0][lv] + levels[lv] - nums[i];
                }
            }
        }

        int res = MAX;
        for (int kr = 0; kr <= k; kr++) {
            for (int lv = 0; lv < len; lv++) {
                res = Math.min(res, dp[kr][lv]);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{10, 20}, 0).expect(10);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{10, 20, 30}, 1).expect(10);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{10, 20, 15, 30, 20}, 2).expect(15);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{38, 28, 3, 2, 6, 14, 15, 33, 39}, 1).expect(165);

}
