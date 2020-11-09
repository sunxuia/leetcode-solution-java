package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1309. Decrypt String from Alphabet to Integer Mapping
 * https://leetcode.com/problems/decrypt-string-from-alphabet-to-integer-mapping/
 *
 * Given a string s formed by digits ('0' - '9') and '#' . We want to map s to English lowercase characters as follows:
 *
 * Characters ('a' to 'i') are represented by ('1' to '9') respectively.
 * Characters ('j' to 'z') are represented by ('10#' to '26#') respectively.
 *
 * Return the string formed after mapping.
 *
 * It's guaranteed that a unique mapping will always exist.
 *
 * Example 1:
 *
 * Input: s = "10#11#12"
 * Output: "jkab"
 * Explanation: "j" -> "10#" , "k" -> "11#" , "a" -> "1" , "b" -> "2".
 *
 * Example 2:
 *
 * Input: s = "1326#"
 * Output: "acz"
 *
 * Example 3:
 *
 * Input: s = "25#"
 * Output: "y"
 *
 * Example 4:
 *
 * Input: s = "12345678910#11#12#13#14#15#16#17#18#19#20#21#22#23#24#25#26#"
 * Output: "abcdefghijklmnopqrstuvwxyz"
 *
 * Constraints:
 *
 * 1 <= s.length <= 1000
 * s[i] only contains digits letters ('0'-'9') and '#' letter.
 * s will be valid string such that mapping is always possible.
 */
@RunWith(LeetCodeRunner.class)
public class Q1309_DecryptStringFromAlphabetToIntegerMapping {

    @Answer
    public String freqAlphabets(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == '#') {
                int idx = (s.charAt(i - 2) - '0') * 10 + s.charAt(i - 1) - '0';
                sb.append((char) (idx - 1 + 'a'));
                i -= 2;
            } else {
                sb.append((char) (c - '1' + 'a'));
            }
        }
        return sb.reverse().toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("10#11#12").expect("jkab");

    @TestData
    public DataExpectation example2 = DataExpectation.create("1326#").expect("acz");

    @TestData
    public DataExpectation example3 = DataExpectation.create("25#").expect("y");

    @TestData
    public DataExpectation example4 = DataExpectation
            .create("12345678910#11#12#13#14#15#16#17#18#19#20#21#22#23#24#25#26#")
            .expect("abcdefghijklmnopqrstuvwxyz");

}
