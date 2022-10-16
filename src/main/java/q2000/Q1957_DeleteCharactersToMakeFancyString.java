package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1957. Delete Characters to Make Fancy String
 * https://leetcode.com/problems/delete-characters-to-make-fancy-string/
 *
 * A fancy string is a string where no three consecutive characters are equal.
 *
 * Given a string s, delete the minimum possible number of characters from s to make it fancy.
 *
 * Return the final string after the deletion. It can be shown that the answer will always be unique.
 *
 * Example 1:
 *
 * Input: s = "leeetcode"
 * Output: "leetcode"
 * Explanation:
 * Remove an 'e' from the first group of 'e's to create "leetcode".
 * No three consecutive characters are equal, so return "leetcode".
 *
 * Example 2:
 *
 * Input: s = "aaabaaaa"
 * Output: "aabaa"
 * Explanation:
 * Remove an 'a' from the first group of 'a's to create "aabaaaa".
 * Remove two 'a's from the second group of 'a's to create "aabaa".
 * No three consecutive characters are equal, so return "aabaa".
 *
 * Example 3:
 *
 * Input: s = "aab"
 * Output: "aab"
 * Explanation: No three consecutive characters are equal, so return "aab".
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * s consists only of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1957_DeleteCharactersToMakeFancyString {

    @Answer
    public String makeFancyString(String s) {
        if (s.length() < 3) {
            return s;
        }
        char[] cs = s.toCharArray();
        int len = 2;
        for (int i = 2; i < cs.length; i++) {
            if (cs[len - 2] != cs[len - 1]
                    || cs[len - 1] != cs[i]) {
                cs[len++] = cs[i];
            }
        }
        return new String(cs, 0, len);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("leeetcode").expect("leetcode");

    @TestData
    public DataExpectation example2 = DataExpectation.create("aaabaaaa").expect("aabaa");

    @TestData
    public DataExpectation example3 = DataExpectation.create("aab").expect("aab");

    @TestData
    public DataExpectation normal1 = DataExpectation.create("a").expect("a");

}
