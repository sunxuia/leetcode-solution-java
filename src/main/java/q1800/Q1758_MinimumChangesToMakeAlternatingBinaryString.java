package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1758. Minimum Changes To Make Alternating Binary String
 * https://leetcode.com/problems/minimum-changes-to-make-alternating-binary-string/
 *
 * You are given a string s consisting only of the characters '0' and '1'. In one operation, you can change any '0' to
 * '1' or vice versa.
 *
 * The string is called alternating if no two adjacent characters are equal. For example, the string "010" is
 * alternating, while the string "0100" is not.
 *
 * Return the minimum number of operations needed to make s alternating.
 *
 * Example 1:
 *
 * Input: s = "0100"
 * Output: 1
 * Explanation: If you change the last character to '1', s will be "0101", which is alternating.
 *
 * Example 2:
 *
 * Input: s = "10"
 * Output: 0
 * Explanation: s is already alternating.
 *
 * Example 3:
 *
 * Input: s = "1111"
 * Output: 2
 * Explanation: You need two operations to reach "0101" or "1010".
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^4
 * s[i] is either '0' or '1'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1758_MinimumChangesToMakeAlternatingBinaryString {

    @Answer
    public int minOperations(String s) {
        int c1 = 0, c2 = 0;
        for (int i = 0; i < s.length(); i++) {
            int val = s.charAt(i) - '0';
            if (val != i % 2) {
                c1++;
            }
            if (val != 1 - i % 2) {
                c2++;
            }
        }
        return Math.min(c1, c2);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("0100").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create("10").expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create("1111").expect(2);

}
