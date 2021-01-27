package q1600;

import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import q1650.Q1601_MaximumNumberOfAchievableTransferRequests;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1595. Minimum Cost to Connect Two Groups of Points
 * https://leetcode.com/problems/minimum-cost-to-connect-two-groups-of-points/
 *
 * You are given two groups of points where the first group has size1 points, the second group has size2 points, and
 * size1 >= size2.
 *
 * The cost of the connection between any two points are given in an size1 x size2 matrix where cost[i][j] is the cost
 * of connecting point i of the first group and point j of the second group. The groups are connected if each point in
 * both groups is connected to one or more points in the opposite group. In other words, each point in the first group
 * must be connected to at least one point in the second group, and each point in the second group must be connected to
 * at least one point in the first group.
 *
 * Return the minimum cost it takes to connect the two groups.
 *
 * Example 1:
 * <img src="./Q1595_PIC1.png">
 * Input: cost = [[15, 96], [36, 2]]
 * Output: 17
 * Explanation: The optimal way of connecting the groups is:
 * 1--A
 * 2--B
 * This results in a total cost of 17.
 *
 * Example 2:
 * <img src="./Q1595_PIC2.png">
 * Input: cost = [[1, 3, 5], [4, 1, 1], [1, 5, 3]]
 * Output: 4
 * Explanation: The optimal way of connecting the groups is:
 * 1--A
 * 2--B
 * 2--C
 * 3--A
 * This results in a total cost of 4.
 * Note that there are multiple points connected to point 2 in the first group and point A in the second group. This
 * does not matter as there is no limit to the number of points that can be connected. We only care about the minimum
 * total cost.
 *
 * Example 3:
 *
 * Input: cost = [[2, 5, 1], [3, 4, 7], [8, 1, 2], [6, 2, 4], [3, 8, 8]]
 * Output: 10
 *
 * Constraints:
 *
 * size1 == cost.length
 * size2 == cost[i].length
 * 1 <= size1, size2 <= 12
 * size1 >= size2
 * 0 <= cost[i][j] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1595_MinimumCostToConnectTwoGroupsOfPoints {

    /**
     * 根据 hint, 可以用 bit mask 来表示右边与左边连接的情况.
     * 其实就是位运算暴力破解的方式, 这种做法比较慢.
     * 另一个相似题目 {@link Q1601_MaximumNumberOfAchievableTransferRequests}
     */
    @Answer
    public int connectTwoGroups(List<List<Integer>> cost) {
        final int size1 = cost.size();
        final int size2 = cost.get(0).size();
        final int range = 1 << size2;
        int[][] dp = new int[size1][range];
        for (int mask = 1; mask < range; mask++) {
            dp[0][mask] = getCost(cost.get(0), mask);
        }
        for (int i = 1; i < size1; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
            for (int curr = 1; curr < range; curr++) {
                int sum = getCost(cost.get(i), curr);
                for (int mask = 1; mask < range; mask++) {
                    dp[i][curr | mask] = Math.min(dp[i][curr | mask], sum + dp[i - 1][mask]);
                }
            }
        }
        return dp[size1 - 1][range - 1];
    }

    private int getCost(List<Integer> cost, int mask) {
        int res = 0;
        for (int i = 0; i < cost.size(); i++) {
            if ((mask >> i & 1) == 1) {
                res += cost.get(i);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(List.of(List.of(15, 96), List.of(36, 2)))
            .expect(17);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(List.of(List.of(1, 3, 5), List.of(4, 1, 1), List.of(1, 5, 3)))
            .expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(List.of(List.of(2, 5, 1), List.of(3, 4, 7), List.of(8, 1, 2), List.of(6, 2, 4), List.of(3, 8, 8)))
            .expect(10);

}
