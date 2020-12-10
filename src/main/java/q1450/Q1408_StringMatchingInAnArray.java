package q1450;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1408. String Matching in an Array
 * https://leetcode.com/problems/string-matching-in-an-array/
 *
 * Given an array of string words. Return all strings in words which is substring of another word in any order.
 *
 * String words[i] is substring of words[j], if can be obtained removing some characters to left and/or right side of
 * words[j].
 *
 * Example 1:
 *
 * Input: words = ["mass","as","hero","superhero"]
 * Output: ["as","hero"]
 * Explanation: "as" is substring of "mass" and "hero" is substring of "superhero".
 * ["hero","as"] is also a valid answer.
 *
 * Example 2:
 *
 * Input: words = ["leetcode","et","code"]
 * Output: ["et","code"]
 * Explanation: "et", "code" are substring of "leetcode".
 *
 * Example 3:
 *
 * Input: words = ["blue","green","bu"]
 * Output: []
 *
 * Constraints:
 *
 * 1 <= words.length <= 100
 * 1 <= words[i].length <= 30
 * words[i] contains only lowercase English letters.
 * It's guaranteed that words[i] will be unique.
 */
@RunWith(LeetCodeRunner.class)
public class Q1408_StringMatchingInAnArray {

    @Answer
    public List<String> stringMatching(String[] words) {
        List<String> res = new ArrayList<>();
        for (String word : words) {
            for (String parentWord : words) {
                if (parentWord.length() > word.length()
                        && parentWord.contains(word)) {
                    res.add(word);
                    break;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new String[]{"mass", "as", "hero", "superhero"})
            .expect(List.of("as", "hero"))
            .unOrder();

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new String[]{"leetcode", "et", "code"})
            .expect(List.of("et", "code"))
            .unOrder();

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new String[]{"blue", "green", "bu"})
            .expect(List.of());

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new String[]{"leetcoder", "leetcode", "od", "hamlet", "am"})
            .expect(List.of("leetcode", "od", "am"))
            .unOrder();

}
