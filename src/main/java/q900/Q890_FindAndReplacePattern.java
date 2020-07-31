package q900;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 890. Find and Replace Pattern
 * https://leetcode.com/problems/find-and-replace-pattern/
 *
 * You have a list of words and a pattern, and you want to know which words in words matches the pattern.
 *
 * A word matches the pattern if there exists a permutation of letters p so that after replacing every letter x in the
 * pattern with p(x), we get the desired word.
 *
 * (Recall that a permutation of letters is a bijection from letters to letters: every letter maps to another letter,
 * and no two letters map to the same letter.)
 *
 * Return a list of the words in words that match the given pattern.
 *
 * You may return the answer in any order.
 *
 * Example 1:
 *
 * Input: words = ["abc","deq","mee","aqq","dkd","ccc"], pattern = "abb"
 * Output: ["mee","aqq"]
 * Explanation: "mee" matches the pattern because there is a permutation {a -> m, b -> e, ...}.
 * "ccc" does not match the pattern because {a -> c, b -> c, ...} is not a permutation,
 * since a and b map to the same letter.
 *
 * Note:
 *
 * 1 <= words.length <= 50
 * 1 <= pattern.length = words[i].length <= 20
 */
@RunWith(LeetCodeRunner.class)
public class Q890_FindAndReplacePattern {

    @Answer
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        char[] pc = pattern.toCharArray();
        List<String> res = new ArrayList<>();
        char[] wp = new char[128], pw = new char[128];
        ct:
        for (String word : words) {
            Arrays.fill(wp, '0');
            Arrays.fill(pw, '0');
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (wp[c] == '0' && pw[pc[i]] == '0') {
                    wp[c] = pc[i];
                    pw[pc[i]] = c;
                } else if (wp[c] != pc[i] || pw[pc[i]] != c) {
                    continue ct;
                }
            }
            res.add(word);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new String[]{"abc", "deq", "mee", "aqq", "dkd", "ccc"})
            .addArgument("abb")
            .expect(Arrays.asList("mee", "aqq"))
            .unorderResult()
            .build();

}
