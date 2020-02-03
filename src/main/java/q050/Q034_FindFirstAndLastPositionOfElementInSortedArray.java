package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 *
 * Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target
 * value.
 *
 * Your algorithm's runtime complexity must be in the order of O(log n).
 *
 * If the target is not found in the array, return [-1, -1].
 *
 * Example 1:
 *
 * Input: nums = [5,7,7,8,8,10], target = 8
 * Output: [3,4]
 *
 * Example 2:
 *
 * Input: nums = [5,7,7,8,8,10], target = 6
 * Output: [-1,-1]
 */
@RunWith(LeetCodeRunner.class)
public class Q034_FindFirstAndLastPositionOfElementInSortedArray {

    @Answer
    public int[] searchRange(int[] nums, int target) {
        int[] res = new int[]{-1, -1};
        if (nums == null || nums.length == 0) {
            return res;
        }
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int middle = (start + end) / 2;
            if (nums[middle] < target) {
                start = middle + 1;
            } else {
                end = middle;
            }
        }
        if (nums[start] != target) {
            return res;
        }
        res[0] = start;
        end = nums.length - 1;
        while (start < end) {
            int middle = (start + end + 1) / 2;
            if (nums[middle] > target) {
                end = middle - 1;
            } else {
                start = middle;
            }
        }
        res[1] = start;
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{5, 7, 7, 8, 8, 10})
            .addArgument(8)
            .expect(new int[]{3, 4})
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{5, 7, 7, 8, 8, 10})
            .addArgument(6)
            .expect(new int[]{-1, -1})
            .build();

    @TestData
    public DataExpectation border1 = DataExpectation.builder()
            .addArgument(new int[0])
            .addArgument(6)
            .expect(new int[]{-1, -1})
            .build();

    @TestData
    public DataExpectation border2 = DataExpectation.builder()
            .addArgument(new int[]{1, 1, 1, 1, 1})
            .addArgument(1)
            .expect(new int[]{0, 4})
            .build();

    @TestData
    public DataExpectation border3 = DataExpectation.builder()
            .addArgument(new int[]{-1, 0, 1, 2, 3})
            .addArgument(-2)
            .expect(new int[]{-1, -1})
            .build();

    @TestData
    public DataExpectation border4 = DataExpectation.builder()
            .addArgument(new int[]{-1, 0, 1, 2, 3})
            .addArgument(4)
            .expect(new int[]{-1, -1})
            .build();
}
