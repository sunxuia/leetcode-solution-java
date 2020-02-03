package q400;

import java.util.TreeSet;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/max-sum-of-rectangle-no-larger-than-k/
 *
 * Given a non-empty 2D matrix matrix and an integer k, find the max sum of a rectangle in the matrix such that its
 * sum is no larger than k.
 *
 * Example:
 *
 * Input: matrix = [[1,0,1],[0,-2,3]], k = 2
 * Output: 2
 * Explanation: Because the sum of rectangle [[0, 1], [-2, 3]] is 2,
 * and 2 is the max number no larger than k (k = 2).
 *
 * Note:
 *
 * The rectangle inside the matrix must have an area > 0.
 * What if the number of rows is much larger than the number of columns?
 */
@RunWith(LeetCodeRunner.class)
public class Q363_MaxSumOfRectangleNoLargerThanK {

    // 每次确定矩形的左上角, 选中右下角, 然后计算结果.
    // 这种方式时间复杂度 O(m^2*n^2), 能通过但是比较慢.
    @Answer
    public int maxSumSubmatrix(int[][] matrix, int k) {
        final int m = matrix.length, n = matrix[0].length;
        int res = Integer.MIN_VALUE;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int a = 0; a < m - i; a++) {
                    for (int b = 0; b < n - j; b++) {
                        int sum = matrix[a + i][b + j] + dp[a + 1][b] + dp[a][b + 1] - dp[a][b];
                        dp[a + 1][b + 1] = sum;
                        if (sum <= k && res < sum) {
                            res = sum;
                        }
                    }
                }
            }
        }
        return res;
    }

    /**
     * LeetCode 上比较主流的方法, 时间复杂度 O(m*log(m)*n^2).
     * 这个方法是首先确定矩形的左右边, 然后通过 TreeSet 来保存和获取上下边的信息.
     */
    @Answer
    public int maxSumSubmatrix_treeSet(int[][] matrix, int k) {
        final int m = matrix.length, n = matrix[0].length;
        int res = Integer.MIN_VALUE;
        // 首先确定左边界(包含)
        for (int left = 0; left < n; left++) {
            // 表示每行 [left, right] 元素之和(矩形每行的列之和)
            int[] sums = new int[m];
            // 然后确定右边界(包含)
            for (int right = left; right < n; right++) {
                // 计算当前扩展右边界 (right 一列) 之后的每行的列之和
                for (int i = 0; i < m; i++) {
                    sums[i] += matrix[i][right];
                }

                // 使用TreeSet 来在O(logN) 的时间复杂度内找出  <= k 的最大矩形和.
                TreeSet<Integer> set = new TreeSet<>();
                // 哨兵, 针对 sum[0] <= k 的情况, 这样set.ceiling(total - k) 的结果就是0
                set.add(0);
                for (int i = 0, total = 0; i < m; i++) {
                    total += sums[i];
                    // total - prev <= k, 这样的一个矩形区域的结果就可能是最大值.
                    Integer prev = set.ceiling(total - k);
                    if (prev != null) {
                        res = Math.max(res, total - prev);
                    }
                    set.add(total);
                }
            }
        }
        return res;
    }

    /**
     * LeetCode 上最快的解法, 是上面解法的改进, 时间复杂度 O(m^2*n^2).
     * 添加了对最多的情况 max <= k 的处理, 则时间复杂度会下降到 O(n^2).
     */
    @Answer
    public int maxSumSubmatrix3(int[][] matrix, int k) {
        final int m = matrix.length, n = matrix[0].length;
        int res = Integer.MIN_VALUE;
        for (int left = 0; left < n; left++) {
            int[] sums = new int[m];
            for (int right = left; right < n; right++) {
                // 矩形中多行之和的最大值
                int max = Integer.MIN_VALUE;
                for (int i = 0, sum = 0; i < m; i++) {
                    sums[i] += matrix[i][right];
                    sum = sum < 0 ? sums[i] : sum + sums[i];
                    max = Math.max(max, sum);
                }

                // 最大值 > k, 则要找出 <=k 的最大值.
                if (max > k) {
                    max = Integer.MIN_VALUE;
                    for (int top = 0; top < m; top++) {
                        int sum = 0;
                        for (int bottom = top; bottom < m; bottom++) {
                            sum += sums[bottom];
                            if (sum <= k && max < sum) {
                                max = sum;
                            }
                        }
                    }
                }
                res = Math.max(res, max);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[][]{
            {1, 0, 1},
            {0, -2, 3}
    }, 2).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[][]{
            {2, 2, -1}
    }, 0).expect(-1);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(new int[][]{
            {5, -4, -3, 4},
            {-3, -4, 4, 5},
            {5, 1, 5, -4}
    }, 8).expect(8);

}
