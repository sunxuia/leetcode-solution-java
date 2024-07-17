package q2050;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 2045. Second Minimum Time to Reach Destination
 * https://leetcode.com/problems/second-minimum-time-to-reach-destination/
 *
 * A city is represented as a bi-directional connected graph with n vertices where each vertex is labeled from 1 to n
 * (inclusive). The edges in the graph are represented as a 2D integer array edges, where each edges[i] = [ui, vi]
 * denotes a bi-directional edge between vertex ui and vertex vi. Every vertex pair is connected by at most one edge,
 * and no vertex has an edge to itself. The time taken to traverse any edge is time minutes.
 *
 * Each vertex has a traffic signal which changes its color from green to red and vice versa every change minutes. All
 * signals change at the same time. You can enter a vertex at any time, but can leave a vertex only when the signal is
 * green. You cannot wait at a vertex if the signal is green.
 *
 * The second minimum value is defined as the smallest value strictly larger than the minimum value.
 *
 * For example the second minimum value of [2, 3, 4] is 3, and the second minimum value of [2, 2, 4] is 4.
 *
 * Given n, edges, time, and change, return the second minimum time it will take to go from vertex 1 to vertex n.
 *
 * Notes:
 *
 * You can go through any vertex any number of times, including 1 and n.
 * You can assume that when the journey starts, all signals have just turned green.
 *
 * Example 1:
 * (图Q2045_PIC1.png)
 * (图Q2045_PIC2.png)
 * Input: n = 5, edges = [[1,2],[1,3],[1,4],[3,4],[4,5]], time = 3, change = 5
 * Output: 13
 * Explanation:
 * The figure on the left shows the given graph.
 * The blue path in the figure on the right is the minimum time path.
 * The time taken is:
 * - Start at 1, time elapsed=0
 * - 1 -> 4: 3 minutes, time elapsed=3
 * - 4 -> 5: 3 minutes, time elapsed=6
 * Hence the minimum time needed is 6 minutes.
 *
 * The red path shows the path to get the second minimum time.
 * - Start at 1, time elapsed=0
 * - 1 -> 3: 3 minutes, time elapsed=3
 * - 3 -> 4: 3 minutes, time elapsed=6
 * - Wait at 4 for 4 minutes, time elapsed=10
 * - 4 -> 5: 3 minutes, time elapsed=13
 * Hence the second minimum time is 13 minutes.
 *
 * Example 2:
 * (图Q2045_PIC3.png)
 * Input: n = 2, edges = [[1,2]], time = 3, change = 2
 * Output: 11
 * Explanation:
 * The minimum time path is 1 -> 2 with time = 3 minutes.
 * The second minimum time path is 1 -> 2 -> 1 -> 2 with time = 11 minutes.
 *
 * Constraints:
 *
 * 2 <= n <= 10^4
 * n - 1 <= edges.length <= min(2 * 10^4, n * (n - 1) / 2)
 * edges[i].length == 2
 * 1 <= ui, vi <= n
 * ui != vi
 * There are no duplicate edges.
 * Each vertex can be reached directly or indirectly from every other vertex.
 * 1 <= time, change <= 10^3
 */
@RunWith(LeetCodeRunner.class)
public class Q2045_SecondMinimumTimeToReachDestination {

