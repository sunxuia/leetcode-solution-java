package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1016. Binary String With Substrings Representing 1 To N
 * https://leetcode.com/problems/binary-string-with-substrings-representing-1-to-n/
 *
 * Given a binary string S (a string consisting only of '0' and '1's) and a positive integer N, return true if and only
 * if for every integer X from 1 to N, the binary representation of X is a substring of S.
 *
 * Example 1:
 *
 * Input: S = "0110", N = 3
 * Output: true
 *
 * Example 2:
 *
 * Input: S = "0110", N = 4
 * Output: false
 *
 * Note:
 *
 * 1 <= S.length <= 1000
 * 1 <= N <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1016_BinaryStringWithSubstringsRepresenting1ToN {

    /**
     * 暴力求解
     */
    @Answer
    public boolean queryString(String S, int N) {
        for (int i = 1; i <= N; i++) {
            if (!S.contains(Integer.toBinaryString(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 优化后的方式 (对LeetCode 中的测试用例并不会更快)
     * 因为如果一个数 i 的二进制是S的子串, 那么 i/2 只不过少了一位, 一定也是 S 的子串, 没有必要再算
     * 参考文档 https://xingxingpark.com/Leetcode-1016-Binary-String-With-Substrings-Representing-1-To-N/
     */
    @Answer
    public boolean queryString2(String S, int N) {
        for (int i = N; i > N / 2; i--) {
            if (!S.contains(Integer.toBinaryString(i))) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("0110", 3).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("0110", 4).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("1", 1).expect(true);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("110101011011000011011111000000", 15).expect(false);

}
