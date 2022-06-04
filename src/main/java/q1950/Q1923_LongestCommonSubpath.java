package q1950;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1923. Longest Common Subpath
 * https://leetcode.com/problems/longest-common-subpath/
 *
 * There is a country of n cities numbered from 0 to n - 1. In this country, there is a road connecting every pair of
 * cities.
 *
 * There are m friends numbered from 0 to m - 1 who are traveling through the country. Each one of them will take a path
 * consisting of some cities. Each path is represented by an integer array that contains the visited cities in order.
 * The path may contain a city more than once, but the same city will not be listed consecutively.
 *
 * Given an integer n and a 2D integer array paths where paths[i] is an integer array representing the path of the ith
 * friend, return the length of the longest common subpath that is shared by every friend's path, or 0 if there is no
 * common subpath at all.
 *
 * A subpath of a path is a contiguous sequence of cities within that path.
 *
 * Example 1:
 *
 * > Input: n = 5, paths = [[0,1,2,3,4],
 * >                            [2,3,4],
 * >                      [4,0,1,2,3]]
 * Output: 2
 * Explanation: The longest common subpath is [2,3].
 *
 * Example 2:
 *
 * Input: n = 3, paths = [[0],[1],[2]]
 * Output: 0
 * Explanation: There is no common subpath shared by the three paths.
 *
 * Example 3:
 *
 * > Input: n = 5, paths = [[0,1,2,3,4],
 * >                        [4,3,2,1,0]]
 * Output: 1
 * Explanation: The possible longest common subpaths are [0], [1], [2], [3], and [4]. All have a length of 1.
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 * m == paths.length
 * 2 <= m <= 10^5
 * sum(paths[i].length) <= 10^5
 * 0 <= paths[i][j] < n
 * The same city is not listed multiple times consecutively in paths[i].
 */
@RunWith(LeetCodeRunner.class)
public class Q1923_LongestCommonSubpath {

    /**
     * 这题需要计算 rolling hash, 这里自己实现了一个使用多个factor 的方式.
     */
    @Answer
    public int longestCommonSubpath(int n, int[][] paths) {
        moveMinToFront(paths);
        int start = 0, end = paths[0].length;
        while (start < end) {
            int len = (start + end + 1) / 2;
            boolean match = true;
            HashTree tree = new HashTree();
            rollingHash(paths[0], len, hashes -> tree.add(hashes, 0));
            for (int i = 1; match && i < paths.length; i++) {
                int sign = i;
                rollingHash(paths[i], len, hashes -> tree.match(hashes, 0, sign));
                match = tree.sign == sign;
            }
            if (match) {
                start = len;
            } else {
                end = len - 1;
            }
        }
        return end;
    }

    // 这种组合可以通过OJ
    private static final int[] FACTORS = new int[]{10, 1009};

    private static class HashTree extends HashMap<Integer, HashTree> {

        int sign;

        void add(int[] hashes, int i) {
            if (i < hashes.length) {
                computeIfAbsent(hashes[i], k -> new HashTree())
                        .add(hashes, i + 1);
            }
        }

        boolean match(int[] hashes, int i, int newSign) {
            if (sign < newSign - 1) {
                return false;
            }
            if (i < hashes.length) {
                HashTree child = get(hashes[i]);
                if (child == null || !child.match(hashes, i + 1, newSign)) {
                    return false;
                }
            }
            sign = newSign;
            return true;
        }
    }

    private void moveMinToFront(int[][] paths) {
        int min = 0;
        for (int i = 1; i < paths.length; i++) {
            if (paths[i].length < paths[min].length) {
                min = i;
            }
        }
        int[] path = paths[0];
        paths[0] = paths[min];
        paths[min] = path;
    }

    private void rollingHash(int[] path, int len, Consumer<int[]> consumer) {
        final int size = FACTORS.length;
        int[] rollings = new int[size];
        Arrays.fill(rollings, 1);
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < size; j++) {
                rollings[j] *= FACTORS[j];
            }
        }

        int[] hashes = new int[size];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < size; j++) {
                hashes[j] = hashes[j] * FACTORS[j] + path[i];
            }
        }
        consumer.accept(hashes);

        for (int i = len; i < path.length; i++) {
            for (int j = 0; j < size; j++) {
                hashes[j] = hashes[j] * FACTORS[j] + path[i] - path[i - len] * rollings[j];
            }
            consumer.accept(hashes);
        }
    }


    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(5, new int[][]{{0, 1, 2, 3, 4}, {2, 3, 4}, {4, 0, 1, 2, 3}})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(3, new int[][]{{0}, {1}, {2}})
            .expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(5, new int[][]{{0, 1, 2, 3, 4}, {4, 3, 2, 1, 0}})
            .expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(3, new int[][]{{2, 0}, {1, 2}, {0, 1, 0}})
            .expect(0);

}
