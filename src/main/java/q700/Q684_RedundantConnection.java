package q700;

import org.junit.runner.RunWith;
import q150.Q130_SurroundedRegions;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/redundant-connection/
 *
 * In this problem, a tree is an undirected graph that is connected and has no cycles.
 *
 * The given input is a graph that started as a tree with N nodes (with distinct values 1, 2, ..., N), with one
 * additional edge added. The added edge has two different vertices chosen from 1 to N, and was not an edge that
 * already existed.
 *
 * The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] with u < v, that
 * represents an undirected edge connecting nodes u and v.
 *
 * Return an edge that can be removed so that the resulting graph is a tree of N nodes. If there are multiple
 * answers, return the answer that occurs last in the given 2D-array. The answer edge [u, v] should be in the same
 * format, with u < v.
 *
 * Example 1:
 *
 * Input: [[1,2], [1,3], [2,3]]
 * Output: [2,3]
 * Explanation: The given undirected graph will be like this:
 * >   1
 * >  / \
 * > 2 - 3
 *
 * Example 2:
 *
 * Input: [[1,2], [2,3], [3,4], [1,4], [1,5]]
 * Output: [1,4]
 * Explanation: The given undirected graph will be like this:
 * > 5 - 1 - 2
 * >     |   |
 * >     4 - 3
 *
 * Note:
 * The size of the input 2D-array will be between 3 and 1000.
 * Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.
 *
 *
 * Update (2017-09-26):
 * We have overhauled the problem description + test cases and specified clearly the graph is an undirected graph.
 * For the directed graph follow up please see Redundant Connection II). We apologize for any inconvenience caused.
 */
@RunWith(LeetCodeRunner.class)
public class Q684_RedundantConnection {

    /**
     * 这题可以使用union find
     * 类似的题目参见 {@link Q130_SurroundedRegions#solve_UnionFind(char[][])}
     */
    @Answer
    public int[] findRedundantConnection(int[][] edges) {
        int n = 0;
        for (int[] edge : edges) {
            n = Math.max(n, edge[1]);
        }
        UnionFind uf = new UnionFind(n + 1);
        for (int[] edge : edges) {
            if (uf.isConnected(edge[0], edge[1])) {
                return edge;
            }
            uf.union(edge[0], edge[1]);
        }
        return null;
    }

    // 使用加权路径压缩(weights) 的 union find
    private static class UnionFind {

        // 节点的 id 对应的上级节点, 下标是节点id, 值是连接的节点的id.
        // 如果 parent[id] = id, 那么就表示 id 节点是根节点.
        private final int[] id;

        // 路径权重. 下标是节点id, 值是连接到这个节点的子节点的总数.
        private final int[] weight;

        public UnionFind(int n) {
            id = new int[n];
            weight = new int[n];
            // 初始化让所有节点自己连到自己(都是根节点)
            for (int i = 0; i < n; i++) {
                id[i] = i;
                weight[i] = 1;
            }
        }

        // 连接2 个节点, 首先找出它们的根节点, 如果这2 个节点不通 (根节点不同), 则连接2 个根节点.
        // 使用weights 来让小权重小的节点连接到权重大的节点上, 来减端树的路径长度.
        public void union(int a, int b) {
            int p = find(a);
            int q = find(b);
            if (p != q) {
                if (weight[q] > weight[p]) {
                    int moveWeight = weight[p];
                    weight[id[p]] -= moveWeight;
                    weight[q] += moveWeight;

                    id[p] = q;
                } else {
                    int moveWeight = weight[q];
                    weight[id[q]] -= moveWeight;
                    weight[p] += moveWeight;

                    id[q] = p;
                }
            }
        }

        // 查找节点的对应根节点, 并在查找的过程中压缩路径.
        private int find(int cur) {
            int p = id[cur];
            while (p != id[p]) {
                weight[p] -= weight[cur];
                weight[id[p]] += weight[cur];
                id[cur] = id[p];
                cur = id[p];
                p = id[cur];
            }
            return p;
        }

        private boolean isConnected(int a, int b) {
            return find(a) == find(b);
        }
    }

    /**
     * LeetCode 上最快的方案.
     * 简化了上面的 union find 的操作, 主要是省略了加权的操作, 思路一样.
     */
    @Answer
    public int[] findRedundantConnection2(int[][] edges) {
        int n = edges.length;
        int[] parent = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }
        for (int[] edge : edges) {
            int p1 = find(parent, edge[0]);
            int p2 = find(parent, edge[1]);

            if (p1 != p2) {
                parent[p2] = p1;
            } else {
                return edge;
            }
        }
        return null;
    }

    int find(int[] parent, int x) {
        if (parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent, parent[x]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 2}, {1, 3}, {2, 3}
    }).expect(new int[]{2, 3});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 2}, {2, 3}, {3, 4}, {1, 4}, {1, 5}
    }).expect(new int[]{1, 4});

}
