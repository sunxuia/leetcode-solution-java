package q200;

import org.junit.runner.RunWith;
import q100.Q081_SearchInRotatedSortedArrayII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array
 *
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 *
 * (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
 *
 * Find the minimum element.
 *
 * You may assume no duplicate exists in the array.
 *
 * Example 1:
 *
 * Input: [3,4,5,1,2]
 * Output: 1
 *
 * Example 2:
 *
 * Input: [4,5,6,7,0,1,2]
 * Output: 0
 *
 * 题解: 相关题目可见 {@link Q081_SearchInRotatedSortedArrayII}
 */
@RunWith(LeetCodeRunner.class)
public class Q153_FindMinimumInRotatedSortedArray {

    // 这里利用元素非重复的特性可以使用非递归的方式去做.
    @Answer
    public int findMin(int[] nums) {
        int start = 0, end = nums.length - 1;
        while (true) {
            int middle = (start + end) / 2;
            if (nums[start] > nums[middle]) {

                end = middle;
            } else if (nums[middle] > nums[end]) {
                start = middle + 1;
            } else {
                return nums[start];
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 4, 5, 1, 2}).expect(1);

    @TestData
    public DataExpectation exmaple2 = DataExpectation.create(new int[]{4, 5, 6, 7, 0, 1, 2}).expect(0);

}
