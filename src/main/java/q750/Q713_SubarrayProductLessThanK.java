package q750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/subarray-product-less-than-k/
 *
 * Your are given an array of positive integers nums.
 *
 * Count and print the number of (contiguous) subarrays where the product of all the elements in the subarray is less
 * than k.
 *
 * Example 1:
 *
 * Input: nums = [10, 5, 2, 6], k = 100
 * Output: 8
 * Explanation: The 8 subarrays that have product less than 100 are: [10], [5], [2], [6], [10, 5], [5, 2], [2, 6],
 * [5, 2, 6].
 * Note that [10, 5, 2] is not included as the product of 100 is not strictly less than k.
 *
 * Note:
 * 0 < nums.length <= 50000.
 * 0 < nums[i] < 1000.
 * 0 <= k < 10^6.
 */
@RunWith(LeetCodeRunner.class)
public class Q713_SubarrayProductLessThanK {

    @Answer
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int res = 0, product = 1, start = 0;
        for (int i = 0; i < nums.length; i++) {
            product *= nums[i];
            while (start <= i && product >= k) {
                product /= nums[start++];
            }
            res += i - start + 1;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[]{10, 5, 2, 6}, 100).expect(8);

    @TestData
    public DataExpectation border0 = DataExpectation.createWith(new int[]{10, 5, 2, 6}, 0).expect(0);

    @TestData
    public DataExpectation border1 = DataExpectation.createWith(new int[]{1}, 1).expect(0);

}
