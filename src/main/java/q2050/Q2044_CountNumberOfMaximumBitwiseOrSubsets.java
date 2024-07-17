package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 2044. Count Number of Maximum Bitwise-OR Subsets
 * https://leetcode.com/problems/count-number-of-maximum-bitwise-or-subsets/
 *
 * Given an integer array nums, find the maximum possible bitwise OR of a subset of nums and return the number of
 * different non-empty subsets with the maximum bitwise OR.
 *
 * An array a is a subset of an array b if a can be obtained from b by deleting some (possibly zero) elements of b. Two
 * subsets are considered different if the indices of the elements chosen are different.
 *
 * The bitwise OR of an array a is equal to a[0] OR a[1] OR ... OR a[a.length - 1] (0-indexed).
 *
 * Example 1:
 *
 * Input: nums = [3,1]
 * Output: 2
 * Explanation: The maximum possible bitwise OR of a subset is 3. There are 2 subsets with a bitwise OR of 3:
 * - [3]
 * - [3,1]
 *
 * Example 2:
 *
 * Input: nums = [2,2,2]
 * Output: 7
 * Explanation: All non-empty subsets of [2,2,2] have a bitwise OR of 2. There are 2^3 - 1 = 7 total subsets.
 *
 * Example 3:
 *
 * Input: nums = [3,2,1,5]
 * Output: 6
 * Explanation: The maximum possible bitwise OR of a subset is 7. There are 6 subsets with a bitwise OR of 7:
 * - [3,5]
 * - [3,1,5]
 * - [3,2,5]
 * - [3,2,1,5]
 * - [2,5]
 * - [2,1,5]
 *
 * Constraints:
 *
 * 1 <= nums.length <= 16
 * 1 <= nums[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q2044_CountNumberOfMaximumBitwiseOrSubsets {

    @Answer
    public int countMaxOrSubsets(int[] nums) {
        max = 0;
        maxMask = 0;
        dfs(nums, 0, 0);
        return max;
    }

    private int max, maxMask;

    private void dfs(int[] nums, int i, int mask) {
        if (i == nums.length) {
            if (mask > maxMask) {
                maxMask = mask;
                max = 1;
            } else if (mask == maxMask) {
                max++;
            }
        } else {
            dfs(nums, i + 1, mask);
            dfs(nums, i + 1, mask | nums[i]);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 1}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 2, 2}).expect(7);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{3, 2, 1, 5}).expect(6);

}
