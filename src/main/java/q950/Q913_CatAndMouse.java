package q950;

import java.util.Arrays;
import org.junit.runner.RunWith;
import q1750.Q1728_CatAndMouseII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 913. Cat and Mouse
 * https://leetcode.com/problems/cat-and-mouse/
 *
 * A game on an undirected graph is played by two players, Mouse and Cat, who alternate turns.
 *
 * The graph is given as follows: graph[a] is a list of all nodes b such that ab is an edge of the graph.
 *
 * Mouse starts at node 1 and goes first, Cat starts at node 2 and goes second, and there is a Hole at node 0.
 *
 * During each player's turn, they must travel along one edge of the graph that meets where they are.  For example, if
 * the Mouse is at node 1, it must travel to any node in graph[1].
 *
 * Additionally, it is not allowed for the Cat to travel to the Hole (node 0.)
 *
 * Then, the game can end in 3 ways:
 *
 * If ever the Cat occupies the same node as the Mouse, the Cat wins.
 * If ever the Mouse reaches the Hole, the Mouse wins.
 * If ever a position is repeated (ie. the players are in the same position as a previous turn, and it is the same
 * player's turn to move), the game is a draw.
 *
 * Given a graph, and assuming both players play optimally, return 1 if the game is won by Mouse, 2 if the game is won
 * by Cat, and 0 if the game is a draw.
 *
 * Example 1:
 *
 * Input: [[2,5],[3],[0,4,5],[1,4,5],[2,3],[0,2,3]]
 * Output: 0
 * Explanation:
 * > 4---3---1
 * > |   |
 * > 2---5
 * >  \ /
 * >   0
 *
 * Note:
 *
 * 3 <= graph.length <= 50
 * It is guaranteed that graph[1] is non-empty.
 * It is guaranteed that graph[2] contains a non-zero element.
 *
 * 下一题 {@link Q1728_CatAndMouseII}
 */
@RunWith(LeetCodeRunner.class)
public class Q913_CatAndMouse {

    /**
     * 参考文档 https://www.cnblogs.com/grandyang/p/11515655.html
     */
    @Answer
    public int catMouseGame(int[][] graph) {
        final int n = graph.length;

        // dp[r][m][c] 表示在第r 轮, 鼠在m, 猫在c 的结果.
        int[][][] dp = new int[2 * n][n][n];
        for (int i = 0; i < 2 * n; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        // 起始位置: 第0 轮, 鼠在1, 猫在2
        return dfs(graph, dp, 0, 1, 2);
    }

    private int dfs(int[][] graph, int[][][] dp, int round, int mouse, int cat) {
        final int n = graph.length;
        // 步数到了2n 则表示平局, 2n 表示猫鼠都把全图跑了一遍
        if (round == n * 2) {
            return 0;
        }
        // 老鼠和猫遇上了
        if (mouse == cat) {
            return dp[round][mouse][cat] = 2;
        }
        // 老鼠进洞
        if (mouse == 0) {
            return dp[round][mouse][cat] = 1;
        }
        // 已经缓存的结果
        if (dp[round][mouse][cat] != -1) {
            return dp[round][mouse][cat];
        }

        if (round % 2 == 0) {
            // 老鼠的回合, 尝试从该节点向不同相邻节点走
            int res = 2;
            for (int neighbor : graph[mouse]) {
                int ret = dfs(graph, dp, round + 1, neighbor, cat);
                if (ret == 1) {
                    // 走这条线老鼠可以赢
                    res = 1;
                    break;
                } else if (ret == 0) {
                    // 平局
                    res = 0;
                }
            }
            return dp[round][mouse][cat] = res;
        } else {
            // 猫的回合, 尝试从该节点向不同相邻节点走
            int res = 1;
            for (int neighbor : graph[cat]) {
                if (neighbor == 0) {
                    continue;
                }
                int ret = dfs(graph, dp, round + 1, mouse, neighbor);
                if (ret == 2) {
                    res = 2;
                    break;
                } else if (ret == 0) {
                    res = 0;
                }
            }
            return dp[round][mouse][cat] = res;
        }
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(new int[][]{{2, 5}, {3}, {0, 4, 5}, {1, 4, 5}, {2, 3}, {0, 2, 3}})
            .expect(0);

}
