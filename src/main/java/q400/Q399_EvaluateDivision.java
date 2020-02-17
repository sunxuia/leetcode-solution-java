package q400;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/evaluate-division/
 *
 * Equations are given in the format A / B = k, where A and B are variables represented as strings, and k is a real
 * number (floating point number). Given some queries, return the answers. If the answer does not exist, return -1.0.
 *
 * Example:
 * Given a / b = 2.0, b / c = 3.0.
 * queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
 * return [6.0, 0.5, -1.0, 1.0, -1.0 ].
 *
 * The input is: vector<pair<string, string>> equations, vector<double>& values, vector<pair<string, string>> queries
 * , where equations.size() == values.size(), and the values are positive. This represents the equations. Return
 * vector<double>.
 *
 * According to the example above:
 *
 * equations = [ ["a", "b"], ["b", "c"] ],
 * values = [2.0, 3.0],
 * queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ].
 *
 *
 *
 * The input is always valid. You may assume that evaluating the queries will result in no division by zero and there
 * is no contradiction.
 */
@RunWith(LeetCodeRunner.class)
public class Q399_EvaluateDivision {

    // 图的遍历
    @Answer
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Node> nodes = new HashMap<>();
        for (int i = 0; i < equations.size(); i++) {
            String dividend = equations.get(i).get(0);
            String divider = equations.get(i).get(1);
            double value = values[i];
            Node dividendNode = nodes.computeIfAbsent(dividend, k -> new Node());
            Node dividerNode = nodes.computeIfAbsent(divider, k -> new Node());
            dividendNode.edges.add(new Edge(dividerNode, value));
            if (value != 0) {
                dividerNode.edges.add(new Edge(dividendNode, 1.0 / value));
            }
        }

        double[] res = new double[queries.size()];
        Set<Node> visited = new HashSet<>();
        for (int i = 0; i < queries.size(); i++) {
            Node dividendNode = nodes.get(queries.get(i).get(0));
            Node dividerNode = nodes.get(queries.get(i).get(1));
            if (dividendNode == null || dividerNode == null) {
                // -1.0 表示没有找到
                res[i] = -1.0;
            } else {
                visited.clear();
                res[i] = dfs(dividendNode, dividerNode, visited);
            }
        }
        return res;
    }

    private static class Node {

        private final List<Edge> edges = new ArrayList<>();
    }

    private static class Edge {

        private final Node other;

        private final double value;

        private Edge(Node other, double value) {
            this.other = other;
            this.value = value;
        }
    }

    private double dfs(Node curr, Node target, Set<Node> visited) {
        if (curr == target) {
            return 1.0;
        }
        visited.add(curr);
        for (Edge edge : curr.edges) {
            if (!visited.contains(edge.other)) {
                double val = dfs(edge.other, target, visited);
                if (val != -1.0) {
                    return edge.value * val;
                }
            }
        }
        return -1.0;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(Arrays.asList(Arrays.asList("a", "b"), Arrays.asList("b", "c")))
            .addArgument(new double[]{2.0, 3.0})
            .addArgument(Arrays.asList(Arrays.asList("a", "c"), Arrays.asList("b", "a"), Arrays.asList("a", "e"),
                    Arrays.asList("a", "a"), Arrays.asList("x", "x")))
            .expect(new double[]{6.0, 0.5, -1.0, 1.0, -1.0})
            .build();

}
