package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1528. Shuffle String
 * https://leetcode.com/problems/shuffle-string/
 *
 * Given a string s and an integer array indices of the same length.
 *
 * The string s will be shuffled such that the character at the ith position moves to indices[i] in the shuffled string.
 *
 * Return the shuffled string.
 *
 * Example 1:
 * <img src="./Q1528_PIC.png">
 * Input: s = "codeleet", indices = [4,5,6,7,0,2,1,3]
 * Output: "leetcode"
 * Explanation: As shown, "codeleet" becomes "leetcode" after shuffling.
 *
 * Example 2:
 *
 * Input: s = "abc", indices = [0,1,2]
 * Output: "abc"
 * Explanation: After shuffling, each character remains in its position.
 *
 * Example 3:
 *
 * Input: s = "aiohn", indices = [3,1,4,2,0]
 * Output: "nihao"
 *
 * Example 4:
 *
 * Input: s = "aaiougrt", indices = [4,0,2,6,7,3,1,5]
 * Output: "arigatou"
 *
 * Example 5:
 *
 * Input: s = "art", indices = [1,0,2]
 * Output: "rat"
 *
 * Constraints:
 *
 * s.length == indices.length == n
 * 1 <= n <= 100
 * s contains only lower-case English letters.
 * 0 <= indices[i] < n
 * All values of indices are unique (i.e. indices is a permutation of the integers from 0 to n - 1).
 */
@RunWith(LeetCodeRunner.class)
public class Q1528_ShuffleString {

    @Answer
    public String restoreString(String s, int[] indices) {
        final int n = s.length();
        char[] cs = new char[n];
        for (int i = 0; i < n; i++) {
            cs[indices[i]] = s.charAt(i);
        }
        return new String(cs);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("codeleet", new int[]{4, 5, 6, 7, 0, 2, 1, 3})
            .expect("leetcode");

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("abc", new int[]{0, 1, 2})
            .expect("abc");

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith("aiohn", new int[]{3, 1, 4, 2, 0})
            .expect("nihao");

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith("aaiougrt", new int[]{4, 0, 2, 6, 7, 3, 1, 5})
            .expect("arigatou");

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith("art", new int[]{1, 0, 2})
            .expect("rat");

}
