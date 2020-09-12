package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1039. Minimum Score Triangulation of Polygon
 * https://leetcode.com/problems/minimum-score-triangulation-of-polygon/
 *
 * Given N, consider a convex N-sided polygon with vertices labelled A[0], A[i], ..., A[N-1] in clockwise order.
 *
 * Suppose you triangulate the polygon into N-2 triangles.  For each triangle, the value of that triangle is the product
 * of the labels of the vertices, and the total score of the triangulation is the sum of these values over all N-2
 * triangles in the triangulation.
 *
 * Return the smallest possible total score that you can achieve with some triangulation of the polygon.
 *
 * Example 1:
 *
 * Input: [1,2,3]
 * Output: 6
 * Explanation: The polygon is already triangulated, and the score of the only triangle is 6.
 *
 * Example 2:
 * (图 Q1039_PIC.png)
 * Input: [3,7,4,5]
 * Output: 144
 * Explanation: There are two triangulations, with possible scores: 3*7*5 + 4*5*7 = 245, or 3*4*5 + 3*4*7 = 144.  The
 * minimum score is 144.
 *
 * Example 3:
 *
 * Input: [1,3,1,4,1,5]
 * Output: 13
 * Explanation: The minimum score triangulation has score 1*1*3 + 1*1*4 + 1*1*5 + 1*1*1 = 13.
 *
 * Note:
 *
 * 3 <= A.length <= 50
 * 1 <= A[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1039_MinimumScoreTriangulationOfPolygon {

    /**
     * 贪婪算法不可解; 删除1 个点的 dfs 回溯法带不带缓存都会超时, 所以只能使用dp.
     */
    @Answer
    public int minScoreTriangulation(int[] A) {
        final int n = A.length;
        // dp[s][e] 表示A[s:e] 区间内的分数
        int[][] dp = new int[n][n];
        // 区间跨度(r+1) 从小到大
        for (int r = 2; r < n; r++) {
            // 区间从头到尾滑动, s < n < e 的情况可以不考虑
            for (int s = 0, e = r; e < n; s++, e++) {
                // 遍历区间组成三角形需要的中间的点
                for (int m = s + 1; m < e; m++) {
                    int score = A[s] * A[m] * A[e] + dp[s][m] + dp[m][e];
                    dp[s][e] = dp[s][e] == 0 ? score : Math.min(dp[s][e], score);
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * LeetCode 上比较快的解法, 是上面解法的dfs 版本.
     */
    @Answer
    public int minScoreTriangulation2(int[] A) {
        final int n = A.length;
        return dfs(A, new int[n][n], 0, n - 1);
    }

    private int dfs(int[] A, int[][] dp, int s, int e) {
        if (e - s < 2) {
            return 0;
        }
        if (dp[s][e] > 0) {
            return dp[s][e];
        }
        int res = Integer.MAX_VALUE;
        for (int m = s + 1; m < e; m++) {
            int score = A[s] * A[m] * A[e] + dfs(A, dp, s, m) + dfs(A, dp, m, e);
            res = Math.min(res, score);
        }
        dp[s][e] = res;
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3}).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{3, 7, 4, 5}).expect(144);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 3, 1, 4, 1, 5}).expect(13);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{4, 3, 1, 3}).expect(24);

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(new int[]{26, 35, 78, 22, 14, 62, 30, 21, 86, 7, 70, 67, 8, 28, 61, 33, 3, 78, 18, 71})
            .expect(76857);

}
