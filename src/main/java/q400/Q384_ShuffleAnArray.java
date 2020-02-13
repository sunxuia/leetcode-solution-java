package q400;

import java.util.Random;
import org.junit.Test;
import util.asserthelper.AssertUtils;
import util.asserthelper.ObjectEqualsHelper;

/**
 * https://leetcode.com/problems/shuffle-an-array/
 *
 * Shuffle a set of numbers without duplicates.
 *
 * Example:
 *
 * // Init an array with set 1, 2, and 3.
 * int[] nums = {1,2,3};
 * Solution solution = new Solution(nums);
 *
 * // Shuffle the array [1,2,3] and return its result. Any permutation of [1,2,3] must equally likely to be returned.
 * solution.shuffle();
 *
 * // Resets the array back to its original configuration [1,2,3].
 * solution.reset();
 *
 * // Returns the random shuffling of array [1,2,3].
 * solution.shuffle();
 */
public class Q384_ShuffleAnArray {

    private static class Solution {

        private final int[] nums, copy;

        private final int length;

        public Solution(int[] nums) {
            this.length = nums.length;
            this.nums = nums;
            this.copy = nums.clone();
        }

        /**
         * Resets the array to its original configuration and return it.
         */
        public int[] reset() {
            return copy;
        }

        /**
         * Returns a random shuffling of the array.
         */
        public int[] shuffle() {
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                int r = random.nextInt(length - i) + i;
                int t = nums[i];
                nums[i] = nums[r];
                nums[r] = t;
            }
            return nums;
        }
    }

    @Test
    public void testMethod() {
        // Init an array with set 1, 2, and 3.
        int[] nums = {1, 2, 3};
        int[] expected = nums.clone();
        Solution solution = new Solution(nums);
        int[] res;

        // Shuffle the array [1,2,3] and return its result.
        // Any permutation of [1,2,3] must equally likely to be returned.
        res = solution.shuffle();
        AssertUtils.assertEquals(expected, res, new ObjectEqualsHelper().unorder(""));

        // Resets the array back to its original configuration [1,2,3].
        res = solution.reset();
        AssertUtils.assertEquals(expected, res);

        // Returns the random shuffling of array [1,2,3].
        res = solution.shuffle();
        AssertUtils.assertEquals(expected, res, new ObjectEqualsHelper().unorder(""));
    }

}
