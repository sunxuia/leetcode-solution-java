package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1947. Maximum Compatibility Score Sum
 * https://leetcode.com/problems/maximum-compatibility-score-sum/
 *
 * There is a survey that consists of n questions where each question's answer is either 0 (no) or 1 (yes).
 *
 * The survey was given to m students numbered from 0 to m - 1 and m mentors numbered from 0 to m - 1. The answers of
 * the students are represented by a 2D integer array students where students[i] is an integer array that contains the
 * answers of the ith student (0-indexed). The answers of the mentors are represented by a 2D integer array mentors
 * where mentors[j] is an integer array that contains the answers of the jth mentor (0-indexed).
 *
 * Each student will be assigned to one mentor, and each mentor will have one student assigned to them. The
 * compatibility score of a student-mentor pair is the number of answers that are the same for both the student and the
 * mentor.
 *
 * For example, if the student's answers were [1, 0, 1] and the mentor's answers were [0, 0, 1], then their
 * compatibility score is 2 because only the second and the third answers are the same.
 *
 * You are tasked with finding the optimal student-mentor pairings to maximize the sum of the compatibility scores.
 *
 * Given students and mentors, return the maximum compatibility score sum that can be achieved.
 *
 * Example 1:
 *
 * Input: students = [[1,1,0],[1,0,1],[0,0,1]], mentors = [[1,0,0],[0,0,1],[1,1,0]]
 * Output: 8
 * Explanation: We assign students to mentors in the following way:
 * - student 0 to mentor 2 with a compatibility score of 3.
 * - student 1 to mentor 0 with a compatibility score of 2.
 * - student 2 to mentor 1 with a compatibility score of 3.
 * The compatibility score sum is 3 + 2 + 3 = 8.
 *
 * Example 2:
 *
 * Input: students = [[0,0],[0,0],[0,0]], mentors = [[1,1],[1,1],[1,1]]
 * Output: 0
 * Explanation: The compatibility score of any student-mentor pair is 0.
 *
 * Constraints:
 *
 * m == students.length == mentors.length
 * n == students[i].length == mentors[j].length
 * 1 <= m, n <= 8
 * students[i][k] is either 0 or 1.
 * mentors[j][k] is either 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1947_MaximumCompatibilityScoreSum {

    /**
     * 按照hint 中的提示, 使用位表示学生选择的导师的情况.
     */
    @Answer
    public int maxCompatibilitySum(int[][] students, int[][] mentors) {
        final int m = students.length;
        final int n = students[0].length;

        // 计算匹配度
        int[][] matches = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    if (students[i][k] == mentors[j][k]) {
                        matches[i][j]++;
                    }
                }
            }
        }

        // dp[i][mask] 表示匹配到第i个学生时已选导师的分布(bit mask).
        int size = 1 << m;
        int[][] dp = new int[m][size];
        for (int i = 0; i < m; i++) {
            dp[0][1 << i] = matches[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int mask = 0; mask < size; mask++) {
                if (dp[i - 1][mask] > 0) {
                    for (int j = 0; j < m; j++) {
                        int nMask = mask | 1 << j;
                        if (mask != nMask) {
                            dp[i][nMask] = Math.max(dp[i][nMask], dp[i - 1][mask] + matches[i][j]);
                        }
                    }
                }
            }
        }

        int res = 0;
        for (int i = 0; i < size; i++) {
            res = Math.max(res, dp[m - 1][i]);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{1, 1, 0}, {1, 0, 1}, {0, 0, 1}}, new int[][]{{1, 0, 0}, {0, 0, 1}, {1, 1, 0}})
            .expect(8);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{0, 0}, {0, 0}, {0, 0}}, new int[][]{{1, 1}, {1, 1}, {1, 1}})
            .expect(0);

}
