package q1400;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1377. Frog Position After T Seconds
 * https://leetcode.com/problems/frog-position-after-t-seconds/
 *
 * Given an undirected tree consisting of n vertices numbered from 1 to n. A frog starts jumping from vertex 1. In one
 * second, the frog jumps from its current vertex to another unvisited vertex if they are directly connected. The frog
 * can not jump back to a visited vertex. In case the frog can jump to several vertices, it jumps randomly to one of
 * them with the same probability. Otherwise, when the frog can not jump to any unvisited vertex, it jumps forever on
 * the same vertex.
 *
 * The edges of the undirected tree are given in the array edges, where edges[i] = [ai, bi] means that exists an edge
 * connecting the vertices ai and bi.
 *
 * Return the probability that after t seconds the frog is on the vertex target.
 *
 * Example 1:
 * <img src="./Q1377_PIC1.png">
 * Input: n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 2, target = 4
 * Output: 0.16666666666666666
 * Explanation: The figure above shows the given graph. The frog starts at vertex 1, jumping with 1/3 probability to the
 * vertex 2 after second 1 and then jumping with 1/2 probability to vertex 4 after second 2. Thus the probability for
 * the frog is on the vertex 4 after 2 seconds is 1/3 * 1/2 = 1/6 = 0.16666666666666666.
 *
 * Example 2:
 * <img src="./Q1377_PIC2.png">
 * Input: n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 1, target = 7
 * Output: 0.3333333333333333
 * Explanation: The figure above shows the given graph. The frog starts at vertex 1, jumping with 1/3 =
 * 0.3333333333333333 probability to the vertex 7 after second 1.
 *
 * Example 3:
 *
 * Input: n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 20, target = 6
 * Output: 0.16666666666666666
 *
 * Constraints:
 *
 * 1 <= n <= 100
 * edges.length == n - 1
 * edges[i].length == 2
 * 1 <= ai, bi <= n
 * 1 <= t <= 50
 * 1 <= target <= n
 * Answers within 10-5 of the actual value will be accepted as correct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1377_FrogPositionAfterTSeconds {

    @Answer
    public double frogPosition(int n, int[][] edges, int t, int target) {
        Node[] nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }
        for (int[] edge : edges) {
            nodes[edge[0]].nexts.add(nodes[edge[1]]);
            nodes[edge[1]].nexts.add(nodes[edge[0]]);
        }

        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(nodes[1]);
        nodes[1].posibility = 1;
        for (int i = 1; i <= t && !queue.isEmpty(); i++) {
            for (int len = queue.size(); len > 0; len--) {
                Node node = queue.poll();
                int divisor = node.nexts.size();
                if (node.i != 1) {
                    divisor--;
                }
                if (divisor == 0) {
                    queue.offer(node);
                    continue;
                }
                for (Node next : node.nexts) {
                    if (next.posibility == 0) {
                        next.posibility = node.posibility / divisor;
                        queue.offer(next);
                    }
                }
                node.posibility = -1;
            }
        }
        double res = nodes[target].posibility;
        return res > 0 ? res : 0;
    }

    private static class Node {

        final int i;

        final List<Node> nexts = new ArrayList<>();

        double posibility = 0;

        public Node(int i) {
            this.i = i;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(7, new int[][]{{1, 2}, {1, 3}, {1, 7}, {2, 4}, {2, 6}, {3, 5}}, 2, 4)
            .expectDouble(0.16666666666666666);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(7, new int[][]{{1, 2}, {1, 3}, {1, 7}, {2, 4}, {2, 6}, {3, 5}}, 1, 7)
            .expectDouble(0.3333333333333333);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(7, new int[][]{{1, 2}, {1, 3}, {1, 7}, {2, 4}, {2, 6}, {3, 5}}, 20, 6)
            .expectDouble(0.16666666666666666);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(8, new int[][]{{2, 1}, {3, 2}, {4, 1}, {5, 1}, {6, 4}, {7, 1}, {8, 7}}, 7, 7)
            .expectDouble(0.00000);

}
