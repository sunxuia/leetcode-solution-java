package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1761. Minimum Degree of a Connected Trio in a Graph
 * https://leetcode.com/problems/minimum-degree-of-a-connected-trio-in-a-graph/
 *
 * You are given an undirected graph. You are given an integer n which is the number of nodes in the graph and an array
 * edges, where each edges[i] = [ui, vi] indicates that there is an undirected edge between ui and vi.
 *
 * A connected trio is a set of three nodes where there is an edge between every pair of them.
 *
 * The degree of a connected trio is the number of edges where one endpoint is in the trio, and the other is not.
 *
 * Return the minimum degree of a connected trio in the graph, or -1 if the graph has no connected trios.
 *
 * Example 1:
 * <img src="./Q1761_PIC1.png">
 * Input: n = 6, edges = [[1,2],[1,3],[3,2],[4,1],[5,2],[3,6]]
 * Output: 3
 * Explanation: There is exactly one trio, which is [1,2,3]. The edges that form its degree are bolded in the figure
 * above.
 *
 * Example 2:
 * <img src="./Q1761_PIC2.png">
 * Input: n = 7, edges = [[1,3],[4,1],[4,3],[2,5],[5,6],[6,7],[7,5],[2,6]]
 * Output: 0
 * Explanation: There are exactly three trios:
 * 1) [1,4,3] with degree 0.
 * 2) [2,5,6] with degree 2.
 * 3) [5,6,7] with degree 2.
 *
 * Constraints:
 *
 * 2 <= n <= 400
 * edges[i].length == 2
 * 1 <= edges.length <= n * (n-1) / 2
 * 1 <= ui, vi <= n
 * ui != vi
 * There are no repeated edges.
 */
@RunWith(LeetCodeRunner.class)
public class Q1761_MinimumDegreeOfAConnectedTrioInAGraph {

    @Answer
    public int minTrioDegree(int n, int[][] edges) {
        int[] degrees = new int[n];
        boolean[][] connects = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            connects[i][i] = true;
        }
        for (int[] edge : edges) {
            int u = edge[0] - 1, v = edge[1] - 1;
            connects[u][v] = connects[v][u] = true;
            degrees[u]++;
            degrees[v]++;
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (connects[i][j] && connects[i][k] && connects[j][k]) {
                        int count = degrees[i] + degrees[j] + degrees[k] - 6;
                        res = Math.min(res, count);
                    }
                }
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(6, new int[][]{{1, 2}, {1, 3}, {3, 2}, {4, 1}, {5, 2}, {3, 6}})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(7, new int[][]{{1, 3}, {4, 1}, {4, 3}, {2, 5}, {5, 6}, {6, 7}, {7, 5}, {2, 6}})
            .expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(6, new int[][]{{6, 5}, {4, 3}, {5, 1}, {1, 4}, {2, 3}, {4, 5}, {2, 6}, {1, 3}})
            .expect(3);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(400, TestDataFileHelper.read(int[][].class))
            .expect(1191);

}
