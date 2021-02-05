package q1700;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1697. Checking Existence of Edge Length Limited Paths
 * https://leetcode.com/problems/checking-existence-of-edge-length-limited-paths/
 *
 * An undirected graph of n nodes is defined by edgeList, where edgeList[i] = [ui, vi, disi] denotes an edge between
 * nodes ui and vi with distance disi. Note that there may be multiple edges between two nodes.
 *
 * Given an array queries, where queries[j] = [pj, qj, limitj], your task is to determine for each queries[j] whether
 * there is a path between pj and qj such that each edge on the path has a distance strictly less than limitj .
 *
 * Return a boolean array answer, where answer.length == queries.length and the jth value of answer is true if there is
 * a path for queries[j] is true, and false otherwise.
 *
 * Example 1:
 * <img src="./Q1697_PIC1.png">
 * Input: n = 3, edgeList = [[0,1,2],[1,2,4],[2,0,8],[1,0,16]], queries = [[0,1,2],[0,2,5]]
 * Output: [false,true]
 * Explanation: The above figure shows the given graph. Note that there are two overlapping edges between 0 and 1 with
 * distances 2 and 16.
 * For the first query, between 0 and 1 there is no path where each distance is less than 2, thus we return false for
 * this query.
 * For the second query, there is a path (0 -> 1 -> 2) of two edges with distances less than 5, thus we return true for
 * this query.
 *
 * Example 2:
 * <img src="./Q1697_PIC2.png">
 * Input: n = 5, edgeList = [[0,1,10],[1,2,5],[2,3,9],[3,4,13]], queries = [[0,4,14],[1,4,13]]
 * Output: [true,false]
 * Exaplanation: The above figure shows the given graph.
 *
 * Constraints:
 *
 * 2 <= n <= 10^5
 * 1 <= edgeList.length, queries.length <= 10^5
 * edgeList[i].length == 3
 * queries[j].length == 3
 * 0 <= ui, vi, pj, qj <= n - 1
 * ui != vi
 * pj != qj
 * 1 <= disi, limitj <= 10^9
 * There may be multiple edges between two nodes.
 */
@RunWith(LeetCodeRunner.class)
public class Q1697_CheckingExistenceOfEdgeLengthLimitedPaths {

    /**
     * 根据相关标签的提示, 这题可以使用排序和并查集来做.
     */
    @Answer
    public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
        final int len = queries.length;
        Integer[] querySort = new Integer[len];
        for (int i = 0; i < len; i++) {
            querySort[i] = i;
        }
        // 按照边长排序
        Arrays.sort(querySort, Comparator.comparingInt(i -> queries[i][2]));

        int[] roots = new int[n];
        for (int i = 0; i < n; i++) {
            roots[i] = i;
        }

        // 按照边长排序
        Arrays.sort(edgeList, Comparator.comparingInt(a -> a[2]));

        boolean[] res = new boolean[len];
        for (int i = 0, ei = 0; i < len; i++) {
            int limit = queries[querySort[i]][2];
            // 将最大边长 < limit 的节点联合起来
            for (; ei < edgeList.length && edgeList[ei][2] < limit; ei++) {
                int u = edgeList[ei][0];
                int v = edgeList[ei][1];
                union(roots, u, v);
            }
            // 判断 p 与 q 在最大边长 < limit 的情况下是否是联通的.
            int p = queries[querySort[i]][0];
            int q = queries[querySort[i]][1];
            res[querySort[i]] = isUnion(roots, p, q);
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

    private void union(int[] roots, int a, int b) {
        if (a < b) {
            roots[findRoot(roots, b)] = findRoot(roots, a);
        } else {
            roots[findRoot(roots, a)] = findRoot(roots, b);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(3, new int[][]{{0, 1, 2}, {1, 2, 4}, {2, 0, 8}, {1, 0, 16}},
                    new int[][]{{0, 1, 2}, {0, 2, 5}})
            .expect(new boolean[]{false, true});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(5, new int[][]{{0, 1, 10}, {1, 2, 5}, {2, 3, 9}, {3, 4, 13}},
                    new int[][]{{0, 4, 14}, {1, 4, 13}})
            .expect(new boolean[]{true, false});

}
