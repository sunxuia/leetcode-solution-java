package q2050;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] Maximum Earnings From Taxi
 * https://leetcode.com/problems/maximum-earnings-from-taxi/
 * There are n points on a road you are driving your taxi on. The n points on the road are labeled from 1 to n in the
 * direction you are going, and you want to drive from point 1 to point n to make money by picking up passengers. You
 * cannot change the direction of the taxi.
 *
 * The passengers are represented by a 0-indexed 2D integer array rides, where rides[i] = [starti, endi, tipi]
 * denotes the ith passenger requesting a ride from point starti to point endi who is willing to give a tipi dollar tip.
 *
 * For each passenger i you pick up, you earn endi - starti + tipi dollars. You may only drive at most one passenger
 * at a time.
 *
 * Given n and rides, return the maximum number of dollars you can earn by picking up the passengers optimally.
 *
 * Note: You may drop off a passenger and pick up a different passenger at the same point.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 5, rides = [[2,5,4],[1,5,1]]
 * Output: 7
 * Explanation: We can pick up passenger 0 to earn 5 - 2 + 4 = 7 dollars.
 *
 * Example 2:
 *
 * Input: n = 20, rides = [[1,6,1],[3,10,2],[10,12,3],[11,12,2],[12,15,2],[13,18,1]]
 * Output: 20
 * Explanation: We will pick up the following passengers:
 * - Drive passenger 1 from point 3 to point 10 for a profit of 10 - 3 + 2 = 9 dollars.
 * - Drive passenger 2 from point 10 to point 12 for a profit of 12 - 10 + 3 = 5 dollars.
 * - Drive passenger 5 from point 13 to point 18 for a profit of 18 - 13 + 1 = 6 dollars.
 * We earn 9 + 5 + 6 = 20 dollars in total.
 *
 *
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 * 1 <= rides.length <= 3 * 10^4
 * rides[i].length == 3
 * 1 <= starti < endi <= n
 * 1 <= tipi <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q2008_MaximumEarningsFromTaxi {

    /**
     * 排序的算法, leetcode 上最快的算法是把排序改成了桶排序, 思路类似.
     */
    @Answer
    public long maxTaxiEarnings(int n, int[][] rides) {
        Arrays.sort(rides, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        // dp[i] 标识到第i 位置时空车的最大结果
        long[] dp = new long[n + 1];
        int s = 0;
        for (int i = 1; i <= n; i++) {
            dp[i] = Math.max(dp[i], dp[i - 1]);
            while (s < rides.length && rides[s][0] <= i) {
                // 在此处载客上车, 计算到目的地的费用.
                int start = rides[s][0], end = rides[s][1], fee = rides[s][2];
                dp[end] = Math.max(dp[end], dp[i] + end - start + fee);
                s++;
            }
        }
        return dp[n];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(5, new int[][]{{2, 5, 4}, {1, 5, 1}})
            .expect(7L);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(20, new int[][]{{1, 6, 1}, {3, 10, 2}, {10, 12, 3}, {11, 12, 2}, {12, 15, 2}, {13, 18, 1}})
            .expect(20L);
}
