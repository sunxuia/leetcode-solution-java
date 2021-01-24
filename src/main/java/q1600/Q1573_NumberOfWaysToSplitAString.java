package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1573. Number of Ways to Split a String
 * https://leetcode.com/problems/number-of-ways-to-split-a-string/
 *
 * Given a binary string s (a string consisting only of '0's and '1's), we can split s into 3 non-empty strings s1, s2,
 * s3 (s1+ s2+ s3 = s).
 *
 * Return the number of ways s can be split such that the number of characters '1' is the same in s1, s2, and s3.
 *
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: s = "10101"
 * Output: 4
 * Explanation: There are four ways to split s in 3 parts where each part contain the same number of letters '1'.
 * "1|010|1"
 * "1|01|01"
 * "10|10|1"
 * "10|1|01"
 *
 * Example 2:
 *
 * Input: s = "1001"
 * Output: 0
 *
 * Example 3:
 *
 * Input: s = "0000"
 * Output: 3
 * Explanation: There are three ways to split s in 3 parts.
 * "0|0|00"
 * "0|00|0"
 * "00|0|0"
 *
 * Example 4:
 *
 * Input: s = "100100010100110"
 * Output: 12
 *
 * Constraints:
 *
 * 3 <= s.length <= 10^5
 * s[i] is '0' or '1'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1573_NumberOfWaysToSplitAString {

    @Answer
    public int numWays(String s) {
        final int mod = 10_0000_0007;
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        int ones = 0;
        for (int i = 0; i < n; i++) {
            ones += cs[i] - '0';
        }

        if (ones % 3 != 0) {
            return 0;
        }

        if (ones == 0) {
            return (int) ((long) (n - 1) * (n - 2) / 2 % mod);
        }

        int leftS = -1, leftE;
        for (int c = 0; c < ones / 3; ) {
            c += cs[++leftS] - '0';
        }
        leftE = leftS + 1;
        while (cs[leftE] != '1') {
            leftE++;
        }
        int rightS = leftE, rightE;
        for (int c = 1; c < ones / 3; ) {
            c += cs[++rightS] - '0';
        }
        rightE = rightS + 1;
        while (cs[rightE] != '1') {
            rightE++;
        }
        return (int) ((long) (leftE - leftS) * (rightE - rightS) % mod);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("10101").expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create("1001").expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create("0000").expect(3);

    @TestData
    public DataExpectation example4 = DataExpectation.create("100100010100110").expect(12);

    @TestData
    public DataExpectation overflow = DataExpectation.create(TestDataFileHelper.read(String.class)).expect(401097987);

}
