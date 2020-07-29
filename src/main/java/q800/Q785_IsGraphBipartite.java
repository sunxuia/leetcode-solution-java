package q800;

import org.junit.runner.RunWith;
import q900.Q886_PossibleBipartition;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/is-graph-bipartite/
 *
 * Given an undirected graph, return true if and only if it is bipartite.
 *
 * Recall that a graph is bipartite if we can split it's set of nodes into two independent subsets A and B such that
 * every edge in the graph has one node in A and another node in B.
 *
 * The graph is given in the following form: graph[i] is a list of indexes j for which the edge between nodes i and j
 * exists.  Each node is an integer between 0 and graph.length - 1.  There are no self edges or parallel edges:
 * graph[i] does not contain i, and it doesn't contain any element twice.
 *
 * Example 1:
 * Input: [[1,3], [0,2], [1,3], [0,2]]
 * Output: true
 * Explanation:
 * The graph looks like this:
 * 0----1
 * |    |
 * |    |
 * 3----2
 * We can divide the vertices into two groups: {0, 2} and {1, 3}.
 *
 * Example 2:
 * Input: [[1,2,3], [0,2], [0,1,3], [0,2]]
 * Output: false
 * Explanation:
 * The graph looks like this:
 * 0----1
 * | \  |
 * |  \ |
 * 3----2
 * We cannot find a way to divide the set of nodes into two independent subsets.
 *
 *
 *
 * Note:
 *
 * 1. graph will have length in range [1, 100].
 * 2. graph[i] will contain integers in range [0, graph.length - 1].
 * 3. graph[i] will not contain i or duplicate values.
 * 4. The graph is undirected: if any element j is in graph[i], then i will be in graph[j].
 *
 * 关联问题 {@link Q886_PossibleBipartition}
 */
@RunWith(LeetCodeRunner.class)
public class Q785_IsGraphBipartite {

    // 无向图的最大割问题. 这题可以使用dfs 来求解.
    @Answer
    public boolean isBipartite(int[][] graph) {
        int[] pos = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            // pos[i] 表示节点i 所处的集合, 分别用1/-1 来表示,
            // 为 0 说明i 处在一个新的孤立的集合, 与目前的2 个集合没有集合, 可以加入任意一个 (这里默认加入1)
            if (pos[i] == 0) {
                if (!dfs(graph, i, pos, 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean dfs(int[][] graph, int i, int[] pos, int expect) {
        if (pos[i] != 0) {
            return pos[i] == expect;
        }
        pos[i] = expect;
        for (int j = 0; j < graph[i].length; j++) {
            if (!dfs(graph, graph[i][j], pos, -expect)) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{1, 3}, {0, 2}, {1, 3}, {0, 2}})
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1, 2, 3}, {0, 2}, {0, 1, 3}, {0, 2}})
            .expect(false);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(new int[][]{{2, 4}, {2, 3, 4}, {0, 1}, {1}, {0, 1}, {7}, {9}, {5}, {}, {6}, {12, 14}, {}, {10}, {},
                    {10}, {19}, {18}, {}, {16}, {15}, {23}, {23}, {}, {20, 21}, {}, {}, {27}, {26}, {}, {}, {34},
                    {33, 34}, {}, {31}, {30, 31}, {38, 39}, {37, 38, 39}, {36}, {35, 36}, {35, 36}, {43}, {}, {}, {40},
                    {}, {49}, {47, 48, 49}, {46, 48, 49}, {46, 47, 49}, {45, 46, 47, 48}})
            .expect(false);

}
