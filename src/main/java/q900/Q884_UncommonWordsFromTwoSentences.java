package q900;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 884. Uncommon Words from Two Sentences
 * https://leetcode.com/problems/uncommon-words-from-two-sentences/
 *
 * We are given two sentences A and B.  (A sentence is a string of space separated words.  Each word consists only of
 * lowercase letters.)
 *
 * A word is uncommon if it appears exactly once in one of the sentences, and does not appear in the other sentence.
 *
 * Return a list of all uncommon words.
 *
 * You may return the list in any order.
 *
 * Example 1:
 *
 * Input: A = "this apple is sweet", B = "this apple is sour"
 * Output: ["sweet","sour"]
 *
 * Example 2:
 *
 * Input: A = "apple apple", B = "banana"
 * Output: ["banana"]
 *
 * Note:
 *
 * 0 <= A.length <= 200
 * 0 <= B.length <= 200
 * A and B both contain only spaces and lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q884_UncommonWordsFromTwoSentences {

    @Answer
    public String[] uncommonFromSentences(String A, String B) {
        Map<String, Integer> counts = new HashMap<>();
        for (String str : A.split(" ")) {
            counts.put(str, counts.getOrDefault(str, 0) + 1);
        }
        for (String str : B.split(" ")) {
            counts.put(str, counts.getOrDefault(str, 0) + 1);
        }
        return counts.keySet().stream()
                .filter(str -> counts.get(str) == 1)
                .toArray(String[]::new);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("this apple is sweet")
            .addArgument("this apple is sour")
            .expect(new String[]{"sweet", "sour"})
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("apple apple")
            .addArgument("banana")
            .expect(new String[]{"banana"})
            .build();

}
