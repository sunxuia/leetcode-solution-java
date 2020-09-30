package q1150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1137. N-th Tribonacci Number
 * https://leetcode.com/problems/n-th-tribonacci-number/
 *
 * The Tribonacci sequence Tn is defined as follows:
 *
 * T0 = 0, T1 = 1, T2 = 1, and Tn+3 = Tn + Tn+1 + Tn+2 for n >= 0.
 *
 * Given n, return the value of Tn.
 *
 * Example 1:
 *
 * Input: n = 4
 * Output: 4
 * Explanation:
 * T_3 = 0 + 1 + 1 = 2
 * T_4 = 1 + 1 + 2 = 4
 *
 * Example 2:
 *
 * Input: n = 25
 * Output: 1389537
 *
 * Constraints:
 *
 * 0 <= n <= 37
 * The answer is guaranteed to fit within a 32-bit integer, ie. answer <= 2^31 - 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1137_NThTribonacciNumber {

    @Answer
    public int tribonacci(int n) {
        int[] dp = new int[3];
        dp[1] = 1;
        dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i % 3] = dp[(i - 3) % 3] + dp[(i - 2) % 3] + dp[(i - 1) % 3];
        }
        return dp[n % 3];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(4).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(25).expect(1389537);

}
