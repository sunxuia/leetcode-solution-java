package q1800;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1782. Count Pairs Of Nodes
 * https://leetcode.com/problems/count-pairs-of-nodes/
 *
 * You are given an undirected graph defined by an integer n, the number of nodes, and a 2D integer array edges, the
 * edges in the graph, where edges[i] = [ui, vi] indicates that there is an undirected edge between ui and vi. You are
 * also given an integer array queries.
 *
 * Let incident(a, b) be defined as the number of edges that are connected to either node a or b.
 *
 * The answer to the jth query is the number of pairs of nodes (a, b) that satisfy both of the following conditions:
 *
 * a < b
 * incident(a, b) > queries[j]
 *
 * Return an array answers such that answers.length == queries.length and answers[j] is the answer of the jth query.
 *
 * Note that there can be multiple edges between the same two nodes.
 *
 * Example 1:
 * (图 Q1782_PIC1.png)
 * Input: n = 4, edges = [[1,2],[2,4],[1,3],[2,3],[2,1]], queries = [2,3]
 * Output: [6,5]
 * Explanation: The calculations for incident(a, b) are shown in the table above.
 * The answers for each of the queries are as follows:
 * - answers[0] = 6. All the pairs have an incident(a, b) value greater than 2.
 * - answers[1] = 5. All the pairs except (3, 4) have an incident(a, b) value greater than 3.
 *
 * Example 2:
 *
 * Input: n = 5, edges = [[1,5],[1,5],[3,4],[2,5],[1,3],[5,1],[2,3],[2,5]], queries = [1,2,3,4,5]
 * Output: [10,10,9,8,6]
 *
 * Constraints:
 *
 * 2 <= n <= 2 * 10^4
 * 1 <= edges.length <= 10^5
 * 1 <= ui, vi <= n
 * ui != vi
 * 1 <= queries.length <= 20
 * 0 <= queries[j] < edges.length
 */
@RunWith(LeetCodeRunner.class)
public class Q1782_CountPairsOfNodes {

    /**
     * 参考 https://walkccc.me/LeetCode/problems/1782/ (没有题解)
     * 这题隐含了图是稀疏矩阵的意思, 因此可以统计节点与节点之间的边的数量.
     */
    @Answer
    public int[] countPairs(int n, int[][] edges, int[] queries) {
        // count[i] 表示节点i 的边
        int[] count = new int[n + 1];
        // shared[a][b] 表示节点a和b 之间边的数量.
        Map<Integer, Integer>[] shared = new Map[n + 1];
        for (int i = 1; i <= n; i++) {
            shared[i] = new HashMap<>();
        }
        for (int[] edge : edges) {
            int a = Math.min(edge[0], edge[1]);
            int b = Math.max(edge[0], edge[1]);
            count[a]++;
            count[b]++;
            shared[a].merge(b, 1, Integer::sum);
        }

        // 按边的数量给节点排序
        int[] sorted = count.clone();
        Arrays.sort(sorted);

        int[] res = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            // 左右两边逼近计算数量
            for (int a = 1, b = n; a < b; ) {
                if (sorted[a] + sorted[b] > queries[q]) {
                    // sorted[a] + sorted[b] > q
                    // sorted[a + 1] + sorted[b] > q
                    // ...
                    // sorted[b - 1] + sorted[b] > q
                    // 于是有 (b - 1) - a + 1 个incident 对大于 queries[q]
                    res[q] += (b--) - a;
                } else {
                    a++;
                }
            }
            // 删除重复边的数量统计
            for (int a = 1; a <= n; a++) {
                for (int b : shared[a].keySet()) {
                    int ct = shared[a].get(b);
                    if (count[a] + count[b] > queries[q]
                            && count[a] + count[b] - ct <= queries[q]) {
                        res[q]--;
                    }
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(4, new int[][]{{1, 2}, {2, 4}, {1, 3}, {2, 3}, {2, 1}}, new int[]{2, 3})
            .expect(new int[]{6, 5});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(5, new int[][]{{1, 5}, {1, 5}, {3, 4}, {2, 5}, {1, 3}, {5, 1}, {2, 3}, {2, 5}},
                    new int[]{1, 2, 3, 4, 5})
            .expect(new int[]{10, 10, 9, 8, 6});

}
