package q750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/largest-number-at-least-twice-of-others/
 *
 * In a given integer array nums, there is always exactly one largest element.
 *
 * Find whether the largest element in the array is at least twice as much as every other number in the array.
 *
 * If it is, return the index of the largest element, otherwise return -1.
 *
 * Example 1:
 *
 * Input: nums = [3, 6, 1, 0]
 * Output: 1
 * Explanation: 6 is the largest integer, and for every other number in the array x,
 * 6 is more than twice as big as x.  The index of value 6 is 1, so we return 1.
 *
 *
 *
 * Example 2:
 *
 * Input: nums = [1, 2, 3, 4]
 * Output: -1
 * Explanation: 4 isn't at least as big as twice the value of 3, so we return -1.
 *
 *
 *
 * Note:
 *
 * nums will have a length in the range [1, 50].
 * Every nums[i] will be an integer in the range [0, 99].
 */
@RunWith(LeetCodeRunner.class)
public class Q747_LargestNumberAtLeastTwiceOfOthers {

    @Answer
    public int dominantIndex(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int max1 = 0, max2 = 1;
        if (nums[0] < nums[1]) {
            max1 = 1;
            max2 = 0;
        }
        for (int i = 2; i < nums.length; i++) {
            if (nums[max1] < nums[i]) {
                max2 = max1;
                max1 = i;
            } else if (nums[max2] < nums[i]) {
                max2 = i;
            }
        }
        return nums[max1] >= nums[max2] * 2 ? max1 : -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 6, 1, 0}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 3, 4}).expect(-1);

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[]{1}).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{0, 0, 1, 2}).expect(3);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{0, 0, 3, 2}).expect(-1);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new int[]{1, 0}).expect(0);

}
