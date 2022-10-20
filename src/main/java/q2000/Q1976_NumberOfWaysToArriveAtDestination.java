package q2000;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1976. Number of Ways to Arrive at Destination
 * https://leetcode.com/problems/number-of-ways-to-arrive-at-destination/
 *
 * You are in a city that consists of n intersections numbered from 0 to n - 1 with bi-directional roads between some
 * intersections. The inputs are generated such that you can reach any intersection from any other intersection and that
 * there is at most one road between any two intersections.
 *
 * You are given an integer n and a 2D integer array roads where roads[i] = [ui, vi, timei] means that there is a road
 * between intersections ui and vi that takes timei minutes to travel. You want to know in how many ways you can travel
 * from intersection 0 to intersection n - 1 in the shortest amount of time.
 *
 * Return the number of ways you can arrive at your destination in the shortest amount of time. Since the answer may be
 * large, return it modulo 10^9 + 7.
 *
 * Example 1:
 * (图Q1976_PIC.png)
 * Input: n = 7, roads = [[0,6,7],[0,1,2],[1,2,3],[1,3,3],[6,3,3],[3,5,1],[6,5,1],[2,5,1],[0,4,5],[4,6,2]]
 * Output: 4
 * Explanation: The shortest amount of time it takes to go from intersection 0 to intersection 6 is 7 minutes.
 * The four ways to get there in 7 minutes are:
 * - 0 -> 6
 * - 0 -> 4 -> 6
 * - 0 -> 1 -> 2 -> 5 -> 6
 * - 0 -> 1 -> 3 -> 5 -> 6
 *
 * Example 2:
 *
 * Input: n = 2, roads = [[1,0,10]]
 * Output: 1
 * Explanation: There is only one way to go from intersection 0 to intersection 1, and it takes 10 minutes.
 *
 * Constraints:
 *
 * 1 <= n <= 200
 * n - 1 <= roads.length <= n * (n - 1) / 2
 * roads[i].length == 3
 * 0 <= ui, vi <= n - 1
 * 1 <= timei <= 10^9
 * ui != vi
 * There is at most one road connecting any two intersections.
 * You can reach any intersection from any other intersection.
 */
@RunWith(LeetCodeRunner.class)
public class Q1976_NumberOfWaysToArriveAtDestination {

    @Answer
    public int countPaths(int n, int[][] roads) {
        final int mod = 10_0000_0007;
        long[][] dist = new long[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Long.MAX_VALUE / 2);
        }
        for (int[] road : roads) {
            dist[road[0]][road[1]] = road[2];
            dist[road[1]][road[0]] = road[2];
        }

        // dijkstra 算法计算0 到其他节点的最短路径
        long[] minDist = new long[n];
        Arrays.fill(minDist, Long.MAX_VALUE / 2);
        minDist[0] = 0;
        int[] count = new int[n];
        count[0] = 1;
        Set<Integer> nodes = new HashSet<>();
        nodes.add(0);
        while (!nodes.isEmpty()) {
            int min = nodes.iterator().next();
            for (int node : nodes) {
                if (minDist[min] > minDist[node]) {
                    min = node;
                }
            }
            nodes.remove(min);

            // 计算到这个节点的路径数量
            for (int from = 0; from < n; from++) {
                if (minDist[from] + dist[from][min] == minDist[min]) {
                    count[min] = (count[min] + count[from]) % mod;
                }
            }

            // 松弛最短路径
            for (int to = 0; to < n; to++) {
                if (minDist[min] + dist[min][to] < minDist[to]) {
                    minDist[to] = minDist[min] + dist[min][to];
                    nodes.add(to);
                }
            }
        }
        return count[n - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(7,
                    new int[][]{{0, 6, 7}, {0, 1, 2}, {1, 2, 3}, {1, 3, 3}, {6, 3, 3}, {3, 5, 1}, {6, 5, 1}, {2, 5, 1},
                            {0, 4, 5}, {4, 6, 2}})
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(2, new int[][]{{1, 0, 10}})
            .expect(1);

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(13, TestDataFileHelper.read(testDataFile, 1, int[][].class))
            .expect(184);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(200, TestDataFileHelper.read(testDataFile, 2, int[][].class))
            .expect(1);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(200, TestDataFileHelper.read(testDataFile, 3, int[][].class))
            .expect(940420443);

}
