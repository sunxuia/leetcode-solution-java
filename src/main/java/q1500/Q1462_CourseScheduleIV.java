package q1500;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import q650.Q630_CourseScheduleIII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1462. Course Schedule IV
 * https://leetcode.com/problems/course-schedule-iv/
 *
 * There are a total of n courses you have to take, labeled from 0 to n-1.
 *
 * Some courses may have direct prerequisites, for example, to take course 0 you have first to take course 1, which is
 * expressed as a pair: [1,0]
 *
 * Given the total number of courses n, a list of direct prerequisite pairs and a list of queries pairs.
 *
 * You should answer for each queries[i] whether the course queries[i][0] is a prerequisite of the course queries[i][1]
 * or not.
 *
 * Return a list of boolean, the answers to the given queries.
 *
 * Please note that if course a is a prerequisite of course b and course b is a prerequisite of course c, then, course a
 * is a prerequisite of course c.
 *
 * Example 1:
 * <img src="./Q1462_PIC1.png">
 * Input: n = 2, prerequisites = [[1,0]], queries = [[0,1],[1,0]]
 * Output: [false,true]
 * Explanation: course 0 is not a prerequisite of course 1 but the opposite is true.
 *
 * Example 2:
 *
 * Input: n = 2, prerequisites = [], queries = [[1,0],[0,1]]
 * Output: [false,false]
 * Explanation: There are no prerequisites and each course is independent.
 *
 * Example 3:
 * <img src="./Q1462_PIC2.png">
 * Input: n = 3, prerequisites = [[1,2],[1,0],[2,0]], queries = [[1,0],[1,2]]
 * Output: [true,true]
 *
 * Example 4:
 *
 * Input: n = 3, prerequisites = [[1,0],[2,0]], queries = [[0,1],[2,0]]
 * Output: [false,true]
 *
 * Example 5:
 *
 * Input: n = 5, prerequisites = [[0,1],[1,2],[2,3],[3,4]], queries = [[0,4],[4,0],[1,3],[3,0]]
 * Output: [true,false,true,false]
 *
 * Constraints:
 *
 * 2 <= n <= 100
 * 0 <= prerequisite.length <= (n * (n - 1) / 2)
 * 0 <= prerequisite[i][0], prerequisite[i][1] < n
 * prerequisite[i][0] != prerequisite[i][1]
 * The prerequisites graph has no cycles.
 * The prerequisites graph has no repeated edges.
 * 1 <= queries.length <= 10^4
 * queries[i][0] != queries[i][1]
 *
 * 上一题 {@link Q630_CourseScheduleIII}
 */
@RunWith(LeetCodeRunner.class)
public class Q1462_CourseScheduleIV {

    /**
     * 有向无环图中的连通性问题
     */
    @Answer
    public List<Boolean> checkIfPrerequisite(int n, int[][] prerequisites, int[][] queries) {
        int[][] prevs = new int[n][n];
        for (int[] prerequisite : prerequisites) {
            prevs[prerequisite[1]][prerequisite[0]] = 1;
        }

        List<Boolean> res = new ArrayList<>();
        for (int[] query : queries) {
            res.add(dfs(prevs, query[1], query[0]));
        }
        return res;
    }

    private boolean dfs(int[][] prevs, int c, int p) {
        final int n = prevs.length;
        if (prevs[c][p] != 0) {
            return prevs[c][p] == 1;
        }
        boolean res = false;
        for (int i = 0; i < n && !res; i++) {
            res = prevs[c][i] == 1 && dfs(prevs, i, p);
        }
        prevs[c][p] = res ? 1 : -1;
        return res;
    }

    /**
     * LeetCode 上的另一种解法
     */
    @Answer
    public List<Boolean> checkIfPrerequisite2(int n, int[][] prerequisites, int[][] queries) {
        // 联通矩阵(connections[i][j] 表示 i->j 的连通性)
        boolean[][] connections = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            connections[i][i] = true;
        }
        for (int[] edge : prerequisites) {
            connections[edge[0]][edge[1]] = true;
        }

        // 设置联通矩阵 (Floyd-Warshall 算法)
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // 是否能通过中间点k, 让 i -> j
                    connections[i][j] |= connections[i][k] && connections[k][j];
                }
            }
        }

        ArrayList<Boolean> res = new ArrayList<>();
        for (int[] eachQuery : queries) {
            res.add(connections[eachQuery[0]][eachQuery[1]]);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(2, new int[][]{{1, 0}}, new int[][]{{0, 1}, {1, 0}})
            .expect(List.of(false, true));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(2, new int[][]{}, new int[][]{{1, 0}, {0, 1}})
            .expect(List.of(false, false));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(3, new int[][]{{1, 2}, {1, 0}, {2, 0}}, new int[][]{{1, 0}, {1, 2}})
            .expect(List.of(true, true));

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(3, new int[][]{{1, 0}, {2, 0}}, new int[][]{{0, 1}, {2, 0}})
            .expect(List.of(false, true));

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(5, new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 4}}, new int[][]{{0, 4}, {4, 0}, {1, 3}, {3, 0}})
            .expect(List.of(true, false, true, false));

}
