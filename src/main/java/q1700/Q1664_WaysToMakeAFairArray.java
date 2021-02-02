package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1664. Ways to Make a Fair Array
 * https://leetcode.com/problems/ways-to-make-a-fair-array/
 *
 * You are given an integer array nums. You can choose exactly one index (0-indexed) and remove the element. Notice that
 * the index of the elements may change after the removal.
 *
 * For example, if nums = [6,1,7,4,1]:
 *
 * Choosing to remove index 1 results in nums = [6,7,4,1].
 * Choosing to remove index 2 results in nums = [6,1,4,1].
 * Choosing to remove index 4 results in nums = [6,1,7,4].
 *
 * An array is fair if the sum of the odd-indexed values equals the sum of the even-indexed values.
 *
 * Return the number of indices that you could choose such that after the removal, nums is fair.
 *
 * Example 1:
 *
 * Input: nums = [2,1,6,4]
 * Output: 1
 * Explanation:
 * Remove index 0: [1,6,4] -> Even sum: 1 + 4 = 5. Odd sum: 6. Not fair.
 * Remove index 1: [2,6,4] -> Even sum: 2 + 4 = 6. Odd sum: 6. Fair.
 * Remove index 2: [2,1,4] -> Even sum: 2 + 4 = 6. Odd sum: 1. Not fair.
 * Remove index 3: [2,1,6] -> Even sum: 2 + 6 = 8. Odd sum: 1. Not fair.
 * There is 1 index that you can remove to make nums fair.
 *
 * Example 2:
 *
 * Input: nums = [1,1,1]
 * Output: 3
 * Explanation: You can remove any index and the remaining array is fair.
 *
 * Example 3:
 *
 * Input: nums = [1,2,3]
 * Output: 0
 * Explanation: You cannot make a fair array after removing any index.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1664_WaysToMakeAFairArray {

    @Answer
    public int waysToMakeFair(int[] nums) {
        final int n = nums.length;
        int[] evenSums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            evenSums[i + 1] = evenSums[i] + (1 - i % 2) * nums[i];
        }
        int[] oddSums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            oddSums[i + 1] = oddSums[i] + i % 2 * nums[i];
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            int even = evenSums[i] + oddSums[n] - oddSums[i + 1];
            int odd = oddSums[i] + evenSums[n] - evenSums[i + 1];
            if (even == odd) {
                res++;
            }
        }
        return res;
    }

    /**
     * LeetCode 上比较快的解法.
     * 通过滚动的方式替换上面的累加.
     */
    @Answer
    public int waysToMakeFair2(int[] nums) {
        final int n = nums.length;
        int[] left = new int[2], right = new int[2];
        for (int i = 0; i < n; i++) {
            right[i % 2] += nums[i];
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            right[i % 2] -= nums[i];
            if (left[0] + right[1] == left[1] + right[0]) {
                res++;
            }
            left[i % 2] += nums[i];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 1, 6, 4}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 1}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3}).expect(0);

}
