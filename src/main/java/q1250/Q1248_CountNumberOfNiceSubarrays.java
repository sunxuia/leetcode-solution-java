package q1250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1248. Count Number of Nice Subarrays
 * https://leetcode.com/problems/count-number-of-nice-subarrays/
 *
 * Given an array of integers nums and an integer k. A continuous subarray is called nice if there are k odd numbers on
 * it.
 *
 * Return the number of nice sub-arrays.
 *
 * Example 1:
 *
 * Input: nums = [1,1,2,1,1], k = 3
 * Output: 2
 * Explanation: The only sub-arrays with 3 odd numbers are [1,1,2,1] and [1,2,1,1].
 *
 * Example 2:
 *
 * Input: nums = [2,4,6], k = 1
 * Output: 0
 * Explanation: There is no odd numbers in the array.
 *
 * Example 3:
 *
 * Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
 * Output: 16
 *
 * Constraints:
 *
 * 1 <= nums.length <= 50000
 * 1 <= nums[i] <= 10^5
 * 1 <= k <= nums.length
 */
@RunWith(LeetCodeRunner.class)
public class Q1248_CountNumberOfNiceSubarrays {

    @Answer
    public int numberOfSubarrays(int[] nums, int k) {
        final int n = nums.length;
        int res = 0, odd = 0, left = -1, right = -1;
        while (right + 1 < n && odd < k) {
            odd += nums[++right] % 2;
        }
        if (odd < k) {
            return 0;
        }
        while (right < n) {
            int farLeft = left;
            while (nums[++left] % 2 == 0) {
            }
            int farRight = right + 1;
            while (farRight < n && nums[farRight] % 2 == 0) {
                farRight++;
            }
            res += (left - farLeft) * (farRight - right);
            right = farRight;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 1, 2, 1, 1}, 3).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{2, 4, 6}, 1).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{2, 2, 2, 1, 2, 2, 1, 2, 2, 2}, 2).expect(16);

}
