package q1900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1881. Maximum Value after Insertion
 * https://leetcode.com/problems/maximum-value-after-insertion/
 *
 * You are given a very large integer n, represented as a string, and an integer digit x. The digits in n and the
 * digit x are in the inclusive range [1, 9], and n may represent a negative number.
 *
 * You want to maximize n's numerical value by inserting x anywhere in the decimal representation of n. You cannot
 * insert x to the left of the negative sign.
 *
 * For example, if n = 73 and x = 6, it would be best to insert it between 7 and 3, making n = 763.
 * If n = -55 and x = 2, it would be best to insert it before the first 5, making n = -255.
 *
 * Return a string representing the maximum value of n after the insertion.
 *
 * Example 1:
 *
 * Input: n = "99", x = 9
 * Output: "999"
 * Explanation: The result is the same regardless of where you insert 9.
 *
 * Example 2:
 *
 * Input: n = "-13", x = 2
 * Output: "-123"
 * Explanation: You can make n one of {-213, -123, -132}, and the largest of those three is -123.
 *
 * Constraints:
 *
 * 1 <= n.length <= 10^5
 * 1 <= x <= 9
 * The digits in n are in the range [1, 9].
 * n is a valid representation of an integer.
 * In the case of a negative n, it will begin with '-'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1881_MaximumValueAfterInsertion {

    @Answer
    public String maxValue(String n, int x) {
        if (n.startsWith("-")) {
            for (int i = 1; i < n.length(); i++) {
                if (n.charAt(i) - '0' > x) {
                    return n.substring(0, i) + x + n.substring(i);
                }
            }
        } else {
            for (int i = 0; i < n.length(); i++) {
                if (n.charAt(i) - '0' < x) {
                    return n.substring(0, i) + x + n.substring(i);
                }
            }
        }
        return n + x;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("99", 9).expect("999");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("-13", 2).expect("-123");

}
