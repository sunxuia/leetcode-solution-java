package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/circular-array-loop/
 *
 * You are given a circular array nums of positive and negative integers. If a number k at an index is positive, then
 * move forward k steps. Conversely, if it's negative (-k), move backward k steps. Since the array is circular, you
 * may assume that the last element's next element is the first element, and the first element's previous element is
 * the last element.
 *
 * Determine if there is a loop (or a cycle) in nums. A cycle must start and end at the same index and the cycle's
 * length > 1. Furthermore, movements in a cycle must all follow a single direction. In other words, a cycle must not
 * consist of both forward and backward movements.
 *
 *
 *
 * Example 1:
 *
 * Input: [2,-1,1,2,2]
 * Output: true
 * Explanation: There is a cycle, from index 0 -> 2 -> 3 -> 0. The cycle's length is 3.
 *
 * Example 2:
 *
 * Input: [-1,2]
 * Output: false
 * Explanation: The movement from index 1 -> 1 -> 1 ... is not a cycle, because the cycle's length is 1. By
 * definition the cycle's length must be greater than 1.
 *
 * Example 3:
 *
 * Input: [-2,1,-1,-2,-2]
 * Output: false
 * Explanation: The movement from index 1 -> 2 -> 1 -> ... is not a cycle, because movement from index 1 -> 2 is a
 * forward movement, but movement from index 2 -> 1 is a backward movement. All movements in a cycle must follow a
 * single direction.
 *
 *
 *
 * Note:
 *
 * -1000 ≤ nums[i] ≤ 1000
 * nums[i] ≠ 0
 * 1 ≤ nums.length ≤ 5000
 *
 * 题解: nums 是一个数组, 逻辑上
 */
@RunWith(LeetCodeRunner.class)
public class Q457_CircularArrayLoop {

    @Answer
    public boolean circularArrayLoop(int[] nums) {
        final int length = nums.length;
        // circle 用作标识位, 因为这里的数字都 <= 1000
        for (int i = 0, circle = 1001; i < length; i++, circle++) {
            if (nums[i] > 1000) {
                continue;
            }
            int sign = nums[i] < 0 ? -1 : 1;
            int idx = i, prev = i - 1;
            while (true) {
                // 循环的长度要 > 1
                if (prev == idx) {
                    break;
                }
                // 命中循环路径
                if (nums[idx] == circle) {
                    return true;
                }
                // 进入其他失败的循环或方向错误
                if (nums[idx] > 1000 || sign * nums[idx] < 0) {
                    break;
                }
                prev = idx;
                idx = (length + (idx + nums[idx]) % length) % length;
                nums[prev] = circle;
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, -1, 1, 2, 2}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{-1, 2}).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{-2, 1, -1, -2, -2}).expect(false);

    @TestData
    public DataExpectation border = DataExpectation.create(new int[]{2}).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{-1, -2, -3, -4, -5}).expect(false);

}
