package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1784. Check if Binary String Has at Most One Segment of Ones
 * https://leetcode.com/problems/check-if-binary-string-has-at-most-one-segment-of-ones/
 *
 * Given a binary string s without leading zeros, return true if s contains at most one contiguous segment of
 * ones. Otherwise, return false.
 *
 * Example 1:
 *
 * Input: s = "1001"
 * Output: false
 * Explanation: The ones do not form a contiguous segment.
 *
 * Example 2:
 *
 * Input: s = "110"
 * Output: true
 *
 * Constraints:
 *
 * 1 <= s.length <= 100
 * s[i] is either '0' or '1'.
 * s[0] is '1'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1784_CheckIfBinaryStringHasAtMostOneSegmentOfOnes {

    @Answer
    public boolean checkOnesSegment(String s) {
        int i = 1;
        while (i < s.length() && s.charAt(i) == '1') {
            i++;
        }
        while (i < s.length() && s.charAt(i) == '0') {
            i++;
        }
        return i == s.length();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("1001").expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation.create("110").expect(true);

    @TestData
    public DataExpectation only1 = DataExpectation.create("1").expect(true);

}
