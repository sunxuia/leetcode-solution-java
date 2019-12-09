package q350;

import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/range-sum-query-immutable/
 *
 * Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.
 *
 * Example:
 *
 * Given nums = [-2, 0, 3, -5, 2, -1]
 *
 * sumRange(0, 2) -> 1
 * sumRange(2, 5) -> -1
 * sumRange(0, 5) -> -3
 *
 * Note:
 *
 * You may assume that the array does not change.
 * There are many calls to sumRange function.
 */
public class Q303_RangeSumQueryImmutable {

    private static class NumArray {

        private int[] sum;

        public NumArray(int[] nums) {
            sum = new int[nums.length + 1];
            for (int i = 1; i < sum.length; i++) {
                sum[i] = nums[i - 1] + sum[i - 1];
            }
        }

        public int sumRange(int i, int j) {
            return sum[j + 1] - sum[i];
        }
    }

    @Test
    public void testMethod() {
        NumArray obj = new NumArray(new int[]{-2, 0, 3, -5, 2, -1});
        Assert.assertEquals(1, obj.sumRange(0, 2));
        Assert.assertEquals(-1, obj.sumRange(2, 5));
        Assert.assertEquals(-3, obj.sumRange(0, 5));
    }

}
