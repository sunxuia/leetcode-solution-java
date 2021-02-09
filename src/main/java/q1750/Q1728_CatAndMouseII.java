package q1750;

import java.util.Arrays;
import org.junit.runner.RunWith;
import q950.Q913_CatAndMouse;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1728. Cat and Mouse II
 * https://leetcode.com/problems/cat-and-mouse-ii/
 *
 * A game is played by a cat and a mouse named Cat and Mouse.
 *
 * The environment is represented by a grid of size rows x cols, where each element is a wall, floor, player (Cat,
 * Mouse), or food.
 *
 * Players are represented by the characters 'C'(Cat),'M'(Mouse).
 * Floors are represented by the character '.' and can be walked on.
 * Walls are represented by the character '#' and cannot be walked on.
 * Food is represented by the character 'F' and can be walked on.
 * There is only one of each character 'C', 'M', and 'F' in grid.
 *
 * Mouse and Cat play according to the following rules:
 *
 * Mouse moves first, then they take turns to move.
 * During each turn, Cat and Mouse can jump in one of the four directions (left, right, up, down). They cannot jump over
 * the wall nor outside of the grid.
 * catJump, mouseJump are the maximum lengths Cat and Mouse can jump at a time, respectively. Cat and Mouse can jump
 * less than the maximum length.
 * Staying in the same position is allowed.
 * Mouse can jump over Cat.
 *
 * The game can end in 4 ways:
 *
 * If Cat occupies the same position as Mouse, Cat wins.
 * If Cat reaches the food first, Cat wins.
 * If Mouse reaches the food first, Mouse wins.
 * If Mouse cannot get to the food within 1000 turns, Cat wins.
 *
 * Given a rows x cols matrix grid and two integers catJump and mouseJump, return true if Mouse can win the game if both
 * Cat and Mouse play optimally, otherwise return false.
 *
 * Example 1:
 * <img src="./Q1728_PIC1.png">
 * Input: grid = ["####F","#C...","M...."], catJump = 1, mouseJump = 2
 * Output: true
 * Explanation: Cat cannot catch Mouse on its turn nor can it get the food before Mouse.
 *
 * Example 2:
 * <img src="./Q1728_PIC2.png">
 * Input: grid = ["M.C...F"], catJump = 1, mouseJump = 4
 * Output: true
 *
 * Example 3:
 *
 * Input: grid = ["M.C...F"], catJump = 1, mouseJump = 3
 * Output: false
 *
 * Example 4:
 *
 * Input: grid = ["C...#","...#F","....#","M...."], catJump = 2, mouseJump = 5
 * Output: false
 *
 * Example 5:
 *
 * Input: grid = [".M...","..#..","#..#.","C#.#.","...#F"], catJump = 3, mouseJump = 1
 * Output: true
 *
 * Constraints:
 *
 * rows == grid.length
 * cols = grid[i].length
 * 1 <= rows, cols <= 8
 * grid[i][j] consist only of characters 'C', 'M', 'F', '.', and '#'.
 * There is only one of each character 'C', 'M', and 'F' in grid.
 * 1 <= catJump, mouseJump <= 8
 *
 * 上一题 {@link Q913_CatAndMouse}
 */
@RunWith(LeetCodeRunner.class)
public class Q1728_CatAndMouseII {

    /**
     * @formatter:off
     * 参考文档
     * https://leetcode-cn.com/problems/ways-to-split-array-into-three-subarrays/solution/5643-jiang-shu-zu-fen-cheng-san-ge-zi-sh-fmep/
     * @formatter:on
     */
    @Answer
    public boolean canMouseWin(String[] grid, int catJump, int mouseJump) {
        m = grid.length;
        n = grid[0].length();
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        int pm = 0, pc = 0, f = 0, t = 0;
        int[][] g = new int[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                char c = grid[i].charAt(j);
                if (c == '#') {
                    g[i][j] = -1;
                }
                if (c == 'M') {
                    pm = i * n + j;
                }
                if (c == 'C') {
                    pc = i * n + j;
                }
                if (c == 'F') {
                    f = i * n + j;
                }
            }
        }
        return dfs(g, t, pm, pc, f, mouseJump, catJump) == 1;
    }

    private int[][][] dp = new int[130][65][65];

    private int m, n;

    private final int[] dirs = {0, 1, 0, -1, 0};

    int dfs(int[][] g, int t, int pm, int pc, int f, int mj, int cj) {
        //某一状态为1对应mouse胜利，为0对应cat胜利，初始化为-1
        if (pm == f) {
            return 1;
        }
        if (pc == f || pm == pc || t >= 128) {
            return 0;
        }
        if (dp[t][pm][pc] != -1) {
            return dp[t][pm][pc];
        }
        if (t % 2 == 0) {
            // mouse's turn
            int x = pm / n, y = pm % n;
            for (int d = 0; d < 4; d++) {
                for (int j = 0; j <= mj; j++) {
                    int xx = x + dirs[d] * j, yy = y + dirs[d + 1] * j;
                    if (xx >= 0 && xx < m && yy >= 0 && yy < n) {
                        if (g[xx][yy] == -1) {
                            break;
                        }
                        //mouse有必胜策略, 该状态可以直接返回mouse胜利
                        else if (dfs(g, t + 1, xx * n + yy, pc, f, mj, cj) == 1) {
                            return dp[t][pm][pc] = 1;
                        }
                    } else {
                        break;
                    }
                }
            }
            dp[t][pm][pc] = 0;
        } else {
            // cat's turn
            int x = pc / n, y = pc % n;
            for (int d = 0; d < 4; d++) {
                for (int j = 0; j <= cj; j++) {
                    int xx = x + dirs[d] * j, yy = y + dirs[d + 1] * j;
                    if (xx >= 0 && xx < m && yy >= 0 && yy < n) {
                        if (g[xx][yy] == -1) {
                            break;
                        }
                        //cat有必胜策略, 该状态可以直接返回cat胜利
                        else if (dfs(g, t + 1, pm, xx * n + yy, f, mj, cj) == 0) {
                            return dp[t][pm][pc] = 0;
                        }
                    } else {
                        break;
                    }
                }
            }
            dp[t][pm][pc] = 1;
        }
        return dp[t][pm][pc];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{
                    "####F",
                    "#C...",
                    "M...."}, 1, 2)
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"M.C...F"}, 1, 4)
            .expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new String[]{"M.C...F"}, 1, 3)
            .expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new String[]{
                    "C...#",
                    "...#F",
                    "....#",
                    "M...."}, 2, 5)
            .expect(false);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(new String[]{
                    ".M...",
                    "..#..",
                    "#..#.",
                    "C#.#.",
                    "...#F"}, 3, 1)
            .expect(true);

}
