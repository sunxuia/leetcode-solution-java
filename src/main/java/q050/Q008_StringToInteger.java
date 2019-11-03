package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/string-to-integer-atoi/
 *
 * Implement atoi which converts a string to an integer.
 *
 * The function first discards as many whitespace characters as necessary until the first non-whitespace character is
 * found. Then, starting from this character, takes an optional initial plus or minus sign followed by as many
 * numerical digits as possible, and interprets them as a numerical value.
 *
 * The string can contain additional characters after those that form the integral number, which are ignored and have
 * no effect on the behavior of this function.
 *
 * If the first sequence of non-whitespace characters in str is not a valid integral number, or if no such sequence
 * exists because either str is empty or it contains only whitespace characters, no conversion is performed.
 *
 * If no valid conversion could be performed, a zero value is returned.
 *
 * Note:
 *
 * Only the space character ' ' is considered as whitespace character.
 * Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range:
 * [−2^31,  2^31 − 1]. If the numerical value is out of the range of representable values, INT_MAX (2^31 − 1) or INT_MIN
 * (−2^31) is returned.
 * Example 1:
 *
 * Input: "42"
 * Output: 42
 * Example 2:
 *
 * Input: "   -42"
 * Output: -42
 * Explanation: The first non-whitespace character is '-', which is the minus sign.
 * Then take as many numerical digits as possible, which gets 42.
 * Example 3:
 *
 * Input: "4193 with words"
 * Output: 4193
 * Explanation: Conversion stops at digit '3' as the next character is not a numerical digit.
 * Example 4:
 *
 * Input: "words and 987"
 * Output: 0
 * Explanation: The first non-whitespace character is 'w', which is not a numerical
 * digit or a +/- sign. Therefore no valid conversion could be performed.
 * Example 5:
 *
 * Input: "-91283472332"
 * Output: -2147483648
 * Explanation: The number "-91283472332" is out of the range of a 32-bit signed integer.
 * Thefore INT_MIN (−2^31) is returned.
 */
@RunWith(LeetCodeRunner.class)
public class Q008_StringToInteger {

    /**
     * 简单的ascii to integer (atoi) 程序.
     * 解析字符串为数字.
     * 字符串限制: 字符串只能以空格或数字或+-号开始, 空格被忽略, 其它字符开头的字符串使用0 来表示.
     * 超过32 位整数表示范围的按照 Integer.MAX_VALUE 或 Integer.MIN_VALUE 来表示.
     * 需要注意的是数字的范围 [−2^31,  2^31 − 1].
     */
    @Answer
    public int myAtoi(String str) {
        final int length = str.length();
        int i = 0;

        // 去掉开头空格
        while (i < length && str.charAt(i) == ' ') {
            i++;
        }
        if (i == length) {
            return 0;
        }

        // 处理正负号
        char c = str.charAt(i);
        boolean isNegative = false;
        if (c == '-' || c == '+') {
            isNegative = c == '-';
            i++;
        } else if (c < '0' || c > '9') {
            return 0;
        }

        // 计算结果. 分正负不同进行处理.
        int res = 0;
        if (isNegative) {
            while (i < length) {
                int n = str.charAt(i) - '0';
                if (n < 0 || n > 9) {
                    break;
                }
                if ((Integer.MIN_VALUE + n) / 10 > res) {
                    return Integer.MIN_VALUE;
                }
                res = res * 10 - n;
                i++;
            }
        } else {
            while (i < length) {
                int n = str.charAt(i) - '0';
                if (n < 0 || n > 9) {
                    break;
                }
                if ((Integer.MAX_VALUE - n) / 10 < res) {
                    return Integer.MAX_VALUE;
                }
                res = res * 10 + n;
                i++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("42")
            .expect(42)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("-42")
            .expect(-42)
            .build();
    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument("4193 with words")
            .expect(4193)
            .build();

    @TestData
    public DataExpectation example4 = DataExpectation.builder()
            .addArgument("words and 987")
            .expect(0)
            .build();

    @TestData
    public DataExpectation example5 = DataExpectation.builder()
            .addArgument("-91283472332")
            .expect(Integer.MIN_VALUE)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument("  -1")
            .expect(-1)
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument("2147483646")
            .expect(2147483646)
            .build();

    @TestData
    public DataExpectation normal3 = DataExpectation.builder()
            .addArgument("2147483648")
            .expect(2147483647)
            .build();
}
