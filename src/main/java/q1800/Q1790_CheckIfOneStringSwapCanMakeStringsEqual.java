package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1790. Check if One String Swap Can Make Strings Equal
 * https://leetcode.com/problems/check-if-one-string-swap-can-make-strings-equal/
 *
 * You are given two strings s1 and s2 of equal length. A string swap is an operation where you choose two indices in a
 * string (not necessarily different) and swap the characters at these indices.
 *
 * Return true if it is possible to make both strings equal by performing at most one string swap on exactly one of the
 * strings. Otherwise, return false.
 *
 * Example 1:
 *
 * Input: s1 = "bank", s2 = "kanb"
 * Output: true
 * Explanation: For example, swap the first character with the last character of s2 to make "bank".
 *
 * Example 2:
 *
 * Input: s1 = "attack", s2 = "defend"
 * Output: false
 * Explanation: It is impossible to make them equal with one string swap.
 *
 * Example 3:
 *
 * Input: s1 = "kelb", s2 = "kelb"
 * Output: true
 * Explanation: The two strings are already equal, so no string swap operation is required.
 *
 * Constraints:
 *
 * 1 <= s1.length, s2.length <= 100
 * s1.length == s2.length
 * s1 and s2 consist of only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1790_CheckIfOneStringSwapCanMakeStringsEqual {

    @Answer
    public boolean areAlmostEqual(String s1, String s2) {
        char a = '0', b = '0';
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                if (a == '0') {
                    a = s1.charAt(i);
                    b = s2.charAt(i);
                } else if (s1.charAt(i) == b
                        && s2.charAt(i) == a) {
                    a = '1';
                } else {
                    return false;
                }
            }
        }
        return a < '2';
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("bank", "kanb").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("attack", "defend").expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("kelb", "kelb").expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("abcd", "dcba").expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("aa", "ac").expect(false);

}
