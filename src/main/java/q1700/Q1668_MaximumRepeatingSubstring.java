package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1668. Maximum Repeating Substring
 * https://leetcode.com/problems/maximum-repeating-substring/
 *
 * For a string sequence, a string word is k-repeating if word concatenated k times is a substring of sequence. The
 * word's maximum k-repeating value is the highest value k where word is k-repeating in sequence. If word is not a
 * substring of sequence, word's maximum k-repeating value is 0.
 *
 * Given strings sequence and word, return the maximum k-repeating value of word in sequence.
 *
 * Example 1:
 *
 * Input: sequence = "ababc", word = "ab"
 * Output: 2
 * Explanation: "abab" is a substring in "ababc".
 *
 * Example 2:
 *
 * Input: sequence = "ababc", word = "ba"
 * Output: 1
 * Explanation: "ba" is a substring in "ababc". "baba" is not a substring in "ababc".
 *
 * Example 3:
 *
 * Input: sequence = "ababc", word = "ac"
 * Output: 0
 * Explanation: "ac" is not a substring in "ababc".
 *
 * Constraints:
 *
 * 1 <= sequence.length <= 100
 * 1 <= word.length <= 100
 * sequence and word contains only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1668_MaximumRepeatingSubstring {

    @Answer
    public int maxRepeating(String sequence, String word) {
        final char[] sc = sequence.toCharArray();
        final char[] wc = word.toCharArray();
        final int m = sc.length, n = wc.length;
        int res = 0;
        for (int i = 0; i <= m - n; i++) {
            int wi = 0;
            for (int j = i; j < m; j++, wi++) {
                if (sc[j] != wc[wi % n]) {
                    break;
                }
            }
            res = Math.max(res, wi / n);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("ababc", "ab").expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("ababc", "ba").expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("ababc", "ac").expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("a", "a").expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith("aaabaaaabaaabaaaabaaaabaaaabaaaaba", "aaaba")
            .expect(5);

}
