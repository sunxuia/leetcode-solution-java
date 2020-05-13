package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/longest-continuous-increasing-subsequence/
 *
 * Given an unsorted array of integers, find the length of longest continuous increasing subsequence (subarray).
 *
 * Example 1:
 *
 * Input: [1,3,5,4,7]
 * Output: 3
 * Explanation: The longest continuous increasing subsequence is [1,3,5], its length is 3.
 * Even though [1,3,5,7] is also an increasing subsequence, it's not a continuous one where 5 and 7 are separated by 4.
 *
 * Example 2:
 *
 * Input: [2,2,2,2,2]
 * Output: 1
 * Explanation: The longest continuous increasing subsequence is [2], its length is 1.
 *
 * Note: Length of the array will not exceed 10,000.
 */
@RunWith(LeetCodeRunner.class)
public class Q674_LongestContinuousIncreasingSubsequence {

    @Answer
    public int findLengthOfLCIS(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }
        int res = 0, count = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] >= nums[i]) {
                res = Math.max(res, count);
                count = 1;
            } else {
                count++;
            }
        }
        return Math.max(res, count);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 3, 5, 4, 7}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 2, 2, 2, 2}).expect(1);

    @TestData
    public DataExpectation border0 = DataExpectation.create(new int[0]).expect(0);

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[]{1}).expect(1);

}
