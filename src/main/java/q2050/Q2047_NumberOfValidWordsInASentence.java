package q2050;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 2047. Number of Valid Words in a Sentence
 * https://leetcode.com/problems/number-of-valid-words-in-a-sentence/
 *
 * A sentence consists of lowercase letters ('a' to 'z'), digits ('0' to '9'), hyphens ('-'), punctuation marks ('!',
 * '.', and ','), and spaces (' ') only. Each sentence can be broken down into one or more tokens separated by one or
 * more spaces ' '.
 *
 * A token is a valid word if all three of the following are true:
 *
 * - It only contains lowercase letters, hyphens, and/or punctuation (no digits).
 * - There is at most one hyphen '-'. If present, it must be surrounded by lowercase characters ("a-b" is valid, but
 * "-ab" and "ab-" are not valid).
 * - There is at most one punctuation mark. If present, it must be at the end of the token ("ab,", "cd!", and "." are
 * valid, but "a!b" and "c.," are not valid).
 *
 * Examples of valid words include "a-b.", "afad", "ba-c", "a!", and "!".
 *
 * Given a string sentence, return the number of valid words in sentence.
 *
 * Example 1:
 *
 * Input: sentence = "cat and  dog"
 * Output: 3
 * Explanation: The valid words in the sentence are "cat", "and", and "dog".
 *
 * Example 2:
 *
 * Input: sentence = "!this  1-s b8d!"
 * Output: 0
 * Explanation: There are no valid words in the sentence.
 * "!this" is invalid because it starts with a punctuation mark.
 * "1-s" and "b8d" are invalid because they contain digits.
 *
 * Example 3:
 *
 * Input: sentence = "alice and  bob are playing stone-game10"
 * Output: 5
 * Explanation: The valid words in the sentence are "alice", "and", "bob", "are", and "playing".
 * "stone-game10" is invalid because it contains digits.
 *
 * Constraints:
 *
 * 1 <= sentence.length <= 1000
 * sentence only contains lowercase English letters, digits, ' ', '-', '!', '.', and ','.
 * There will be at least 1 token.
 */
@RunWith(LeetCodeRunner.class)
public class Q2047_NumberOfValidWordsInASentence {

    @Answer
    public int countValidWords(String sentence) {
        Matcher matcher = PATTERN.matcher(sentence);
        int res = 0;
        while (matcher.find()) {
            res++;
        }
        return res;
    }

    private static final Pattern PATTERN = Pattern.compile("(?<=^| )(?:[a-z]+(?:-[a-z]+)?[,.!]?|[,.!])(?: |$)");

    @TestData
    public DataExpectation example1 = DataExpectation.create("cat and  dog").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create("!this  1-s b8d!").expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create("alice and  bob are playing stone-game10").expect(5);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("!").expect(1);

}
