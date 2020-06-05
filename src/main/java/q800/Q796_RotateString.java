package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/rotate-string/
 *
 * We are given two strings, A and B.
 *
 * A shift on A consists of taking string A and moving the leftmost character to the rightmost position. For example,
 * if A = 'abcde', then it will be 'bcdea' after one shift on A. Return True if and only if A can become B after some
 * number of shifts on A.
 *
 * Example 1:
 * Input: A = 'abcde', B = 'cdeab'
 * Output: true
 *
 * Example 2:
 * Input: A = 'abcde', B = 'abced'
 * Output: false
 *
 * Note:
 *
 * A and B will have length at most 100.
 */
@RunWith(LeetCodeRunner.class)
public class Q796_RotateString {

    @Answer
    public boolean rotateString(String A, String B) {
        if (A.isEmpty()) {
            return B.isEmpty();
        }
        StringBuilder sb = new StringBuilder(A);
        for (int i = 0; i < A.length(); i++) {
            if (sb.toString().equals(B)) {
                return true;
            }
            char c = sb.charAt(0);
            sb.deleteCharAt(0);
            sb.append(c);
        }
        return false;
    }

    // LeetCode 上最简洁的解答
    @Answer
    public boolean rotateString2(String A, String B) {
        return A.length() == B.length() && (A + A).contains(B);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("abcde", "cdeab").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("abcde", "abced").expect(false);

    @TestData
    public DataExpectation border = DataExpectation.createWith("", "").expect(true);

}
