package q300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/add-digits/
 *
 * Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.
 *
 * Example:
 *
 * Input: 38
 * Output: 2
 * Explanation: The process is like: 3 + 8 = 11, 1 + 1 = 2.
 * Since 2 has only one digit, return it.
 *
 * Follow up:
 * Could you do it without any loop/recursion in O(1) runtime?
 */
@RunWith(LeetCodeRunner.class)
public class Q258_AddDigits {

    // 常规做法
    @Answer
    public int addDigits(int num) {
        while (num > 9) {
            int sum = 0;
            while (num != 0) {
                sum += num % 10;
                num /= 10;
            }
            num = sum;
        }
        return num;
    }

    // LeetCode 上非循环的 O(1) 解法
    @Answer
    public int addDigits_O1(int num) {
        return (num - 1) % 9 + 1;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(38).expect(2);

}
