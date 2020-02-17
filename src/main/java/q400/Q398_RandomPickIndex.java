package q400;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/random-pick-index/
 *
 * Given an array of integers with possible duplicates, randomly output the index of a given target number. You can
 * assume that the given target number must exist in the array.
 *
 * Note:
 * The array size can be very large. Solution that uses too much extra space will not pass the judge.
 *
 * Example:
 *
 * int[] nums = new int[] {1,2,3,3,3};
 * Solution solution = new Solution(nums);
 *
 * // pick(3) should return either index 2, 3, or 4 randomly. Each index should have equal probability of returning.
 * solution.pick(3);
 *
 * // pick(1) should return 0. Since in the array only nums[0] is equal to 1.
 * solution.pick(1);
 */
public class Q398_RandomPickIndex {

    /**
     * 测试用例的数组是无序的.
     * 和 {@link Q382_LinkedListRandomNode} 类似, 使用水塘抽样.
     */
    private static class Solution {

        private int[] nums;

        public Solution(int[] nums) {
            this.nums = nums;
        }

        public int pick(int target) {
            Random random = new Random();
            int i = 0;
            while (nums[i] != target) {
                i++;
            }
            int res = i;
            int count = 1;
            while (++i < nums.length) {
                if (nums[i] == target) {
                    int r = random.nextInt(++count);
                    if (r == 0) {
                        res = i;
                    }
                }
            }
            return res;
        }
    }

    @Test
    public void testMethod() {
        Solution solution;
        int res;

        solution = new Solution(new int[]{1, 2, 3, 3, 3});

        // pick(3) should return either index 2, 3, or 4 randomly.
        // Each index should have equal probability of returning.
        res = solution.pick(3);
        Assert.assertTrue(res == 2 || res == 3 || res == 4);

        // pick(1) should return 0. Since in the array only nums[0] is equal to 1.
        res = solution.pick(1);
        Assert.assertEquals(0, res);

        solution = new Solution(new int[]{1});
        res = solution.pick(1);
        Assert.assertEquals(0, res);

        solution = new Solution(TestDataFileHelper.readIntegerArray("Q398_LongTestData").get());
        res = solution.pick(-760627172);
        Assert.assertEquals(4857, res);
    }

}
