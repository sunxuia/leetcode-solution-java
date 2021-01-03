package q1500;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1473. Paint House III
 * https://leetcode.com/problems/paint-house-iii/
 *
 * There is a row of m houses in a small city, each house must be painted with one of the n colors (labeled from 1 to
 * n), some houses that has been painted last summer should not be painted again.
 *
 * A neighborhood is a maximal group of continuous houses that are painted with the same color. (For example: houses =
 * [1,2,2,3,3,2,1,1] contains 5 neighborhoods  [{1}, {2,2}, {3,3}, {2}, {1,1}]).
 *
 * Given an array houses, an m * n matrix cost and an integer target where:
 *
 * houses[i]: is the color of the house i, 0 if the house is not painted yet.
 * cost[i][j]: is the cost of paint the house i with the color j+1.
 *
 * Return the minimum cost of painting all the remaining houses in such a way that there are exactly target
 * neighborhoods, if not possible return -1.
 *
 * Example 1:
 *
 * Input: houses = [0,0,0,0,0], cost = [[1,10],[10,1],[10,1],[1,10],[5,1]], m = 5, n = 2, target = 3
 * Output: 9
 * Explanation: Paint houses of this way [1,2,2,1,1]
 * This array contains target = 3 neighborhoods, [{1}, {2,2}, {1,1}].
 * Cost of paint all houses (1 + 1 + 1 + 1 + 5) = 9.
 *
 * Example 2:
 *
 * Input: houses = [0,2,1,2,0], cost = [[1,10],[10,1],[10,1],[1,10],[5,1]], m = 5, n = 2, target = 3
 * Output: 11
 * Explanation: Some houses are already painted, Paint the houses of this way [2,2,1,2,2]
 * This array contains target = 3 neighborhoods, [{2,2}, {1}, {2,2}].
 * Cost of paint the first and last house (10 + 1) = 11.
 *
 * Example 3:
 *
 * Input: houses = [0,0,0,0,0], cost = [[1,10],[10,1],[1,10],[10,1],[1,10]], m = 5, n = 2, target = 5
 * Output: 5
 *
 * Example 4:
 *
 * Input: houses = [3,1,2,3], cost = [[1,1,1],[1,1,1],[1,1,1],[1,1,1]], m = 4, n = 3, target = 3
 * Output: -1
 * Explanation: Houses are already painted with a total of 4 neighborhoods [{3},{1},{2},{3}] different of target = 3.
 *
 * Constraints:
 *
 * m == houses.length == cost.length
 * n == cost[i].length
 * 1 <= m <= 100
 * 1 <= n <= 20
 * 1 <= target <= m
 * 0 <= houses[i] <= n
 * 1 <= cost[i][j] <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1473_PaintHouseIII {

    /**
     * 带缓存的dfs
     */
    @Answer
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
        int[][][] cache = new int[m][n + 1][target + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j <= n; j++) {
                Arrays.fill(cache[i][j], -2);
            }
        }
        return dfs(cache, houses, cost, 0, 0, target);
    }

    private int dfs(int[][][] cache, int[] houses, int[][] cost, int idx, int prev, int target) {
        final int m = houses.length, n = cost[0].length;
        if (idx == m) {
            return target == 0 ? 0 : -1;
        }
        if (target == -1) {
            return -1;
        }
        if (cache[idx][prev][target] != -2) {
            return cache[idx][prev][target];
        }

        if (houses[idx] != 0) {
            int res = dfs(cache, houses, cost, idx + 1, houses[idx], prev == houses[idx] ? target : target - 1);
            cache[idx][prev][target] = res;
            return res;
        }

        int res = -1;
        for (int i = 1; i <= n; i++) {
            int count = dfs(cache, houses, cost, idx + 1, i, i == prev ? target : target - 1);
            if (count >= 0) {
                count += cost[idx][i - 1];
                if (res == -1 || count < res) {
                    res = count;
                }
            }
        }
        cache[idx][prev][target] = res;
        return res;
    }

    /**
     * dp 的解法, 是上面解法的改写.
     */
    @Answer
    public int minCost2(int[] houses, int[][] cost, int m, int n, int target) {
        int[][][] dp = new int[m + 1][n + 1][target + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE);
            }
        }
        dp[0][0][0] = 0;
        for (int i = 0; i < m; i++) {
            if (houses[i] == 0) {
                for (int c = 1; c <= n; c++) {
                    for (int pc = 0; pc <= n; pc++) {
                        for (int t = 1; t <= target; t++) {
                            int prev = c == pc ? dp[i][pc][t] : dp[i][pc][t - 1];
                            if (prev < Integer.MAX_VALUE) {
                                dp[i + 1][c][t] = Math.min(dp[i + 1][c][t], cost[i][c - 1] + prev);
                            }
                        }
                    }
                }
            } else {
                for (int pc = 0; pc <= n; pc++) {
                    for (int t = 1; t <= target; t++) {
                        dp[i + 1][houses[i]][t] = Math.min(dp[i + 1][houses[i]][t],
                                houses[i] == pc ? dp[i][pc][t] : dp[i][pc][t - 1]);
                    }
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            res = Math.min(dp[m][i][target], res);
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{0, 0, 0, 0, 0}, new int[][]{{1, 10}, {10, 1}, {10, 1}, {1, 10}, {5, 1}}, 5, 2, 3)
            .expect(9);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{0, 2, 1, 2, 0}, new int[][]{{1, 10}, {10, 1}, {10, 1}, {1, 10}, {5, 1}}, 5, 2, 3)
            .expect(11);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{0, 0, 0, 0, 0}, new int[][]{{1, 10}, {10, 1}, {1, 10}, {10, 1}, {1, 10}}, 5, 2, 5)
            .expect(5);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{3, 1, 2, 3}, new int[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, 4, 3, 3)
            .expect(-1);

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.read(testDataFile, 1, int[].class),
                    TestDataFileHelper.read(testDataFile, 2, int[][].class), 100, 20, 100)
            .expect(1000000);

}
