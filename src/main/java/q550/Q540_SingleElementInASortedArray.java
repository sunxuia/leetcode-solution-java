package q550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/single-element-in-a-sorted-array/
 *
 * You are given a sorted array consisting of only integers where every element appears exactly twice, except for
 * one element which appears exactly once. Find this single element that appears only once.
 *
 * Example 1:
 *
 * Input: [1,1,2,3,3,4,4,8,8]
 * Output: 2
 *
 * Example 2:
 *
 * Input: [3,3,7,7,10,11,11]
 * Output: 10
 *
 * Note: Your solution should run in O(log n) time and O(1) space.
 */
@RunWith(LeetCodeRunner.class)
public class Q540_SingleElementInASortedArray {

    @Answer
    public int singleNonDuplicate(int[] nums) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            int mid = (start + end + 1) / 2;
            int left = mid > 0 && nums[mid - 1] == nums[mid]
                    ? mid - 1 : mid;
            if (left % 2 == 0) {
                start = mid;
            } else {
                end = left - 1;
            }
        }
        return nums[start];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 1, 2, 3, 3, 4, 4, 8, 8}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{3, 3, 7, 7, 10, 11, 11}).expect(10);

}
