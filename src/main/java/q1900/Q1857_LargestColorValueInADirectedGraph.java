package q1900;

import javax.swing.plaf.IconUIResource;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Hard] 1857. Largest Color Value in a Directed Graph
 * https://leetcode.com/problems/largest-color-value-in-a-directed-graph/
 *
 * There is a directed graph of n colored nodes and m edges. The nodes are numbered from 0 to n - 1.
 *
 * You are given a string colors where colors[i] is a lowercase English letter representing the color of the ith node in
 * this graph (0-indexed). You are also given a 2D array edges where edges[j] = [aj, bj] indicates that there is a
 * directed edge from node aj to node bj.
 *
 * A valid path in the graph is a sequence of nodes x1 -> x2 -> x3 -> ... -> xk such that there is a directed edge from
 * xi to xi+1 for every 1 <= i < k. The color value of the path is the number of nodes that are colored the most
 * frequently occurring color along that path.
 *
 * Return the largest color value of any valid path in the given graph, or -1 if the graph contains a cycle.
 *
 * Example 1:
 * (图Q1857_PIC1.png)
 * Input: colors = "abaca", edges = [[0,1],[0,2],[2,3],[3,4]]
 * Output: 3
 * Explanation: The path 0 -> 2 -> 3 -> 4 contains 3 nodes that are colored "a" (red in the above image).
 *
 * Example 2:
 * (图Q1857_PIC2.png)
 * Input: colors = "a", edges = [[0,0]]
 * Output: -1
 * Explanation: There is a cycle from 0 to 0.
 *
 * Constraints:
 *
 * n == colors.length
 * m == edges.length
 * 1 <= n <= 10^5
 * 0 <= m <= 10^5
 * colors consists of lowercase English letters.
 * 0 <= aj, bj < n
 */
@RunWith(LeetCodeRunner.class)
public class Q1857_LargestColorValueInADirectedGraph {

    @Answer
    public int largestPathValue(String colors, int[][] edges) {
        final int n = colors.length();
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
            nodes[i].color = colors.charAt(i) - 'a';
        }
        for (int[] edge : edges) {
            nodes[edge[0]].neighbors.add(nodes[edge[1]]);
        }

        for (int i = 0; i < n; i++) {
            if (dfs(nodes[i])) {
                return -1;
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 26; j++) {
                res = Math.max(res, nodes[i].counts[j]);
            }
        }
        return res;
    }

    // 返回true 表示存在循环
    private boolean dfs(Node node) {
        if (node.path) {
            return true;
        }
        if (node.visited) {
            return false;
        }
        node.visited = true;
        node.path = true;
        for (Node neighbor : node.neighbors) {
            if (dfs(neighbor)) {
                return true;
            }
            for (int i = 0; i < 26; i++) {
                node.counts[i] = Math.max(node.counts[i], neighbor.counts[i]);
            }
        }
        node.counts[node.color]++;
        node.path = false;
        return false;
    }

    private static class Node {

        int color;

        int[] counts = new int[26];

        boolean path, visited;

        List<Node> neighbors = new ArrayList<>();

    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("abaca", new int[][]{{0, 1}, {0, 2}, {2, 3}, {3, 4}})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("a", new int[][]{{0, 0}})
            .expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("hhqhuqhqff",
                    new int[][]{{0, 1}, {0, 2}, {2, 3}, {3, 4}, {3, 5}, {5, 6}, {2, 7}, {6, 7}, {7, 8}, {3, 8},
                            {5, 8}, {8, 9}, {3, 9}, {6, 9}})
            .expect(3);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("nnllnzznn",
                    new int[][]{{0, 1}, {1, 2}, {2, 3}, {2, 4}, {3, 5}, {4, 6}, {3, 6}, {5, 6}, {6, 7}, {7, 8}})
            .expect(5);

}
