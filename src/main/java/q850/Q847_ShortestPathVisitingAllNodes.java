package q850;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import q950.Q943_FindTheShortestSuperstring;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/shortest-path-visiting-all-nodes/
 *
 * An undirected, connected graph of N nodes (labeled 0, 1, 2, ..., N-1) is given as graph.
 *
 * graph.length = N, and j != i is in the list graph[i] exactly once, if and only if nodes i and j are connected.
 *
 * Return the length of the shortest path that visits every node. You may start and stop at any node, you may revisit
 * nodes multiple times, and you may reuse edges.
 *
 *
 *
 * Example 1:
 *
 * Input: [[1,2,3],[0],[0],[0]]
 * Output: 4
 * Explanation: One possible path is [1,0,2,0,3]
 *
 * Example 2:
 *
 * Input: [[1],[0,2,4],[1,3,4],[2],[1,2]]
 * Output: 4
 * Explanation: One possible path is [0,1,4,2,3]
 *
 *
 *
 * Note:
 *
 * 1 <= graph.length <= 12
 * 0 <= graph[i].length < graph.length
 *
 * 相关题目 {@link Q943_FindTheShortestSuperstring}
 */
@RunWith(LeetCodeRunner.class)
public class Q847_ShortestPathVisitingAllNodes {

    // bfs, https://blog.csdn.net/qq_41855420/article/details/90736398
    @Answer
    public int shortestPathLength(int[][] graph) {
        final int n = graph.length;
        // 当所有节点都访问了的时候, 第n - 1到第0位都是1的状态
        final int allVisited = (1 << n) - 1;
        // passed[i][state] 记录在访问到节点i 并且经过的节点为 state 中位所标识的, 是否已经出现过了(用于剪枝)
        boolean[][] passed = new boolean[n][1 << n];
        // {i, state} i为现在所处的节点, state为到达i 时经过的节点
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            // 初始化所有起点以及对应的状态
            queue.offer(new int[]{i, 1 << i});
        }

        int res = 0;
        while (!queue.isEmpty()) {
            // 每次都将当前队列中的所有状态都向后移动一步
            for (int i = queue.size(); i > 0; i--) {
                int[] curr = queue.poll();
                // 已经访问过所有点
                if (curr[1] == allVisited) {
                    return res;
                }
                // 将 curr[0] 这个位置转移到它能到达的下一个位置
                for (int next : graph[curr[0]]) {
                    // 访问next这个点更新后的路径状态
                    int nextState = curr[1] | (1 << next);
                    // 如果
                    if (!passed[next][nextState]) {
                        passed[next][nextState] = true;
                        queue.offer(new int[]{next, nextState});
                    }
                }
            }
            // 路边的条数自增
            res++;
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{1, 2, 3}, {0}, {0}, {0}}).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1}, {0, 2, 4}, {1, 3, 4}, {2}, {1, 2}}).expect(4);

}
