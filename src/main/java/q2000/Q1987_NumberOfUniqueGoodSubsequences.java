package q2000;

import org.junit.runner.RunWith;
import q950.Q940_DistinctSubsequencesII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1987. Number of Unique Good Subsequences
 * https://leetcode.com/problems/number-of-unique-good-subsequences/
 *
 * You are given a binary string binary. A subsequence of binary is considered good if it is not empty and has no
 * leading zeros (with the exception of "0").
 *
 * Find the number of unique good subsequences of binary.
 *
 * For example, if binary = "001", then all the good subsequences are ["0", "0", "1"], so the unique good subsequences
 * are "0" and "1". Note that subsequences "00", "01", and "001" are not good because they have leading zeros.
 *
 * Return the number of unique good subsequences of binary. Since the answer may be very large, return it modulo 10^9 +
 * 7.
 *
 * A subsequence is a sequence that can be derived from another sequence by deleting some or no elements without
 * changing the order of the remaining elements.
 *
 * Example 1:
 *
 * Input: binary = "001"
 * Output: 2
 * Explanation: The good subsequences of binary are ["0", "0", "1"].
 * The unique good subsequences are "0" and "1".
 *
 * Example 2:
 *
 * Input: binary = "11"
 * Output: 2
 * Explanation: The good subsequences of binary are ["1", "1", "11"].
 * The unique good subsequences are "1" and "11".
 *
 * Example 3:
 *
 * Input: binary = "101"
 * Output: 5
 * Explanation: The good subsequences of binary are ["1", "0", "1", "10", "11", "101"].
 * The unique good subsequences are "0", "1", "10", "11", and "101".
 *
 * Constraints:
 *
 * 1 <= binary.length <= 10^5
 * binary consists of only '0's and '1's.
 *
 * 相关题目 {@link Q940_DistinctSubsequencesII}
 */
@RunWith(LeetCodeRunner.class)
public class Q1987_NumberOfUniqueGoodSubsequences {

    /**
     * 解法同 940 题
     */
    @Answer
    public int numberOfUniqueGoodSubsequences(String binary) {
        if (binary.indexOf('0') == -1) {
            // 没有0
            return binary.length();
        }
        final int mod = 10_0000_0007;
        int res = 0, zero = 0, one = 0;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '0') {
                int plus = (res - zero + mod) % mod;
                res = (res + plus) % mod;
                // 以0 结尾的数字增加([10] -> [10, 100] 这样)
                zero = (zero + plus) % mod;
            } else {
                // 1 可以单独成为一个数字
                int plus = (res - one + 1 + mod) % mod;
                res = (res + plus) % mod;
                one = (one + plus) % mod;
            }
        }
        return res + 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("001").expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create("11").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create("101").expect(5);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("100").expect(4);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("1110001").expect(23);

}
