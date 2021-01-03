package q1500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1461. Check If a String Contains All Binary Codes of Size K
 * https://leetcode.com/problems/check-if-a-string-contains-all-binary-codes-of-size-k/
 *
 * Given a binary string s and an integer k.
 *
 * Return True if every binary code of length k is a substring of s. Otherwise, return False.
 *
 * Example 1:
 *
 * Input: s = "00110110", k = 2
 * Output: true
 * Explanation: The binary codes of length 2 are "00", "01", "10" and "11". They can be all found as substrings at
 * indicies 0, 1, 3 and 2 respectively.
 *
 * Example 2:
 *
 * Input: s = "00110", k = 2
 * Output: true
 *
 * Example 3:
 *
 * Input: s = "0110", k = 1
 * Output: true
 * Explanation: The binary codes of length 1 are "0" and "1", it is clear that both exist as a substring.
 *
 * Example 4:
 *
 * Input: s = "0110", k = 2
 * Output: false
 * Explanation: The binary code "00" is of length 2 and doesn't exist in the array.
 *
 * Example 5:
 *
 * Input: s = "0000000001011100", k = 4
 * Output: false
 *
 * Constraints:
 *
 * 1 <= s.length <= 5 * 10^5
 * s consists of 0's and 1's only.
 * 1 <= k <= 20
 */
@RunWith(LeetCodeRunner.class)
public class Q1461_CheckIfAStringContainsAllBinaryCodesOfSizeK {

    @Answer
    public boolean hasAllCodes(String s, int k) {
        if (s.length() < (1 << k) + k - 1) {
            return false;
        }
        boolean[] occurs = new boolean[1 << k];
        int value = 0;
        k--;
        for (int i = 0; i < k; i++) {
            value = value * 2 + s.charAt(i) - '0';
        }
        for (int i = k; i < s.length(); i++) {
            value = value * 2 + s.charAt(i) - '0';
            occurs[value] = true;
            value -= (s.charAt(i - k) - '0') << k;
        }

        for (boolean occur : occurs) {
            if (!occur) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("00110110", 2).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("00110", 2).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("0110", 1).expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("0110", 2).expect(false);

    @TestData
    public DataExpectation example5 = DataExpectation.createWith("0000000001011100", 4).expect(false);

}