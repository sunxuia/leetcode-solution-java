package q1200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1189. Maximum Number of Balloons
 * https://leetcode.com/problems/maximum-number-of-balloons/
 *
 * Given a string text, you want to use the characters of text to form as many instances of the word "balloon" as
 * possible.
 *
 * You can use each character in text at most once. Return the maximum number of instances that can be formed.
 *
 * Example 1:
 *
 * Input: text = "nlaebolko"
 * Output: 1
 *
 * Example 2:
 *
 * Input: text = "loonbalxballpoon"
 * Output: 2
 *
 * Example 3:
 *
 * Input: text = "leetcode"
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= text.length <= 10^4
 * text consists of lower case English letters only.
 */
@RunWith(LeetCodeRunner.class)
public class Q1189_MaximumNumberOfBalloons {

    @Answer
    public int maxNumberOfBalloons(String text) {
        int[] counts = new int[26];
        for (int i = 0; i < text.length(); i++) {
            counts[text.charAt(i) - 'a']++;
        }
        int res = counts[0];
        res = Math.min(res, counts['b' - 'a']);
        res = Math.min(res, counts['l' - 'a'] / 2);
        res = Math.min(res, counts['o' - 'a'] / 2);
        res = Math.min(res, counts['n' - 'a']);
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("nlaebolko").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create("loonbalxballpoon").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create("leetcode").expect(0);

}
