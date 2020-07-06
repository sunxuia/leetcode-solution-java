package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/shifting-letters/
 *
 * We have a string S of lowercase letters, and an integer array shifts.
 *
 * Call the shift of a letter, the next letter in the alphabet, (wrapping around so that 'z' becomes 'a').
 *
 * For example, shift('a') = 'b', shift('t') = 'u', and shift('z') = 'a'.
 *
 * Now for each shifts[i] = x, we want to shift the first i+1 letters of S, x times.
 *
 * Return the final string after all such shifts to S are applied.
 *
 * Example 1:
 *
 * Input: S = "abc", shifts = [3,5,9]
 * Output: "rpl"
 * Explanation:
 * We start with "abc".
 * After shifting the first 1 letters of S by 3, we have "dbc".
 * After shifting the first 2 letters of S by 5, we have "igc".
 * After shifting the first 3 letters of S by 9, we have "rpl", the answer.
 *
 * Note:
 *
 * 1 <= S.length = shifts.length <= 20000
 * 0 <= shifts[i] <= 10 ^ 9
 */
@RunWith(LeetCodeRunner.class)
public class Q848_ShiftingLetters {

    @Answer
    public String shiftingLetters(String S, int[] shifts) {
        char[] sc = S.toCharArray();
        int offset = 0;
        for (int i = shifts.length - 1; i >= 0; i--) {
            offset = (offset + shifts[i]) % 26;
            sc[i] = (char) ('a' + (sc[i] - 'a' + offset) % 26);
        }
        return new String(sc);
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith("abc", new int[]{3, 5, 9}).expect("rpl");

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("ruu", new int[]{26, 9, 17}).expect("rul");
}
