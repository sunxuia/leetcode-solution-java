package q1550;

import java.util.Map.Entry;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1514. Path with Maximum Probability
 * https://leetcode.com/problems/path-with-maximum-probability/
 *
 * You are given an undirected weighted graph of n nodes (0-indexed), represented by an edge list where edges[i] = [a,
 * b] is an undirected edge connecting the nodes a and b with a probability of success of traversing that edge
 * succProb[i].
 *
 * Given two nodes start and end, find the path with the maximum probability of success to go from start to end and
 * return its success probability.
 *
 * If there is no path from start to end, return 0. Your answer will be accepted if it differs from the correct answer
 * by at most 1e-5.
 *
 * Example 1:
 * <img src="./Q11514_PIC1.png">
 * Input: n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.2], start = 0, end = 2
 * Output: 0.25000
 * Explanation: There are two paths from start to end, one having a probability of success = 0.2 and the other has 0.5 *
 * 0.5 = 0.25.
 *
 * Example 2:
 * <img src="./Q11514_PIC2.png">
 * Input: n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.3], start = 0, end = 2
 * Output: 0.30000
 *
 * Example 3:
 * <img src="./Q11514_PIC3.png">
 * Input: n = 3, edges = [[0,1]], succProb = [0.5], start = 0, end = 2
 * Output: 0.00000
 * Explanation: There is no path between 0 and 2.
 *
 * Constraints:
 *
 * 2 <= n <= 10^4
 * 0 <= start, end < n
 * start != end
 * 0 <= a, b < n
 * a != b
 * 0 <= succProb.length == edges.length <= 2*10^4
 * 0 <= succProb[i] <= 1
 * There is at most one edge between every two nodes.
 */
@RunWith(LeetCodeRunner.class)
public class Q1514_PathWithMaximumProbability {

    @Answer
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        Map<Integer, Double>[] paths = new Map[n];
        for (int i = 0; i < n; i++) {
            paths[i] = new HashMap<>();
        }
        for (int i = 0; i < edges.length; i++) {
            int a = edges[i][0], b = edges[i][1];
            paths[a].put(b, succProb[i]);
            paths[b].put(a, succProb[i]);
        }

        double[] posibilities = new double[n];
        posibilities[start] = 1;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (Integer next : paths[curr].keySet()) {
                double posibility = paths[curr].get(next);
                if (posibilities[next] < posibility * posibilities[curr]) {
                    posibilities[next] = posibility * posibilities[curr];
                    queue.offer(next);
                }
            }
        }
        return posibilities[end];
    }

    /**
     * LeetCode 上最快的解法, 所有边遍历n-1 次, 这样就会产生一个最小生成树, 概率最大的路径就在里面.
     */
    @Answer
    public double maxProbability2(int n, int[][] edges, double[] succProb, int start, int end) {
        double[] probs = new double[n];
        probs[start] = 1;
        boolean changed = true;
        for (int i = 0; i < n - 1 && changed; i++) {
            changed = false;
            for (int j = 0; j < edges.length; j++) {
                int a = edges[j][0];
                int b = edges[j][1];
                double prob = succProb[j];

                if (probs[b] < probs[a] * prob) {
                    probs[b] = probs[a] * prob;
                    changed = true;
                }

                if (probs[a] < probs[b] * prob) {
                    probs[a] = probs[b] * prob;
                    changed = true;
                }
            }
        }
        return probs[end];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(3, new int[][]{{0, 1}, {1, 2}, {0, 2}}, new double[]{0.5, 0.5, 0.2}, 0, 2)
            .expectDouble(0.25000);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(3, new int[][]{{0, 1}, {1, 2}, {0, 2}}, new double[]{0.5, 0.5, 0.3}, 0, 2)
            .expectDouble(0.30000);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(3, new int[][]{{0, 1}}, new double[]{0.5}, 0, 2)
            .expectDouble(0.00000);

    private TestDataFile testDataFile = new TestDataFile();

    /**
     * 5000 个节点, 9269 条边
     */
    @TestData
    public DataExpectation overMemory = DataExpectation.createWith(5000,
            TestDataFileHelper.read(testDataFile, 1, int[][].class),
            TestDataFileHelper.read(testDataFile, 2, double[].class),
            4106,
            2650)
            .expectDouble(0.133230);

}
