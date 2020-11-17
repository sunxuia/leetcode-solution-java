package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1349. Maximum Students Taking Exam
 * https://leetcode.com/problems/maximum-students-taking-exam/
 *
 * Given a m * n matrix seats  that represent seats distributions in a classroom. If a seat is broken, it is denoted by
 * '#' character otherwise it is denoted by a '.' character.
 *
 * Students can see the answers of those sitting next to the left, right, upper left and upper right, but he cannot see
 * the answers of the student sitting directly in front or behind him. Return the maximum number of students that can
 * take the exam together without any cheating being possible..
 *
 * Students must be placed in seats in good condition.
 *
 * Example 1:
 * <img src="./Q1349_PIC.png">
 * Input: seats = [["#",".","#","#",".","#"],
 * [".","#","#","#","#","."],
 * ["#",".","#","#",".","#"]]
 * Output: 4
 * Explanation: Teacher can place 4 students in available seats so they don't cheat on the exam.
 *
 * Example 2:
 *
 * Input: seats = [[".","#"],
 * ["#","#"],
 * ["#","."],
 * ["#","#"],
 * [".","#"]]
 * Output: 3
 * Explanation: Place all students in available seats.
 *
 * Example 3:
 *
 * Input: seats = [["#",".",".",".","#"],
 * [".","#",".","#","."],
 * [".",".","#",".","."],
 * [".","#",".","#","."],
 * ["#",".",".",".","#"]]
 * Output: 10
 * Explanation: Place students in available seats in column 1, 3 and 5.
 *
 * Constraints:
 *
 * seats contains only characters '.' and'#'.
 * m == seats.length
 * n == seats[i].length
 * 1 <= m <= 8
 * 1 <= n <= 8
 */
@RunWith(LeetCodeRunner.class)
public class Q1349_MaximumStudentsTakingExam {

    /**
     * 根据hint 中的提示, 使用每行的 bit mask 来表示每行座位的分配.
     * 由此根据上一行得出下一行的结果.
     */
    @Answer
    public int maxStudents(char[][] seats) {
        final int m = seats.length, n = seats[0].length;
        // dp[i+1][mask] 表示第i 行占用座位排列是 mask 时的最小结果.
        int[][] dp = new int[m + 1][1 << n];
        // 哨兵, 由此所有结果都会 +1, 所以要在返回的时候 -1
        dp[0][0] = 1;
        // bfs 缓存结果的数据结构
        int[] cache = new int[1 << n];
        int cacheLen;
        // 每行每行遍历
        for (int i = 0; i < m; i++) {
            for (int mask = 0; mask < (1 << n); mask++) {
                if (dp[i][mask] == 0) {
                    continue;
                }
                cache[0] = 0;
                cacheLen = 1;
                dp[i + 1][0] = Math.max(dp[i + 1][0], dp[i][mask]);
                // bfs 遍历该行可能的位组合
                for (int j = 0; j < n; j++) {
                    if (seats[i][j] == '#'
                            || ((mask >> j - 1) & 1) == 1
                            || ((mask >> j + 1) & 1) == 1) {
                        // 这里不能安排座位
                        continue;
                    }
                    for (int k = cacheLen - 1; k >= 0; k--) {
                        if (((cache[k] >> j - 1) & 1) == 1) {
                            // 前一个位置已经安排了座位
                            continue;
                        }
                        int newMask = cache[k] | (1 << j);
                        cache[cacheLen++] = newMask;
                        int count = 0;
                        for (int l = 0; l < n; l++) {
                            count += (newMask >> l) & 1;
                        }
                        dp[i + 1][newMask] = Math.max(dp[i + 1][newMask], count + dp[i][mask]);
                    }
                }
            }
        }

        int res = 0;
        for (int i = 0; i < (1 << n); i++) {
            res = Math.max(res, dp[m][i] - 1);
        }
        return res;
    }

    /**
     * 简化了上面的求解过程, 思路一样, 更加慢了.
     */
    @Answer
    public int maxStudents2(char[][] seats) {
        final int m = seats.length, n = seats[0].length;
        int[][] dp = new int[2][1 << n];
        dp[0][0] = 1;
        for (int i = 0; i < m; i++) {
            int[] prev = dp[i % 2];
            int[] curr = dp[(i + 1) % 2];
            int currMask = 0;
            for (int j = 0; j < n; j++) {
                if (seats[i][j] == '.') {
                    currMask |= 1 << j;
                }
            }
            for (int prevMask = 0; prevMask < (1 << n); prevMask++) {
                if (prev[prevMask] == 0) {
                    continue;
                }
                // 遍历当前行所有可能的 mask 的情况, 判断是否满足条件.
                for (int mask = 0; mask < (1 << n); mask++) {
                    if ((mask | currMask) == currMask
                            && ((mask << 1) & prevMask) == 0
                            && ((mask >> 1) & prevMask) == 0) {
                        int count = 0;
                        for (int j = 0; j < n; j++) {
                            count += (mask >> j) & 1;
                            if (((mask >> j) & (mask >> j + 1) & 1) == 1) {
                                count = -1;
                                break;
                            }
                        }
                        if (count >= 0) {
                            curr[mask] = Math.max(curr[mask], count + prev[prevMask]);
                        }
                    }
                }
            }
        }

        int res = 0;
        for (int i = 0; i < (1 << n); i++) {
            res = Math.max(res, dp[m % 2][i] - 1);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new char[][]{
            {'#', '.', '#', '#', '.', '#'},
            {'.', '#', '#', '#', '#', '.'},
            {'#', '.', '#', '#', '.', '#'}
    }).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new char[][]{
            {'.', '#'},
            {'#', '#'},
            {'#', '.'},
            {'#', '#'},
            {'.', '#'}
    }).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new char[][]{
            {'#', '.', '.', '.', '#'},
            {'.', '#', '.', '#', '.'},
            {'.', '.', '#', '.', '.'},
            {'.', '#', '.', '#', '.'},
            {'#', '.', '.', '.', '#'}
    }).expect(10);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new char[][]{
            {'.', '#', '#', '.'},
            {'.', '.', '.', '#'},
            {'.', '.', '.', '.'},
            {'#', '.', '#', '#'}
    }).expect(5);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new char[][]{
            {'.', '#', '.', '#'},
            {'.', '.', '.', '#'},
            {'#', '#', '.', '.'},
            {'.', '#', '#', '#'}
    }).expect(6);

}
