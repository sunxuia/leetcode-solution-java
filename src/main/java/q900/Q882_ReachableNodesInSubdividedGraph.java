package q900;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 882. Reachable Nodes In Subdivided Graph
 * https://leetcode.com/problems/reachable-nodes-in-subdivided-graph/
 *
 * Starting with an undirected graph (the "original graph") with nodes from 0 to N-1, subdivisions are made to some of
 * the edges.
 *
 * The graph is given as follows: edges[k] is a list of integer pairs (i, j, n) such that (i, j) is an edge of the
 * original graph, and n is the total number of new nodes on that edge.
 *
 * Then, the edge (i, j) is deleted from the original graph, n new nodes (x_1, x_2, ..., x_n) are added to the original
 * graph, and n+1 new edges (i, x_1), (x_1, x_2), (x_2, x_3), ..., (x_{n-1}, x_n), (x_n, j) are added to the original
 * graph.
 *
 * Now, you start at node 0 from the original graph, and in each move, you travel along one edge.
 *
 * Return how many nodes you can reach in at most M moves.
 *
 * Example 1:
 *
 * Input: edges = [[0,1,10],[0,2,1],[1,2,2]], M = 6, N = 3
 * Output: 13
 * Explanation:
 * The nodes that are reachable in the final graph after M = 6 moves are indicated below.
 *
 * (图Q882_PIC.png)
 *
 * Example 2:
 *
 * Input: edges = [[0,1,4],[1,2,6],[0,2,8],[1,3,1]], M = 10, N = 4
 * Output: 23
 *
 * Note:
 *
 * 0 <= edges.length <= 10000
 * 0 <= edges[i][0] < edges[i][1] < N
 * There does not exist any i != j for which edges[i][0] == edges[j][0] and edges[i][1] == edges[j][1].
 * The original graph has no parallel edges.
 * 0 <= edges[i][2] <= 10000
 * 0 <= M <= 10^9
 * 1 <= N <= 3000
 * A reachable node is a node that can be travelled to using at most M moves starting from node 0.
 */
@RunWith(LeetCodeRunner.class)
public class Q882_ReachableNodesInSubdividedGraph {

    @Answer
    public int reachableNodes(int[][] edges, int M, int N) {
        int[][] paths = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(paths[i], -1);
        }
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1], count = edge[2];
            paths[a][b] = paths[b][a] = count;
        }
        // 求每个点上最大的M 值 (bfs 求最短路径)
        int[] maxMs = new int[N];
        Arrays.fill(maxMs, Integer.MIN_VALUE);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        maxMs[0] = M;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int i = 0; i < N; i++) {
                if (paths[curr][i] >= 0) {
                    int next = maxMs[curr] - paths[curr][i] - 1;
                    if (maxMs[i] < next) {
                        maxMs[i] = next;
                        queue.add(i);
                    }
                }
            }
        }

        int res = 1;
        // 表示旧节点是否被访问过
        boolean[] visited = new boolean[N];
        visited[0] = true;
        // 遍历所有边, 找出访问过的新旧节点
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1], count = edge[2];
            int total = 0;
            // a 可以到达
            if (maxMs[a] >= 0) {
                res += visited[a] ? 0 : 1;
                visited[a] = true;
                total += maxMs[a];
            }
            // b 可以到达
            if (maxMs[b] >= 0) {
                res += visited[b] ? 0 : 1;
                visited[b] = true;
                total += maxMs[b];
            }
            res += Math.min(count, total);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{0, 1, 10}, {0, 2, 1}, {1, 2, 2}}, 6, 3)
            .expect(13);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{0, 1, 4}, {1, 2, 6}, {0, 2, 8}, {1, 3, 1}}, 10, 4)
            .expect(23);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[][]{{1, 2, 4}, {1, 4, 5}, {1, 3, 1}, {2, 3, 4}, {3, 4, 5}}, 17, 5)
            .expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[][]{{3, 4, 8}, {0, 1, 3}, {1, 4, 0}, {1, 2, 3},
                    {0, 3, 2}, {0, 4, 10}, {1, 3, 3}, {2, 4, 3}, {2, 3, 3}, {0, 2, 10}}, 7, 5)
            .expect(43);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.read(int[][].class), 25091255, 3000)
            .expect(50176030);

}
