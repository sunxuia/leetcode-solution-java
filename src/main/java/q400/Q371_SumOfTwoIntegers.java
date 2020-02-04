package q400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/sum-of-two-integers/
 *
 * Calculate the sum of two integers a and b, but you are not allowed to use the operator + and -.
 *
 * Example 1:
 *
 * Input: a = 1, b = 2
 * Output: 3
 *
 * Example 2:
 *
 * Input: a = -2, b = 3
 * Output: 1
 */
@RunWith(LeetCodeRunner.class)
public class Q371_SumOfTwoIntegers {

    // 加减的进位计算
    @Answer
    public int getSum(int a, int b) {
        while (b != 0) {
            int carry = (a & b & 0x7fffffff) << 1;
            a = a ^ b;
            b = carry;
        }
        return a;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 2).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(-2, 3).expect(1);

}
