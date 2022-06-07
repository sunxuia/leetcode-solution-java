package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1935. Maximum Number of Words You Can Type
 * https://leetcode.com/problems/maximum-number-of-words-you-can-type/
 *
 * There is a malfunctioning keyboard where some letter keys do not work. All other keys on the keyboard work properly.
 *
 * Given a string text of words separated by a single space (no leading or trailing spaces) and a string brokenLetters
 * of all distinct letter keys that are broken, return the number of words in text you can fully type using this
 * keyboard.
 *
 * Example 1:
 *
 * Input: text = "hello world", brokenLetters = "ad"
 * Output: 1
 * Explanation: We cannot type "world" because the 'd' key is broken.
 *
 * Example 2:
 *
 * Input: text = "leet code", brokenLetters = "lt"
 * Output: 1
 * Explanation: We cannot type "leet" because the 'l' and 't' keys are broken.
 *
 * Example 3:
 *
 * Input: text = "leet code", brokenLetters = "e"
 * Output: 0
 * Explanation: We cannot type either word because the 'e' key is broken.
 *
 * Constraints:
 *
 * 1 <= text.length <= 10^4
 * 0 <= brokenLetters.length <= 26
 * text consists of words separated by a single space without any leading or trailing spaces.
 * Each word only consists of lowercase English letters.
 * brokenLetters consists of distinct lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1935_MaximumNumberOfWordsYouCanType {

    @Answer
    public int canBeTypedWords(String text, String brokenLetters) {
        boolean[] broken = new boolean[26];
        for (int i = 0; i < brokenLetters.length(); i++) {
            broken[brokenLetters.charAt(i) - 'a'] = true;
        }
        int res = 0;
        loop:
        for (String word : text.split(" ")) {
            for (int i = 0; i < word.length(); i++) {
                if (broken[word.charAt(i) - 'a']) {
                    continue loop;
                }
            }
            res++;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("hello world", "ad").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("leet code", "lt").expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("leet code", "e").expect(0);

}
