package q250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/number-of-digit-one/
 *
 * Given an integer n, count the total number of digit 1 appearing in all non-negative integers less than or equal to n.
 *
 * Example:
 *
 * Input: 13
 * Output: 6
 * Explanation: Digit 1 occurred in the following numbers: 1, 10, 11, 12, 13.
 *
 * 题解: 求 >0 且 <= n 的十进制数字各位中1 出现的次数.
 */
@RunWith(LeetCodeRunner.class)
public class Q233_NumberOfDigitOne {

    /**
     * 每位最大1 的数量的计算公式:
     * f(i) = 10^(i-1) + 10*f(i-1)
     * i 表示位数, f(i) 表示 < 10^i 的数字中1 的个数.
     * 算式中: 10^(i-1) 表示1xx 中的这个1 的数量, 10*f(i-1) 表示0xx, 1xx, 2xx, ... 9xx 中的xx 中的1 的和.
     * 比如:
     * f(1) = 10^0 = 1
     * f(2) = 10^1 + 10*1 = 20
     * f(3) = 10^2 + 10*20 = 300
     *
     * 针对特定数字的计算公式:
     * (下面的符号: i 表示位数, x 表示除了最高位以外的其它数字, kx = k*10^(i-1) + x, s(kx) 表示 <= kx 的数字的中1 的总和)
     * s(1x) = (x + 1) + f(i-1) + s(x)
     * s(kx) = 10^(i-1) + k*f(i-1) + s(x), k>1
     *
     * 下面的算式反过来计算了(从低位到高位).
     */
    @Answer
    public int countDigitOne(int n) {
        int res = 0, prevF = 0, remain = 0, digit = 1;
        while (n > 0) {
            int k = n % 10;
            if (k == 1) {
                res += remain + 1 + prevF;
            } else if (k > 0) {
                res += digit + k * prevF;
            }
            prevF = digit + 10 * prevF;
            remain += digit * k;
            digit *= 10;
            n /= 10;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(13).expect(6);

    @TestData
    public DataExpectation border = DataExpectation.create(0).expect(0);

    @TestData
    public DataExpectation v100 = DataExpectation.create(100).expect(21);

}
