package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * Given a 32-bit signed integer, reverse digits of an integer.
 *
 * Example 1:
 *
 * Input: 123
 * Output: 321
 *
 * Example 2:
 *
 * Input: -123
 * Output: -321
 *
 * Example 3:
 *
 * Input: 120
 * Output: 21
 *
 * Note:
 * Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range:
 * [−2^31,  2^31 − 1]. For the purpose of this problem, assume that your function returns 0 when the reversed integer
 * overflows.
 */
@RunWith(LeetCodeRunner.class)
public class Q007_ReverseInteger {

    /**
     * 使用long 类型的对象来保存结果.
     */
    @Answer
    public int reverseWithLong(int x) {
        long res = 0;
        while (x != 0) {
            res = res * 10 + x % 10;
            x = x / 10;
        }
        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) res;
    }

    /**
     * 使用int 来保存结果.
     */
    @Answer
    public int reverseWithInt(int x) {
        int res = 0;
        while (x != 0) {
            int newRes = res * 10 + x % 10;
            if ((newRes - x % 10) / 10 != res) {
                return 0;
            }
            res = newRes;
            x = x / 10;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(123)
            .expect(321)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(-123)
            .expect(-321)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(-10)
            .expect(-1)
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(1534236469)
            .expect(0)
            .build();

    @TestData
    public DataExpectation border1 = DataExpectation.builder()
            .addArgument(0)
            .expect(0)
            .build();

    @TestData
    public DataExpectation border2 = DataExpectation.builder()
            .addArgument(Integer.MAX_VALUE)
            .expect(0)
            .build();

    @TestData
    public DataExpectation border3 = DataExpectation.builder()
            .addArgument(Integer.MIN_VALUE)
            .expect(0)
            .build();
}
