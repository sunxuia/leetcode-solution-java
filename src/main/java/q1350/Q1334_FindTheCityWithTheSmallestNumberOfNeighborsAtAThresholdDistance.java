package q1350;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1334. Find the City With the Smallest Number of Neighbors at a Threshold Distance
 * https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/
 *
 * There are n cities numbered from 0 to n-1. Given the array edges where edges[i] = [fromi, toi, weighti] represents a
 * bidirectional and weighted edge between cities fromi and toi, and given the integer distanceThreshold.
 *
 * Return the city with the smallest number of cities that are reachable through some path and whose distance is at most
 * distanceThreshold, If there are multiple such cities, return the city with the greatest number.
 *
 * Notice that the distance of a path connecting cities i and j is equal to the sum of the edges' weights along that
 * path.
 *
 * Example 1:
 * <img src="./Q1334_PIC1.png">
 * Input: n = 4, edges = [[0,1,3],[1,2,1],[1,3,4],[2,3,1]], distanceThreshold = 4
 * Output: 3
 * Explanation: The figure above describes the graph.
 * The neighboring cities at a distanceThreshold = 4 for each city are:
 * City 0 -> [City 1, City 2]
 * City 1 -> [City 0, City 2, City 3]
 * City 2 -> [City 0, City 1, City 3]
 * City 3 -> [City 1, City 2]
 * Cities 0 and 3 have 2 neighboring cities at a distanceThreshold = 4, but we have to return city 3 since it has the
 * greatest number.
 *
 * Example 2:
 * <img src="./Q1334_PIC2.png">
 * Input: n = 5, edges = [[0,1,2],[0,4,8],[1,2,3],[1,4,2],[2,3,1],[3,4,1]], distanceThreshold = 2
 * Output: 0
 * Explanation: The figure above describes the graph.
 * The neighboring cities at a distanceThreshold = 2 for each city are:
 * City 0 -> [City 1]
 * City 1 -> [City 0, City 4]
 * City 2 -> [City 3, City 4]
 * City 3 -> [City 2, City 4]
 * City 4 -> [City 1, City 2, City 3]
 * The city 0 has 1 neighboring city at a distanceThreshold = 2.
 *
 * Constraints:
 *
 * 2 <= n <= 100
 * 1 <= edges.length <= n * (n - 1) / 2
 * edges[i].length == 3
 * 0 <= fromi < toi < n
 * 1 <= weighti, distanceThreshold <= 10^4
 * All pairs (fromi, toi) are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1334_FindTheCityWithTheSmallestNumberOfNeighborsAtAThresholdDistance {

    /**
     * 计算每个城市到其他城市的距离, 时间复杂度 O(N^3)
     */
    @Answer
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] dists = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dists[i], distanceThreshold + 1);
            dists[i][i] = 0;
        }
        for (int[] edge : edges) {
            dists[edge[0]][edge[1]] = edge[2];
            dists[edge[1]][edge[0]] = edge[2];
        }

        // 以每个点为起点, 计算能到达的点的数量 (dijkstra 算法)
        Set<Integer> set = new HashSet<>(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dists[i][j] < distanceThreshold) {
                    set.add(j);
                }
            }
            while (!set.isEmpty()) {
                int min = set.iterator().next();
                for (Integer idx : set) {
                    if (dists[i][min] > dists[i][idx]) {
                        min = idx;
                    }
                }
                set.remove(min);
                for (int j = 0; j < n; j++) {
                    int dist = dists[i][min] + dists[min][j];
                    if (dist <= distanceThreshold && dist < dists[i][j]) {
                        dists[i][j] = dist;
                        set.add(j);
                    }
                }
            }
        }

        int res = -1, resCount = n;
        for (int i = 0; i < n; i++) {
            int count = -1;
            for (int j = 0; j < n; j++) {
                count += dists[i][j] <= distanceThreshold ? 1 : 0;
            }
            if (resCount >= count) {
                res = i;
                resCount = count;
            }
        }
        return res;
    }

    /**
     * LeetCode 上的主流算法, 与上面比, 算到每个点距离的做法不同, 时间复杂度 O(N^3).
     */
    @Answer
    public int findTheCity2(int n, int[][] edges, int distanceThreshold) {
        int[][] dists = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dists[i], distanceThreshold + 1);
            dists[i][i] = 0;
        }
        for (int[] edge : edges) {
            dists[edge[0]][edge[1]] = edge[2];
            dists[edge[1]][edge[0]] = edge[2];
        }

        // 这里与上面不同: 以点k 为中转点, 计算两点之间的最短距离
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dists[i][j] = Math.min(dists[i][j], dists[i][k] + dists[k][j]);
                }
            }
        }

        int res = -1, min = n;
        for (int i = 0; i < n; i++) {
            int count = -1;
            for (int j = 0; j < n; j++) {
                count += dists[i][j] <= distanceThreshold ? 1 : 0;
            }
            if (min >= count) {
                res = i;
                min = count;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(4, new int[][]{{0, 1, 3}, {1, 2, 1}, {1, 3, 4}, {2, 3, 1}}, 4)
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(5, new int[][]{{0, 1, 2}, {0, 4, 8}, {1, 2, 3}, {1, 4, 2}, {2, 3, 1}, {3, 4, 1}}, 2)
            .expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(39, TestDataFileHelper.read2DArray("Q1334_TestData"), 6586)
            .expect(38);

}
