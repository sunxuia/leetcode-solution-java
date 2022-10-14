package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1955. Count Number of Special Subsequences
 * https://leetcode.com/problems/count-number-of-special-subsequences/
 *
 * A sequence is special if it consists of a positive number of 0s, followed by a positive number of 1s, then a positive
 * number of 2s.
 *
 * For example, [0,1,2] and [0,0,1,1,1,2] are special.
 * In contrast, [2,1,0], [1], and [0,1,2,0] are not special.
 *
 * Given an array nums (consisting of only integers 0, 1, and 2), return the number of different subsequences that are
 * special. Since the answer may be very large, return it modulo 10^9 + 7.
 *
 * A subsequence of an array is a sequence that can be derived from the array by deleting some or no elements without
 * changing the order of the remaining elements. Two subsequences are different if the set of indices chosen are
 * different.
 *
 * Example 1:
 *
 * Input: nums = [0,1,2,2]
 * Output: 3
 * Explanation: The special subsequences are bolded [0,1,2,2], [0,1,2,2], and [0,1,2,2].
 *
 * Example 2:
 *
 * Input: nums = [2,2,0,0]
 * Output: 0
 * Explanation: There are no special subsequences in [2,2,0,0].
 *
 * Example 3:
 *
 * Input: nums = [0,1,2,0,1,2]
 * Output: 7
 * Explanation: The special subsequences are bolded:
 * - [0,1,2,0,1,2]
 * - [0,1,2,0,1,2]
 * - [0,1,2,0,1,2]
 * - [0,1,2,0,1,2]
 * - [0,1,2,0,1,2]
 * - [0,1,2,0,1,2]
 * - [0,1,2,0,1,2]
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 2
 */
@RunWith(LeetCodeRunner.class)
public class Q1955_CountNumberOfSpecialSubsequences {

    @Answer
    public int countSpecialSubsequences(int[] nums) {
        final int mod = 10_0000_0007;
        int[] dp = new int[4];
        dp[0] = 1;
        for (int num : nums) {
            dp[num + 1] = (dp[num + 1] * 2 % mod + dp[num]) % mod;
        }
        return dp[3] % mod;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{0, 1, 2, 2}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 2, 0, 0}).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{0, 1, 2, 0, 1, 2}).expect(7);

}
