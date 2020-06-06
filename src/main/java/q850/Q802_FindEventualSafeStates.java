package q850;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * https://leetcode.com/problems/find-eventual-safe-states/
 *
 * In a directed graph, we start at some node and every turn, walk along a directed edge of the graph.  If we reach a
 * node that is terminal (that is, it has no outgoing directed edges), we stop.
 *
 * Now, say our starting node is eventually safe if and only if we must eventually walk to a terminal node.  More
 * specifically, there exists a natural number K so that for any choice of where to walk, we must have stopped at a
 * terminal node in less than K steps.
 *
 * Which nodes are eventually safe?  Return them as an array in sorted order.
 *
 * The directed graph has N nodes with labels 0, 1, ..., N-1, where N is the length of graph.  The graph is given in
 * the following form: graph[i] is a list of labels j such that (i, j) is a directed edge of the graph.
 *
 * Example:
 * Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
 * Output: [2,4,5,6]
 * Here is a diagram of the above graph.
 *
 * (图 Q802_PIC.png)
 *
 * Illustration of graph
 *
 * Note:
 *
 * graph will have length at most 10000.
 * The number of edges in the graph will not exceed 32000.
 * Each graph[i] will be a sorted list of different integers, chosen within the range [0, graph.length - 1].
 */
@RunWith(LeetCodeRunner.class)
public class Q802_FindEventualSafeStates {

    @Answer
    public List<Integer> eventualSafeNodes(int[][] graph) {
        final int n = graph.length;
        // 1 表示在环, -1 表示不在, 0 表示未被访问
        int[] circle = new int[n];
        for (int i = 0; i < n; i++) {
            dfs(circle, graph, i);
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (circle[i] == -1) {
                res.add(i);
            }
        }
        return res;
    }

    private int dfs(int[] circle, int[][] graph, int i) {
        if (circle[i] != 0) {
            return circle[i];
        }
        circle[i] = 1;
        for (int j = 0; j < graph[i].length; j++) {
            if (dfs(circle, graph, graph[i][j]) == 1) {
                return 1;
            }
        }
        return circle[i] = -1;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(new int[][]{{1, 2}, {2, 3}, {5}, {0}, {5}, {}, {}})
            .expect(new int[]{2, 4, 5, 6});

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read2DArray("Q802_TestData"))
            .expect(TestDataFileHelper.readIntegerArray("Q802_TestData_Result"));

}
