package q350;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/maximum-product-of-word-lengths/
 *
 * Given a string array words, find the maximum value of length(word[i]) * length(word[j]) where the two words do not
 * share common letters. You may assume that each word will contain only lower case letters. If no such two words
 * exist, return 0.
 *
 * Example 1:
 *
 * Input: ["abcw","baz","foo","bar","xtfn","abcdef"]
 * Output: 16
 * Explanation: The two words can be "abcw", "xtfn".
 *
 * Example 2:
 *
 * Input: ["a","ab","abc","d","cd","bcd","abcd"]
 * Output: 4
 * Explanation: The two words can be "ab", "cd".
 *
 * Example 3:
 *
 * Input: ["a","aa","aaa","aaaa"]
 * Output: 0
 * Explanation: No such pair of words.
 */
@RunWith(LeetCodeRunner.class)
public class Q319_MaximumProductOfWordLengths {

    // O(N^3) 的解法.
    @Answer
    public int maxProduct(String[] words) {
        boolean[] exist = new boolean[26];
        int res = 0;
        for (int i = 0; i < words.length - 1; i++) {
            Arrays.fill(exist, false);
            for (int j = 0; j < words[i].length(); j++) {
                exist[words[i].charAt(j) - 'a'] = true;
            }
            for (int j = i + 1; j < words.length; j++) {
                if (isDifferent(exist, words[j])) {
                    res = Math.max(res, words[i].length() * words[j].length());
                }
            }
        }
        return res;
    }

    private boolean isDifferent(boolean[] exit, String word) {
        for (int i = 0; i < word.length(); i++) {
            if (exit[word.charAt(i) - 'a']) {
                return false;
            }
        }
        return true;
    }

    // LeetCode 上最快的解法, 通过int 的位来保存字母出现的位数, 这样直接通过位与操作就能判断是否一致了.
    @Answer
    public int maxProduct_LeetCode(String[] words) {
        final int len = words.length;
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < words[i].length(); j++) {
                nums[i] |= 1 << (words[i].charAt(j) - 'a');
            }
        }
        int res = 0;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if ((nums[i] & nums[j]) == 0) {
                    res = Math.max(res, words[i].length() * words[j].length());
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new String[]{"abcw", "baz", "foo", "bar", "xtfn", "abcdef"})
            .expect(16);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new String[]{"a", "ab", "abc", "d", "cd", "bcd", "abcd"})
            .expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new String[]{"a", "aa", "aaa", "aaaa"})
            .expect(0);

    @TestData
    public DataExpectation border = DataExpectation
            .create(new String[0])
            .expect(0);

}
