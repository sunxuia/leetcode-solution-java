package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1323. Maximum 69 Number
 * https://leetcode.com/problems/maximum-69-number/
 *
 * Given a positive integer num consisting only of digits 6 and 9.
 *
 * Return the maximum number you can get by changing at most one digit (6 becomes 9, and 9 becomes 6).
 *
 * Example 1:
 *
 * Input: num = 9669
 * Output: 9969
 * Explanation:
 * Changing the first digit results in 6669.
 * Changing the second digit results in 9969.
 * Changing the third digit results in 9699.
 * Changing the fourth digit results in 9666.
 * The maximum number is 9969.
 *
 * Example 2:
 *
 * Input: num = 9996
 * Output: 9999
 * Explanation: Changing the last digit 6 to 9 results in the maximum number.
 *
 * Example 3:
 *
 * Input: num = 9999
 * Output: 9999
 * Explanation: It is better not to apply any change.
 *
 * Constraints:
 *
 * 1 <= num <= 10^4
 * num's digits are 6 or 9.
 */
@RunWith(LeetCodeRunner.class)
public class Q1323_Maximum69Number {

    @Answer
    public int maximum69Number(int num) {
        int digit = 0;
        for (int i = 1; num / i > 0; i *= 10) {
            if (num / i % 10 == 6) {
                digit = i;
            }
        }
        return num + 3 * digit;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(9669).expect(9969);

    @TestData
    public DataExpectation example2 = DataExpectation.create(9996).expect(9999);

    @TestData
    public DataExpectation example3 = DataExpectation.create(9999).expect(9999);

}
