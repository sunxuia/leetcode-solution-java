package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1793. Maximum Score of a Good Subarray
 * https://leetcode.com/problems/maximum-score-of-a-good-subarray/
 *
 * You are given an array of integers nums (0-indexed) and an integer k.
 *
 * The score of a subarray (i, j) is defined as min(nums[i], nums[i+1], ..., nums[j]) * (j - i + 1). A good subarray is
 * a subarray where i <= k <= j.
 *
 * Return the maximum possible score of a good subarray.
 *
 * Example 1:
 *
 * Input: nums = [1,4,3,7,4,5], k = 3
 * Output: 15
 * Explanation: The optimal subarray is (1, 5) with a score of min(4,3,7,4,5) * (5-1+1) = 3 * 5 = 15.
 *
 * Example 2:
 *
 * Input: nums = [5,5,4,5,4,1,1,1], k = 0
 * Output: 20
 * Explanation: The optimal subarray is (0, 4) with a score of min(5,5,4,5,4) * (4-0+1) = 4 * 5 = 20.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 2 * 10^4
 * 0 <= k < nums.length
 */
@RunWith(LeetCodeRunner.class)
public class Q1793_MaximumScoreOfAGoodSubarray {

    @Answer
    public int maximumScore(int[] nums, int k) {
        final int n = nums.length;
        int res = 0, min = nums[k];
        int i = k, j = k;
        while (0 <= i && j < n) {
            min = Math.min(min, Math.min(nums[i], nums[j]));
            res = Math.max(res, (j - i + 1) * min);
            if (i == 0 || j < n - 1 && nums[i - 1] < nums[j + 1]) {
                j++;
            } else {
                i--;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 4, 3, 7, 4, 5}, 3).expect(15);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{5, 5, 4, 5, 4, 1, 1, 1}, 0).expect(20);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{6569, 9667, 3148, 7698, 1622, 2194, 793, 9041, 1670, 1872}, 5).expect(9732);

}
