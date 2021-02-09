package q1750;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1722. Minimize Hamming Distance After Swap Operations
 * https://leetcode.com/problems/minimize-hamming-distance-after-swap-operations/
 *
 * You are given two integer arrays, source and target, both of length n. You are also given an array allowedSwaps where
 * each allowedSwaps[i] = [ai, bi] indicates that you are allowed to swap the elements at index ai and index bi
 * (0-indexed) of array source. Note that you can swap elements at a specific pair of indices multiple times and in any
 * order.
 *
 * The Hamming distance of two arrays of the same length, source and target, is the number of positions where the
 * elements are different. Formally, it is the number of indices i for 0 <= i <= n-1 where source[i] != target[i]
 * (0-indexed).
 *
 * Return the minimum Hamming distance of source and target after performing any amount of swap operations on array
 * source.
 *
 * Example 1:
 *
 * Input: source = [1,2,3,4], target = [2,1,4,5], allowedSwaps = [[0,1],[2,3]]
 * Output: 1
 * Explanation: source can be transformed the following way:
 * - Swap indices 0 and 1: source = [2,1,3,4]
 * - Swap indices 2 and 3: source = [2,1,4,3]
 * The Hamming distance of source and target is 1 as they differ in 1 position: index 3.
 *
 * Example 2:
 *
 * Input: source = [1,2,3,4], target = [1,3,2,4], allowedSwaps = []
 * Output: 2
 * Explanation: There are no allowed swaps.
 * The Hamming distance of source and target is 2 as they differ in 2 positions: index 1 and index 2.
 *
 * Example 3:
 *
 * Input: source = [5,1,2,4,3], target = [1,5,4,2,3], allowedSwaps = [[0,4],[4,2],[1,3],[1,4]]
 * Output: 0
 *
 * Constraints:
 *
 * n == source.length == target.length
 * 1 <= n <= 10^5
 * 1 <= source[i], target[i] <= 10^5
 * 0 <= allowedSwaps.length <= 10^5
 * allowedSwaps[i].length == 2
 * 0 <= ai, bi <= n - 1
 * ai != bi
 */
@RunWith(LeetCodeRunner.class)
public class Q1722_MinimizeHammingDistanceAfterSwapOperations {

    /**
     * 并查集
     */
    @Answer
    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        final int n = source.length;
        UnionFind uf = new UnionFind(n);
        for (int[] allowedSwap : allowedSwaps) {
            uf.union(allowedSwap[0], allowedSwap[1]);
        }

        Map<Integer, Integer>[] sets = new Map[n];
        for (int i = 0; i < n; i++) {
            sets[i] = new HashMap<>();
        }
        for (int i = 0; i < n; i++) {
            int root = uf.findRoot(i);
            Map<Integer, Integer> map = sets[root];
            sets[i] = map;
            map.put(source[i], map.getOrDefault(source[i], 0) + 1);
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            Integer count = sets[i].get(target[i]);
            if (count == null) {
                res++;
            } else if (count == 1) {
                sets[i].remove(target[i]);
            } else {
                sets[i].put(target[i], count - 1);
            }
        }
        return res;
    }

    private static class UnionFind {

        final int[] parents;

        UnionFind(int n) {
            parents = new int[n];
            for (int i = 0; i < n; i++) {
                parents[i] = i;
            }
        }

        int findRoot(int i) {
            return parents[i] == i ? i : (parents[i] = findRoot(parents[i]));
        }

        void union(int i, int j) {
            if (i < j) {
                parents[findRoot(j)] = findRoot(i);
            } else {
                parents[findRoot(i)] = findRoot(j);
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4}, new int[]{2, 1, 4, 5}, new int[][]{{0, 1}, {2, 3}})
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4}, new int[]{1, 3, 2, 4}, new int[][]{})
            .expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{5, 1, 2, 4, 3}, new int[]{1, 5, 4, 2, 3}, new int[][]{{0, 4}, {4, 2}, {1, 3}, {1, 4}})
            .expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{2, 3, 1}, new int[]{1, 2, 2}, new int[][]{{0, 2}, {1, 2}})
            .expect(1);

}
