package q1100;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1078. Occurrences After Bigram
 * https://leetcode.com/problems/occurrences-after-bigram/
 *
 * Given words first and second, consider occurrences in some text of the form "first second third", where second comes
 * immediately after first, and third comes immediately after second.
 *
 * For each such occurrence, add "third" to the answer, and return the answer.
 *
 * Example 1:
 *
 * Input: text = "alice is a good girl she is a good student", first = "a", second = "good"
 * Output: ["girl","student"]
 *
 * Example 2:
 *
 * Input: text = "we will we will rock you", first = "we", second = "will"
 * Output: ["we","rock"]
 *
 * Note:
 *
 * 1 <= text.length <= 1000
 * text consists of space separated words, where each word consists of lowercase English letters.
 * 1 <= first.length, second.length <= 10
 * first and second consist of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1078_OccurrencesAfterBigram {

    @Answer
    public String[] findOcurrences(String text, String first, String second) {
        List<String> resList = new ArrayList<>();
        String[] words = text.split(" ");
        for (int i = 0; i < words.length - 2; i++) {
            if (first.equals(words[i]) && second.equals(words[i + 1])) {
                resList.add(words[i + 2]);
            }
        }
        return resList.toArray(new String[0]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("alice is a good girl she is a good student", "a", "good")
            .expect(new String[]{"girl", "student"});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("we will we will rock you", "we", "will")
            .expect(new String[]{"we", "rock"});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith("obo jvezipre obo jnvavldde jvezipre jvezipre jnvavldde jvezipre jvezipre jvezipre "
                            + "y jnvavldde jnvavldde obo jnvavldde jnvavldde obo jnvavldde jnvavldde jvezipre",
                    "jnvavldde", "y")
            .expect(new String[0]);

}
