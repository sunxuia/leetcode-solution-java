package q1600;

import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1576. Replace All ?'s to Avoid Consecutive Repeating Characters
 * https://leetcode.com/problems/replace-all-s-to-avoid-consecutive-repeating-characters/
 *
 * Given a string s containing only lower case English letters and the '?' character, convert all the '?' characters
 * into lower case letters such that the final string does not contain any consecutive repeating characters. You cannot
 * modify the non '?' characters.
 *
 * It is guaranteed that there are no consecutive repeating characters in the given string except for '?'.
 *
 * Return the final string after all the conversions (possibly zero) have been made. If there is more than one solution,
 * return any of them. It can be shown that an answer is always possible with the given constraints.
 *
 * Example 1:
 *
 * Input: s = "?zs"
 * Output: "azs"
 * Explanation: There are 25 solutions for this problem. From "azs" to "yzs", all are valid. Only "z" is an invalid
 * modification as the string will consist of consecutive repeating characters in "zzs".
 *
 * Example 2:
 *
 * Input: s = "ubv?w"
 * Output: "ubvaw"
 * Explanation: There are 24 solutions for this problem. Only "v" and "w" are invalid modifications as the strings will
 * consist of consecutive repeating characters in "ubvvw" and "ubvww".
 *
 * Example 3:
 *
 * Input: s = "j?qg??b"
 * Output: "jaqgacb"
 *
 * Example 4:
 *
 * Input: s = "??yw?ipkj?"
 * Output: "acywaipkja"
 *
 * Constraints:
 *
 * 1 <= s.length <= 100
 * s contains only lower case English letters and '?'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1576_ReplaceAllSToAvoidConsecutiveRepeatingCharacters {

    @Answer
    public String modifyString(String s) {
        char[] cs = s.toCharArray();
        final int n = cs.length;
        for (int i = 0; i < n; i++) {
            if (cs[i] == '?') {
                char c = 'a';
                while (i > 0 && c == cs[i - 1]
                        || i < n - 1 && c == cs[i + 1]) {
                    c++;
                }
                cs[i] = c;
            }
        }
        return new String(cs);
    }

    @TestData
    public DataExpectation example1 = createTestData("?zs");

    private DataExpectation createTestData(String str) {
        return DataExpectation.create(str)
                .assertResult((String res) -> {
                    final int n = str.length();
                    Assert.assertEquals(n, res.length());
                    for (int i = 0; i < n; i++) {
                        char e = str.charAt(i);
                        char a = res.charAt(i);
                        if (e == '?') {
                            Assert.assertTrue('a' <= a && a <= 'z');
                            if (i > 0) {
                                Assert.assertNotEquals(res.charAt(i - 1), a);
                            }
                            if (i < n - 1) {
                                Assert.assertNotEquals(res.charAt(i + 1), a);
                            }
                        } else {
                            Assert.assertEquals(e, a);
                        }
                    }
                });
    }

    @TestData
    public DataExpectation example2 = createTestData("ubv?w");

    @TestData
    public DataExpectation example3 = createTestData("j?qg??b");

    @TestData
    public DataExpectation example4 = createTestData("??yw?ipkj?");

    @TestData
    public DataExpectation normal1 = createTestData("b?a");

}
