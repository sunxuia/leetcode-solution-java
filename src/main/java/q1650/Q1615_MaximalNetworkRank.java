package q1650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1615. Maximal Network Rank
 * https://leetcode.com/problems/maximal-network-rank/
 *
 * There is an infrastructure of n cities with some number of roads connecting these cities. Each roads[i] = [ai, bi]
 * indicates that there is a bidirectional road between cities ai and bi.
 *
 * The network rank of two different cities is defined as the total number of directly connected roads to either city.
 * If a road is directly connected to both cities, it is only counted once.
 *
 * The maximal network rank of the infrastructure is the maximum network rank of all pairs of different cities.
 *
 * Given the integer n and the array roads, return the maximal network rank of the entire infrastructure.
 *
 * Example 1:
 * <img src="./Q1615_PIC1.png">
 * Input: n = 4, roads = [[0,1],[0,3],[1,2],[1,3]]
 * Output: 4
 * Explanation: The network rank of cities 0 and 1 is 4 as there are 4 roads that are connected to either 0 or 1. The
 * road between 0 and 1 is only counted once.
 *
 * Example 2:
 * <img src="./Q1615_PIC2.png">
 * Input: n = 5, roads = [[0,1],[0,3],[1,2],[1,3],[2,3],[2,4]]
 * Output: 5
 * Explanation: There are 5 roads that are connected to cities 1 or 2.
 *
 * Example 3:
 *
 * Input: n = 8, roads = [[0,1],[1,2],[2,3],[2,4],[5,6],[5,7]]
 * Output: 5
 * Explanation: The network rank of 2 and 5 is 5. Notice that all the cities do not have to be connected.
 *
 * Constraints:
 *
 * 2 <= n <= 100
 * 0 <= roads.length <= n * (n - 1) / 2
 * roads[i].length == 2
 * 0 <= ai, bi <= n-1
 * ai != bi
 * Each pair of cities has at most one road connecting them.
 */
@RunWith(LeetCodeRunner.class)
public class Q1615_MaximalNetworkRank {

    @Answer
    public int maximalNetworkRank(int n, int[][] roads) {
        int[] total = new int[n];
        int[][] dists = new int[n][n];
        for (int[] road : roads) {
            int a = road[0], b = road[1];
            total[a]++;
            total[b]++;
            dists[a][b]++;
            dists[b][a]++;
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    int rank = total[i] + total[j] - dists[i][j];
                    res = Math.max(res, rank);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(4, new int[][]{{0, 1}, {0, 3}, {1, 2}, {1, 3}})
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(5, new int[][]{{0, 1}, {0, 3}, {1, 2}, {1, 3}, {2, 3}, {2, 4}})
            .expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(8, new int[][]{{0, 1}, {1, 2}, {2, 3}, {2, 4}, {5, 6}, {5, 7}})
            .expect(5);

}
