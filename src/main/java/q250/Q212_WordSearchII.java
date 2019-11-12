package q250;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import q100.Q079_WordSearch;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/word-search-ii/
 *
 * Given a 2D board and a list of words from the dictionary, find all words in the board.
 *
 * Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those
 * horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.
 *
 *
 *
 * Example:
 *
 * Input:
 * board = [
 * ['o','a','a','n'],
 * ['e','t','a','e'],
 * ['i','h','k','r'],
 * ['i','f','l','v']
 * ]
 * words = ["oath","pea","eat","rain"]
 *
 * Output: ["eat","oath"]
 *
 *
 *
 * Note:
 *
 * All inputs are consist of lowercase letters a-z.
 * The values of words are distinct.
 *
 * 相关题目: {@link Q079_WordSearch}
 */
@RunWith(LeetCodeRunner.class)
public class Q212_WordSearchII {

    @Answer
    public List<String> findWords(char[][] board, String[] words) {
        m = board.length;
        if (m == 0) {
            return Collections.emptyList();
        }
        n = board[0].length;
        this.board = board;
        visited = new boolean[m][n];
        WordDictionary dic = new WordDictionary();
        for (String word : words) {
            addWord(dic, word);
        }
        res = new ArrayList<>();
        StringBuilder path = new StringBuilder();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(path, dic, i, j);
            }
        }
        return res;
    }

    private int m, n;

    private char[][] board;

    private boolean[][] visited;

    private List<String> res;

    private static class WordDictionary {

        private int childCount;

        private WordDictionary[] children = new WordDictionary[26];

        private int parentIdx;

        private WordDictionary parent;

        private boolean deleted;

        // 题目规定单词不存在重复值
        private boolean isWord;

    }

    private void addWord(WordDictionary dic, String word) {
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            WordDictionary next = dic.children[idx];
            if (next == null) {
                dic.children[idx] = next = new WordDictionary();
                dic.childCount++;
                next.parent = dic;
                next.parentIdx = idx;
            }
            dic = next;
        }
        dic.isWord = true;
    }

    private void dfs(StringBuilder path, WordDictionary dic, int i, int j) {
        if (i == -1 || i == m || j == -1 || j == n
                || visited[i][j]
                || dic.deleted
                || (dic = dic.children[board[i][j] - 'a']) == null) {
            return;
        }
        visited[i][j] = true;
        path.append(board[i][j]);
        if (dic.isWord) {
            res.add(path.toString());
            deleteWord(dic);
        }
        dfs(path, dic, i - 1, j);
        dfs(path, dic, i, j + 1);
        dfs(path, dic, i + 1, j);
        dfs(path, dic, i, j - 1);
        visited[i][j] = false;
        path.deleteCharAt(path.length() - 1);
    }

    private void deleteWord(WordDictionary dic) {
        dic.isWord = false;
        if (dic.childCount == 0) {
            dic.deleted = true;
            while (dic.parent != null) {
                dic.parent.children[dic.parentIdx] = null;
                dic = dic.parent;
                if (--dic.childCount > 0 || dic.isWord) {
                    break;
                }
                dic.deleted = true;
            }
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new char[][]{
                    {'o', 'a', 'a', 'n'},
                    {'e', 't', 'a', 'e'},
                    {'i', 'h', 'k', 'r'},
                    {'i', 'f', 'l', 'v'}
            })
            .addArgument(new String[]{"oath", "pea", "eat", "rain"})
            .expect(Arrays.asList("eat", "oath"))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new char[][]{{'a', 'a'}}, new String[]{"a"})
            .expect(new String[]{"a"});

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new char[][]{
                    {'b', 'a', 'a', 'b', 'a', 'b'},
                    {'a', 'b', 'a', 'a', 'a', 'a'},
                    {'a', 'b', 'a', 'a', 'a', 'b'},
                    {'a', 'b', 'a', 'b', 'b', 'a'},
                    {'a', 'a', 'b', 'b', 'a', 'b'},
                    {'a', 'a', 'b', 'b', 'b', 'a'},
                    {'a', 'a', 'b', 'a', 'a', 'b'}
            })
            .addArgument(new String[]{"bbaabaabaaaaabaababaaaaababb", "aabbaaabaaabaabaaaaaabbaaaba",
                    "babaababbbbbbbaabaababaabaaa", "bbbaaabaabbaaababababbbbbaaa", "babbabbbbaabbabaaaaaabbbaaab",
                    "bbbababbbbbbbababbabbbbbabaa", "babababbababaabbbbabbbbabbba", "abbbbbbaabaaabaaababaabbabba",
                    "aabaabababbbbbbababbbababbaa", "aabbbbabbaababaaaabababbaaba", "ababaababaaabbabbaabbaabbaba",
                    "abaabbbaaaaababbbaaaaabbbaab", "aabbabaabaabbabababaaabbbaab", "baaabaaaabbabaaabaabababaaaa",
                    "aaabbabaaaababbabbaabbaabbaa", "aaabaaaaabaabbabaabbbbaabaaa", "abbaabbaaaabbaababababbaabbb",
                    "baabaababbbbaaaabaaabbababbb", "aabaababbaababbaaabaabababab", "abbaaabbaabaabaabbbbaabbbbbb",
                    "aaababaabbaaabbbaaabbabbabab", "bbababbbabbbbabbbbabbbbbabaa", "abbbaabbbaaababbbababbababba",
                    "bbbbbbbabbbababbabaabababaab", "aaaababaabbbbabaaaaabaaaaabb", "bbaaabbbbabbaaabbaabbabbaaba",
                    "aabaabbbbaabaabbabaabababaaa", "abbababbbaababaabbababababbb", "aabbbabbaaaababbbbabbababbbb",
                    "babbbaabababbbbbbbbbaabbabaa"})
            .expect(Arrays.asList("aabbbbabbaababaaaabababbaaba", "abaabbbaaaaababbbaaaaabbbaab",
                    "ababaababaaabbabbaabbaabbaba"))
            .unorderResult()
            .build();

}
