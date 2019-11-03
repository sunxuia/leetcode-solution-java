package q150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/word-break-ii/
 *
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add spaces in s to
 * construct a sentence where each word is a valid dictionary word. Return all such possible sentences.
 *
 * Note:
 *
 * The same word in the dictionary may be reused multiple times in the segmentation.
 * You may assume the dictionary does not contain duplicate words.
 *
 * Example 1:
 *
 * Input:
 * s = "catsanddog"
 * wordDict = ["cat", "cats", "and", "sand", "dog"]
 * Output:
 * [
 * "cats and dog",
 * "cat sand dog"
 * ]
 *
 * Example 2:
 *
 * Input:
 * s = "pineapplepenapple"
 * wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
 * Output:
 * [
 * "pine apple pen apple",
 * "pineapple pen apple",
 * "pine applepen apple"
 * ]
 * Explanation: Note that you are allowed to reuse a dictionary word.
 *
 * Example 3:
 *
 * Input:
 * s = "catsandog"
 * wordDict = ["cats", "dog", "sand", "and", "cat"]
 * Output:
 * []
 */
@RunWith(LeetCodeRunner.class)
public class Q140_WordBreakII {

    @Answer
    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String>[] exists = new List[s.length() + 1];
        exists[s.length()] = Collections.singletonList("");
        return dfs(s, 0, wordDict, exists);
    }

    private List<String> dfs(String s, int start, List<String> wordDict, List<String>[] exists) {
        if (exists[start] == null) {
            exists[start] = new ArrayList<>();
            for (String word : wordDict) {
                if (s.startsWith(word, start)) {
                    List<String> tails = dfs(s, start + word.length(), wordDict, exists);
                    for (String tail : tails) {
                        if (tail.isEmpty()) {
                            exists[start].add(word);
                        } else {
                            exists[start].add(word + " " + tail);
                        }
                    }
                }
            }
        }
        return exists[start];
    }


    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("catsanddog")
            .addArgument(Arrays.asList("cat", "cats", "and", "sand", "dog"))
            .expect(Arrays.asList("cats and dog", "cat sand dog"))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("pineapplepenapple")
            .addArgument(Arrays.asList("apple", "pen", "applepen", "pine", "pineapple"))
            .expect(Arrays.asList("pine apple pen apple", "pineapple pen apple", "pine applepen apple"))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument("catsandog")
            .addArgument(Arrays.asList("cats", "dog", "sand", "and", "cat"))
            .expect(Collections.emptyList())
            .unorderResult()
            .build();

}
