package q200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/dungeon-game/
 *
 * princess (P) and imprisoned her in the bottom-right corner of a dungeon. The dungeon consists of M x N rooms laid
 * out in a 2D grid. Our valiant knight (K) was initially positioned in the top-left room and must fight his way
 * through the dungeon to rescue the princess.
 *
 * The knight has an initial health point represented by a positive integer. If at any point his health point drops
 * to 0 or below, he dies immediately.
 *
 * Some of the rooms are guarded by demons, so the knight loses health (negative integers) upon entering these rooms;
 * other rooms are either empty (0's) or contain magic orbs that increase the knight's health (positive integers).
 *
 * In order to reach the princess as quickly as possible, the knight decides to move only rightward or downward in
 * each step.
 *
 *
 *
 * Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.
 *
 * For example, given the dungeon below, the initial health of the knight must be at least 7 if he follows the
 * optimal path RIGHT-> RIGHT -> DOWN -> DOWN.
 * > -2 (K) 	-3 	3
 * > -5 	-10 	1
 * > 10 	30 	-5 (P)
 *
 *
 *
 * Note:
 *
 * The knight's health has no upper bound.
 * Any room can contain threats or power-ups, even the first room the knight enters and the bottom-right room
 * where the princess is imprisoned.
 *
 * 题解:
 * 题目的大致含义: 2d 数组表示地牢的房间, 每个房间的数值表示对骑士生命值的影响(正数增加, 负数减小, 0表示没有影响), 生命值为0 则骑士死亡,
 * 现在骑士(K) 要从左上角到右下角去解救公主(P), 骑士每次只能往左或往下走1 步, 要求骑士能够成功救出公主的最小初始生命值 (可以确保从左上角
 * 到右下角的路径中骑士的生命值始终 > 0).
 */
@RunWith(LeetCodeRunner.class)
public class Q174_DungeonGame {

    @Answer
    public int calculateMinimumHP(int[][] dungeon) {
        final int m = dungeon.length, n = dungeon[0].length;
        int[][] dp = new int[m][n];
        dp[m - 1][n - 1] = Math.max(1, 1 - dungeon[m - 1][n - 1]);
        for (int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = Math.max(1, dp[i + 1][n - 1] - dungeon[i][n - 1]);
        }
        for (int i = n - 2; i >= 0; i--) {
            dp[m - 1][i] = Math.max(1, dp[m - 1][i + 1] - dungeon[m - 1][i]);
        }
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dp[i][j] = Math.max(1, Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j]);
            }
        }
        return dp[0][0];
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[][]{
            {-2, -3, 3},
            {-5, -10, 1},
            {10, 30, -5}
    }).expect(7);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {2, 1}
    }).expect(1);

}
