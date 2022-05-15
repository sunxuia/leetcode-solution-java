package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1913. Maximum Product Difference Between Two Pairs
 * https://leetcode.com/problems/maximum-product-difference-between-two-pairs/
 *
 * The product difference between two pairs (a, b) and (c, d) is defined as (a * b) - (c * d).
 *
 * For example, the product difference between (5, 6) and (2, 7) is (5 * 6) - (2 * 7) = 16.
 *
 * Given an integer array nums, choose four distinct indices w, x, y, and z such that the product difference between
 * pairs (nums[w], nums[x]) and (nums[y], nums[z]) is maximized.
 *
 * Return the maximum such product difference.
 *
 * Example 1:
 *
 * Input: nums = [5,6,2,7,4]
 * Output: 34
 * Explanation: We can choose indices 1 and 3 for the first pair (6, 7) and indices 2 and 4 for the second pair (2, 4).
 * The product difference is (6 * 7) - (2 * 4) = 34.
 *
 * Example 2:
 *
 * Input: nums = [4,2,5,9,7,4,8]
 * Output: 64
 * Explanation: We can choose indices 3 and 6 for the first pair (9, 8) and indices 1 and 5 for the second pair (2, 4).
 * The product difference is (9 * 8) - (2 * 4) = 64.
 *
 * Constraints:
 *
 * 4 <= nums.length <= 10^4
 * 1 <= nums[i] <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1913_MaximumProductDifferenceBetweenTwoPairs {

    @Answer
    public int maxProductDifference(int[] nums) {
        int max1 = 0, max2 = 0;
        int min1 = 10000, min2 = 10000;
        for (int num : nums) {
            if (max1 < num) {
                max2 = max1;
                max1 = num;
            } else if (max2 < num) {
                max2 = num;
            }
            if (min1 > num) {
                min2 = min1;
                min1 = num;
            } else if (min2 > num) {
                min2 = num;
            }
        }
        return max1 * max2 - min1 * min2;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{5, 6, 2, 7, 4}).expect(34);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{4, 2, 5, 9, 7, 4, 8}).expect(64);

}
