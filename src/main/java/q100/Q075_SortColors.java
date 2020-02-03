package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/sort-colors/
 *
 * Given an array with n objects colored red, white or blue, sort them in-place so that objects of the same color are
 * adjacent, with the colors in the order red, white and blue.
 *
 * Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
 *
 * Note: You are not suppose to use the library's sort function for this problem.
 *
 * Example:
 *
 * Input: [2,0,2,1,1,0]
 * Output: [0,0,1,1,2,2]
 * Follow up:
 *
 * A rather straight forward solution is a two-pass algorithm using counting sort.
 * First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's, then
 * 1's and followed by 2's.
 * Could you come up with a one-pass algorithm using only constant space?
 */
@RunWith(LeetCodeRunner.class)
public class Q075_SortColors {

    @Answer
    public void sortColors(int[] nums) {
        int[] next = new int[3];
        for (int i = 0; i < nums.length; i++) {
            int v = nums[i];
            nums[next[v]++] = v;
            switch (v) {
                case 0:
                    if (next[0] <= next[1]) {
                        nums[next[1]] = 1;
                    }
                    next[1]++;
                case 1:
                    if (next[1] <= next[2]) {
                        nums[next[2]] = 2;
                    }
                    next[2]++;
            }
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{2, 0, 2, 1, 1, 0})
            .expectArgument(0, new int[]{0, 0, 1, 1, 2, 2})
            .build();
}
