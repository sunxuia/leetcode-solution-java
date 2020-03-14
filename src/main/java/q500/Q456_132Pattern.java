package q500;

import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/132-pattern/
 *
 * Given a sequence of n integers a1, a2, ..., an, a 132 pattern is a subsequence ai, aj, ak such that i < j < k and
 * ai < ak < aj. Design an algorithm that takes a list of n numbers as input and checks whether there is a 132
 * pattern in the list.
 *
 * Note: n will be less than 15,000.
 *
 * Example 1:
 *
 * Input: [1, 2, 3, 4]
 *
 * Output: False
 *
 * Explanation: There is no 132 pattern in the sequence.
 *
 * Example 2:
 *
 * Input: [3, 1, 4, 2]
 *
 * Output: True
 *
 * Explanation: There is a 132 pattern in the sequence: [1, 4, 2].
 *
 * Example 3:
 *
 * Input: [-1, 3, 2, 0]
 *
 * Output: True
 *
 * Explanation: There are three 132 patterns in the sequence: [-1, 3, 2], [-1, 3, 0] and [-1, 2, 0].
 */
@RunWith(LeetCodeRunner.class)
public class Q456_132Pattern {

    @Answer
    public boolean find132pattern(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int min = Integer.MIN_VALUE;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] < min) {
                return true;
            }
            while (!stack.empty() && nums[i] > stack.peek()) {
                min = stack.pop();
            }
            stack.push(nums[i]);
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 4}).expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{3, 1, 4, 2}).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{-1, 3, 2, 0}).expect(true);

    @TestData
    public DataExpectation border0 = DataExpectation.create(new int[0]).expect(false);

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[]{1}).expect(false);

    @TestData
    public DataExpectation border2 = DataExpectation.create(new int[]{1, 2}).expect(false);

    @TestData
    public DataExpectation border3 = DataExpectation.create(new int[]{1, 2, 3}).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 0, 1, -4, -3}).expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{3, 5, 0, 3, 4}).expect(true);

}
