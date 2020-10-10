package q1200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1170. Compare Strings by Frequency of the Smallest Character
 * https://leetcode.com/problems/compare-strings-by-frequency-of-the-smallest-character/
 *
 * Let's define a function f(s) over a non-empty string s, which calculates the frequency of the smallest character in
 * s. For example, if s = "dcce" then f(s) = 2 because the smallest character is "c" and its frequency is 2.
 *
 * Now, given string arrays queries and words, return an integer array answer, where each answer[i] is the number of
 * words such that f(queries[i]) < f(W), where W is a word in words.
 *
 * Example 1:
 *
 * Input: queries = ["cbd"], words = ["zaaaz"]
 * Output: [1]
 * Explanation: On the first query we have f("cbd") = 1, f("zaaaz") = 3 so f("cbd") < f("zaaaz").
 *
 * Example 2:
 *
 * Input: queries = ["bbb","cc"], words = ["a","aa","aaa","aaaa"]
 * Output: [1,2]
 * Explanation: On the first query only f("bbb") < f("aaaa"). On the second query both f("aaa") and f("aaaa") are both >
 * f("cc").
 *
 * Constraints:
 *
 * 1 <= queries.length <= 2000
 * 1 <= words.length <= 2000
 * 1 <= queries[i].length, words[i].length <= 10
 * queries[i][j], words[i][j] are English lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1170_CompareStringsByFrequencyOfTheSmallestCharacter {

    @Answer
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        // 计算words 中的f 运算的结果, 并保存到桶中(桶排序)
        int[] counts = new int[12];
        for (String word : words) {
            counts[f(word)]++;
        }
        // counts 转换为累计值, counts[i] 表示 >= i 的数量.
        for (int i = 10; i >= 1; i--) {
            counts[i] += counts[i + 1];
        }
        // 计算结果
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            res[i] = counts[f(queries[i]) + 1];
        }
        return res;
    }

    private int f(String str) {
        char min = 'z';
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (min == c) {
                count++;
            } else if (min > c) {
                min = c;
                count = 1;
            }
        }
        return count;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"cbd"}, new String[]{"zaaaz"})
            .expect(new int[]{1});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"bbb", "cc"}, new String[]{"a", "aa", "aaa", "aaaa"})
            .expect(new int[]{1, 2});

}
