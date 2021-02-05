package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1689. Partitioning Into Minimum Number Of Deci-Binary Numbers
 * https://leetcode.com/problems/partitioning-into-minimum-number-of-deci-binary-numbers/
 *
 * A decimal number is called deci-binary if each of its digits is either 0 or 1 without any leading zeros. For example,
 * 101 and 1100 are deci-binary, while 112 and 3001 are not.
 *
 * Given a string n that represents a positive decimal integer, return the minimum number of positive deci-binary
 * numbers needed so that they sum up to n.
 *
 * Example 1:
 *
 * Input: n = "32"
 * Output: 3
 * Explanation: 10 + 11 + 11 = 32
 *
 * Example 2:
 *
 * Input: n = "82734"
 * Output: 8
 *
 * Example 3:
 *
 * Input: n = "27346209830709182346"
 * Output: 9
 *
 * Constraints:
 *
 * 1 <= n.length <= 10^5
 * n consists of only digits.
 * n does not contain any leading zeros and represents a positive integer.
 */
@RunWith(LeetCodeRunner.class)
public class Q1689_PartitioningIntoMinimumNumberOfDeciBinaryNumbers {

    @Answer
    public int minPartitions(String n) {
        int res = 0;
        for (int i = 0; i < n.length(); i++) {
            res = Math.max(res, n.charAt(i) - '0');
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("32").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create("82734").expect(8);

    @TestData
    public DataExpectation example3 = DataExpectation.create("27346209830709182346").expect(9);

}
