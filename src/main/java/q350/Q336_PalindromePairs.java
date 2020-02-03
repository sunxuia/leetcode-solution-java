package q350;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/palindrome-pairs/
 *
 * Given a list of unique words, find all pairs of distinct indices (i, j) in the given list, so that the
 * concatenation of the two words, i.e. words[i] + words[j] is a palindrome.
 *
 * Example 1:
 *
 * Input: ["abcd","dcba","lls","s","sssll"]
 * Output: [[0,1],[1,0],[3,2],[2,4]]
 * Explanation: The palindromes are ["dcbaabcd","abcddcba","slls","llssssll"]
 *
 * Example 2:
 *
 * Input: ["bat","tab","cat"]
 * Output: [[0,1],[1,0]]
 * Explanation: The palindromes are ["battab","tabbat"]
 */
@RunWith(LeetCodeRunner.class)
public class Q336_PalindromePairs {

    /**
     * 通过字典树来搜索, 时间复杂度降低到 O(NM), n 是单词数, m 是单词长度.
     * 暴力方式时间复杂度 O(N*N*M), 会超时.
     * 网上还有其他方式 https://www.cnblogs.com/grandyang/p/5272039.html
     */
    @Answer
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> res = new ArrayList<>();
        WordDict root = new WordDict();

        // 构建字典树, 将字符串反向保存, 以便后续搜索
        for (int i = 0; i < words.length; i++) {
            WordDict dict = root;
            for (int j = words[i].length() - 1; j >= 0; j--) {
                dict.childIndexes.add(i);
                dict = dict.children.computeIfAbsent(
                        words[i].charAt(j), c -> new WordDict());
            }
            dict.wordIndex = i;
        }

        // 搜索字典树, 搜索 prev + next 的组合
        for (int i = 0; i < words.length; i++) {
            String prev = words[i];
            WordDict dict = root;
            for (int j = 0; dict != null; j++) {
                if (dict.wordIndex >= 0 && dict.wordIndex != i
                        && isPalindrome(prev, j, prev.length() - 1)) {
                    // prev 长
                    res.add(Arrays.asList(i, dict.wordIndex));
                }
                if (j == prev.length()) {
                    break;
                }
                dict = dict.children.get(prev.charAt(j));
            }
            if (dict != null) {
                // next 长
                for (Integer nextIdx : dict.childIndexes) {
                    if (nextIdx != i && isPalindrome(words[nextIdx], 0,
                            words[nextIdx].length() - 1 - prev.length())) {
                        res.add(Arrays.asList(i, nextIdx));
                    }
                }
            }
        }
        return res;
    }

    private static class WordDict {

        // 字典树
        private Map<Character, WordDict> children = new HashMap<>();

        // 经过当前树节点但是并未结束的word 下标
        private List<Integer> childIndexes = new ArrayList<>();

        // 在当前树节点结束的word 下标 (题目中规定每个字符串都是唯一的, 所以这里可以是单个数字)
        private int wordIndex = -1;

    }

    private boolean isPalindrome(String word, int start, int end) {
        while (start < end && word.charAt(start) == word.charAt(end)) {
            start++;
            end--;
        }
        return start >= end;
    }


    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new String[]{"abcd", "dcba", "lls", "s", "sssll"})
            .expect(new int[][]{{0, 1}, {1, 0}, {3, 2}, {2, 4}})
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new String[]{"bat", "tab", "cat"})
            .expect(new int[][]{{0, 1}, {1, 0}})
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation overTime = DataExpectation.builder()
            .addArgument(TestDataFileHelper.readStringArray("Q336_LongTestData"))
            .expect(TestDataFileHelper.read2DArray("Q336_LongTestData_Result"))
            .unorderResult("")
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new String[]{"", "a"})
            .expect(new int[][]{{0, 1}, {1, 0}})
            .unorderResult("")
            .build();

}
