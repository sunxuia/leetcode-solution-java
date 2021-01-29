package q1650;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1617. Count Subtrees With Max Distance Between Cities
 * https://leetcode.com/problems/count-subtrees-with-max-distance-between-cities/
 *
 * There are n cities numbered from 1 to n. You are given an array edges of size n-1, where edges[i] = [ui, vi]
 * represents a bidirectional edge between cities ui and vi. There exists a unique path between each pair of cities. In
 * other words, the cities form a tree.
 *
 * A subtree is a subset of cities where every city is reachable from every other city in the subset, where the path
 * between each pair passes through only the cities from the subset. Two subtrees are different if there is a city in
 * one subtree that is not present in the other.
 *
 * For each d from 1 to n-1, find the number of subtrees in which the maximum distance between any two cities in the
 * subtree is equal to d.
 *
 * Return an array of size n-1 where the dth element (1-indexed) is the number of subtrees in which the maximum distance
 * between any two cities is equal to d.
 *
 * Notice that the distance between the two cities is the number of edges in the path between them.
 *
 * Example 1:
 * <img src="./Q1617_PIC.png">
 * Input: n = 4, edges = [[1,2],[2,3],[2,4]]
 * Output: [3,4,0]
 * Explanation:
 * The subtrees with subsets {1,2}, {2,3} and {2,4} have a max distance of 1.
 * The subtrees with subsets {1,2,3}, {1,2,4}, {2,3,4} and {1,2,3,4} have a max distance of 2.
 * No subtree has two nodes where the max distance between them is 3.
 *
 * Example 2:
 *
 * Input: n = 2, edges = [[1,2]]
 * Output: [1]
 *
 * Example 3:
 *
 * Input: n = 3, edges = [[1,2],[2,3]]
 * Output: [2,1]
 *
 * Constraints:
 *
 * 2 <= n <= 15
 * edges.length == n-1
 * edges[i].length == 2
 * 1 <= ui, vi <= n
 * All pairs (ui, vi) are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1617_CountSubtreesWithMaxDistanceBetweenCities {

    /**
     * @formatter:off 
     * 参考文档
     * https://leetcode-cn.com/problems/count-subtrees-with-max-distance-between-cities/solution/5538-java-6msfloyedbfszhuang-ya-by-zhangyixing/
     * @formatter:on
     */
    @Answer
    public int[] countSubgraphsForEachDiameter(int n, int[][] edges) {
        // 表示两点之间的距离
        int[][] dists = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dists[i], n);
            dists[i][i] = 0;
        }
        // dp[mask] 表示节点集合(mask 表示) 的最大距离的节点数(距离+1)
        int[] dp = new int[1 << n];
        for (int[] edge : edges) {
            int c1 = edge[0] - 1;
            int c2 = edge[1] - 1;
            dists[c1][c2] = dists[c2][c1] = 1;
            dp[(1 << c1) | (1 << c2)] = 1;
        }

        // Floyed 求每两点之间的最短距离
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dists[i][j] = Math.min(dists[i][j], dists[i][k] + dists[k][j]);
                }
            }
        }

        // 从小到大遍历各种可能的状态
        for (int mask = 1; mask < (1 << n); mask++) {
            if (dp[mask] == 0) {
                // 无效组合
                continue;
            }
            // 加上1 个节点构成新的集合, 判断是否是组成有效的子树
            for (int i = 0; i < n; i++) {
                int next = mask + (1 << i);
                // 判断这个点是否已经在mask 中或者next 已经被访问过了.
                // 比如 111 = 101 + 10 = 11 + 100 添加点的顺序不同, 但是能得出同样的一棵子树.
                if ((mask >> i & 1) == 1 || dp[next] != 0) {
                    continue;
                }
                // 检查next 的集合中是否有和 mask 中的点相连
                for (int j = 0; j < n; j++) {
                    if ((mask >> j & 1) == 1 && dists[i][j] == 1) {
                        dp[next] = dp[mask];
                        break;
                    }
                }
                // 如果不连通则集合无效
                if (dp[next] == 0) {
                    continue;
                }
                // 更新next 的子树最大距离,
                // 针对新加入的i 点计算i 到其他点的距离即可.
                for (int j = 0; j < n; j++) {
                    if ((mask >> j & 1) == 1) {
                        dp[next] = Math.max(dp[next], dists[i][j]);
                    }
                }
            }
        }

        // 计算结果, dp 中计算的是节点数所以距离要-1
        int[] res = new int[n - 1];
        for (int count : dp) {
            if (count > 0) {
                res[count - 1]++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(4, new int[][]{{1, 2}, {2, 3}, {2, 4}})
            .expect(new int[]{3, 4, 0});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(2, new int[][]{{1, 2}})
            .expect(new int[]{1});

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(3, new int[][]{{1, 2}, {2, 3}})
            .expect(new int[]{2, 1});

}
