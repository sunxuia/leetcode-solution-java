package q500;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/concatenated-words/
 *
 * Given a list of words (without duplicates), please write a program that returns all concatenated words in the
 * given list of words.
 *
 * A concatenated word is defined as a string that is comprised entirely of at least two shorter words in the given
 * array.
 *
 * Example:
 *
 * Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
 *
 * Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]
 *
 * Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats";
 * "dogcatsdog" can be concatenated by "dog", "cats" and "dog";
 * "ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
 *
 * Note:
 *
 * The number of elements of the given array will not exceed 10,000
 * The length sum of elements in the given array will not exceed 600,000.
 * All the input string will only include lower case letters.
 * The returned elements order does not matter.
 */
@RunWith(LeetCodeRunner.class)
public class Q472_ConcatenatedWords {

    /**
     * 参考 {@link q150.Q139_WordBreak} 中的解答, 思路类似.
     * 为了避免超时, 将遍历字典改为使用字典树的方式.
     */
    @Answer
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Arrays.sort(words, Comparator.comparingInt(String::length));
        List<String> res = new ArrayList<>();
        final Dict root = new Dict();
        for (String word : words) {
            final int len = word.length();
            if (len == 0) {
                continue;
            }
            boolean[] dp = new boolean[len + 1];
            dp[0] = true;
            for (int i = 0; i < len; i++) {
                if (dp[i]) {
                    // 当前位置和之前都有匹配, 则遍历字典树, 找出下一个匹配的单词
                    Dict dict = root.children[word.charAt(i) - 'a'];
                    for (int j = i + 1; dict != null; j++) {
                        dp[j] = dp[j] || dict.isWord;
                        dict = j == len ? null : dict.children[word.charAt(j) - 'a'];
                    }
                }
            }
            if (dp[len]) {
                // 组合词, 放入结果
                res.add(word);
            } else {
                // 单词, 加入字典树
                Dict dict = root;
                for (int i = 0; i < len; i++) {
                    int idx = word.charAt(i) - 'a';
                    if (dict.children[idx] == null) {
                        dict.children[idx] = new Dict();
                    }
                    dict = dict.children[idx];
                }
                dict.isWord = true;
            }
        }
        return res;
    }

    private static class Dict {

        boolean isWord;

        Dict[] children = new Dict[26];

    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new String[]{"cat", "cats", "catsdogcats", "dog",
                    "dogcatsdog", "hippopotamuses", "rat", "ratcatdogcat"})
            .expect(new String[]{"catsdogcats", "dogcatsdog", "ratcatdogcat"})
            .unorderResult()
            .build();

    @TestData
    public DataExpectation border0 = DataExpectation.create(new String[0]).expect(Collections.emptyList());

    @TestData
    public DataExpectation border1 = DataExpectation.create(new String[]{""}).expect(Collections.emptyList());

    private TestDataFile testDataFile = new TestDataFile("Q472_LongTestData");

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read(testDataFile, 1, String[].class))
            .expect(TestDataFileHelper.read(testDataFile, 2, List.class));

}