    // 比较慢的一种解法, 没有剪枝操作 (如果不优化数据结构的话会超时)
    @Answer
    public int secondMinimum(int n, int[][] edges, int time, int change) {
        // 构建稀疏邻接矩阵
        // 到邻居的边的数量
        int[] counts = new int[n + 1];
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1];
            counts[a]++;
            counts[b]++;
        }
        int[][] neighbors = new int[n + 1][];
        for (int i = 1; i <= n; i++) {
            neighbors[i] = new int[counts[i]];
        }
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1];
            neighbors[a][--counts[a]] = b;
            neighbors[b][--counts[b]] = a;
        }

        // 按时间前进 BFS
        int[] visited = counts; // 表示是否在本轮中被访问过 (=counts 仅仅是为了内存复用)
        int[] froms = new int[n]; // 当前所在节点(出发)
        int[] tos = new int[n]; // 下一个可到达的节点
        froms[0] = 1;
        int fromLen = 1, toLen = 0;

        int current = 0, finish = 0;
        while (true) {
            if (current / change % 2 != 0) {
                // 等绿灯
                current = (current + change) / change * change;
            }

            // 走一轮
            current += time;
            for (int i = 0; i < fromLen; i++) {
                // 从当前节点进入邻居节点
                for (int to : neighbors[froms[i]]) {
                    if (visited[to] != current) {
                        if (to == n) {
                            finish++;
                            if (finish == 2) {
                                return current;
                            }
                        }
                        tos[toLen++] = to;
                        visited[to] = current;
                    }
                }
            }

            // 准备下一轮转移
            int[] t = froms;
            froms = tos;
            fromLen = toLen;
            toLen = 0;
            tos = t;
        }
    }

    // 剪枝逻辑:
    // 如果一个(无向)边同一个方向走过 2 次之后就不允许通过 (将这条边删除).
    // 相同方向走一遍 a->b 表示第一次经过, 再走一遍 a->b 表示第二次经过, 之后如果再走 a->b 到终点的时间肯定会大于第一和第二次经过的时间.
    // 同时如果无向边的总数超过3次, a->b, b->a, a->b 之后就不再允许 b->a, 因为此时 a->target 和 a->b->a->target 要更快.
    @Answer
    public int secondMinimum2(int n, int[][] edges, int time, int change) {
        Node[] nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node();
        }
        nodes[n].target = true;

        for (int[] edge : edges) {
            Node a = nodes[edge[0]];
            Node b = nodes[edge[1]];
            Edge e = new Edge(a, b);
            a.edges.add(e);
            b.edges.add(e);
        }

        // bfs
        Set<Node> thisRound = new HashSet<>();
        thisRound.add(nodes[1]);
        Set<Node> nextRound = new HashSet<>();
        int current = 0, finish = 0;
        while (true) {
            if (current / change % 2 != 0) {
                // 等绿灯
                current = (current + change) / change * change;
            }

            // 走一轮
            current += time;
            for (Node from : thisRound) {
                for (var it = from.edges.iterator();
                        it.hasNext(); ) {
                    Edge edge = it.next();
                    if (edge.shouldRemove()) {
                        it.remove();
                        continue;
                    }
                    Node neighbor = edge.next(from);
                    if (nextRound.add(neighbor)) {
                        if (neighbor.target) {
                            finish++;
                            if (finish == 2) {
                                return current;
                            }
                        }
                    }
                }
            }

            // 准备下一轮转移
            Set<Node> t = thisRound;
            thisRound = nextRound;
            nextRound = t;
            nextRound.clear();
        }
    }

    private static class Node {

        // 是否是终点, 只有 nodes[n] 才为true
        boolean target;

        // 边
        LinkedList<Edge> edges = new LinkedList<>();

    }

    private static class Edge {

        final Node a, b;

        int ab, ba;

        private Edge(Node a, Node b) {
            this.a = a;
            this.b = b;
        }

        Node next(Node from) {
            if (a == from) {
                ab++;
                return b;
            } else {
                ba++;
                return a;
            }
        }

        boolean shouldRemove() {
            return ab == 2 || ba == 2;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(5, new int[][]{{1, 2}, {1, 3}, {1, 4}, {3, 4}, {4, 5}}, 3, 5)
            .expect(13);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(2, new int[][]{{1, 2}}, 3, 2)
            .expect(11);

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation overtime1 = DataExpectation
            .createWith(10000, TestDataFileHelper.read(testDataFile, 1, int[][].class), 1000, 1000)
            .expect(19717000);

    @TestData
    public DataExpectation overtime2 = DataExpectation
            .createWith(520, TestDataFileHelper.read(testDataFile, 2, int[][].class), 946, 183)
            .expect(3142);

}
