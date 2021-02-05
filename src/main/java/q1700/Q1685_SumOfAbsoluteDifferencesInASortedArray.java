package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1685. Sum of Absolute Differences in a Sorted Array
 * https://leetcode.com/problems/sum-of-absolute-differences-in-a-sorted-array/
 *
 * You are given an integer array nums sorted in non-decreasing order.
 *
 * Build and return an integer array result with the same length as nums such that result[i] is equal to the summation
 * of absolute differences between nums[i] and all the other elements in the array.
 *
 * In other words, result[i] is equal to sum(|nums[i]-nums[j]|) where 0 <= j < nums.length and j != i (0-indexed).
 *
 * Example 1:
 *
 * Input: nums = [2,3,5]
 * Output: [4,3,5]
 * Explanation: Assuming the arrays are 0-indexed, then
 * result[0] = |2-2| + |2-3| + |2-5| = 0 + 1 + 3 = 4,
 * result[1] = |3-2| + |3-3| + |3-5| = 1 + 0 + 2 = 3,
 * result[2] = |5-2| + |5-3| + |5-5| = 3 + 2 + 0 = 5.
 *
 * Example 2:
 *
 * Input: nums = [1,4,6,8,10]
 * Output: [24,15,13,15,21]
 *
 * Constraints:
 *
 * 2 <= nums.length <= 10^5
 * 1 <= nums[i] <= nums[i + 1] <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1685_SumOfAbsoluteDifferencesInASortedArray {

    @Answer
    public int[] getSumAbsoluteDifferences(int[] nums) {
        final int n = nums.length;
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            int left = nums[i] * i - sums[i];
            int right = sums[n] - sums[i + 1] - (n - 1 - i) * nums[i];
            res[i] = left + right;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{2, 3, 5})
            .expect(new int[]{4, 3, 5});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{1, 4, 6, 8, 10})
            .expect(new int[]{24, 15, 13, 15, 21});

}
