package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1579. Remove Max Number of Edges to Keep Graph Fully Traversable
 * https://leetcode.com/problems/remove-max-number-of-edges-to-keep-graph-fully-traversable/
 *
 * Alice and Bob have an undirected graph of n nodes and 3 types of edges:
 *
 * Type 1: Can be traversed by Alice only.
 * Type 2: Can be traversed by Bob only.
 * Type 3: Can by traversed by both Alice and Bob.
 *
 * Given an array edges where edges[i] = [typei, ui, vi] represents a bidirectional edge of type typei between nodes ui
 * and vi, find the maximum number of edges you can remove so that after removing the edges, the graph can still be
 * fully traversed by both Alice and Bob. The graph is fully traversed by Alice and Bob if starting from any node, they
 * can reach all other nodes.
 *
 * Return the maximum number of edges you can remove, or return -1 if it's impossible for the graph to be fully
 * traversed by Alice and Bob.
 *
 * Example 1:
 * <img src="./Q1579_PIC1.png">
 * Input: n = 4, edges = [[3,1,2],[3,2,3],[1,1,3],[1,2,4],[1,1,2],[2,3,4]]
 * Output: 2
 * Explanation: If we remove the 2 edges [1,1,2] and [1,1,3]. The graph will still be fully traversable by Alice and
 * Bob. Removing any additional edge will not make it so. So the maximum number of edges we can remove is 2.
 *
 * Example 2:
 * <img src="./Q1579_PIC2.png">
 * Input: n = 4, edges = [[3,1,2],[3,2,3],[1,1,4],[2,1,4]]
 * Output: 0
 * Explanation: Notice that removing any edge will not make the graph fully traversable by Alice and Bob.
 *
 * Example 3:
 * <img src="./Q1579_PIC3.png">
 * Input: n = 4, edges = [[3,2,3],[1,1,2],[2,3,4]]
 * Output: -1
 * Explanation: In the current graph, Alice cannot reach node 4 from the other nodes. Likewise, Bob cannot reach 1.
 * Therefore it's impossible to make the graph fully traversable.
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 * 1 <= edges.length <= min(10^5, 3 * n * (n-1) / 2)
 * edges[i].length == 3
 * 1 <= edges[i][0] <= 3
 * 1 <= edges[i][1] < edges[i][2] <= n
 * All tuples (typei, ui, vi) are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1579_RemoveMaxNumberOfEdgesToKeepGraphFullyTraversable {

    /**
     * @formatter:off
     * 参考文档
     * https://leetcode-cn.com/problems/remove-max-number-of-edges-to-keep-graph-fully-traversable/solution/javabing-cha-ji-qiu-jie-bao-zheng-tu-ke-06rs3/
     * @formatter:on
     */
    @Answer
    public int maxNumEdgesToRemove(int n, int[][] edges) {
        int[] rootsA = new int[n];
        int[] rootsB = new int[n];
        for (int i = 0; i < n; i++) {
            rootsA[i] = rootsB[i] = i;
        }

        int res = 0;
        // 优先使用 Alice Bob 共用的边
        for (int[] edge : edges) {
            final int type = edge[0];
            final int n1 = edge[1] - 1, n2 = edge[2] - 1;
            if (type == 3) {
                if (isUnion(rootsA, n1, n2) && isUnion(rootsB, n1, n2)) {
                    // 已经联通, 这个边就不要了
                    res++;
                } else {
                    union(rootsA, n1, n2);
                    union(rootsB, n1, n2);
                }
            }
        }
        // 再使用 Alice Bob 独用的边
        for (int[] edge : edges) {
            final int type = edge[0];
            final int n1 = edge[1] - 1, n2 = edge[2] - 1;
            if (type == 1) {
                if (isUnion(rootsA, n1, n2)) {
                    res++;
                } else {
                    union(rootsA, n1, n2);
                }
            } else if (type == 2) {
                if (isUnion(rootsB, n1, n2)) {
                    res++;
                } else {
                    union(rootsB, n1, n2);
                }
            }
        }

        // 判断是否联通
        int ca = 0, cb = 0;
        for (int i = 0; i < n; i++) {
            ca += rootsA[i] == i ? 1 : 0;
            cb += rootsB[i] == i ? 1 : 0;
            if (ca > 1 || cb > 1) {
                return -1;
            }
        }

        return res;
    }

    private boolean isUnion(int[] roots, int a, int b) {
        return findRoot(roots, a) == findRoot(roots, b);
    }

    private int findRoot(int[] roots, int i) {
        if (roots[i] == i) {
            return i;
        }
        return roots[i] = findRoot(roots, roots[i]);
    }

    private void union(int[] roots, int i, int p) {
        roots[findRoot(roots, i)] = findRoot(roots, p);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(4, new int[][]{{3, 1, 2}, {3, 2, 3}, {1, 1, 3}, {1, 2, 4}, {1, 1, 2}, {2, 3, 4}})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(4, new int[][]{{3, 1, 2}, {3, 2, 3}, {1, 1, 4}, {2, 1, 4}})
            .expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(4, new int[][]{{3, 2, 3}, {1, 1, 2}, {2, 3, 4}})
            .expect(-1);

}
