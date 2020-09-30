package q1150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1144. Decrease Elements To Make Array Zigzag
 * https://leetcode.com/problems/decrease-elements-to-make-array-zigzag/
 *
 * Given an array nums of integers, a move consists of choosing any element and decreasing it by 1.
 *
 * An array A is a zigzag array if either:
 *
 * Every even-indexed element is greater than adjacent elements, ie. A[0] > A[1] < A[2] > A[3] < A[4] > ...
 * OR, every odd-indexed element is greater than adjacent elements, ie. A[0] < A[1] > A[2] < A[3] > A[4] < ...
 *
 * Return the minimum number of moves to transform the given array nums into a zigzag array.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3]
 * Output: 2
 * Explanation: We can decrease 2 to 0 or 3 to 1.
 *
 * Example 2:
 *
 * Input: nums = [9,6,1,6,2]
 * Output: 4
 *
 * Constraints:
 *
 * 1 <= nums.length <= 1000
 * 1 <= nums[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1144_DecreaseElementsToMakeArrayZigzag {

    /**
     * 挺无聊的一题
     */
    @Answer
    public int movesToMakeZigzag(int[] nums) {
        return Math.min(decreaseOdd(nums), decreaseEven(nums));
    }

    private int decreaseOdd(int[] nums) {
        final int n = nums.length;
        int res = 0;
        for (int i = 1; i < n; i += 2) {
            int min = i == n - 1 ? nums[i - 1]
                    : Math.min(nums[i - 1], nums[i + 1]);
            res += Math.max(0, nums[i] - (min - 1));
        }
        return res;
    }

    private int decreaseEven(int[] nums) {
        final int n = nums.length;
        int res = 0;
        for (int i = 0; i < n; i += 2) {
            int min = Integer.MAX_VALUE;
            if (0 < i) {
                min = Math.min(min, nums[i - 1]);
            }
            if (i < n - 1) {
                min = Math.min(min, nums[i + 1]);
            }
            res += Math.max(0, nums[i] - (min - 1));
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{9, 6, 1, 6, 2}).expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{2, 7, 10, 9, 8, 9}).expect(4);

    @TestData
    public DataExpectation border = DataExpectation.create(new int[]{2}).expect(0);

}
