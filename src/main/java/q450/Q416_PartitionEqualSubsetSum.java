package q450;

import org.junit.runner.RunWith;
import q700.Q698_PartitionToKEqualSumSubsets;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/partition-equal-subset-sum
 *
 * Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets
 * such that the sum of elements in both subsets is equal.
 *
 * Note:
 *
 * Each of the array element will not exceed 100.
 * The array size will not exceed 200.
 *
 *
 *
 * Example 1:
 *
 * Input: [1, 5, 11, 5]
 *
 * Output: true
 *
 * Explanation: The array can be partitioned as [1, 5, 5] and [11].
 *
 *
 *
 * Example 2:
 *
 * Input: [1, 2, 3, 5]
 *
 * Output: false
 *
 * Explanation: The array cannot be partitioned into equal sum subsets.
 */
@RunWith(LeetCodeRunner.class)
public class Q416_PartitionEqualSubsetSum {

    /**
     * 通常的遍历方式会超时,
     * https://www.cnblogs.com/grandyang/p/5951422.html
     * 中提供了一种dp 的方式.
     *
     * 类似题目参见 {@link Q698_PartitionToKEqualSumSubsets}, 将2 份扩展为多份
     */
    @Answer
    public boolean canPartition(int[] nums) {
        // 平分nums 则两边之和就分别是nums 的元素之和(偶数) 除以2.
        // 这样就将问题转化为能否找出和为target 的元素子集.
        int target = getTarget(nums);
        if (target == 0) {
            return false;
        }

        // dp[i] 表示可以从nums 中取出若干数字, 其和为i.
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        // 按照顺序取出num 中的数字, 组成nums 的子集.
        for (int num : nums) {
            // 计算这个子集中, 是否有和为i 的子集.
            // 如果dp[j] 可以组合出和为j 的结果, 那么 dp[j+num] 就能组合出和为j+num 的结果.
            for (int i = target; i >= num; i--) {
                dp[i] = dp[i] || dp[i - num];
            }
        }
        return dp[target];
    }

    private int getTarget(int[] nums) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        if (total % 2 == 1) {
            return 0;
        }
        return total / 2;
    }

    /**
     * LeetCode 中最快的解法. 通过dfs 来求target, 但是加入了一个判断, 避免了超时的情况.
     */
    @Answer
    public boolean canPartition2(int[] nums) {
        int target = getTarget(nums);
        if (target == 0) {
            return false;
        }
        return dfs(nums, 0, target);
    }

    private boolean dfs(int[] nums, int start, int target) {
        if (target <= 0) {
            return target == 0;
        }
        for (int i = start; i < nums.length; i++) {
            // 通过这个 nums[i] != nums[i - 1] 判断来避免测试用例中的大量重复数字造成的超时情况
            if ((i == start || nums[i] != nums[i - 1])
                    && dfs(nums, i + 1, target - nums[i])) {
                return true;
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 5, 11, 5}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 3, 5}).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 2, 3, 4, 5, 6, 7}).expect(true);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{1, 2, 5}).expect(false);

    @TestData
    public DataExpectation overTime = DataExpectation.create(new int[]{
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100
    }).expect(false);

}
