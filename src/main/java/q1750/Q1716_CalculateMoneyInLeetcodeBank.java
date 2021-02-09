package q1750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1716. Calculate Money in Leetcode Bank
 * https://leetcode.com/problems/calculate-money-in-leetcode-bank/
 *
 * Hercy wants to save money for his first car. He puts money in the Leetcode bank every day.
 *
 * He starts by putting in $1 on Monday, the first day. Every day from Tuesday to Sunday, he will put in $1 more than
 * the day before. On every subsequent Monday, he will put in $1 more than the previous Monday.
 *
 * Given n, return the total amount of money he will have in the Leetcode bank at the end of the nth day.
 *
 * Example 1:
 *
 * Input: n = 4
 * Output: 10
 * Explanation: After the 4th day, the total is 1 + 2 + 3 + 4 = 10.
 *
 * Example 2:
 *
 * Input: n = 10
 * Output: 37
 * Explanation: After the 10th day, the total is (1 + 2 + 3 + 4 + 5 + 6 + 7) + (2 + 3 + 4) = 37. Notice that on the 2nd
 * Monday, Hercy only puts in $2.
 *
 * Example 3:
 *
 * Input: n = 20
 * Output: 96
 * Explanation: After the 20th day, the total is (1 + 2 + 3 + 4 + 5 + 6 + 7) + (2 + 3 + 4 + 5 + 6 + 7 + 8) + (3 + 4 + 5
 * + 6 + 7 + 8) = 96.
 *
 * Constraints:
 *
 * 1 <= n <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1716_CalculateMoneyInLeetcodeBank {

    @Answer
    public int totalMoney(int n) {
        final int week = n / 7, day = n % 7;
        return 7 * (week + 1) * week / 2 + 21 * week
                + day * week + (1 + day) * day / 2;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(4).expect(10);

    @TestData
    public DataExpectation example2 = DataExpectation.create(10).expect(37);

    @TestData
    public DataExpectation example3 = DataExpectation.create(20).expect(96);

}
