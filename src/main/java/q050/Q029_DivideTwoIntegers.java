package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/divide-two-integers/
 *
 * Given two integers dividend and divisor, divide two integers without using multiplication, division and mod operator.
 *
 * Return the quotient after dividing dividend by divisor.
 *
 * The integer division should truncate toward zero.
 *
 * Example 1:
 *
 * Input: dividend = 10, divisor = 3
 * Output: 3
 * Example 2:
 *
 * Input: dividend = 7, divisor = -3
 * Output: -2
 * Note:
 *
 * Both dividend and divisor will be 32-bit signed integers.
 * The divisor will never be 0.
 * Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range:
 * [−2^31,  2^31 − 1]. For the purpose of this problem, assume that your function returns 2^31 − 1 when the division
 * result overflows.
 *
 * 题解:
 * 自定义实现除法功能. 不能使用乘法/ 除法/ 取余 操作.
 * 输入条件: divisor 不为0.
 * 输出要求: 如果运算结果超过了32 位整数范围, 则返回Integer 的最大/ 最小值.
 */
@RunWith(LeetCodeRunner.class)
public class Q029_DivideTwoIntegers {

    /**
     * 通过位移一点一点逼近.
     */
    @Answer
    public int divide(int dividend, int divisor) {
        // 全部转换成负数, 规避正数最大值比负数少1 个的边界条件.
        boolean isNegative = dividend > 0 && divisor < 0 || dividend < 0 && divisor > 0;
        dividend = dividend < 0 ? dividend : -dividend;
        divisor = divisor < 0 ? divisor : -divisor;

        // 先找到最大的 2^divisor, 让 dividend / 2^divisor > 0
        int exponent = 0;
        while (true) {
            int d = divisor << exponent;
            if (d < Integer.MIN_VALUE >> 1 || d <= dividend) {
                break;
            }
            exponent++;
        }

        //根据找到的 2^divisor, 开始逐步逼近.
        int res = 0;
        while (exponent >= 0 && dividend <= divisor) {
            int d = divisor << exponent;
            dividend -= d;
            if (dividend <= 0) {
                res -= 1 << exponent;
            } else {
                dividend += d;
            }
            exponent--;
        }

        // 转换正负值并处理边界条件
        return isNegative ? res : res == Integer.MIN_VALUE ? Integer.MAX_VALUE : -res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(10)
            .addArgument(3)
            .expect(3)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(7)
            .addArgument(-3)
            .expect(-2)
            .build();

    @TestData
    public DataExpectation border1 = DataExpectation.builder()
            .addArgument(Integer.MIN_VALUE)
            .addArgument(Integer.MAX_VALUE)
            .expect(-1)
            .build();

    @TestData
    public DataExpectation border2 = DataExpectation.builder()
            .addArgument(Integer.MIN_VALUE)
            .addArgument(1)
            .expect(Integer.MIN_VALUE)
            .build();

    @TestData
    public DataExpectation border3 = DataExpectation.builder()
            .addArgument(Integer.MAX_VALUE)
            .addArgument(3)
            .expect(715827882)
            .build();

}
