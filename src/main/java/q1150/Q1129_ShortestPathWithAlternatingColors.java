package q1150;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1129. Shortest Path with Alternating Colors
 * https://leetcode.com/problems/shortest-path-with-alternating-colors/
 *
 * Consider a directed graph, with nodes labelled 0, 1, ..., n-1.  In this graph, each edge is either red or blue, and
 * there could be self-edges or parallel edges.
 *
 * Each [i, j] in red_edges denotes a red directed edge from node i to node j.  Similarly, each [i, j] in blue_edges
 * denotes a blue directed edge from node i to node j.
 *
 * Return an array answer of length n, where each answer[X] is the length of the shortest path from node 0 to node X
 * such that the edge colors alternate along the path (or -1 if such a path doesn't exist).
 *
 * Example 1:
 * Input: n = 3, red_edges = [[0,1],[1,2]], blue_edges = []
 * Output: [0,1,-1]
 * Example 2:
 * Input: n = 3, red_edges = [[0,1]], blue_edges = [[2,1]]
 * Output: [0,1,-1]
 * Example 3:
 * Input: n = 3, red_edges = [[1,0]], blue_edges = [[2,1]]
 * Output: [0,-1,-1]
 * Example 4:
 * Input: n = 3, red_edges = [[0,1]], blue_edges = [[1,2]]
 * Output: [0,1,2]
 * Example 5:
 * Input: n = 3, red_edges = [[0,1],[0,2]], blue_edges = [[1,0]]
 * Output: [0,1,1]
 *
 * Constraints:
 *
 * 1 <= n <= 100
 * red_edges.length <= 400
 * blue_edges.length <= 400
 * red_edges[i].length == blue_edges[i].length == 2
 * 0 <= red_edges[i][j], blue_edges[i][j] < n
 */
@RunWith(LeetCodeRunner.class)
public class Q1129_ShortestPathWithAlternatingColors {

    @Answer
    public int[] shortestAlternatingPaths(int n, int[][] red_edges, int[][] blue_edges) {
        boolean[][][] path = new boolean[2][n][n];
        for (int[] edge : red_edges) {
            path[0][edge[0]][edge[1]] = true;
        }
        for (int[] edge : blue_edges) {
            path[1][edge[0]][edge[1]] = true;
        }

        int[] res = new int[n];
        Arrays.fill(res, -1);
        calculateDist(res, path, 0);
        calculateDist(res, path, 1);
        return res;
    }

    private void calculateDist(int[] res, boolean[][][] path, int color) {
        final int n = res.length;
        boolean[][] visited = new boolean[2][n];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        for (int dist = 0; !queue.isEmpty(); dist++, color = 1 - color) {
            for (int len = queue.size(); len > 0; len--) {
                int node = queue.poll();
                if (visited[color][node]) {
                    continue;
                }
                visited[color][node] = true;
                if (res[node] == -1 || res[node] > dist) {
                    res[node] = dist;
                }
                for (int next = 0; next < n; next++) {
                    if (path[color][node][next]) {
                        queue.add(next);
                    }
                }
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(3, new int[][]{{0, 1}, {1, 2}}, new int[][]{})
            .expect(new int[]{0, 1, -1});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(3, new int[][]{{0, 1}}, new int[][]{{2, 1}})
            .expect(new int[]{0, 1, -1});

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(3, new int[][]{{1, 0}}, new int[][]{{2, 1}})
            .expect(new int[]{0, -1, -1});

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(3, new int[][]{{0, 1}}, new int[][]{{1, 2}})
            .expect(new int[]{0, 1, 2});

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(3, new int[][]{{0, 1}, {0, 2}}, new int[][]{{1, 0}})
            .expect(new int[]{0, 1, 1});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(5, new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 4}}, new int[][]{{1, 2}, {2, 3}, {3, 1}})
            .expect(new int[]{0, 1, 2, 3, 7});

}
