package q150;

import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/word-break/
 *
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be
 * segmented into a space-separated sequence of one or more dictionary words.
 *
 * Note:
 *
 * The same word in the dictionary may be reused multiple times in the segmentation.
 * You may assume the dictionary does not contain duplicate words.
 *
 * Example 1:
 *
 * Input: s = "leetcode", wordDict = ["leet", "code"]
 * Output: true
 * Explanation: Return true because "leetcode" can be segmented as "leet code".
 *
 * Example 2:
 *
 * Input: s = "applepenapple", wordDict = ["apple", "pen"]
 * Output: true
 * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 * Note that you are allowed to reuse a dictionary word.
 *
 * Example 3:
 *
 * Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * Output: false
 */
@RunWith(LeetCodeRunner.class)
public class Q139_WordBreak {

    // 简单dfs 的方式会超时, 所以改用dp. dfs 的优化可参见下一题(Q140)
    @Answer
    public boolean wordBreak(String s, List<String> wordDict) {
        final int len = s.length();
        boolean[] dp = new boolean[len + 1];
        dp[0] = true;
        for (int i = 0; i < len; i++) {
            if (dp[i]) {
                for (String word : wordDict) {
                    if (s.startsWith(word, i)) {
                        dp[i + word.length()] = true;
                    }
                }
            }
        }
        return dp[len];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("leetcode", Arrays.asList("leet", "code"))
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("applepenapple", Arrays.asList("apple", "pen"))
            .expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat"))
            .expect(false);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab",
                    Arrays.asList("a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa", "aaaaaaaa", "aaaaaaaaa",
                            "aaaaaaaaaa"))
            .expect(false);

}
