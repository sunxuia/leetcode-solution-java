package q400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/wiggle-subsequence/
 *
 * A sequence of numbers is called a wiggle sequence if the differences between successive numbers strictly alternate
 * between positive and negative. The first difference (if one exists) may be either positive or negative. A sequence
 * with fewer than two elements is trivially a wiggle sequence.
 *
 * For example, [1,7,4,9,2,5] is a wiggle sequence because the differences (6,-3,5,-7,3) are alternately positive and
 * negative. In contrast, [1,4,7,2,5] and [1,7,4,5,5] are not wiggle sequences, the first because its first two
 * differences are positive and the second because its last difference is zero.
 *
 * Given a sequence of integers, return the length of the longest subsequence that is a wiggle sequence. A
 * subsequence is obtained by deleting some number of elements (eventually, also zero) from the original sequence,
 * leaving the remaining elements in their original order.
 *
 * Example 1:
 *
 * Input: [1,7,4,9,2,5]
 * Output: 6
 * Explanation: The entire sequence is a wiggle sequence.
 *
 * Example 2:
 *
 * Input: [1,17,5,10,13,15,10,5,16,8]
 * Output: 7
 * Explanation: There are several subsequences that achieve this length. One is [1,17,10,13,10,16,8].
 *
 * Example 3:
 *
 * Input: [1,2,3,4,5,6,7,8,9]
 * Output: 2
 *
 * Follow up:
 * Can you do it in O(n) time?
 */
@RunWith(LeetCodeRunner.class)
public class Q376_WiggleSubsequence {

    // dp 的解法, 时间辅助度是 O(N^2)
    @Answer
    public int wiggleMaxLength(int[] nums) {
        final int len = nums.length;
        if (len < 2) {
            return len;
        }
        // 表示该元素处于上升和下降区间的子序列长度
        int[] rise = new int[len];
        int[] down = new int[len];
        rise[0] = down[0] = 1;
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[i]) {
                    down[i] = Math.max(down[i], rise[j]);
                } else if (nums[j] < nums[i]) {
                    rise[i] = Math.max(rise[i], down[j]);
                }
            }
            rise[i]++;
            down[i]++;
        }
        int res = 1;
        for (int i = 0; i < len; i++) {
            res = Math.max(res, Math.max(rise[i], down[i]));
        }
        return res;
    }

    // LeetCode 上给出的O(n) 时间复杂度的解法.
    @Answer
    public int wiggleMaxLength2(int[] nums) {
        final int len = nums.length;
        if (len < 2) {
            return len;
        }
        // 这里表示的是出于上升区间和下降区间到该元素的最大子序列长度
        int[] rise = new int[len];
        int[] down = new int[len];
        rise[0] = down[0] = 1;
        for (int i = 1; i < len; i++) {
            if (nums[i] > nums[i - 1]) {
                // ↗, 上升+1, 下降不变.
                rise[i] = down[i - 1] + 1;
                down[i] = down[i - 1];
            } else if (nums[i] < nums[i - 1]) {
                // ↘, 上升不变, 下降+1.
                down[i] = rise[i - 1] + 1;
                rise[i] = rise[i - 1];
            } else {
                // →, 上升不变, 下降不变.
                down[i] = down[i - 1];
                rise[i] = rise[i - 1];
            }
        }
        return Math.max(down[len - 1], rise[len - 1]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 7, 4, 9, 2, 5}).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 17, 5, 10, 13, 15, 10, 5, 16, 8}).expect(7);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}).expect(2);

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[]{1, 1}).expect(1);

}
