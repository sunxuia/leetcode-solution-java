package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1813. Sentence Similarity III
 * https://leetcode.com/problems/sentence-similarity-iii/
 *
 * A sentence is a list of words that are separated by a single space with no leading or trailing spaces. For example,
 * "Hello World", "HELLO", "hello world hello world" are all sentences. Words consist of only uppercase and lowercase
 * English letters.
 *
 * Two sentences sentence1 and sentence2 are similar if it is possible to insert an arbitrary sentence (possibly empty)
 * inside one of these sentences such that the two sentences become equal. For example, sentence1 = "Hello my name is
 * Jane" and sentence2 = "Hello Jane" can be made equal by inserting "my name is" between "Hello" and "Jane" in
 * sentence2.
 *
 * Given two sentences sentence1 and sentence2, return true if sentence1 and sentence2 are similar. Otherwise, return
 * false.
 *
 * Example 1:
 *
 * Input: sentence1 = "My name is Haley", sentence2 = "My Haley"
 * Output: true
 * Explanation: sentence2 can be turned to sentence1 by inserting "name is" between "My" and "Haley".
 *
 * Example 2:
 *
 * Input: sentence1 = "of", sentence2 = "A lot of words"
 * Output: false
 * Explanation: No single sentence can be inserted inside one of the sentences to make it equal to the other.
 *
 * Example 3:
 *
 * Input: sentence1 = "Eating right now", sentence2 = "Eating"
 * Output: true
 * Explanation: sentence2 can be turned to sentence1 by inserting "right now" at the end of the sentence.
 *
 * Constraints:
 *
 * 1 <= sentence1.length, sentence2.length <= 100
 * sentence1 and sentence2 consist of lowercase and uppercase English letters and spaces.
 * The words in sentence1 and sentence2 are separated by a single space.
 */
@RunWith(LeetCodeRunner.class)
public class Q1813_SentenceSimilarityIII {

    @Answer
    public boolean areSentencesSimilar(String sentence1, String sentence2) {
        String[] strs1 = sentence1.split(" ");
        String[] strs2 = sentence2.split(" ");
        int is = 0, ie = strs1.length;
        int js = 0, je = strs2.length;
        while (is < ie && js < je
                && strs1[is].equals(strs2[js])) {
            is++;
            js++;
        }
        while (is < ie && js < je
                && strs1[ie - 1].equals(strs2[je - 1])) {
            ie--;
            je--;
        }
        return is == ie || js == je;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("My name is Haley", "My Haley").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("of", "A lot of words").expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("Eating", "Eating").expect(true);

}
