package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 2027. Minimum Moves to Convert String
 * https://leetcode.com/problems/minimum-moves-to-convert-string/
 *
 * You are given a string s consisting of n characters which are either 'X' or 'O'.
 *
 * A move is defined as selecting three consecutive characters of s and converting them to 'O'. Note that if a move is
 * applied to the character 'O', it will stay the same.
 *
 * Return the minimum number of moves required so that all the characters of s are converted to 'O'.
 *
 * Example 1:
 *
 * Input: s = "XXX"
 * Output: 1
 * Explanation: XXX -> OOO
 * We select all the 3 characters and convert them in one move.
 *
 * Example 2:
 *
 * Input: s = "XXOX"
 * Output: 2
 * Explanation: XXOX -> OOOX -> OOOO
 * We select the first 3 characters in the first move, and convert them to 'O'.
 * Then we select the last 3 characters and convert them so that the final string contains all 'O's.
 *
 * Example 3:
 *
 * Input: s = "OOOO"
 * Output: 0
 * Explanation: There are no 'X's in s to convert.
 *
 * Constraints:
 *
 * 3 <= s.length <= 1000
 * s[i] is either 'X' or 'O'.
 */
@RunWith(LeetCodeRunner.class)
public class Q2027_MinimumMovesToConvertString {

    @Answer
    public int minimumMoves(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'X') {
                i += 2;
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("XXX").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create("XXOX").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create("OOOO").expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("OXOX").expect(1);

}
