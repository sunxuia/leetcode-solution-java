package q1650;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1608. Special Array With X Elements Greater Than or Equal X
 * https://leetcode.com/problems/special-array-with-x-elements-greater-than-or-equal-x/
 *
 * You are given an array nums of non-negative integers. nums is considered special if there exists a number x such that
 * there are exactly x numbers in nums that are greater than or equal to x.
 *
 * Notice that x does not have to be an element in nums.
 *
 * Return x if the array is special, otherwise, return -1. It can be proven that if nums is special, the value for x is
 * unique.
 *
 * Example 1:
 *
 * Input: nums = [3,5]
 * Output: 2
 * Explanation: There are 2 values (3 and 5) that are greater than or equal to 2.
 *
 * Example 2:
 *
 * Input: nums = [0,0]
 * Output: -1
 * Explanation: No numbers fit the criteria for x.
 * If x = 0, there should be 0 numbers >= x, but there are 2.
 * If x = 1, there should be 1 number >= x, but there are 0.
 * If x = 2, there should be 2 numbers >= x, but there are 0.
 * x cannot be greater since there are only 2 numbers in nums.
 *
 * Example 3:
 *
 * Input: nums = [0,4,3,0,4]
 * Output: 3
 * Explanation: There are 3 values that are greater than or equal to 3.
 *
 * Example 4:
 *
 * Input: nums = [3,6,7,7,0]
 * Output: -1
 *
 * Constraints:
 *
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1608_SpecialArrayWithXElementsGreaterThanOrEqualX {

    @Answer
    public int specialArray(int[] nums) {
        final int n = nums.length;
        Arrays.sort(nums);
        for (int i = n - 1; i > 0; i--) {
            if (nums[i - 1] < n - i && n - i <= nums[i]) {
                return n - i;
            }
        }
        return nums[0] >= n ? n : -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 5}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{0, 0}).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{0, 4, 3, 0, 4}).expect(3);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{3, 6, 7, 7, 0}).expect(-1);

}
