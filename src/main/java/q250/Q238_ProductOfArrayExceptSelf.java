package q250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/product-of-array-except-self/
 *
 * Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to the product
 * of all the elements of nums except nums[i].
 *
 * Example:
 *
 * Input:  [1,2,3,4]
 * Output: [24,12,8,6]
 *
 * Note: Please solve it without division and in O(n).
 *
 * Follow up:
 * Could you solve it with constant space complexity? (The output array does not count as extra space for the purpose
 * of space complexity analysis.)
 *
 * 题解:
 * 题目要求不能使用除法, 时间复杂度 O(n), 除了返回结果外空间占用为O(1).
 */
@RunWith(LeetCodeRunner.class)
public class Q238_ProductOfArrayExceptSelf {

    // 从前往后乘一遍, 再从后往前乘一遍就好了
    @Answer
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        res[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }
        for (int i = nums.length - 2, after = 1; i >= 0; i--) {
            res[i] *= after *= nums[i + 1];
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{1, 2, 3, 4}).expect(new int[]{24, 12, 8, 6});

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[]{0, 0}).expect(new int[]{0, 0});

    @TestData
    public DataExpectation border2 = DataExpectation.create(new int[]{Integer.MAX_VALUE / 3, 3, 2})
            .expect(new int[]{6, Integer.MAX_VALUE / 3 * 2, Integer.MAX_VALUE / 3 * 3});
}
