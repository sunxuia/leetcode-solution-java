package q2000;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1981. Minimize the Difference Between Target and Chosen Elements
 * https://leetcode.com/problems/minimize-the-difference-between-target-and-chosen-elements/
 *
 * You are given an m x n integer matrix mat and an integer target.
 *
 * Choose one integer from each row in the matrix such that the absolute difference between target and the sum of the
 * chosen elements is minimized.
 *
 * Return the minimum absolute difference.
 *
 * The absolute difference between two numbers a and b is the absolute value of a - b.
 *
 * Example 1:
 * (图Q1981_PIC1.png)
 * Input: mat = [[1,2,3],[4,5,6],[7,8,9]], target = 13
 * Output: 0
 * Explanation: One possible choice is to:
 * - Choose 1 from the first row.
 * - Choose 5 from the second row.
 * - Choose 7 from the third row.
 * The sum of the chosen elements is 13, which equals the target, so the absolute difference is 0.
 *
 * Example 2:
 * (图Q1981_PIC2.png)
 * Input: mat = [[1],[2],[3]], target = 100
 * Output: 94
 * Explanation: The best possible choice is to:
 * - Choose 1 from the first row.
 * - Choose 2 from the second row.
 * - Choose 3 from the third row.
 * The sum of the chosen elements is 6, and the absolute difference is 94.
 *
 * Example 3:
 * (图Q1981_PIC3.png)
 * Input: mat = [[1,2,9,8,7]], target = 6
 * Output: 1
 * Explanation: The best choice is to choose 7 from the first row.
 * The absolute difference is 1.
 *
 * Constraints:
 *
 * m == mat.length
 * n == mat[i].length
 * 1 <= m, n <= 70
 * 1 <= mat[i][j] <= 70
 * 1 <= target <= 800
 */
@RunWith(LeetCodeRunner.class)
public class Q1981_MinimizeTheDifferenceBetweenTargetAndChosenElements {

    @Answer
    public int minimizeTheDifference(int[][] mat, int target) {
        final int m = mat.length;
        final int n = mat[0].length;
        final int max = 70 * 70;

        int[] dp = new int[max + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int i = 0; i < m; i++) {
            for (int k = max; k >= 0; k--) {
                if (dp[k] == i) {
                    for (int j = 0; j < n; j++) {
                        dp[k + mat[i][j]] = i + 1;
                    }
                }
            }
        }

        int res = max;
        for (int i = 0; i <= max; i++) {
            if (dp[i] == m) {
                res = Math.min(res, Math.abs(target - i));
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9}}, 13)
            .expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{1}, {2}, {3}}, 100)
            .expect(94);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[][]{{1, 2, 9, 8, 7}}, 6)
            .expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[][]{{3, 5}, {5, 10}}, 47)
            .expect(32);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[][]{
                    {10, 3, 7, 7, 9, 6, 9, 8, 9, 5},
                    {1, 1, 6, 8, 6, 7, 7, 9, 3, 9},
                    {3, 4, 4, 1, 3, 6, 3, 3, 9, 9},
                    {6, 9, 9, 3, 8, 7, 9, 6, 10, 6}}, 5)
            .expect(3);

}
