package q1200;

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
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1178. Number of Valid Words for Each Puzzle
 * https://leetcode.com/problems/number-of-valid-words-for-each-puzzle/
 *
 * With respect to a given puzzle string, a word is valid if both the following conditions are satisfied:
 *
 * word contains the first letter of puzzle.
 * For each letter in word, that letter is in puzzle.
 *
 * For example, if the puzzle is "abcdefg", then valid words are "faced", "cabbage", and "baggage"; while invalid words
 * are "beefed" (doesn't include "a") and "based" (includes "s" which isn't in the puzzle).
 *
 * Return an array answer, where answer[i] is the number of words in the given word list words that are valid with
 * respect to the puzzle puzzles[i].
 *
 * Example :
 *
 * Input:
 * words = ["aaaa","asas","able","ability","actt","actor","access"],
 * puzzles = ["aboveyz","abrodyz","abslute","absoryz","actresz","gaswxyz"]
 * Output: [1,1,3,2,4,0]
 * Explanation:
 * 1 valid word for "aboveyz" : "aaaa"
 * 1 valid word for "abrodyz" : "aaaa"
 * 3 valid words for "abslute" : "aaaa", "asas", "able"
 * 2 valid words for "absoryz" : "aaaa", "asas"
 * 4 valid words for "actresz" : "aaaa", "asas", "actt", "access"
 * There're no valid words for "gaswxyz" cause none of the words in the list contains letter 'g'.
 *
 * Constraints:
 *
 * 1 <= words.length <= 10^5
 * 4 <= words[i].length <= 50
 * 1 <= puzzles.length <= 10^4
 * puzzles[i].length == 7
 * words[i][j], puzzles[i][j] are English lowercase letters.
 * Each puzzles[i] doesn't contain repeated characters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1178_NumberOfValidWordsForEachPuzzle {

    /**
     * 符合题意的解法, 供参考题目意思 (会超时)
     */
//    @Answer
    public List<Integer> findNumOfValidWords_overTime(String[] words, String[] puzzles) {
        List<Integer> res = new ArrayList<>();
        for (String puzzle : puzzles) {
            int count = 0;
            for (String word : words) {
                // 条件 1: word contains the first letter of puzzle.
                if (word.indexOf(puzzle.charAt(0)) == -1) {
                    continue;
                }
                // 条件 2: For each letter in word, that letter is in puzzle.
                boolean find = true;
                for (int i = 0; find && i < word.length(); i++) {
                    find = puzzle.indexOf(word.charAt(i)) > -1;
                }
                if (find) {
                    count++;
                }
            }
            res.add(count);
        }
        return res;
    }

    /**
     * 通过位运算来求得结果.
     * 参考文档 https://github.com/cherryljr/LeetCode/blob/master/Number%20of%20Valid%20Words%20for%20Each%20Puzzle.java
     */
    @Answer
    public List<Integer> findNumOfValidWords(String[] words, String[] puzzles) {
        // counts[word的mask] = word 中的相应次数
        Map<Integer, Integer> counts = new HashMap<>();
        for (String word : words) {
            int mask = 0;
            for (int i = 0; i < word.length(); i++) {
                mask |= 1 << word.charAt(i) - 'a';
            }
            counts.put(mask, counts.getOrDefault(mask, 0) + 1);
        }

        List<Integer> res = new ArrayList<>(puzzles.length);
        for (String puzzle : puzzles) {
            int first = 1 << puzzle.charAt(0) - 'a';
            int mask = 0;
            for (int i = 0; i < puzzle.length(); i++) {
                mask |= 1 << puzzle.charAt(i) - 'a';
            }
            int total = 0;
            // 求 SubSet 的第三种解法, 与 Count 1 in Binary 用到了相近的数学原理 (除去 DFS 和 枚举0~1<<n 详见 SubSet参考链接)
            // 更加适用于一个集合中只能使用某些特定元素的情况，
            // 且此处通过 mask 实现了去重（该做法同样的一个 num 不会被枚举两次）
            for (int curr = mask; curr > 0; curr = (curr - 1) & mask) {
                // 所求的 subset 必须包含第一个字符
                if ((curr & first) == first && counts.containsKey(curr)) {
                    total += counts.get(curr);
                }
            }
            res.add(total);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(
            new String[]{"aaaa", "asas", "able", "ability", "actt", "actor", "access"},
            new String[]{"aboveyz", "abrodyz", "abslute", "absoryz", "actresz", "gaswxyz"})
            .expect(Arrays.asList(1, 1, 3, 2, 4, 0));

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(
            new String[]{"apple", "pleas", "please"},
            new String[]{"aelwxyz", "aelpxyz", "aelpsxy", "saelpxy", "xaelpsy"})
            .expect(Arrays.asList(0, 1, 3, 2, 0));

    @TestData
    public DataExpectation normal2() {
        var file = new TestDataFile("Q1178_TestData_normal2");
        return DataExpectation.createWith(
                TestDataFileHelper.read(file, 1, String[].class),
                TestDataFileHelper.read(file, 2, String[].class)
        ).expect(TestDataFileHelper.read(file, 3, Integer[].class));
    }

    @TestData
    public DataExpectation overTime() {
        var file = new TestDataFile("Q1178_TestData_overTime");
        return DataExpectation.createWith(
                TestDataFileHelper.read(file, 1, String[].class),
                TestDataFileHelper.read(file, 2, String[].class)
        ).expect(TestDataFileHelper.read(file, 3, Integer[].class));
    }

}
