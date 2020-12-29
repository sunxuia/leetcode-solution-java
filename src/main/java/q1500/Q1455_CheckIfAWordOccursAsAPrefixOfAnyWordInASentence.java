package q1500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1455. Check If a Word Occurs As a Prefix of Any Word in a Sentence
 * https://leetcode.com/problems/check-if-a-word-occurs-as-a-prefix-of-any-word-in-a-sentence/
 *
 * Given a sentence that consists of some words separated by a single space, and a searchWord.
 *
 * You have to check if searchWord is a prefix of any word in sentence.
 *
 * Return the index of the word in sentence where searchWord is a prefix of this word (1-indexed).
 *
 * If searchWord is a prefix of more than one word, return the index of the first word (minimum index). If there is no
 * such word return -1.
 *
 * A prefix of a string S is any leading contiguous substring of S.
 *
 * Example 1:
 *
 * Input: sentence = "i love eating burger", searchWord = "burg"
 * Output: 4
 * Explanation: "burg" is prefix of "burger" which is the 4th word in the sentence.
 *
 * Example 2:
 *
 * Input: sentence = "this problem is an easy problem", searchWord = "pro"
 * Output: 2
 * Explanation: "pro" is prefix of "problem" which is the 2nd and the 6th word in the sentence, but we return 2 as it's
 * the minimal index.
 *
 * Example 3:
 *
 * Input: sentence = "i am tired", searchWord = "you"
 * Output: -1
 * Explanation: "you" is not a prefix of any word in the sentence.
 *
 * Example 4:
 *
 * Input: sentence = "i use triple pillow", searchWord = "pill"
 * Output: 4
 *
 * Example 5:
 *
 * Input: sentence = "hello from the other side", searchWord = "they"
 * Output: -1
 *
 * Constraints:
 *
 * 1 <= sentence.length <= 100
 * 1 <= searchWord.length <= 10
 * sentence consists of lowercase English letters and spaces.
 * searchWord consists of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1455_CheckIfAWordOccursAsAPrefixOfAnyWordInASentence {

    @Answer
    public int isPrefixOfWord(String sentence, String searchWord) {
        String[] strs = sentence.split(" ");
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].startsWith(searchWord)) {
                return i + 1;
            }
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("i love eating burger", "burg")
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("this problem is an easy problem", "pro")
            .expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith("i am tired", "you")
            .expect(-1);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith("i use triple pillow", "pill")
            .expect(4);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith("hello from the other side", "they")
            .expect(-1);

}
