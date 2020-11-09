package q1350;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1307. Verbal Arithmetic Puzzle
 * https://leetcode.com/problems/verbal-arithmetic-puzzle/
 *
 * Given an equation, represented by words on left side and the result on right side.
 *
 * You need to check if the equation is solvable under the following rules:
 *
 * Each character is decoded as one digit (0 - 9).
 * Every pair of different characters they must map to different digits.
 * Each words[i] and result are decoded as one number without leading zeros.
 * Sum of numbers on left side (words) will equal to the number on right side (result).
 *
 * Return True if the equation is solvable otherwise return False.
 *
 * Example 1:
 *
 * Input: words = ["SEND","MORE"], result = "MONEY"
 * Output: true
 * Explanation: Map 'S'-> 9, 'E'->5, 'N'->6, 'D'->7, 'M'->1, 'O'->0, 'R'->8, 'Y'->'2'
 * Such that: "SEND" + "MORE" = "MONEY" ,  9567 + 1085 = 10652
 *
 * Example 2:
 *
 * Input: words = ["SIX","SEVEN","SEVEN"], result = "TWENTY"
 * Output: true
 * Explanation: Map 'S'-> 6, 'I'->5, 'X'->0, 'E'->8, 'V'->7, 'N'->2, 'T'->1, 'W'->'3', 'Y'->4
 * Such that: "SIX" + "SEVEN" + "SEVEN" = "TWENTY" ,  650 + 68782 + 68782 = 138214
 *
 * Example 3:
 *
 * Input: words = ["THIS","IS","TOO"], result = "FUNNY"
 * Output: true
 *
 * Example 4:
 *
 * Input: words = ["LEET","CODE"], result = "POINT"
 * Output: false
 *
 * Constraints:
 *
 * 2 <= words.length <= 5
 * 1 <= words[i].length, result.length <= 7
 * words[i], result contains only upper case English letters.
 * Number of different characters used on the expression is at most 10.
 */
@RunWith(LeetCodeRunner.class)
public class Q1307_VerbalArithmeticPuzzle {

    /**
     * 暴力破解
     */
    @Answer
    public boolean isSolvable(String[] words, String result) {
        int[] starts = new int[26];
        Arrays.fill(starts, -1);
        for (String word : words) {
            updateWord(starts, word);
        }
        updateWord(starts, result);
        return dfs(starts, 0, new int[26], new boolean[10], words, result);
    }

    private void updateWord(int[] starts, String word) {
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'A';
            starts[idx] = Math.max(starts[idx], 0);
        }
        if (word.length() > 1) {
            starts[word.charAt(0) - 'A'] = 1;
        }
    }

    private boolean dfs(int[] starts, int idx, int[] nums, boolean[] used,
            String[] words, String result) {
        while (idx < 26 && starts[idx] == -1) {
            idx++;
        }
        if (idx == 26) {
            int sum = 0;
            for (String word : words) {
                sum += getNumber(nums, word);
            }
            return sum == getNumber(nums, result);
        }
        for (int i = starts[idx]; i < 10; i++) {
            if (!used[i]) {
                used[i] = true;
                nums[idx] = i;
                if (dfs(starts, idx + 1, nums, used, words, result)) {
                    return true;
                }
                used[i] = false;
            }
        }
        return false;
    }

    private int getNumber(int[] nums, String word) {
        int res = 0;
        for (int i = 0; i < word.length(); i++) {
            res = res * 10 + nums[word.charAt(i) - 'A'];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"SEND", "MORE"}, "MONEY")
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"SIX", "SEVEN", "SEVEN"}, "TWENTY")
            .expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new String[]{"THIS", "IS", "TOO"}, "FUNNY")
            .expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new String[]{"LEET", "CODE"}, "POINT")
            .expect(false);

    @TestData
    public DataExpectation border1 = DataExpectation
            .createWith(new String[]{"A", "B"}, "A")
            .expect(true);

}
