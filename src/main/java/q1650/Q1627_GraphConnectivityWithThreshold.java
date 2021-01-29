package q1650;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1627. Graph Connectivity With Threshold
 * https://leetcode.com/problems/graph-connectivity-with-threshold/
 *
 * We have n cities labeled from 1 to n. Two different cities with labels x and y are directly connected by a
 * bidirectional road if and only if x and y share a common divisor strictly greater than some threshold. More formally,
 * cities with labels x and y have a road between them if there exists an integer z such that all of the following are
 * true:
 *
 * x % z == 0,
 * y % z == 0, and
 * z > threshold.
 *
 * Given the two integers, n and threshold, and an array of queries, you must determine for each queries[i] = [ai, bi]
 * if cities ai and bi are connected (i.e. there is some path between them).
 *
 * Return an array answer, where answer.length == queries.length and answer[i] is true if for the ith query, there is a
 * path between ai and bi, or answer[i] is false if there is no path.
 *
 * Example 1:
 * <img scr="./Q1627_PIC1.jpg">
 * Input: n = 6, threshold = 2, queries = [[1,4],[2,5],[3,6]]
 * Output: [false,false,true]
 * Explanation: The divisors for each number:
 * 1:   1
 * 2:   1, 2
 * 3:   1, 3
 * 4:   1, 2, 4
 * 5:   1, 5
 * 6:   1, 2, 3, 6
 * Using the underlined divisors above the threshold, only cities 3 and 6 share a common divisor, so they are the
 * only ones directly connected. The result of each query:
 * [1,4]   1 is not connected to 4
 * [2,5]   2 is not connected to 5
 * [3,6]   3 is connected to 6 through path 3--6
 *
 * Example 2:
 * <img scr="./Q1627_PIC2.jpg">
 * Input: n = 6, threshold = 0, queries = [[4,5],[3,4],[3,2],[2,6],[1,3]]
 * Output: [true,true,true,true,true]
 * Explanation: The divisors for each number are the same as the previous example. However, since the threshold is 0,
 * all divisors can be used. Since all numbers share 1 as a divisor, all cities are connected.
 *
 * Example 3:
 * <img scr="./Q1627_PIC3.jpg">
 * Input: n = 5, threshold = 1, queries = [[4,5],[4,5],[3,2],[2,3],[3,4]]
 * Output: [false,false,false,false,false]
 * Explanation: Only cities 2 and 4 share a common divisor 2 which is strictly greater than the threshold 1, so they are
 * the only ones directly connected.
 * Please notice that there can be multiple queries for the same pair of nodes [x, y], and that the query [x, y] is
 * equivalent to the query [y, x].
 *
 * Constraints:
 *
 * 2 <= n <= 10^4
 * 0 <= threshold <= n
 * 1 <= queries.length <= 10^5
 * queries[i].length == 2
 * 1 <= ai, bi <= cities
 * ai != bi
 */
@RunWith(LeetCodeRunner.class)
public class Q1627_GraphConnectivityWithThreshold {

    /**
     * union find, 似乎 n <= 10^4 没起到拖慢速度的作用.
     */
    @Answer
    public List<Boolean> areConnected(int n, int threshold, int[][] queries) {
        int[] roots = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            roots[i] = i;
        }
        for (int i = threshold + 1; i <= n; i++) {
            for (int j = i + i; j <= n; j += i) {
                union(roots, i, j);
            }
        }

        List<Boolean> res = new ArrayList<>(queries.length);
        for (int[] query : queries) {
            res.add(isUnion(roots, query[0], query[1]));
        }
        return res;
    }

    private void union(int[] roots, int x, int y) {
        // LeetCode 上比较快的方法的优化点:
        // 让比较小的 id 数字变为root, 方便最后的 root id 逐渐向小数集中, 提升效率
        int rx = findRoot(roots, x);
        int ry = findRoot(roots, y);
        if (rx < ry) {
            roots[ry] = rx;
        } else {
            roots[rx] = ry;
        }
    }

    private int findRoot(int[] root, int i) {
        return root[i] == i ? i : (root[i] = findRoot(root, root[i]));
    }

    private boolean isUnion(int[] root, int x, int y) {
        return findRoot(root, x) == findRoot(root, y);
    }

    /**
     * 0-10000 中有 1299 个质数
     */
    private static final List<Integer> PRIMES = new ArrayList<>();

    static {
        loop:
        for (int num = 2; num <= 10000; num++) {
            for (int prime : PRIMES) {
                if (num % prime == 0) {
                    continue loop;
                }
            }
            PRIMES.add(num);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(6, 2, new int[][]{{1, 4}, {2, 5}, {3, 6}})
            .expect(List.of(false, false, true));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(6, 0, new int[][]{{4, 5}, {3, 4}, {3, 2}, {2, 6}, {1, 3}})
            .expect(List.of(true, true, true, true, true));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(5, 1, new int[][]{{4, 5}, {4, 5}, {3, 2}, {2, 3}, {3, 4}})
            .expect(List.of(false, false, false, false, false));

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(26, 3, new int[][]{{16, 9}})
            .expect(List.of(true));

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(6, 3, new int[][]{{4, 1}, {4, 3}})
            .expect(List.of(false, false));

}
