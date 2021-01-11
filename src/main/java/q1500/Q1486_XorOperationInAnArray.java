package q1500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1486. XOR Operation in an Array
 * https://leetcode.com/problems/xor-operation-in-an-array/
 *
 * Given an integer n and an integer start.
 *
 * Define an array nums where nums[i] = start + 2*i (0-indexed) and n == nums.length.
 *
 * Return the bitwise XOR of all elements of nums.
 *
 * Example 1:
 *
 * Input: n = 5, start = 0
 * Output: 8
 * Explanation: Array nums is equal to [0, 2, 4, 6, 8] where (0 ^ 2 ^ 4 ^ 6 ^ 8) = 8.
 * Where "^" corresponds to bitwise XOR operator.
 *
 * Example 2:
 *
 * Input: n = 4, start = 3
 * Output: 8
 * Explanation: Array nums is equal to [3, 5, 7, 9] where (3 ^ 5 ^ 7 ^ 9) = 8.
 *
 * Example 3:
 *
 * Input: n = 1, start = 7
 * Output: 7
 *
 * Example 4:
 *
 * Input: n = 10, start = 5
 * Output: 2
 *
 * Constraints:
 *
 * 1 <= n <= 1000
 * 0 <= start <= 1000
 * n == nums.length
 */
@RunWith(LeetCodeRunner.class)
public class Q1486_XorOperationInAnArray {

    @Answer
    public int xorOperation(int n, int start) {
        int res = 0;
        for (int i = 0; i < n; i++) {
            res ^= start + 2 * i;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(5, 0).expect(8);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(4, 3).expect(8);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(1, 7).expect(7);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(10, 5).expect(2);

}
