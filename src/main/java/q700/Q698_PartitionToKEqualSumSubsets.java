package q700;

import java.util.Arrays;
import org.junit.runner.RunWith;
import q450.Q416_PartitionEqualSubsetSum;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
 *
 * Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into k
 * non-empty subsets whose sums are all equal.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [4, 3, 2, 3, 5, 2, 1], k = 4
 * Output: True
 * Explanation: It's possible to divide it into 4 subsets (5), (1, 4), (2,3), (2,3) with equal sums.
 *
 *
 *
 * Note:
 *
 * 1 <= k <= len(nums) <= 16.
 * 0 < nums[i] < 10000.
 */
@RunWith(LeetCodeRunner.class)
public class Q698_PartitionToKEqualSumSubsets {

    /**
     * 这题类似 {@link Q416_PartitionEqualSubsetSum}, Q416 是分为2 份, 这题是分为k 份
     * 解答参考 https://www.cnblogs.com/grandyang/p/7733098.html
     */
    @Answer
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        if (total % k != 0) {
            return false;
        }
        return recursion(nums, k, total / k, 0, 0, new boolean[nums.length]);
    }

    boolean recursion(int[] nums, int k, int target, int start, int curSum, boolean[] visited) {
        // 已经全部匹配完成
        if (k == 1) {
            return true;
        }
        // 当前匹配完成, 开始下一个匹配
        if (curSum == target) {
            return recursion(nums, k - 1, target, 0, 0, visited);
        }
        // 从当前下标开始尝试匹配结果
        for (int i = start; i < nums.length; ++i) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            if (recursion(nums, k, target, i + 1, curSum + nums[i], visited)) {
                return true;
            }
            visited[i] = false;
        }
        return false;
    }

    /**
     * Solution 中给出的 DP 解法.
     * 解法比较类似 {@link Q691_StickersToSpellWord}
     */
    @Answer
    public boolean canPartitionKSubsets2(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        int sum = Arrays.stream(nums).sum();
        int target = sum / k;
        if (sum % k > 0 || nums[n - 1] > target) {
            return false;
        }

        boolean[] dp = new boolean[1 << n];
        dp[0] = true;
        int[] total = new int[1 << n];

        for (int state = 0; state < (1 << n); state++) {
            if (!dp[state]) {
                continue;
            }
            for (int i = 0; i < n; i++) {
                int future = state | (1 << i);
                if (state != future && !dp[future]) {
                    if (nums[i] <= target - (total[state] % target)) {
                        dp[future] = true;
                        total[future] = total[state] + nums[i];
                    } else {
                        break;
                    }
                }
            }
        }
        return dp[(1 << n) - 1];
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[]{4, 3, 2, 3, 5, 2, 1}, 4).expect(true);

}
