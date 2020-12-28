package q1500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1451. Rearrange Words in a Sentence
 * https://leetcode.com/problems/rearrange-words-in-a-sentence/
 *
 * Given a sentence text (A sentence is a string of space-separated words) in the following format:
 *
 * First letter is in upper case.
 * Each word in text are separated by a single space.
 *
 * Your task is to rearrange the words in text such that all words are rearranged in an increasing order of their
 * lengths. If two words have the same length, arrange them in their original order.
 *
 * Return the new text following the format shown above.
 *
 * Example 1:
 *
 * Input: text = "Leetcode is cool"
 * Output: "Is cool leetcode"
 * Explanation: There are 3 words, "Leetcode" of length 8, "is" of length 2 and "cool" of length 4.
 * Output is ordered by length and the new first word starts with capital letter.
 *
 * Example 2:
 *
 * Input: text = "Keep calm and code on"
 * Output: "On and keep calm code"
 * Explanation: Output is ordered as follows:
 * "On" 2 letters.
 * "and" 3 letters.
 * "keep" 4 letters in case of tie order by position in original text.
 * "calm" 4 letters.
 * "code" 4 letters.
 *
 * Example 3:
 *
 * Input: text = "To be or not to be"
 * Output: "To be or to be not"
 *
 * Constraints:
 *
 * text begins with a capital letter and then contains lowercase letters and single space between words.
 * 1 <= text.length <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1451_RearrangeWordsInASentence {

    /**
     * 很无聊的题目 ╮(╯_╰)╭
     * LeetCode 上最快的做法是用Map<Integer, List<String>> 的方式来进行桶排序.
     */
    @Answer
    public String arrangeWords(String text) {
        String[] strs = text.split(" ");
        // 冒泡排序
        for (int i = strs.length - 1; i >= 0; i--) {
            for (int j = i + 1; j < strs.length; j++) {
                if (strs[j - 1].length() > strs[j].length()) {
                    String t = strs[j];
                    strs[j] = strs[j - 1];
                    strs[j - 1] = t;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str.toLowerCase()).append(' ');
        }
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create("Leetcode is cool")
            .expect("Is cool leetcode");

    @TestData
    public DataExpectation example2 = DataExpectation
            .create("Keep calm and code on")
            .expect("On and keep calm code");

    @TestData
    public DataExpectation example3 = DataExpectation
            .create("To be or not to be")
            .expect("To be or to be not");

}
