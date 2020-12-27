package q1450;

import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.common.json.JsonTypeWrapper;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1434. Number of Ways to Wear Different Hats to Each Other
 * https://leetcode.com/problems/number-of-ways-to-wear-different-hats-to-each-other/
 *
 * There are n people and 40 types of hats labeled from 1 to 40.
 *
 * Given a list of list of integers hats, where hats[i] is a list of all hats preferred by the i-th person.
 *
 * Return the number of ways that the n people wear different hats to each other.
 *
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: hats = [[3,4],[4,5],[5]]
 * Output: 1
 * Explanation: There is only one way to choose hats given the conditions.
 * First person choose hat 3, Second person choose hat 4 and last one hat 5.
 *
 * Example 2:
 *
 * Input: hats = [[3,5,1],[3,5]]
 * Output: 4
 * Explanation: There are 4 ways to choose hats
 * (3,5), (5,3), (1,3) and (1,5)
 *
 * Example 3:
 *
 * Input: hats = [[1,2,3,4],[1,2,3,4],[1,2,3,4],[1,2,3,4]]
 * Output: 24
 * Explanation: Each person can choose hats labeled from 1 to 4.
 * Number of Permutations of (1,2,3,4) = 24.
 *
 * Example 4:
 *
 * Input: hats = [[1,2,3],[2,3,5,6],[1,3,7,9],[1,8,9],[2,5,7]]
 * Output: 111
 *
 * Constraints:
 *
 * n == hats.length
 * 1 <= n <= 10
 * 1 <= hats[i].length <= 40
 * 1 <= hats[i][j] <= 40
 * hats[i] contains a list of unique integers.
 */
@RunWith(LeetCodeRunner.class)
public class Q1434_NumberOfWaysToWearDifferentHatsToEachOther {

    /**
     * 正常思路的cache 因为范围大所以需要使用 hashmap,
     * 而hashmap 的方式会超时, 因此反过来cache.
     */
    @Answer
    public int numberWays(List<List<Integer>> hats) {
        final int n = hats.size();
        int[] available = new int[40];
        for (int i = 0; i < n; i++) {
            for (int hat : hats.get(i)) {
                available[hat - 1] |= 1 << i;
            }
        }
        int[][] cache = new int[41][1 << n];
        for (int i = 0; i < 40; i++) {
            Arrays.fill(cache[i], -1);
        }
        cache[40][(1 << n) - 1] = 1;
        return dfs(cache, available, 0, 0);
    }

    private int dfs(int[][] cache, int[] available, int mask, int index) {
        final int mod = 10_0000_0007;
        if (cache[index][mask] != -1) {
            return cache[index][mask];
        }
        int res = dfs(cache, available, mask, index + 1);
        for (int p = 0; p < 10; p++) {
            if ((available[index] >> p & 1) == 1 && (mask >> p & 1) == 0) {
                int count = dfs(cache, available, mask | 1 << p, index + 1);
                res = (res + count) % mod;
            }
        }
        cache[index][mask] = res;
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(List.of(List.of(3, 4), List.of(4, 5), List.of(5)))
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(List.of(List.of(3, 5, 1), List.of(3, 5)))
            .expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(List.of(List.of(1, 2, 3, 4), List.of(1, 2, 3, 4), List.of(1, 2, 3, 4), List.of(1, 2, 3, 4)))
            .expect(24);

    @TestData
    public DataExpectation example4 = DataExpectation
            .create(List.of(
                    List.of(1, 2, 3), List.of(2, 3, 5, 6), List.of(1, 3, 7, 9), List.of(1, 8, 9), List.of(2, 5, 7)))
            .expect(111);

    @TestData
    public DataExpectation overMemory = DataExpectation
            .create(List.of(
                    List.of(4, 15, 16, 26, 28),
                    List.of(1, 2, 3, 4, 6, 7, 8, 10, 13, 14, 15, 16, 17, 18, 19, 21, 22, 24, 25, 27, 28, 29, 30),
                    List.of(1, 2, 3, 4, 5, 7, 9, 10, 11, 12, 14, 15, 17, 18, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30),
                    List.of(2, 3, 6, 7, 14, 16, 17, 18, 19, 20, 21, 24, 25, 27, 28, 29),
                    List.of(1, 10),
                    List.of(1, 5, 6, 7, 8, 9, 10, 11, 13, 14, 15, 16, 19, 20, 21, 22, 24, 25, 27, 28),
                    List.of(2, 5, 10, 14, 16, 19, 21, 22, 23, 27, 30)))
            .expect(9497549);

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read(testDataFile, 1, new JsonTypeWrapper<List<List<Integer>>>() {}))
            .expect(842465346);

}
