package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1544. Make The String Great
 * https://leetcode.com/problems/make-the-string-great/
 *
 * Given a string s of lower and upper case English letters.
 *
 * A good string is a string which doesn't have two adjacent characters s[i] and s[i + 1] where:
 *
 * 0 <= i <= s.length - 2
 * s[i] is a lower-case letter and s[i + 1] is the same letter but in upper-case or vice-versa.
 *
 * To make the string good, you can choose two adjacent characters that make the string bad and remove them. You can
 * keep doing this until the string becomes good.
 *
 * Return the string after making it good. The answer is guaranteed to be unique under the given constraints.
 *
 * Notice that an empty string is also good.
 *
 * Example 1:
 *
 * Input: s = "leEeetcode"
 * Output: "leetcode"
 * Explanation: In the first step, either you choose i = 1 or i = 2, both will result "leEeetcode" to be reduced to
 * "leetcode".
 *
 * Example 2:
 *
 * Input: s = "abBAcC"
 * Output: ""
 * Explanation: We have many possible scenarios, and all lead to the same answer. For example:
 * "abBAcC" --> "aAcC" --> "cC" --> ""
 * "abBAcC" --> "abBA" --> "aA" --> ""
 *
 * Example 3:
 *
 * Input: s = "s"
 * Output: "s"
 *
 * Constraints:
 *
 * 1 <= s.length <= 100
 * s contains only lower and upper case English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1544_MakeTheStringGreat {

    @Answer
    public String makeGood(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(' ');
        for (int i = 0; i < s.length(); i++) {
            char prev = sb.charAt(sb.length() - 1);
            char curr = s.charAt(i);
            if (Math.abs(prev - curr) == 'a' - 'A') {
                sb.setLength(sb.length() - 1);
            } else {
                sb.append(curr);
            }
        }
        return sb.substring(1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("leEeetcode").expect("leetcode");

    @TestData
    public DataExpectation example2 = DataExpectation.create("abBAcC").expect("");

    @TestData
    public DataExpectation example3 = DataExpectation.create("s").expect("s");

}
