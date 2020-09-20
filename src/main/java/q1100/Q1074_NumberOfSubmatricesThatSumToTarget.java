package q1100;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1074. Number of Submatrices That Sum to Target
 * https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/
 *
 * Given a matrix and a target, return the number of non-empty submatrices that sum to target.
 *
 * A submatrix x1, y1, x2, y2 is the set of all cells matrix[x][y] with x1 <= x <= x2 and y1 <= y <= y2.
 *
 * Two submatrices (x1, y1, x2, y2) and (x1', y1', x2', y2') are different if they have some coordinate that is
 * different: for example, if x1 != x1'.
 *
 * Example 1:
 * ( 图 Q1074_PIC.png)
 * Input: matrix = [[0,1,0],[1,1,1],[0,1,0]], target = 0
 * Output: 4
 * Explanation: The four 1x1 submatrices that only contain 0.
 *
 * Example 2:
 *
 * Input: matrix = [[1,-1],[-1,1]], target = 0
 * Output: 5
 * Explanation: The two 1x2 submatrices, plus the two 2x1 submatrices, plus the 2x2 submatrix.
 *
 * Example 3:
 *
 * Input: matrix = [[904]], target = 0
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= matrix.length <= 100
 * 1 <= matrix[0].length <= 100
 * -1000 <= matrix[i] <= 1000
 * -10^8 <= target <= 10^8
 */
@RunWith(LeetCodeRunner.class)
public class Q1074_NumberOfSubmatricesThatSumToTarget {

    @Answer
    public int numSubmatrixSumTarget(int[][] matrix, int target) {
        final int m = matrix.length, n = matrix[0].length;
        int[][] sums = new int[m + 1][n + 1];
        for (int y = 0; y < m; y++) {
            for (int x = 0; x < n; x++) {
                sums[y + 1][x + 1] = sums[y + 1][x] + sums[y][x + 1] - sums[y][x] + matrix[y][x];
            }
        }

        int res = 0;
        for (int y1 = 0; y1 < m; y1++) {
            for (int x1 = 0; x1 < n; x1++) {
                for (int y2 = y1; y2 < m; y2++) {
                    for (int x2 = x1; x2 < n; x2++) {
                        int sum = sums[y2 + 1][x2 + 1] - sums[y2 + 1][x1] - sums[y1][x2 + 1] + sums[y1][x1];
                        if (sum == target) {
                            res++;
                        }
                    }
                }
            }
        }
        return res;
    }

    /**
     * LeetCode 上比较快的解法.
     */
    @Answer
    public int numSubmatrixSumTarget2(int[][] matrix, int target) {
        final int m = matrix.length, n = matrix[0].length;
        for (int y = 0; y < m; y++) {
            for (int x = 1; x < n; x++) {
                matrix[y][x] += matrix[y][x - 1];
            }
        }

        int res = 0;
        Map<Integer, Integer> counts = new HashMap<>(m);
        // 首先确定x1 和x2, 然后从0 -> m 水平扫描.
        for (int x1 = 0; x1 < n; x1++) {
            for (int x2 = x1; x2 < n; x2++) {
                counts.clear();
                counts.put(0, 1);
                int sum = 0;
                for (int y = 0; y < m; y++) {
                    // 计算 (x1, 0) -> (x2, y) 的面积
                    sum += matrix[y][x2] - (x1 > 0 ? matrix[y][x1 - 1] : 0);
                    // 统计 (x1, 0) -> (x2, ?) 面积为 sum - target 的数量.
                    res += counts.getOrDefault(sum - target, 0);
                    counts.put(sum, counts.getOrDefault(sum, 0) + 1);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{
                    {0, 1, 0},
                    {1, 1, 1},
                    {0, 1, 0}
            }, 0).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{
                    {1, -1},
                    {-1, 1}
            }, 0).expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[][]{{904}}, 0)
            .expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[][]{
                    {0, 1, 1, 1, 0, 1},
                    {0, 0, 0, 0, 0, 1},
                    {0, 0, 1, 0, 0, 1},
                    {1, 1, 0, 1, 1, 0},
                    {1, 0, 0, 1, 0, 0}
            }, 0).expect(43);

}
