package q1250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1221. Split a String in Balanced Strings
 * https://leetcode.com/problems/split-a-string-in-balanced-strings/
 *
 * <i data-stringify-type="italic">Balanced strings are those who have equal quantity of 'L' and 'R' characters.
 *
 * Given a balanced string <code data-stringify-type="code">s split it in the maximum amount of balanced strings.
 *
 * Return the maximum amount of splitted balanced strings.
 *
 * Example 1:
 *
 * Input: s = "RLRRLLRLRL"
 * Output: 4
 * Explanation: s can be split into "RL", "RRLL", "RL", "RL", each substring contains same number of 'L' and 'R'.
 *
 * Example 2:
 *
 * Input: s = "RLLLLRRRLR"
 * Output: 3
 * Explanation: s can be split into "RL", "LLLRRR", "LR", each substring contains same number of 'L' and 'R'.
 *
 * Example 3:
 *
 * Input: s = "LLLLRRRR"
 * Output: 1
 * Explanation: s can be split into "LLLLRRRR".
 *
 * Example 4:
 *
 * Input: s = "RLRRRLLRLL"
 * Output: 2
 * Explanation: s can be split into "RL", "RRRLLRLL", since each substring contains an equal number of 'L' and 'R'
 *
 * Constraints:
 *
 * 1 <= s.length <= 1000
 * s[i] = 'L' or 'R'
 */
@RunWith(LeetCodeRunner.class)
public class Q1221_SplitAStringInBalancedStrings {

    @Answer
    public int balancedStringSplit(String s) {
        int r = 0, l = 0, res = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'R') {
                r++;
            } else {
                l++;
            }
            if (r == l) {
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("RLRRLLRLRL").expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create("RLLLLRRRLR").expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create("LLLLRRRR").expect(1);

    @TestData
    public DataExpectation example4 = DataExpectation.create("RLRRRLLRLL").expect(2);

}
