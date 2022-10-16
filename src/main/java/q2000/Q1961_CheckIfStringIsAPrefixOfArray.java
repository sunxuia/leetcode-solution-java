package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1961. Check If String Is a Prefix of Array
 * https://leetcode.com/problems/check-if-string-is-a-prefix-of-array/
 *
 * Given a string s and an array of strings words, determine whether s is a prefix string of words.
 *
 * A string s is a prefix string of words if s can be made by concatenating the first k strings in words for some
 * positive k no larger than words.length.
 *
 * Return true if s is a prefix string of words, or false otherwise.
 *
 * Example 1:
 *
 * Input: s = "iloveleetcode", words = ["i","love","leetcode","apples"]
 * Output: true
 * Explanation:
 * s can be made by concatenating "i", "love", and "leetcode" together.
 *
 * Example 2:
 *
 * Input: s = "iloveleetcode", words = ["apples","i","love","leetcode"]
 * Output: false
 * Explanation:
 * It is impossible to make s using a prefix of arr.
 *
 * Constraints:
 *
 * 1 <= words.length <= 100
 * 1 <= words[i].length <= 20
 * 1 <= s.length <= 1000
 * words[i] and s consist of only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1961_CheckIfStringIsAPrefixOfArray {

    @Answer
    public boolean isPrefixString(String s, String[] words) {
        int i = 0;
        for (String word : words) {
            if (!s.startsWith(word, i)) {
                return false;
            }
            i += word.length();
            if (i == s.length()) {
                return true;
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("iloveleetcode", new String[]{"i", "love", "leetcode", "apples"})
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("iloveleetcode", new String[]{"apples", "i", "love", "leetcode"})
            .expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith("ileetcode", new String[]{"i", "love", "leetcode", "apples"})
            .expect(false);

}
