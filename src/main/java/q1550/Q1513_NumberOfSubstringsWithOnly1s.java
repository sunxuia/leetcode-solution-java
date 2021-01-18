package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1513. Number of Substrings With Only 1s
 * https://leetcode.com/problems/number-of-substrings-with-only-1s/
 *
 * Given a binary string s (a string consisting only of '0' and '1's).
 *
 * Return the number of substrings with all characters 1's.
 *
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: s = "0110111"
 * Output: 9
 * Explanation: There are 9 substring in total with only 1's characters.
 * "1" -> 5 times.
 * "11" -> 3 times.
 * "111" -> 1 time.
 *
 * Example 2:
 *
 * Input: s = "101"
 * Output: 2
 * Explanation: Substring "1" is shown 2 times in s.
 *
 * Example 3:
 *
 * Input: s = "111111"
 * Output: 21
 * Explanation: Each substring contains only 1's characters.
 *
 * Example 4:
 *
 * Input: s = "000"
 * Output: 0
 *
 * Constraints:
 *
 * s[i] == '0' or s[i] == '1'
 * 1 <= s.length <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1513_NumberOfSubstringsWithOnly1s {

    @Answer
    public int numSub(String s) {
        final int mod = 10_0000_0007;
        int res = 0, count = 0;
        for (int i = 0; i < s.length(); i++) {
            int val = s.charAt(i) - '0';
            count = (count + 1) * val;
            res = (res + count) % mod;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("0110111").expect(9);

    @TestData
    public DataExpectation example2 = DataExpectation.create("101").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create("111111").expect(21);

    @TestData
    public DataExpectation example4 = DataExpectation.create("000").expect(0);

    // 81016 个1
    @TestData
    public DataExpectation normal1 = DataExpectation.create(TestDataFileHelper.read(String.class)).expect(200542505);

}
