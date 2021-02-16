package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1759. Count Number of Homogenous Substrings
 * https://leetcode.com/problems/count-number-of-homogenous-substrings/
 *
 * Given a string s, return the number of homogenous substrings of s. Since the answer may be too large, return it
 * modulo 10^9 + 7.
 *
 * A string is homogenous if all the characters of the string are the same.
 *
 * A substring is a contiguous sequence of characters within a string.
 *
 * Example 1:
 *
 * Input: s = "abbcccaa"
 * Output: 13
 * Explanation: The homogenous substrings are listed as below:
 * "a"   appears 3 times.
 * "aa"  appears 1 time.
 * "b"   appears 2 times.
 * "bb"  appears 1 time.
 * "c"   appears 3 times.
 * "cc"  appears 2 times.
 * "ccc" appears 1 time.
 * 3 + 1 + 2 + 1 + 3 + 2 + 1 = 13.
 *
 * Example 2:
 *
 * Input: s = "xy"
 * Output: 2
 * Explanation: The homogenous substrings are "x" and "y".
 *
 * Example 3:
 *
 * Input: s = "zzzzz"
 * Output: 15
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * s consists of lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1759_CountNumberOfHomogenousSubstrings {

    @Answer
    public int countHomogenous(String s) {
        final int mod = 10_0000_0007;
        int res = 1, count = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i - 1) == s.charAt(i)) {
                count++;
            } else {
                count = 1;
            }
            res = (res + count) % mod;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("abbcccaa").expect(13);

    @TestData
    public DataExpectation example2 = DataExpectation.create("xy").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create("zzzzz").expect(15);

}
