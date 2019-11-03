package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/sqrtx/
 *
 * Implement int sqrt(int x).
 *
 * Compute and return the square root of x, where x is guaranteed to be a non-negative integer.
 *
 * Since the return type is an integer, the decimal digits are truncated and only the integer part of the result is
 * returned.
 *
 * Example 1:
 *
 * Input: 4
 * Output: 2
 * Example 2:
 *
 * Input: 8
 * Output: 2
 * Explanation: The square root of 8 is 2.82842..., and since
 * the decimal part is truncated, 2 is returned.
 */
@RunWith(LeetCodeRunner.class)
public class Q069_SqrtX {

    /**
     * 求平方根可以使用牛顿法.
     * 通过多次迭代求R[n+1]得出R[n+1]^2 > x的一个结果就好了
     */
    @Answer
    public int mySqrt(int x) {
        long r = x;
        while (r * r > x) {
            r = (r + x / r) / 2;
        }
        return (int) r;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(4).expect(2);

    @TestData
    public DataExpectation exmaple2 = DataExpectation.createWith(8).expect(2);
}
