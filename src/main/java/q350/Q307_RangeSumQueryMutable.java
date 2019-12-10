package q350;

import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/range-sum-query-mutable/
 *
 * Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.
 *
 * The update(i, val) function modifies nums by updating the element at index i to val.
 *
 * Example:
 *
 * Given nums = [1, 3, 5]
 *
 * sumRange(0, 2) -> 9
 * update(1, 2)
 * sumRange(0, 2) -> 8
 *
 * Note:
 *
 * The array is only modifiable by the update function.
 * You may assume the number of calls to update and sumRange function is distributed evenly.
 *
 * 相关题目 {@link Q303_RangeSumQueryImmutable}
 */
public class Q307_RangeSumQueryMutable {

    // 按照网上的提示可以使用分块累加的方法, 避免累加次数过多.
    private static class NumArray {

        private final int[] nums, blockSum;

        private final int blockLen;

        public NumArray(int[] nums) {
            this.nums = nums;
            blockLen = (int) Math.ceil(Math.sqrt(nums.length));
            blockSum = new int[blockLen];
            for (int i = 0; i < nums.length; i++) {
                blockSum[i / blockLen] += nums[i];
            }
        }

        public void update(int i, int val) {
            blockSum[i / blockLen] += val - nums[i];
            nums[i] = val;
        }

        public int sumRange(int i, int j) {
            int blockI = i / blockLen, blockJ = j / blockLen;
            if (blockI == blockJ) {
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += nums[k];
                }
                return sum;
            } else {
                int sum = 0;
                for (int k = i; k < (blockI + 1) * blockLen; k++) {
                    sum += nums[k];
                }
                for (int k = blockI + 1; k < blockJ; k++) {
                    sum += blockSum[k];
                }
                for (int k = blockJ * blockLen; k <= j; k++) {
                    sum += nums[k];
                }
                return sum;
            }
        }
    }

    @Test
    public void testMethod() {
        NumArray arr = new NumArray(new int[]{1, 3, 5});
        Assert.assertEquals(9, arr.sumRange(0, 2));
        arr.update(1, 2);
        Assert.assertEquals(8, arr.sumRange(0, 2));
    }

}
