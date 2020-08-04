package q950;

import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 907. Sum of Subarray Minimums
 * https://leetcode.com/problems/sum-of-subarray-minimums/
 *
 * Given an array of integers A, find the sum of min(B), where B ranges over every (contiguous) subarray of A.
 *
 * Since the answer may be large, return the answer modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: [3,1,2,4]
 * Output: 17
 * Explanation: Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4].
 * Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.  Sum is 17.
 *
 * Note:
 *
 * 1 <= A.length <= 30000
 * 1 <= A[i] <= 30000
 */
@RunWith(LeetCodeRunner.class)
public class Q907_SumOfSubarrayMinimums {

    @Answer
    public int sumSubarrayMins(int[] A) {
        final int n = A.length;

        // 递增堆求左边低于该点的元素坐标
        int[] left = new int[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && A[stack.peek()] > A[i]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        // 递增堆求右边低于该点的元素坐标(这里包括相同元素的值)
        int[] right = new int[n];
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && A[i] <= A[stack.peek()]) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        // 求结果
        int res = 0;
        for (int i = 0; i < A.length; i++) {
            int count = (i - left[i]) * (right[i] - i);
            res = (res + A[i] * count) % 10_0000_0007;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{3, 1, 2, 4}).expect(17);

}
