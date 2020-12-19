package q1450;

import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1417. Reformat The String
 * https://leetcode.com/problems/reformat-the-string/
 *
 * Given alphanumeric string s. (Alphanumeric string is a string consisting of lowercase English letters and digits).
 *
 * You have to find a permutation of the string where no letter is followed by another letter and no digit is followed
 * by another digit. That is, no two adjacent characters have the same type.
 *
 * Return the reformatted string or return an empty string if it is impossible to reformat the string.
 *
 * Example 1:
 *
 * Input: s = "a0b1c2"
 * Output: "0a1b2c"
 * Explanation: No two adjacent characters have the same type in "0a1b2c". "a0b1c2", "0a1b2c", "0c2a1b" are also valid
 * permutations.
 *
 * Example 2:
 *
 * Input: s = "leetcode"
 * Output: ""
 * Explanation: "leetcode" has only characters so we cannot separate them by digits.
 *
 * Example 3:
 *
 * Input: s = "1229857369"
 * Output: ""
 * Explanation: "1229857369" has only digits so we cannot separate them by characters.
 *
 * Example 4:
 *
 * Input: s = "covid2019"
 * Output: "c2o0v1i9d"
 *
 * Example 5:
 *
 * Input: s = "ab123"
 * Output: "1a2b3"
 *
 * Constraints:
 *
 * 1 <= s.length <= 500
 * s consists of only lowercase English letters and/or digits.
 */
@RunWith(LeetCodeRunner.class)
public class Q1417_ReformatTheString {

    @Answer
    public String reformat(String s) {
        final int n = s.length();
        int dc = 0, lc = 0;
        for (int i = 0; i < n; i++) {
            if (Character.isDigit(s.charAt(i))) {
                dc++;
            } else {
                lc++;
            }
        }
        if (Math.abs(dc - lc) > 1) {
            return "";
        }

        char[] sc = new char[n];
        int digit = dc >= lc ? 0 : 1;
        int letter = 1 - digit;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                sc[digit] = c;
                digit += 2;
            } else {
                sc[letter] = c;
                letter += 2;
            }
        }
        return new String(sc);
    }

    @TestData
    public DataExpectation example1 = createTestData("a0b1c2", true);

    private DataExpectation createTestData(String s, boolean hasRes) {
        var de = DataExpectation.create(s);
        if (!hasRes) {
            return de.expect("");
        }
        de.assertResult((String res) -> {
            final int n = s.length();
            Assert.assertEquals(n, res.length());
            for (int i = 1; i < n; i++) {
                if (Character.isDigit(res.charAt(i - 1))) {
                    Assert.assertTrue(Character.isAlphabetic(res.charAt(i)));
                } else {
                    Assert.assertTrue(Character.isDigit(res.charAt(i)));
                }
            }

            int[] counts = new int[127];
            for (int i = 0; i < n; i++) {
                counts[s.charAt(i)]++;
                counts[res.charAt(i)]--;
            }
            for (int i = 0; i < 127; i++) {
                Assert.assertEquals(0, counts[i]);
            }
        });
        return de;
    }

    @TestData
    public DataExpectation example2 = createTestData("leetcode", false);

    @TestData
    public DataExpectation example3 = createTestData("1229857369", false);

    @TestData
    public DataExpectation example4 = createTestData("covid2019", true);

    @TestData
    public DataExpectation example5 = createTestData("ab123", true);

}
