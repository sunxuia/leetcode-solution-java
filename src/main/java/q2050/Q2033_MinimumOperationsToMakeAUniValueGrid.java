package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 2033. Minimum Operations to Make a Uni-Value Grid
 * https://leetcode.com/problems/minimum-operations-to-make-a-uni-value-grid/
 *
 * You are given a 2D integer grid of size m x n and an integer x. In one operation, you can add x to or subtract x from
 * any element in the grid.
 *
 * A uni-value grid is a grid where all the elements of it are equal.
 *
 * Return the minimum number of operations to make the grid uni-value. If it is not possible, return -1.
 *
 * Example 1:
 * (图Q2033_PIC1.png)
 * Input: grid = [[2,4],[6,8]], x = 2
 * Output: 4
 * Explanation: We can make every element equal to 4 by doing the following:
 * - Add x to 2 once.
 * - Subtract x from 6 once.
 * - Subtract x from 8 twice.
 * A total of 4 operations were used.
 *
 * Example 2:
 * (图Q2033_PIC2.png)
 * Input: grid = [[1,5],[2,3]], x = 1
 * Output: 5
 * Explanation: We can make every element equal to 3.
 *
 * Example 3:
 * (图Q2033_PIC3.png)
 * Input: grid = [[1,2],[3,4]], x = 2
 * Output: -1
 * Explanation: It is impossible to make every element equal.
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 10^5
 * 1 <= m * n <= 10^5
 * 1 <= x, grid[i][j] <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q2033_MinimumOperationsToMakeAUniValueGrid {

//    @DebugWith("normal4")
    @Answer
    public int minOperations(int[][] grid, int x) {
        final int m = grid.length;
        final int n = grid[0].length;
        int max = 0, min = 10_0000;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                max = Math.max(max, grid[i][j]);
                min = Math.min(min, grid[i][j]);
            }
        }

        // 二维数组 -> 一维数组
        int[] gaps = new int[(max - min) / x + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if ((grid[i][j] - min) % x != 0) {
                    // 无法通过加减x 来到达的点
                    return -1;
                }
                gaps[(grid[i][j] - min) / x]++;
            }
        }

        // 轴上距离各点最近的点是中位数所在的点
        int median = 0, count = 0;
        for (int i = 0; i < gaps.length && count <= m * n / 2; i++) {
            median = i;
            count += gaps[i];
        }
        int res = 0;
        for (int i = 0; i < gaps.length; i++) {
            res += Math.abs(median - i) * gaps[i];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{2, 4}, {6, 8}}, 2)
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{1, 5}, {2, 3}}, 1)
            .expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[][]{{1, 2}, {3, 4}}, 2)
            .expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[][]{{931, 128}, {639, 712}}, 73)
            .expect(12);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[][]{{529, 529, 989}, {989, 529, 345}, {989, 805, 69}}, 92)
            .expect(25);

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith(new int[][]{
                    {141, 105, 69, 273, 681, 105, 933, 417, 309},
                    {921, 657, 945, 717, 885, 57, 453, 921, 897},
                    {681, 345, 657, 177, 897, 609, 465, 801, 429},
                    {681, 993, 741, 885, 105, 981, 477, 249, 921},
                    {369, 885, 945, 537, 45, 861, 381, 345, 417},
                    {849, 849, 477, 513, 297, 609, 561, 177, 801},
                    {561, 417, 129, 585, 621, 561, 261, 153, 501},
                    {249, 777, 969, 249, 357, 393, 93, 321, 573},
                    {525, 813, 381, 909, 825, 297, 681, 345, 813}}, 12)
            .expect(1632);

    @TestData
    public DataExpectation normal4 = DataExpectation
            .createWith(new int[][]{{4}, {1}}, 2)
            .expect(-1);

}
