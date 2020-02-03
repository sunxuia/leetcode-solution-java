package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/length-of-last-word/
 *
 * Given a string s consists of upper/lower-case alphabets and empty space characters ' ', return the length of last
 * word in the string.
 *
 * If the last word does not exist, return 0.
 *
 * Note: A word is defined as a character sequence consists of non-space characters only.
 *
 * Example:
 *
 * Input: "Hello World"
 * Output: 5
 */
@RunWith(LeetCodeRunner.class)
public class Q058_LengthOfLastWord {

    @Answer
    public int lengthOfLastWord(String s) {
        int i = s.length() - 1;
        while (i >= 0 && s.charAt(i) == ' ') {
            i--;
        }
        int res = 0;
        while (i >= 0 && s.charAt(i) != ' ') {
            i--;
            res++;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create("Hello World").expect(5);
}
