package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Easy] 1822. Sign of the Product of an Array
 * https://leetcode.com/problems/sign-of-the-product-of-an-array/
 *
 * There is a function signFunc(x) that returns:
 *
 * 1 if x is positive.
 * -1 if x is negative.
 * 0 if x is equal to 0.
 *
 * You are given an integer array nums. Let product be the product of all values in the array nums.
 *
 * Return signFunc(product).
 *
 * Example 1:
 *
 * Input: nums = [-1,-2,-3,-4,3,2,1]
 * Output: 1
 * Explanation: The product of all values in the array is 144, and signFunc(144) = 1
 *
 * Example 2:
 *
 * Input: nums = [1,5,0,2,-3]
 * Output: 0
 * Explanation: The product of all values in the array is 0, and signFunc(0) = 0
 *
 * Example 3:
 *
 * Input: nums = [-1,1,-1,1,-1]
 * Output: -1
 * Explanation: The product of all values in the array is -1, and signFunc(-1) = -1
 *
 * Constraints:
 *
 * 1 <= nums.length <= 1000
 * -100 <= nums[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1822_SignOfTheProductOfAnArray {

    @Answer
    public int arraySign(int[] nums) {
        int sign = 1;
        for (int num : nums) {
            if (num == 0) {
                return 0;
            } else if (num < 0) {
                sign = -sign;
            }
        }
        return sign > 0 ? 1 : -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{-1, -2, -3, -4, 3, 2, 1}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{1, 5, 0, 2, -3}).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[]{-1, 1, -1, 1, -1}).expect(-1);

}
