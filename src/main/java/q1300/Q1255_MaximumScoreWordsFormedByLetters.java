package q1300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1255. Maximum Score Words Formed by Letters
 * https://leetcode.com/problems/maximum-score-words-formed-by-letters/
 *
 * Given a list of words, list of  single letters (might be repeating) and score of every character.
 *
 * Return the maximum score of any valid set of words formed by using the given letters (words[i] cannot be used two or
 * more times).
 *
 * It is not necessary to use all characters in letters and each letter can only be used once. Score of letters 'a',
 * 'b', 'c', ... ,'z' is given by score[0], score[1], ... , score[25] respectively.
 *
 * Example 1:
 *
 * Input: words = ["dog","cat","dad","good"], letters = ["a","a","c","d","d","d","g","o","o"], score =
 * [1,0,9,5,0,0,3,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0]
 * Output: 23
 * Explanation:
 * Score  a=1, c=9, d=5, g=3, o=2
 * Given letters, we can form the words "dad" (5+1+5) and "good" (3+2+2+5) with a score of 23.
 * Words "dad" and "dog" only get a score of 21.
 *
 * Example 2:
 *
 * Input: words = ["xxxz","ax","bx","cx"], letters = ["z","a","b","c","x","x","x"], score =
 * [4,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,10]
 * Output: 27
 * Explanation:
 * Score  a=4, b=4, c=4, x=5, z=10
 * Given letters, we can form the words "ax" (4+5), "bx" (4+5) and "cx" (4+5) with a score of 27.
 * Word "xxxz" only get a score of 25.
 *
 * Example 3:
 *
 * Input: words = ["leetcode"], letters = ["l","e","t","c","o","d"], score = [0,0,1,1,1,0,0,0,0,0,0,1,0,0,1,0,0,0,0,
 * 1,0,0,0,0,0,0]
 * Output: 0
 * Explanation:
 * Letter "e" can only be used once.
 *
 * Constraints:
 *
 * 1 <= words.length <= 14
 * 1 <= words[i].length <= 15
 * 1 <= letters.length <= 100
 * letters[i].length == 1
 * score.length == 26
 * 0 <= score[i] <= 10
 * words[i], letters[i] contains only lower case English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1255_MaximumScoreWordsFormedByLetters {

    /**
     * 这题没有什么特殊技巧
     */
    @Answer
    public int maxScoreWords(String[] words, char[] letters, int[] score) {
        int[] counts = new int[26];
        for (char letter : letters) {
            counts[letter - 'a']++;
        }
        int[][] requires = new int[words.length][26];
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words[i].length(); j++) {
                requires[i][words[i].charAt(j) - 'a']++;
            }
        }
        return dfs(requires, 0, counts, score);
    }

    private int dfs(int[][] requires, int i, int[] counts, int[] score) {
        if (i == requires.length) {
            return 0;
        }
        int drop = dfs(requires, i + 1, counts, score);

        for (int j = 0; j < 26; j++) {
            if (counts[j] < requires[i][j]) {
                return drop;
            }
        }
        int pick = 0;
        for (int j = 0; j < 26; j++) {
            pick += requires[i][j] * score[j];
            counts[j] -= requires[i][j];
        }
        pick += dfs(requires, i + 1, counts, score);
        for (int j = 0; j < 26; j++) {
            counts[j] += requires[i][j];
        }
        return Math.max(drop, pick);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(
            new String[]{"dog", "cat", "dad", "good"},
            new char[]{'a', 'a', 'c', 'd', 'd', 'd', 'g', 'o', 'o'},
            new int[]{1, 0, 9, 5, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    ).expect(23);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(
            new String[]{"xxxz", "ax", "bx", "cx"},
            new char[]{'z', 'a', 'b', 'c', 'x', 'x', 'x'},
            new int[]{4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 10}
    ).expect(27);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(
            new String[]{"leetcode"},
            new char[]{'l', 'e', 't', 'c', 'o', 'd'},
            new int[]{0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}
    ).expect(0);

}
