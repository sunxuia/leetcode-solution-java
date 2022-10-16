package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1971. Find if Path Exists in Graph
 * https://leetcode.com/problems/find-if-path-exists-in-graph/
 *
 * There is a bi-directional graph with n vertices, where each vertex is labeled from 0 to n - 1 (inclusive). The edges
 * in the graph are represented as a 2D integer array edges, where each edges[i] = [ui, vi] denotes a bi-directional
 * edge between vertex ui and vertex vi. Every vertex pair is connected by at most one edge, and no vertex has an edge
 * to itself.
 *
 * You want to determine if there is a valid path that exists from vertex source to vertex destination.
 *
 * Given edges and the integers n, source, and destination, return true if there is a valid path from source to
 * destination, or false otherwise.
 *
 * Example 1:
 * (图Q1971_PIC1.png)
 * Input: n = 3, edges = [[0,1],[1,2],[2,0]], source = 0, destination = 2
 * Output: true
 * Explanation: There are two paths from vertex 0 to vertex 2:
 * - 0 -> 1 -> 2
 * - 0 -> 2
 *
 * Example 2:
 * (图Q1971_PIC2.png)
 * Input: n = 6, edges = [[0,1],[0,2],[3,5],[5,4],[4,3]], source = 0, destination = 5
 * Output: false
 * Explanation: There is no path from vertex 0 to vertex 5.
 *
 * Constraints:
 *
 * 1 <= n <= 2 * 10^5
 * 0 <= edges.length <= 2 * 10^5
 * edges[i].length == 2
 * 0 <= ui, vi <= n - 1
 * ui != vi
 * 0 <= source, destination <= n - 1
 * There are no duplicate edges.
 * There are no self edges.
 */
@RunWith(LeetCodeRunner.class)
public class Q1971_FindIfPathExistsInGraph {

    @Answer
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        int[] parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }
        for (int[] edge : edges) {
            int v1 = Math.min(edge[0], edge[1]);
            int v2 = Math.max(edge[0], edge[1]);
            parents[findRoot(parents, v2)] = findRoot(parents, v1);
        }
        return findRoot(parents, source) == findRoot(parents, destination);
    }

    private int findRoot(int[] parents, int i) {
        if (parents[i] != i) {
            parents[i] = findRoot(parents, parents[i]);
        }
        return parents[i];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(3, new int[][]{{0, 1}, {1, 2}, {2, 0}}, 0, 2)
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(6, new int[][]{{0, 1}, {0, 2}, {3, 5}, {5, 4}, {4, 3}}, 0, 5)
            .expect(false);

}
