package q650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/sum-of-square-numbers/
 *
 * Given a non-negative integer c, your task is to decide whether there're two integers a and b such that a2 + b2 = c.
 *
 * Example 1:
 *
 * Input: 5
 * Output: True
 * Explanation: 1 * 1 + 2 * 2 = 5
 *
 *
 *
 * Example 2:
 *
 * Input: 3
 * Output: False
 */
@RunWith(LeetCodeRunner.class)
public class Q633_SumOfSquareNumbers {

    @Answer
    public boolean judgeSquareSum(int c) {
        for (int i = 0; i <= (int) Math.sqrt((double) c / 2); i++) {
            double val = Math.sqrt(c - i * i);
            if (val == (int) val) {
                return true;
            }
        }
        return false;
    }

    // LeetCode 上最快的方法.
    // 利用了 Fermat Theorem, 详情参见 Solution
    @Answer
    public boolean judgeSquareSum2(int c) {
        for (int i = 2; i * i <= c; i++) {
            int count = 0;
            if (c % i == 0) {
                while (c % i == 0) {
                    count++;
                    c /= i;
                }
                if (i % 4 == 3 && count % 2 != 0) {
                    return false;
                }
            }
        }
        return c % 4 != 3;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(5).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(3).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(2).expect(true);

}
