package q300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/move-zeroes/
 *
 * Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the
 * non-zero elements.
 *
 * Example:
 *
 * Input: [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 *
 * Note:
 *
 * You must do this in-place without making a copy of the array.
 * Minimize the total number of operations.
 */
@RunWith(LeetCodeRunner.class)
public class Q283_MoveZeroes {

    @Answer
    public void moveZeroes(int[] nums) {
        int i = 0;
        while (i < nums.length && nums[i] != 0) {
            i++;
        }
        int next = i;
        while (++i < nums.length) {
            if (nums[i] != 0) {
                nums[next++] = nums[i];
            }
        }
        while (next < nums.length) {
            nums[next++] = 0;
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{0, 1, 0, 3, 12})
            .expectArgument(0, new int[]{1, 3, 12, 0, 0})
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{1})
            .expectArgument(0, new int[]{1})
            .build();

}
