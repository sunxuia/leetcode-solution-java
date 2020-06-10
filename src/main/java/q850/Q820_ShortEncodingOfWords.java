package q850;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/short-encoding-of-words/
 *
 * Given a list of words, we may encode it by writing a reference string S and a list of indexes A.
 *
 * For example, if the list of words is ["time", "me", "bell"], we can write it as S = "time#bell#" and indexes = [0,
 * 2, 5].
 *
 * Then for each index, we will recover the word by reading from the reference string from that index until we reach
 * a "#" character.
 *
 * What is the length of the shortest reference string S possible that encodes the given words?
 *
 * Example:
 *
 * Input: words = ["time", "me", "bell"]
 * Output: 10
 * Explanation: S = "time#bell#" and indexes = [0, 2, 5].
 *
 *
 *
 * Note:
 *
 * 1 <= words.length <= 2000.
 * 1 <= words[i].length <= 7.
 * Each word has only lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q820_ShortEncodingOfWords {

    @Answer
    public int minimumLengthEncoding(String[] words) {
        int res = 0;
        Dict root = new Dict();
        root.isEnd = false;
        for (String word : words) {
            Dict dict = root;
            int depth = 1;
            for (int i = word.length() - 1; i >= 0; i--) {
                int prev = word.charAt(i) - 'a';
                if (dict.isEnd) {
                    // 这是一个单词结尾, 但是将要被这个字符串覆盖
                    res -= depth;
                    dict.isEnd = false;
                }
                if (dict.prev[prev] == null) {
                    // 可能是一个新的单词
                    res += depth + 1;
                    dict.prev[prev] = new Dict();
                }
                dict = dict.prev[prev];
                depth++;
            }
        }
        return res;
    }

    private static class Dict {

        boolean isEnd = true;

        Dict[] prev = new Dict[26];
    }

    // LeetCode 上比较快的解决方法, 思路也比较简单
    @Answer
    public int minimumLengthEncoding2(String[] words) {
        Set<String> set = new HashSet<>(Arrays.asList(words));
        for (String word : words) {
            // 从原有集合中直接移除尾部相同的字符串.
            for (int i = 1; i < word.length(); i++) {
                set.remove(word.substring(i));
            }
        }

        int res = 0;
        for (String word : set) {
            res += word.length() + 1;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new String[]{"time", "me", "bell"}).expect(10);

}
