package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 983. Minimum Cost For Tickets
 * https://leetcode.com/problems/minimum-cost-for-tickets/
 *
 * In a country popular for train travel, you have planned some train travelling one year in advance.  The days of the
 * year that you will travel is given as an array days.  Each day is an integer from 1 to 365.
 *
 * Train tickets are sold in 3 different ways:
 *
 * a 1-day pass is sold for costs[0] dollars;
 * a 7-day pass is sold for costs[1] dollars;
 * a 30-day pass is sold for costs[2] dollars.
 *
 * The passes allow that many days of consecutive travel.  For example, if we get a 7-day pass on day 2, then we can
 * travel for 7 days: day 2, 3, 4, 5, 6, 7, and 8.
 *
 * Return the minimum number of dollars you need to travel every day in the given list of days.
 *
 * Example 1:
 *
 * Input: days = [1,4,6,7,8,20], costs = [2,7,15]
 * Output: 11
 * Explanation:
 * For example, here is one way to buy passes that lets you travel your travel plan:
 * On day 1, you bought a 1-day pass for costs[0] = $2, which covered day 1.
 * On day 3, you bought a 7-day pass for costs[1] = $7, which covered days 3, 4, ..., 9.
 * On day 20, you bought a 1-day pass for costs[0] = $2, which covered day 20.
 * In total you spent $11 and covered all the days of your travel.
 *
 * Example 2:
 *
 * Input: days = [1,2,3,4,5,6,7,8,9,10,30,31], costs = [2,7,15]
 * Output: 17
 * Explanation:
 * For example, here is one way to buy passes that lets you travel your travel plan:
 * On day 1, you bought a 30-day pass for costs[2] = $15 which covered days 1, 2, ..., 30.
 * On day 31, you bought a 1-day pass for costs[0] = $2 which covered day 31.
 * In total you spent $17 and covered all the days of your travel.
 *
 * Note:
 *
 * 1. 1 <= days.length <= 365
 * 2. 1 <= days[i] <= 365
 * 3. days is in strictly increasing order.
 * 4. costs.length == 3
 * 5. 1 <= costs[i] <= 1000
 *
 * 题解:
 * 1. 这里的火车旅行卖的不是火车票, 卖的是限期通行证, 和坐的什么火车坐多少次没关系.
 * 2. costs 分别表示有效期为1/7/30 天的通行证价格, 范围 [1, 1000].
 * 3. 在火车上旅行的日子以days 表示, 这题中days 参数是 [1, 365] 严格递增的.
 * 4. 现在题目问购买通行证的最小开销.
 */
@RunWith(LeetCodeRunner.class)
public class Q983_MinimumCostForTickets {

    @Answer
    public int mincostTickets(int[] days, int[] costs) {
        final int[] costDays = new int[]{1, 7, 30};
        final int n = days.length;
        // 表示以当前天结束的 1/7/30 天起始天数下标
        int[] prevs = new int[3];
        // dp[i] 表示第 i-1 天的最小开销 (dp[0] 是哨兵)
        int[] dp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            dp[i + 1] = Integer.MAX_VALUE;
            for (int j = 0; j < 3; j++) {
                while (days[prevs[j]] <= days[i] - costDays[j]) {
                    prevs[j]++;
                }
                // 这天的开销 = 时间区间起始前的开销 + 时间区间开销
                dp[i + 1] = Math.min(dp[i + 1], dp[prevs[j]] + costs[j]);
            }
        }
        return dp[n];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 4, 6, 7, 8, 20}, new int[]{2, 7, 15})
            .expect(11);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 30, 31}, new int[]{2, 7, 15})
            .expect(17);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{1, 4, 6, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 21, 22, 23, 27, 28},
                    new int[]{3, 13, 45})
            .expect(44);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{1, 4, 6, 7, 8, 20}, new int[]{7, 2, 15})
            .expect(6);

}
