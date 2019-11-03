package q200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/maximum-product-subarray/
 *
 * Given an integer array nums, find the contiguous subarray within an array (containing at least one number) which
 * has the largest product.
 *
 * Example 1:
 *
 * Input: [2,3,-2,4]
 * Output: 6
 * Explanation: [2,3] has the largest product 6.
 *
 * Example 2:
 *
 * Input: [-2,0,-1]
 * Output: 0
 * Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 */
@RunWith(LeetCodeRunner.class)
public class Q152_MaximumProductSubarray {

    @Answer
    public int maxProduct(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int res = nums[0], max = res, min = res;
        for (int i = 1; i < nums.length; i++) {
            final int num = nums[i];
            if (num < 0) {
                int t = max;
                max = min;
                min = t;
            }
            max = Math.max(num, max * num);
            min = Math.min(num, min * num);
            res = Math.max(res, max);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 3, -2, 4}).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{-2, 0, -1}).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{-1}).expect(-1);

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[0]).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{-2, 0, -1, 0}).expect(0);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{3, -1, 4}).expect(4);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new int[]{3, -1, 4, 2}).expect(8);

    @TestData
    public DataExpectation normal4 = DataExpectation.create(new int[]{3, -1, -4}).expect(12);

}
