package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/search-insert-position/
 *
 * Given a sorted array and a target value, return the index if the target is found. If not, return the index where
 * it would be if it were inserted in order.
 *
 * You may assume no duplicates in the array.
 *
 * Example 1:
 *
 * Input: [1,3,5,6], 5
 * Output: 2
 *
 * Example 2:
 *
 * Input: [1,3,5,6], 2
 * Output: 1
 *
 * Example 3:
 *
 * Input: [1,3,5,6], 7
 * Output: 4
 *
 * Example 4:
 *
 * Input: [1,3,5,6], 0
 * Output: 0
 */
@RunWith(LeetCodeRunner.class)
public class Q035_SearchInsertPosition {

    @Answer
    public int searchInsert(int[] nums, int target) {
        int start = 0, end = nums.length;
        while (start < end) {
            int middle = (start + end) / 2;
            if (nums[middle] < target) {
                start = middle + 1;
            } else {
                end = middle;
            }
        }
        return end;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{1, 3, 5, 6})
            .addArgument(5)
            .expect(2)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{1, 3, 5, 6})
            .addArgument(2)
            .expect(1)
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument(new int[]{1, 3, 5, 6})
            .addArgument(7)
            .expect(4)
            .build();

    @TestData
    public DataExpectation example4 = DataExpectation.builder()
            .addArgument(new int[]{1, 3, 5, 6})
            .addArgument(0)
            .expect(0)
            .build();

}
