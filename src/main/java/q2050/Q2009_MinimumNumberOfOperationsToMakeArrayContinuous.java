package q2050;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 2009. Minimum Number of Operations to Make Array Continuous
 * https://leetcode.com/problems/minimum-number-of-operations-to-make-array-continuous/
 *
 * You are given an integer array nums. In one operation, you can replace any element in nums with any integer.
 *
 * nums is considered continuous if both of the following conditions are fulfilled:
 *
 * All elements in nums are unique.
 * The difference between the maximum element and the minimum element in nums equals nums.length - 1.
 *
 * For example, nums = [4, 2, 5, 3] is continuous, but nums = [1, 2, 3, 5, 6] is not continuous.
 *
 * Return the minimum number of operations to make nums continuous.
 *
 * Example 1:
 *
 * Input: nums = [4,2,5,3]
 * Output: 0
 * Explanation: nums is already continuous.
 *
 * Example 2:
 *
 * Input: nums = [1,2,3,5,6]
 * Output: 1
 * Explanation: One possible solution is to change the last element to 4.
 * The resulting array is [1,2,3,5,4], which is continuous.
 *
 * Example 3:
 *
 * Input: nums = [1,10,100,1000]
 * Output: 3
 * Explanation: One possible solution is to:
 * - Change the second element to 2.
 * - Change the third element to 3.
 * - Change the fourth element to 4.
 * The resulting array is [1,2,3,4], which is continuous.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q2009_MinimumNumberOfOperationsToMakeArrayContinuous {

    /**
     * 参考文档
     * https://leetcode.com/problems/minimum-number-of-operations-to-make-array-continuous/solutions/1471265/
     * 思路是以长度为n 的窗口去套排序后的nums, 在窗口里的数字不用动, 在窗口外的数字需要移动到窗口里面来.
     */
    @Answer
    public int minOperations(int[] nums) {
        final int n = nums.length;
        Arrays.sort(nums);
        // 在窗口中不用动的数字的最大数量
        int maxNumsInWindow = 0;

        // 保存在窗口中不用移动的数字
        Deque<Integer> numsInWindow = new ArrayDeque<>();
        for (int num : nums) {
            // 窗口长度 >= n, 去掉头
            while (!numsInWindow.isEmpty() && num - numsInWindow.getFirst() >= n) {
                numsInWindow.removeFirst();
            }

            // 把num 加上去 (如果没有重复的话)
            if (numsInWindow.isEmpty() || numsInWindow.getLast() != num) {
                numsInWindow.addLast(num);
            }

            maxNumsInWindow = Math.max(maxNumsInWindow, numsInWindow.size());
        }

        return n - maxNumsInWindow;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{4, 2, 5, 3}).expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 3, 5, 6}).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 10, 100, 1000}).expect(3);

}
