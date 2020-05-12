package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/non-decreasing-array/
 *
 * Given an array nums with n integers, your task is to check if it could become non-decreasing by modifying at most
 * 1 element.
 *
 * We define an array is non-decreasing if nums[i] <= nums[i + 1] holds for every i (0-based) such that (0 <= i <= n
 * - 2).
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [4,2,3]
 * Output: true
 * Explanation: You could modify the first 4 to 1 to get a non-decreasing array.
 *
 * Example 2:
 *
 * Input: nums = [4,2,1]
 * Output: false
 * Explanation: You can't get a non-decreasing array by modify at most one element.
 *
 *
 *
 * Constraints:
 *
 * 1 <= n <= 10 ^ 4
 * - 10 ^ 5 <= nums[i] <= 10 ^ 5
 */
@RunWith(LeetCodeRunner.class)
public class Q665_NonDecreasingArray {

    @Answer
    public boolean checkPossibility(int[] nums) {
        int times = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] > nums[i]) {
                if (times-- == 0) {
                    return false;
                }
                if (!(i == 1 || nums[i - 2] <= nums[i]
                        || i == nums.length - 1 || nums[i - 1] <= nums[i + 1])) {
                    return false;
                }
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{4, 2, 3}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{4, 2, 1}).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{3, 4, 2, 3}).expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{2, 3, 3, 2, 4}).expect(true);

}
