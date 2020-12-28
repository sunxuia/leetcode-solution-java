package q1450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1446. Consecutive Characters
 * https://leetcode.com/problems/consecutive-characters/
 *
 * Given a string s, the power of the string is the maximum length of a non-empty substring that contains only one
 * unique character.
 *
 * Return the power of the string.
 *
 * Example 1:
 *
 * Input: s = "leetcode"
 * Output: 2
 * Explanation: The substring "ee" is of length 2 with the character 'e' only.
 *
 * Example 2:
 *
 * Input: s = "abbcccddddeeeeedcba"
 * Output: 5
 * Explanation: The substring "eeeee" is of length 5 with the character 'e' only.
 *
 * Example 3:
 *
 * Input: s = "triplepillooooow"
 * Output: 5
 *
 * Example 4:
 *
 * Input: s = "hooraaaaaaaaaaay"
 * Output: 11
 *
 * Example 5:
 *
 * Input: s = "tourist"
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= s.length <= 500
 * s contains only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1446_ConsecutiveCharacters {

    @Answer
    public int maxPower(String s) {
        char prev = ' ';
        int res = 0, count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (prev == c) {
                count++;
            } else {
                prev = c;
                count = 1;
            }
            res = Math.max(res, count);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("leetcode").expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create("abbcccddddeeeeedcba").expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation.create("triplepillooooow").expect(5);

    @TestData
    public DataExpectation example4 = DataExpectation.create("hooraaaaaaaaaaay").expect(11);

    @TestData
    public DataExpectation example5 = DataExpectation.create("tourist").expect(1);

}
