package q1500;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1466. Reorder Routes to Make All Paths Lead to the City Zero
 * https://leetcode.com/problems/reorder-routes-to-make-all-paths-lead-to-the-city-zero/
 *
 * There are n cities numbered from 0 to n-1 and n-1 roads such that there is only one way to travel between two
 * different cities (this network form a tree). Last year, The ministry of transport decided to orient the roads in one
 * direction because they are too narrow.
 *
 * Roads are represented by connections where connections[i] = [a, b] represents a road from city a to b.
 *
 * This year, there will be a big event in the capital (city 0), and many people want to travel to this city.
 *
 * Your task consists of reorienting some roads such that each city can visit the city 0. Return the minimum number of
 * edges changed.
 *
 * It's guaranteed that each city can reach the city 0 after reorder.
 *
 * Example 1:
 * <img src="./Q1466_PIC1.png">
 * Input: n = 6, connections = [[0,1],[1,3],[2,3],[4,0],[4,5]]
 * Output: 3
 * Explanation: Change the direction of edges show in red such that each node can reach the node 0 (capital).
 *
 * Example 2:
 * <img src="./Q1466_PIC2.png">
 * Input: n = 5, connections = [[1,0],[1,2],[3,2],[3,4]]
 * Output: 2
 * Explanation: Change the direction of edges show in red such that each node can reach the node 0 (capital).
 *
 * Example 3:
 *
 * Input: n = 3, connections = [[1,0],[2,0]]
 * Output: 0
 *
 * Constraints:
 *
 * 2 <= n <= 5 * 10^4
 * connections.length == n-1
 * connections[i].length == 2
 * 0 <= connections[i][0], connections[i][1] <= n-1
 * connections[i][0] != connections[i][1]
 */
@RunWith(LeetCodeRunner.class)
public class Q1466_ReorderRoutesToMakeAllPathsLeadToTheCityZero {

    /**
     * bfs
     */
    @Answer
    public int minReorder(int n, int[][] connections) {
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }
        for (int[] connection : connections) {
            nodes[connection[0]].addNext(nodes[connection[1]]);
        }
        int res = 0;
        // 未访问过的节点
        Set<Node> unvisited = new HashSet<>(n - 1);
        for (int i = 1; i < n; i++) {
            unvisited.add(nodes[i]);
        }
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(nodes[0]);
        do {
            // 可以从到达0 的节点到达的节点, 翻转后就能到达0
            Set<Node> nexts = new HashSet<>();
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                unvisited.remove(node);
                for (Node next : node.nexts) {
                    if (unvisited.contains(next)) {
                        nexts.add(next);
                    }
                }
                for (Node from : node.froms) {
                    if (unvisited.contains(from)) {
                        queue.add(from);
                    }
                }
            }

            // 翻转nexts 中的路径, 这些节点也可以到达0 了.
            res += nexts.size();
            queue.addAll(nexts);
        } while (!unvisited.isEmpty());
        return res;
    }

    private static class Node {

        final int no;

        Set<Node> nexts = new HashSet<>();

        Set<Node> froms = new HashSet<>();

        Node(int no) {
            this.no = no;
        }

        void addNext(Node next) {
            nexts.add(next);
            next.froms.add(this);
        }

        @Override
        public String toString() {
            return String.valueOf(no);
        }
    }

    /**
     * dfs 的解法, 这是一个最小生成树
     */
    @Answer
    public int minReorder2(int n, int[][] connections) {
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }
        for (int[] connection : connections) {
            nodes[connection[0]].addNext(nodes[connection[1]]);
        }
        return dfs(new boolean[n], nodes[0]);
    }

    private int dfs(boolean[] visited, Node node) {
        visited[node.no] = true;
        int res = 0;
        for (Node from : node.froms) {
            if (!visited[from.no]) {
                res += dfs(visited, from);
            }
        }
        for (Node next : node.nexts) {
            if (!visited[next.no]) {
                res += 1 + dfs(visited, next);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(6, new int[][]{{0, 1}, {1, 3}, {2, 3}, {4, 0}, {4, 5}})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(5, new int[][]{{1, 0}, {1, 2}, {3, 2}, {3, 4}})
            .expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(3, new int[][]{{1, 0}, {2, 0}})
            .expect(0);

}
