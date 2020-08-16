package q950;

import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 946. Validate Stack Sequences
 * https://leetcode.com/problems/validate-stack-sequences/
 *
 * Given two sequences pushed and popped with distinct values, return true if and only if this could have been the
 * result of a sequence of push and pop operations on an initially empty stack.
 *
 * Example 1:
 *
 * Input: pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
 * Output: true
 * Explanation: We might do the following sequence:
 * push(1), push(2), push(3), push(4), pop() -> 4,
 * push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
 *
 * Example 2:
 *
 * Input: pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
 * Output: false
 * Explanation: 1 cannot be popped before 2.
 *
 * Constraints:
 *
 * 0 <= pushed.length == popped.length <= 1000
 * 0 <= pushed[i], popped[i] < 1000
 * pushed is a permutation of popped.
 * pushed and popped have distinct values.
 */
@RunWith(LeetCodeRunner.class)
public class Q946_ValidateStackSequences {

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5}, new int[]{4, 5, 3, 2, 1})
            .expect(true);
    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5}, new int[]{4, 3, 5, 1, 2})
            .expect(false);

    @Answer
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int i = 0, j = 0;
        while (j < popped.length) {
            while (i < pushed.length && (stack.isEmpty() || stack.peek() != popped[j])) {
                stack.push(pushed[i++]);
            }
            if (stack.isEmpty() || stack.peek() != popped[j]) {
                return false;
            }
            stack.pop();
            j++;
        }
        return true;
    }

}
