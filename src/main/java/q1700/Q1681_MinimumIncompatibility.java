package q1700;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1681. Minimum Incompatibility
 * https://leetcode.com/problems/minimum-incompatibility/
 *
 * You are given an integer array nums and an integer k. You are asked to distribute this array into k subsets of
 * equal size such that there are no two equal elements in the same subset.
 *
 * A subset's incompatibility is the difference between the maximum and minimum elements in that array.
 *
 * Return the minimum possible sum of incompatibilities of the k subsets after distributing the array optimally, or
 * return -1 if it is not possible.
 *
 * A subset is a group integers that appear in the array with no particular order.
 *
 * Example 1:
 *
 * Input: nums = [1,2,1,4], k = 2
 * Output: 4
 * Explanation: The optimal distribution of subsets is [1,2] and [1,4].
 * The incompatibility is (2-1) + (4-1) = 4.
 * Note that [1,1] and [2,4] would result in a smaller sum, but the first subset contains 2 equal elements.
 *
 * Example 2:
 *
 * Input: nums = [6,3,8,1,3,1,2,2], k = 4
 * Output: 6
 * Explanation: The optimal distribution of subsets is [1,2], [2,3], [6,8], and [1,3].
 * The incompatibility is (2-1) + (3-2) + (8-6) + (3-1) = 6.
 *
 * Example 3:
 *
 * Input: nums = [5,3,3,6,3,3], k = 3
 * Output: -1
 * Explanation: It is impossible to distribute nums into 3 subsets where no two elements are equal in the same subset.
 *
 * Constraints:
 *
 * 1 <= k <= nums.length <= 16
 * nums.length is divisible by k
 * 1 <= nums[i] <= nums.length
 */
@RunWith(LeetCodeRunner.class)
public class Q1681_MinimumIncompatibility {

    /**
     * 状态压缩 dp
     * 这里需要将 nums 全部分配到 k 组中, 因此使用 mask 表示已经成功分配的 nums 元素, dp[mask] 就表示此时最小的和,
     * 当 mask == 1 << n 时(即全部为1) 则满足题意.
     *
     * 参考文档
     * https://leetcode-cn.com/problems/minimum-incompatibility/solution/zui-xiao-bu-jian-rong-xing-by-zerotrac2-rwje/
     */
    @Answer
    public int minimumIncompatibility(int[] nums, int k) {
        final int n = nums.length, len = 1 << n;
        Arrays.sort(nums);

        // 保存有效的数字组合和不兼容值
        List<int[]> availables = new ArrayList<>();
        for (int mask = 1; mask < len; mask++) {
            int ones = 0, max = 0, min = n, prev = 0;
            for (int i = 0; i < n; i++) {
                if ((mask >> i & 1) == 1) {
                    if (prev == nums[i]) {
                        ones = -1;
                        break;
                    }
                    ones++;
                    prev = max = nums[i];
                    min = Math.min(min, nums[i]);
                }
            }
            if (ones == n / k) {
                availables.add(new int[]{mask, max - min});
            }
        }

        // 从少到多遍历可能的组合.
        int[] dp = new int[len];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int mask = 0; mask < len; mask++) {
            if (dp[mask] == Integer.MAX_VALUE) {
                // 不存在这种组合
                continue;
            }
            for (int[] available : availables) {
                int sub = available[0];
                if ((mask & sub) == 0) {
                    // mask 与 subMask 没有重叠, 可以组合
                    dp[mask | sub] = Math.min(dp[mask | sub], dp[mask] + available[1]);
                }
            }
        }
        return dp[len - 1] == Integer.MAX_VALUE ? -1 : dp[len - 1];
    }


    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 1, 4}, 2)
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{6, 3, 8, 1, 3, 1, 2, 2}, 4)
            .expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{5, 3, 3, 6, 3, 3}, 3)
            .expect(-1);

}
