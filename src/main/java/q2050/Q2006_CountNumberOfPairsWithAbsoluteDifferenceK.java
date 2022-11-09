package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 2006. Count Number of Pairs With Absolute Difference K
 * https://leetcode.com/problems/count-number-of-pairs-with-absolute-difference-k/
 *
 * Given an integer array nums and an integer k, return the number of pairs (i, j) where i < j such that |nums[i] -
 * nums[j]| == k.
 *
 * The value of |x| is defined as:
 *
 * x if x >= 0.
 * -x if x < 0.
 *
 * Example 1:
 *
 * Input: nums = [1,2,2,1], k = 1
 * Output: 4
 * Explanation: The pairs with an absolute difference of 1 are:
 * - [1,2,2,1]
 * - [1,2,2,1]
 * - [1,2,2,1]
 * - [1,2,2,1]
 *
 * Example 2:
 *
 * Input: nums = [1,3], k = 3
 * Output: 0
 * Explanation: There are no pairs with an absolute difference of 3.
 *
 * Example 3:
 *
 * Input: nums = [3,2,1,5,4], k = 2
 * Output: 3
 * Explanation: The pairs with an absolute difference of 2 are:
 * - [3,2,1,5,4]
 * - [3,2,1,5,4]
 * - [3,2,1,5,4]
 *
 * Constraints:
 *
 * 1 <= nums.length <= 200
 * 1 <= nums[i] <= 100
 * 1 <= k <= 99
 */
@RunWith(LeetCodeRunner.class)
public class Q2006_CountNumberOfPairsWithAbsoluteDifferenceK {

    @Answer
    public int countKDifference(int[] nums, int k) {
        int[] buckets = new int[101];
        for (int num : nums) {
            buckets[num]++;
        }
        int res = 0;
        for (int i = 0; i + k <= 100; i++) {
            res += buckets[i] * buckets[i + k];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2, 2, 1}, 1).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 3}, 3).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{3, 2, 1, 5, 4}, 2).expect(3);

}
