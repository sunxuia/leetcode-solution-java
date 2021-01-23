package q1600;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1557. Minimum Number of Vertices to Reach All Nodes
 * https://leetcode.com/problems/minimum-number-of-vertices-to-reach-all-nodes/
 *
 * Given a directed acyclic graph, with n vertices numbered from 0 to n-1, and an array edges where edges[i] = [fromi,
 * toi] represents a directed edge from node fromi to node toi.
 *
 * Find the smallest set of vertices from which all nodes in the graph are reachable. It's guaranteed that a unique
 * solution exists.
 *
 * Notice that you can return the vertices in any order.
 *
 * Example 1:
 * <img src="./Q1557_PIC1.png">
 * Input: n = 6, edges = [[0,1],[0,2],[2,5],[3,4],[4,2]]
 * Output: [0,3]
 * Explanation: It's not possible to reach all the nodes from a single vertex. From 0 we can reach [0,1,2,5]. From 3 we
 * can reach [3,4,2,5]. So we output [0,3].
 *
 * Example 2:
 * <img src="./Q1557_PIC2.png">
 * Input: n = 5, edges = [[0,1],[2,1],[3,1],[1,4],[2,4]]
 * Output: [0,2,3]
 * Explanation: Notice that vertices 0, 3 and 2 are not reachable from any other node, so we must include them. Also any
 * of these vertices can reach nodes 1 and 4.
 *
 * Constraints:
 *
 * 2 <= n <= 10^5
 * 1 <= edges.length <= min(10^5, n * (n - 1) / 2)
 * edges[i].length == 2
 * 0 <= fromi, toi < n
 * All pairs (fromi, toi) are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1557_MinimumNumberOfVerticesToReachAllNodes {

    /**
     * 并查集的解法.
     */
    @Answer
    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
        int[] parents = new int[n];
        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }
        for (List<Integer> edge : edges) {
            int from = edge.get(0), to = edge.get(1);
            // 这里不要溯源到 to 的 root, 直接改变该节点的上级.
            parents[to] = findRoot(parents, from);
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (parents[i] == i) {
                res.add(i);
            }
        }
        return res;
    }

    private int findRoot(int[] parents, int i) {
        if (parents[i] == i) {
            return i;
        }
        return parents[i] = findRoot(parents, parents[i]);
    }

    /**
     * LeetCode 上比较快的做法.
     * 这题题目已经限制了是有向无环图, 因此直接计算入度也可以.
     */
    @Answer
    public List<Integer> findSmallestSetOfVertices2(int n, List<List<Integer>> edges) {
        // 每个节点的入度
        int[] degrees = new int[n];
        for (List<Integer> edge : edges) {
            degrees[edge.get(1)]++;
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (degrees[i] == 0) {
                res.add(i);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(6, List.of(List.of(0, 1), List.of(0, 2), List.of(2, 5), List.of(3, 4), List.of(4, 2)))
            .expect(List.of(0, 3))
            .unOrder();

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(5, List.of(List.of(0, 1), List.of(2, 1), List.of(3, 1), List.of(1, 4), List.of(2, 4)))
            .expect(List.of(0, 2, 3))
            .unOrder();

}
