package q1050;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1048. Longest String Chain
 * https://leetcode.com/problems/longest-string-chain/
 *
 * Given a list of words, each word consists of English lowercase letters.
 *
 * Let's say word1 is a predecessor of word2 if and only if we can add exactly one letter anywhere in word1 to make it
 * equal to word2.  For example, "abc" is a predecessor of "abac".
 *
 * A word chain is a sequence of words [word_1, word_2, ..., word_k] with k >= 1, where word_1 is a predecessor of
 * word_2, word_2 is a predecessor of word_3, and so on.
 *
 * Return the longest possible length of a word chain with words chosen from the given list of words.
 *
 * Example 1:
 *
 * Input: ["a","b","ba","bca","bda","bdca"]
 * Output: 4
 * Explanation: one of the longest word chain is "a","ba","bda","bdca".
 *
 * Note:
 *
 * 1 <= words.length <= 1000
 * 1 <= words[i].length <= 16
 * words[i] only consists of English lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1048_LongestStringChain {

    @Answer
    public int longestStrChain(String[] words) {
        final int n = words.length;
        boolean[][] predecessors = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                predecessors[i][j] = isPredecessor(words[i], words[j]);
            }
        }

        int[] dist = new int[n];
        int res = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, getDist(predecessors, dist, i));
        }
        return res;
    }

    private boolean isPredecessor(String prev, String next) {
        if (prev.length() + 1 != next.length()) {
            return false;
        }
        for (int i = 0; i < prev.length(); i++) {
            if (prev.charAt(i) != next.charAt(i)) {
                return prev.endsWith(next.substring(i + 1));
            }
        }
        return true;
    }

    private int getDist(boolean[][] predecessors, int[] dist, int curr) {
        if (dist[curr] > 0) {
            return dist[curr];
        }
        final int n = dist.length;
        int res = 1;
        for (int i = 0; i < n; i++) {
            if (predecessors[i][curr]) {
                res = Math.max(res, 1 + getDist(predecessors, dist, i));
            }
        }
        dist[curr] = res;
        return res;
    }

    /**
     * leetcode 上比较快的解法
     */
    @Answer
    public int longestStrChain2(String[] words) {
        // 排序后后面字符串的 predecessor 一定在前面
        Arrays.sort(words, Comparator.comparingInt(String::length));
        // 保存字符串和对应的最长路径
        HashMap<String, Integer> map = new HashMap<>();
        int res = 1;
        for (String word : words) {
            int count = 1;
            for (int i = 0; i < word.length(); i++) {
                // 去掉1 个字符, 寻找 predecessor
                String str = word.substring(0, i) + word.substring(i + 1);
                count = Math.max(count, map.getOrDefault(str, 0) + 1);
            }
            map.put(word, count);
            res = Math.max(res, count);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(new String[]{"a", "b", "ba", "bca", "bda", "bdca"})
            .expect(4);

}
