package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 859. Buddy Strings
 * https://leetcode.com/problems/buddy-strings/
 *
 * Given two strings A and B of lowercase letters, return true if and only if we can swap two letters in A so that the
 * result equals B.
 *
 * Example 1:
 *
 * Input: A = "ab", B = "ba"
 * Output: true
 *
 * Example 2:
 *
 * Input: A = "ab", B = "ab"
 * Output: false
 *
 * Example 3:
 *
 * Input: A = "aa", B = "aa"
 * Output: true
 *
 * Example 4:
 *
 * Input: A = "aaaaaaabc", B = "aaaaaaacb"
 * Output: true
 *
 * Example 5:
 *
 * Input: A = "", B = "aa"
 * Output: false
 *
 * Constraints:
 *
 * 0 <= A.length <= 20000
 * 0 <= B.length <= 20000
 * A and B consist only of lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q859_BuddyStrings {

    @Answer
    public boolean buddyStrings(String A, String B) {
        if (A.length() != B.length()) {
            return false;
        }

        // 找不同
        int diff = -1;
        for (int i = 0; i < A.length(); i++) {
            if (A.charAt(i) != B.charAt(i)) {
                switch (diff) {
                    case -2:
                        return false;
                    case -1:
                        diff = i;
                        break;
                    default:
                        if (A.charAt(i) != B.charAt(diff)
                                || A.charAt(diff) != B.charAt(i)) {
                            return false;
                        }
                        diff = -2;
                }
            }
        }
        // 存在1 个交换
        if (diff == -2) {
            return true;
        }
        // 存在1 个不同
        if (diff >= 0) {
            return false;
        }

        // 找相同, 一个字符串中存在相同字符则可以交换
        int mask = 0;
        for (int i = 0; i < A.length(); i++) {
            char c = A.charAt(i);
            if ((mask >> (c - 'a') & 1) == 1) {
                return true;
            }
            mask |= 1 << (c - 'a');
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("ab", "ba").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("ab", "ab").expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("aa", "aa").expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("aaaaaaabc", "aaaaaaacb").expect(true);

    @TestData
    public DataExpectation example5 = DataExpectation.createWith("", "aa").expect(false);

}
