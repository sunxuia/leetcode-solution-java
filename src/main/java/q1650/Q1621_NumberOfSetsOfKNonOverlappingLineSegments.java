package q1650;

import java.math.BigInteger;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1621. Number of Sets of K Non-Overlapping Line Segments
 * https://leetcode.com/problems/number-of-sets-of-k-non-overlapping-line-segments/
 *
 * Given n points on a 1-D plane, where the ith point (from 0 to n-1) is at x = i, find the number of ways we can draw
 * exactly k non-overlapping line segments such that each segment covers two or more points. The endpoints of each
 * segment must have integral coordinates. The k line segments do not have to cover all n points, and they are allowed
 * to share endpoints.
 *
 * Return the number of ways we can draw k non-overlapping line segments. Since this number can be huge, return it
 * modulo 10^9 + 7.
 *
 * Example 1:
 * <img src="./Q1621_PIC.png">
 * Input: n = 4, k = 2
 * Output: 5
 * Explanation:
 * The two line segments are shown in red and blue.
 * The image above shows the 5 different ways {(0,2),(2,3)}, {(0,1),(1,3)}, {(0,1),(2,3)}, {(1,2),(2,3)},
 * {(0,1),(1,2)}.
 *
 * Example 2:
 *
 * Input: n = 3, k = 1
 * Output: 3
 * Explanation: The 3 ways are {(0,1)}, {(0,2)}, {(1,2)}.
 *
 * Example 3:
 *
 * Input: n = 30, k = 7
 * Output: 796297179
 * Explanation: The total number of possible ways to draw 7 line segments is 3796297200. Taking this number modulo 10^9 +
 * 7 gives us 796297179.
 *
 * Example 4:
 *
 * Input: n = 5, k = 3
 * Output: 7
 *
 * Example 5:
 *
 * Input: n = 3, k = 2
 * Output: 1
 *
 * Constraints:
 *
 * 2 <= n <= 1000
 * 1 <= k <= n-1
 */
@RunWith(LeetCodeRunner.class)
public class Q1621_NumberOfSetsOfKNonOverlappingLineSegments {

    @Answer
    public int numberOfSets(int n, int k) {
        final int mod = 10_0000_0007;
        int[][] dp = new int[k][n];
        for (int i = 0; i < n; i++) {
            dp[0][i] = i * (i + 1) / 2;
        }
        for (int j = 1; j < k; j++) {
            long sum = 0, total = 0;
            for (int i = j; i < n; i++) {
                total += sum;
                dp[j][i] = (dp[j][i] + (int) (total % mod)) % mod;
                sum += dp[j - 1][i];
            }
        }
        return dp[k - 1][n - 1];
    }

    /**
     * LeetCode 上比较快的解法.
     */
    @Answer
    public int numberOfSets2(int n, int k) {
        BigInteger res = BigInteger.valueOf(1);
        for (int i = 1; i <= 2 * k; i++) {
            res = res.multiply(BigInteger.valueOf(n + k - i));
            res = res.divide(BigInteger.valueOf(i));
        }
        res = res.mod(BigInteger.valueOf(10_0000_0007));
        return res.intValue();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(4, 2).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(3, 1).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(30, 7).expect(796297179);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(5, 3).expect(7);

    @TestData
    public DataExpectation example5 = DataExpectation.createWith(3, 2).expect(1);

    @TestData
    public DataExpectation overflow = DataExpectation.createWith(33, 20).expect(379405428);

    @TestData
    public DataExpectation overTime = DataExpectation.createWith(1000, 500).expect(70047606);

}
