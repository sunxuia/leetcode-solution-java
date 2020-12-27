package q1450;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1443. Minimum Time to Collect All Apples in a Tree
 * https://leetcode.com/problems/minimum-time-to-collect-all-apples-in-a-tree/
 *
 * Given an undirected tree consisting of n vertices numbered from 0 to n-1, which has some apples in their vertices.
 * You spend 1 second to walk over one edge of the tree. Return the minimum time in seconds you have to spend to collect
 * all apples in the tree, starting at vertex 0 and coming back to this vertex.
 *
 * The edges of the undirected tree are given in the array edges, where edges[i] = [ai, bi] means that exists an edge
 * connecting the vertices ai and bi. Additionally, there is a boolean array hasApple, where hasApple[i] = true means
 * that vertex i has an apple; otherwise, it does not have any apple.
 *
 * Example 1:
 * <img src="./Q1443_PIC1.png">
 * Input: n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,true,false,true,true,false]
 * Output: 8
 * Explanation: The figure above represents the given tree where red vertices have an apple. One optimal path to collect
 * all apples is shown by the green arrows.
 *
 * Example 2:
 * <img src="./Q1443_PIC2.png">
 * Input: n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,true,false,false,true,false]
 * Output: 6
 * Explanation: The figure above represents the given tree where red vertices have an apple. One optimal path to collect
 * all apples is shown by the green arrows.
 *
 * Example 3:
 *
 * Input: n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,false,false,false,false,false]
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 * edges.length == n - 1
 * edges[i].length == 2
 * 0 <= ai < bi <= n - 1
 * fromi < toi
 * hasApple.length == n
 */
@RunWith(LeetCodeRunner.class)
public class Q1443_MinimumTimeToCollectAllApplesInATree {

    @Answer
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
            nodes[i].hasApple = hasApple.get(i);
        }
        for (int[] edge : edges) {
            Node p1 = nodes[edge[0]];
            Node p2 = nodes[edge[1]];
            p1.children.add(p2);
            p2.children.add(p1);
        }

        dfs(nodes[0], null);
        return res;
    }

    private static class Node {

        List<Node> children = new ArrayList<>();

        boolean hasApple;

        boolean isLeaf() {
            return children.isEmpty();
        }
    }

    private int res = 0;

    private boolean dfs(Node node, Node from) {
        if (node.isLeaf() && !node.hasApple) {
            return false;
        }
        boolean pass = node.hasApple;
        for (Node child : node.children) {
            if (child != from && dfs(child, node)) {
                pass = true;
                res += 2;
            }
        }
        return pass;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(7,
            new int[][]{{0, 1}, {0, 2}, {1, 4}, {1, 5}, {2, 3}, {2, 6}},
            List.of(false, false, true, false, true, true, false))
            .expect(8);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(7,
            new int[][]{{0, 1}, {0, 2}, {1, 4}, {1, 5}, {2, 3}, {2, 6}},
            List.of(false, false, true, false, false, true, false))
            .expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(7,
            new int[][]{{0, 1}, {0, 2}, {1, 4}, {1, 5}, {2, 3}, {2, 6}},
            List.of(false, false, false, false, false, false, false))
            .expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(4,
            new int[][]{{0, 2}, {0, 3}, {1, 2}},
            List.of(false, true, false, false))
            .expect(4);

}
