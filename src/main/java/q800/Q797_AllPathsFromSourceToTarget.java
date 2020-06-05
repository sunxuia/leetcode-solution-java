package q800;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/all-paths-from-source-to-target/
 *
 * Given a directed, acyclic graph of N nodes.  Find all possible paths from node 0 to node N-1, and return them in
 * any order.
 *
 * The graph is given as follows:  the nodes are 0, 1, ..., graph.length - 1.  graph[i] is a list of all nodes j for
 * which the edge (i, j) exists.
 *
 * Example:
 * Input: [[1,2], [3], [3], []]
 * Output: [[0,1,3],[0,2,3]]
 * Explanation: The graph looks like this:
 * 0--->1
 * |    |
 * v    v
 * 2--->3
 * There are two paths: 0 -> 1 -> 3 and 0 -> 2 -> 3.
 *
 * Note:
 *
 * The number of nodes in the graph will be in the range [2, 15].
 * You can print different paths in any order, but you should keep the order of nodes inside one path.
 */
@RunWith(LeetCodeRunner.class)
public class Q797_AllPathsFromSourceToTarget {

    @Answer
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(res, new ArrayList<>(), graph, 0);
        return res;
    }

    private void dfs(List<List<Integer>> res, ArrayList<Integer> path, int[][] graph, int index) {
        path.add(index);
        if (index == graph.length - 1) {
            res.add((List<Integer>) path.clone());
        } else {
            for (int i = 0; i < graph[index].length; i++) {
                dfs(res, path, graph, graph[index][i]);
            }
        }
        path.remove(path.size() - 1);
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[][]{{1, 2}, {3}, {3}, {}})
            .expect(Arrays.asList(Arrays.asList(0, 1, 3), Arrays.asList(0, 2, 3)))
            .unorderResult("")
            .build();

}
