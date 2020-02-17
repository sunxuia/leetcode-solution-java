package q400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/nth-digit/
 *
 * Find the nth digit of the infinite integer sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...
 *
 * Note:
 * n is positive and will fit within the range of a 32-bit signed integer (n < 231).
 *
 * Example 1:
 *
 * Input:
 * 3
 *
 * Output:
 * 3
 *
 * Example 2:
 *
 * Input:
 * 11
 *
 * Output:
 * 0
 *
 * Explanation:
 * The 11th digit of the sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ... is a 0, which is part of the number 10.
 */
@RunWith(LeetCodeRunner.class)
public class Q400_NthDigit {

    // https://www.cnblogs.com/grandyang/p/5891871.html
    @Answer
    public int findNthDigit(int n) {
        // digit: 当前数字区间的十进制位数
        // start: 当前数字区间的开始数字
        // count: 当前数字区间的总数
        long digit = 1, start = 1, count = 9;
        while (n > digit * count) {
            n -= digit * count;
            digit++;
            count *= 10;
            start *= 10;
        }
        // 得出n 所在的数字, 因为start 是第1 位, 所以要n-1,
        // /digit 是因为这个区间的数字都是digit 位, 第n 位所在的数字需要/digit.
        String num = String.valueOf(start + (n - 1) / digit);
        // 使用字符串来获取特定位的数字.
        return num.charAt((int) ((n - 1) % digit)) - '0';
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(3).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(11).expect(0);

}
