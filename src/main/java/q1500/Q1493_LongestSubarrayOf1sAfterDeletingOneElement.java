package q1500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1493. Longest Subarray of 1's After Deleting One Element
 * https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element/
 *
 * Given a binary array nums, you should delete one element from it.
 *
 * Return the size of the longest non-empty subarray containing only 1's in the resulting array.
 *
 * Return 0 if there is no such subarray.
 *
 * Example 1:
 *
 * Input: nums = [1,1,0,1]
 * Output: 3
 * Explanation: After deleting the number in position 2, [1,1,1] contains 3 numbers with value of 1's.
 *
 * Example 2:
 *
 * Input: nums = [0,1,1,1,0,1,1,0,1]
 * Output: 5
 * Explanation: After deleting the number in position 4, [0,1,1,1,1,1,0,1] longest subarray with value of 1's is
 * [1,1,1,1,1].
 *
 * Example 3:
 *
 * Input: nums = [1,1,1]
 * Output: 2
 * Explanation: You must delete one element.
 *
 * Example 4:
 *
 * Input: nums = [1,1,0,0,1,1,1,0,1]
 * Output: 4
 *
 * Example 5:
 *
 * Input: nums = [0,0,0]
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * nums[i] is either 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1493_LongestSubarrayOf1sAfterDeletingOneElement {

    @Answer
    public int longestSubarray(int[] nums) {
        int res = 0, prev = 0, curr = 0;
        for (int num : nums) {
            if (num == 1) {
                curr++;
            } else {
                res = Math.max(res, prev + curr);
                prev = curr;
                curr = 0;
            }
        }
        res = Math.max(res, prev + curr);
        // 题目要求必须要删掉一个元素
        return res == nums.length ? res - 1 : res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 1, 0, 1}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{0, 1, 1, 1, 0, 1, 1, 0, 1}).expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 1, 1}).expect(2);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{1, 1, 0, 0, 1, 1, 1, 0, 1}).expect(4);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[]{0, 0, 0}).expect(0);

}
