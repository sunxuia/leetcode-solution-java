package q150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/word-ladder/
 *
 * Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation
 * sequence from beginWord to endWord, such that:
 *
 * Only one letter can be changed at a time.
 * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
 *
 * Note:
 *
 * Return 0 if there is no such transformation sequence.
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.
 * You may assume no duplicates in the word list.
 * You may assume beginWord and endWord are non-empty and are not the same.
 *
 * Example 1:
 *
 * Input:
 * beginWord = "hit",
 * endWord = "cog",
 * wordList = ["hot","dot","dog","lot","log","cog"]
 *
 * Output: 5
 *
 * Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 * return its length 5.
 *
 * Example 2:
 *
 * Input:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log"]
 *
 * Output: 0
 *
 * Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
 */
@RunWith(LeetCodeRunner.class)
public class Q127_WordLadder {

    // 这题就是上题求最短路径的部分
    @Answer
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int end = wordList.indexOf(endWord);
        if (end == -1) {
            return 0;
        }
        int start = wordList.indexOf(beginWord);
        if (start == -1) {
            start = wordList.size();
            wordList = new ArrayList<>(wordList);
            wordList.add(beginWord);
        }

        final int len = wordList.size(), wordLen = beginWord.length();

        boolean[][] linked = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                int count = 0;
                String si = wordList.get(i), sj = wordList.get(j);
                for (int k = 0; k < wordLen; k++) {
                    if (si.charAt(k) != sj.charAt(k)) {
                        count++;
                    }
                }
                linked[i][j] = linked[j][i] = count == 1;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        for (int count = 1; !queue.isEmpty(); count++) {
            for (int i = queue.size(); i > 0; i--) {
                int p = queue.remove();
                if (p == end) {
                    return count;
                }
                for (int to = 0; to < len; to++) {
                    if (linked[p][to]) {
                        queue.add(to);
                        linked[p][to] = linked[to][p] = false;
                    }
                }
            }
        }
        return 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("hit")
            .addArgument("cog")
            .addArgument(Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"))
            .expect(5)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("hit")
            .addArgument("cog")
            .addArgument(Arrays.asList("hot", "dot", "dog", "lot", "log"))
            .expect(0)
            .build();
}
