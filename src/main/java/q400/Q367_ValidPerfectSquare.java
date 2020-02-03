package q400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/valid-perfect-square
 *
 * Given a positive integer num, write a function which returns True if num is a perfect square else False.
 *
 * Note: Do not use any built-in library function such as sqrt.
 *
 * Example 1:
 *
 * Input: 16
 * Output: true
 *
 * Example 2:
 *
 * Input: 14
 * Output: false
 */
@RunWith(LeetCodeRunner.class)
public class Q367_ValidPerfectSquare {

    // 暴力的数字相乘方法会超时
    @Answer
    public boolean isPerfectSquare(int num) {
        long start = 1, end = num / 2;
        while (start < end) {
            long middle = (start + end + 1) / 2;
            if (middle * middle > num) {
                end = middle - 1;
            } else {
                start = middle;
            }
        }
        return start * start == num;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(16).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(14).expect(false);

    @TestData
    public DataExpectation numIs1 = DataExpectation.create(1).expect(true);

    @TestData
    public DataExpectation numIs808201 = DataExpectation.create(808201).expect(true);

    @TestData
    public DataExpectation numIs2147483647 = DataExpectation.create(Integer.MAX_VALUE).expect(false);

}
