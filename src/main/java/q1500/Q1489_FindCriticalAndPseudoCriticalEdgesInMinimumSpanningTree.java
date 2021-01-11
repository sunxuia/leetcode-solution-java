package q1500;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1489. Find Critical and Pseudo-Critical Edges in Minimum Spanning Tree
 * https://leetcode.com/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/
 *
 * Given a weighted undirected connected graph with n vertices numbered from 0 to n - 1, and an array edges where
 * edges[i] = [ai, bi, weighti] represents a bidirectional and weighted edge between nodes ai and bi. A minimum spanning
 * tree (MST) is a subset of the graph's edges that connects all vertices without cycles and with the minimum possible
 * total edge weight.
 *
 * Find all the critical and pseudo-critical edges in the given graph's minimum spanning tree (MST). An MST edge whose
 * deletion from the graph would cause the MST weight to increase is called a critical edge. On the other hand, a
 * pseudo-critical edge is that which can appear in some MSTs but not all.
 *
 * Note that you can return the indices of the edges in any order.
 *
 * Example 1:
 * <img src="./Q1489_PIC1.png">
 * Input: n = 5, edges = [[0,1,1],[1,2,1],[2,3,2],[0,3,2],[0,4,3],[3,4,3],[1,4,6]]
 * Output: [[0,1],[2,3,4,5]]
 * Explanation: The figure above describes the graph.
 * The following figure shows all the possible MSTs:
 * <img src="./Q1489_PIC2.png">
 *
 * Notice that the two edges 0 and 1 appear in all MSTs, therefore they are critical edges, so we return them in the
 * first list of the output.
 * The edges 2, 3, 4, and 5 are only part of some MSTs, therefore they are considered pseudo-critical edges. We add them
 * to the second list of the output.
 *
 * Example 2:
 * <img src="./Q1489_PIC3.png">
 * Input: n = 4, edges = [[0,1,1],[1,2,1],[2,3,1],[0,3,1]]
 * Output: [[],[0,1,2,3]]
 * Explanation: We can observe that since all 4 edges have equal weight, choosing any 3 edges from the given 4 will
 * yield an MST. Therefore all 4 edges are pseudo-critical.
 *
 * Constraints:
 *
 * 2 <= n <= 100
 * 1 <= edges.length <= min(200, n * (n - 1) / 2)
 * edges[i].length == 3
 * 0 <= ai < bi < n
 * 1 <= weighti <= 1000
 * All pairs (ai, bi) are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1489_FindCriticalAndPseudoCriticalEdgesInMinimumSpanningTree {

    @Answer
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        // 设置联通图.
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < edges.length; i++) {
            Node node1 = nodes[edges[i][0]];
            Node node2 = nodes[edges[i][1]];
            Edge edge = new Edge(node1, node2, i, edges[i][2]);
            node1.edges.add(edge);
            node2.edges.add(edge);
        }

        // 计算一条最短路径.
        Set<Node> visited = new HashSet<>(n);
        List<Edge> minEdges = new ArrayList<>(n);
        PriorityQueue<Edge> pq = new PriorityQueue<>(
                Comparator.comparingInt(a -> a.weight));
        // (哨兵)
        pq.offer(new Edge(nodes[0], nodes[0], 0, 0));
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            Node from, curr;
            if (!visited.contains(edge.a)) {
                curr = edge.a;
                from = edge.b;
            } else if (!visited.contains(edge.b)) {
                curr = edge.b;
                from = edge.a;
            } else {
                continue;
            }
            visited.add(curr);
            minEdges.add(edge);
            from.minEdges.add(edge);
            curr.minEdges.add(edge);
            pq.addAll(curr.edges);
        }
        minEdges.remove(0);

        // 从最短路径中删除一条路径, 寻找其他的替代路径,
        // 如果不能找到, 则是关键路径, 否则是伪关键.
        List<Integer> criticals = new ArrayList<>();
        Set<Integer> pseduos = new HashSet<>();
        for (Edge me : minEdges) {
            // 计算删除这条最短路径后被一分为二的节点
            Set<Node> sideNodes = new HashSet<>();
            Queue<Node> queue = new ArrayDeque<>();
            queue.offer(me.a);
            while (!queue.isEmpty()) {
                Node node = queue.poll();
                if (sideNodes.add(node)) {
                    for (Edge edge : node.minEdges) {
                        if (edge != me) {
                            queue.offer(edge.a == node ? edge.b : edge.a);
                        }
                    }
                }
            }

            // 在一分为二的节点中寻找能到达对岸的且和最短路径长度一样的路径
            boolean isCritical = true;
            for (Node node : sideNodes) {
                for (Edge edge : node.edges) {
                    Node other = edge.a == node ? edge.b : edge.a;
                    if (edge != me && edge.weight == me.weight
                            && !sideNodes.contains(other)) {
                        isCritical = false;
                        pseduos.add(edge.no);
                    }
                }
            }
            if (isCritical) {
                criticals.add(me.no);
            } else {
                pseduos.add(me.no);
            }
        }
        return List.of(criticals, new ArrayList<>(pseduos));
    }

    /**
     * 表示边
     */
    private static class Edge {

        final Node a, b;

        final int no, weight;

        Edge(Node a, Node b, int no, int weight) {
            this.a = a;
            this.b = b;
            this.no = no;
            this.weight = weight;
        }

        Node next(Node node) {
            return a == node ? b : a;
        }

        @Override
        public String toString() {
            return String.format("%s<-%d->%s", a, weight, b);
        }
    }

    /**
     * 表示节点
     */
    private static class Node {

        final int no;

        List<Edge> edges = new ArrayList<>();

        Set<Edge> minEdges = new HashSet<>();

        public Node(int no) {
            this.no = no;
        }

        @Override
        public String toString() {
            return String.format("{%d}", no);
        }
    }

    /**
     * LeetCode 上比较快的做法, 使用union find 构造最短路径.
     */
    @Answer
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges2(int n, int[][] edges) {
        // 保存edge 对应的下标
        Map<int[], Integer> edgeIndex = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            edgeIndex.put(edges[i], i);
        }

        // 按照weight 排序, 这样在使用union find 时首先遍历到连接不相邻区块的边就是最短路径.
        Arrays.sort(edges, Comparator.comparingInt(e -> e[2]));
        int minCost = buildMST(n, edges, null, null);

        List<Integer> criticals = new ArrayList<>();
        List<Integer> pseduos = new ArrayList<>();
        for (int[] edge : edges) {
            // 排除这条边后的最短路径长度
            int costWithout = buildMST(n, edges, null, edge);
            if (costWithout > minCost) {
                // 排除后就不是最短, 则说明是关键路径
                criticals.add(edgeIndex.get(edge));
            } else {
                // 使用这条边后的最短路径长度
                int costWith = buildMST(n, edges, edge, null);
                if (costWith == minCost) {
                    // 使用或不使用该路径都最短, 则说明是伪关键路径.
                    pseduos.add(edgeIndex.get(edge));
                }
            }
        }
        return List.of(criticals, pseduos);
    }

    /**
     * 构造最短路径
     *
     * @param pick 构造时要使用的边
     * @param skip 构造时要跳过的边
     * @return 最短路径的长度
     */
    private int buildMST(int n, int[][] edges, int[] pick, int[] skip) {
        UnionFind uf = new UnionFind(n);
        int cost = 0;
        if (pick != null) {
            uf.union(pick[0], pick[1]);
            cost += pick[2];
        }

        for (int[] edge : edges) {
            if (edge != skip && uf.union(edge[0], edge[1])) {
                // 这条边连接起了2 个不相邻区块, 且因为edges
                // 是按顺序排列的, 因此就是最短路径.
                cost += edge[2];
            }
        }
        return uf.count == 1 ? cost : Integer.MAX_VALUE;
    }

    private static class UnionFind {

        final int[] parents;
        int count;

        UnionFind(int n) {
            this.parents = new int[n];
            reset();
        }

        void reset() {
            for (int i = 0; i < parents.length; i++) {
                parents[i] = i;
            }
            count = parents.length;
        }

        int find(int i) {
            if (parents[i] == i) {
                return i;
            }
            return parents[i] = find(parents[i]);
        }

        boolean union(int i, int j) {
            int r1 = find(i);
            int r2 = find(j);
            if (r1 != r2) {
                count--;
                parents[r1] = r2;
                return true;
            } else {
                return false;
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(5, new int[][]{{0, 1, 1}, {1, 2, 1}, {2, 3, 2}, {0, 3, 2}, {0, 4, 3}, {3, 4, 3}, {1, 4, 6}})
            .expect(List.of(List.of(0, 1), List.of(2, 3, 4, 5)))
            .unOrder();

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(4, new int[][]{{0, 1, 1}, {1, 2, 1}, {2, 3, 1}, {0, 3, 1}})
            .expect(List.of(List.of(), List.of(0, 1, 2, 3)))
            .unOrder();

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(14, new int[][]{{0, 1, 13}, {0, 2, 6}, {2, 3, 13}, {3, 4, 4}, {0, 5, 11}, {4, 6, 14}, {4, 7, 8},
                    {2, 8, 6}, {4, 9, 6}, {7, 10, 4}, {5, 11, 3}, {6, 12, 7}, {12, 13, 9}, {7, 13, 2}, {5, 13, 10},
                    {0, 6, 4}, {2, 7, 3}, {0, 7, 8}, {1, 12, 9}, {10, 12, 11}, {1, 2, 7}, {1, 3, 10}, {3, 10, 6},
                    {6, 10, 4}, {4, 8, 5}, {1, 13, 4}, {11, 13, 8}, {2, 12, 10}, {5, 8, 1}, {3, 7, 6}, {7, 12, 12},
                    {1, 7, 9}, {5, 9, 1}, {2, 13, 10}, {10, 11, 4}, {3, 5, 10}, {6, 11, 14}, {5, 12, 3}, {0, 8, 13},
                    {8, 9, 1}, {3, 6, 8}, {0, 3, 4}, {2, 9, 6}, {0, 11, 4}, {2, 5, 14}, {4, 11, 2}, {7, 11, 11},
                    {1, 11, 6}, {2, 10, 12}, {0, 13, 4}, {3, 9, 9}, {4, 12, 3}, {6, 7, 10}, {6, 8, 13}, {9, 11, 3},
                    {1, 6, 2}, {2, 4, 12}, {0, 10, 3}, {3, 12, 1}, {3, 8, 12}, {1, 8, 6}, {8, 13, 2}, {10, 13, 12},
                    {9, 13, 11}, {2, 11, 14}, {5, 10, 9}, {5, 6, 10}, {2, 6, 9}, {4, 10, 7}, {3, 13, 10}, {4, 13, 3},
                    {3, 11, 9}, {7, 9, 14}, {6, 9, 5}, {1, 5, 12}, {4, 5, 3}, {11, 12, 3}, {0, 4, 8}, {5, 7, 8},
                    {9, 12, 13}, {8, 12, 12}, {1, 10, 6}, {1, 9, 9}, {7, 8, 9}, {9, 10, 13}, {8, 11, 3}, {6, 13, 7},
                    {0, 12, 10}, {1, 4, 8}, {8, 10, 2}})
            .expect(List.of(List.of(57, 89, 61, 13, 45, 16, 58, 55),
                    List.of(32, 37, 70, 39, 10, 75, 76, 15, 51, 85, 54, 23, 25, 28)))
            .unOrder();

}
