package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Easy] 1832. Check if the Sentence Is Pangram
 * https://leetcode.com/problems/check-if-the-sentence-is-pangram/
 *
 * A pangram is a sentence where every letter of the English alphabet appears at least once.
 *
 * Given a string sentence containing only lowercase English letters, return true if sentence is a pangram, or false
 * otherwise.
 *
 * Example 1:
 *
 * Input: sentence = "thequickbrownfoxjumpsoverthelazydog"
 * Output: true
 * Explanation: sentence contains at least one of every letter of the English alphabet.
 *
 * Example 2:
 *
 * Input: sentence = "leetcode"
 * Output: false
 *
 * Constraints:
 *
 * 1 <= sentence.length <= 1000
 * sentence consists of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1832_CheckIfTheSentenceIsPangram {

    @Answer
    public boolean checkIfPangram(String sentence) {
        int mask = 0;
        for (int i = 0; i < sentence.length(); i++) {
            mask |= 1 << (sentence.charAt(i) - 'a');
        }
        return mask == (1 << 26) - 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("thequickbrownfoxjumpsoverthelazydog").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create("leetcode").expect(false);

}
