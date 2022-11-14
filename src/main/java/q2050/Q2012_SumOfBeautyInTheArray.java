package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 2012. Sum of Beauty in the Array
 * https://leetcode.com/problems/sum-of-beauty-in-the-array/
 *
 * You are given a 0-indexed integer array nums. For each index i (1 <= i <= nums.length - 2) the beauty of nums[i]
 * equals:
 *
 * 2, if nums[j] < nums[i] < nums[k], for all 0 <= j < i and for all i < k <= nums.length - 1.
 * 1, if nums[i - 1] < nums[i] < nums[i + 1], and the previous condition is not satisfied.
 * 0, if none of the previous conditions holds.
 *
 * Return the sum of beauty of all nums[i] where 1 <= i <= nums.length - 2.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3]
 * Output: 2
 * Explanation: For each index i in the range 1 <= i <= 1:
 * - The beauty of nums[1] equals 2.
 *
 * Example 2:
 *
 * Input: nums = [2,4,6,4]
 * Output: 1
 * Explanation: For each index i in the range 1 <= i <= 2:
 * - The beauty of nums[1] equals 1.
 * - The beauty of nums[2] equals 0.
 *
 * Example 3:
 *
 * Input: nums = [3,2,1]
 * Output: 0
 * Explanation: For each index i in the range 1 <= i <= 1:
 * - The beauty of nums[1] equals 0.
 *
 * Constraints:
 *
 * 3 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q2012_SumOfBeautyInTheArray {

    @Answer
    public int sumOfBeauties(int[] nums) {
        final int n = nums.length;
        int rightMin = nums[n - 1];
        // 如果 nums[i] 比右边的数字都小则置为负数
        for (int i = n - 2; i > 0; i--) {
            if (nums[i] < rightMin) {
                rightMin = nums[i];
                nums[i] = -nums[i];
            }
        }

        int res = 0;
        int leftMax = nums[0];
        for (int i = 1; i < n - 1; i++) {
            if (nums[i] < 0) {
                nums[i] = -nums[i];
                if (leftMax < nums[i]) {
                    res += 2;
                } else if (nums[i - 1] < nums[i]) {
                    res += 1;
                }
            } else {
                if (nums[i - 1] < nums[i]
                        && nums[i] < Math.abs(nums[i + 1])) {
                    res += 1;
                }
            }
            leftMax = Math.max(leftMax, nums[i]);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 4, 6, 4}).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{3, 2, 1}).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 2, 3, 4, 5, 7, 8, 9, 10}).expect(14);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[]{55, 36, 68, 97, 1, 20, 5, 50, 53, 21, 15, 66, 93, 12, 31, 14, 13, 3, 24, 97, 30, 14, 28,
                    4, 67, 86, 60, 59, 40, 69, 83, 49, 28, 88, 98, 27, 94, 56, 55, 66, 3, 75, 76, 7, 54, 68, 75, 24, 13,
                    85, 62, 47, 3, 67, 16, 79, 47, 38, 89, 48, 40, 3, 88, 53, 13, 14, 40, 26, 100, 87, 3, 58, 8, 53, 82,
                    63, 60, 27, 76, 79, 10, 1, 37, 4, 48, 40, 93, 10, 29, 97})
            .expect(14);

}
