package q2150;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2120. Execution of All Suffix Instructions Staying in a Grid</h3>
 * <a href="https://leetcode.com/problems/execution-of-all-suffix-instructions-staying-in-a-grid/">
 * https://leetcode.com/problems/execution-of-all-suffix-instructions-staying-in-a-grid/
 * </a><br/>
 *
 * <p>There is an <code>n x n</code> grid, with the top-left cell at <code>(0, 0)</code> and the bottom-right cell at
 * <code>(n - 1, n - 1)</code>. You are given the integer <code>n</code> and an integer array <code>startPos</code>
 * where <code>startPos = [start<sub>row</sub>, start<sub>col</sub>]</code> indicates that a robot is initially at cell
 * <code>(start<sub>row</sub>, start<sub>col</sub>)</code>.</p>
 *
 * <p>You are also given a <strong>0-indexed</strong> string <code>s</code> of length <code>m</code> where
 * <code>s[i]</code> is the <code>i<sup>th</sup></code> instruction for the robot: <code>&#39;L&#39;</code> (move
 * left),
 * <code>&#39;R&#39;</code> (move right), <code>&#39;U&#39;</code> (move up), and <code>&#39;D&#39;</code> (move
 * down).</p>
 *
 * <p>The robot can begin executing from any <code>i<sup>th</sup></code> instruction in <code>s</code>. It executes the
 * instructions one by one towards the end of <code>s</code> but it stops if either of these conditions is met:</p>
 *
 * <ul>
 * 	<li>The next instruction will move the robot off the grid.</li>
 * 	<li>There are no more instructions left to execute.</li>
 * </ul>
 *
 * <p>Return <em>an array</em> <code>answer</code> <em>of length</em> <code>m</code> <em>where</em>
 * <code>answer[i]</code> <em>is <strong>the number of instructions</strong> the robot can execute if the robot
 * <strong>begins executing from</strong> the</em> <code>i<sup>th</sup></code> <em>instruction in</em> <code>s</code>
 * .</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/09/1.png" style="width: 145px; height: 142px;" />
 * <pre>
 * <strong>Input:</strong> n = 3, startPos = [0,1], s = &quot;RRDDLU&quot;
 * <strong>Output:</strong> [1,5,4,3,1,0]
 * <strong>Explanation:</strong> Starting from startPos and beginning execution from the i<sup>th</sup> instruction:
 * - 0<sup>th</sup>: &quot;<u><strong>R</strong></u>RDDLU&quot;. Only one instruction &quot;R&quot; can be executed before it moves off the grid.
 * - 1<sup>st</sup>:  &quot;<u><strong>RDDLU</strong></u>&quot;. All five instructions can be executed while it stays in the grid and ends at (1, 1).
 * - 2<sup>nd</sup>:   &quot;<u><strong>DDLU</strong></u>&quot;. All four instructions can be executed while it stays in the grid and ends at (1, 0).
 * - 3<sup>rd</sup>:    &quot;<u><strong>DLU</strong></u>&quot;. All three instructions can be executed while it stays in the grid and ends at (0, 0).
 * - 4<sup>th</sup>:     &quot;<u><strong>L</strong></u>U&quot;. Only one instruction &quot;L&quot; can be executed before it moves off the grid.
 * - 5<sup>th</sup>:      &quot;U&quot;. If moving up, it would move off the grid.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/09/2.png" style="width: 106px; height: 103px;" />
 * <pre>
 * <strong>Input:</strong> n = 2, startPos = [1,1], s = &quot;LURD&quot;
 * <strong>Output:</strong> [4,1,0,0]
 * <strong>Explanation:</strong>
 * - 0<sup>th</sup>: &quot;<u><strong>LURD</strong></u>&quot;.
 * - 1<sup>st</sup>:  &quot;<u><strong>U</strong></u>RD&quot;.
 * - 2<sup>nd</sup>:   &quot;RD&quot;.
 * - 3<sup>rd</sup>:    &quot;D&quot;.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/09/3.png" style="width: 67px; height: 64px;" />
 * <pre>
 * <strong>Input:</strong> n = 1, startPos = [0,0], s = &quot;LRUD&quot;
 * <strong>Output:</strong> [0,0,0,0]
 * <strong>Explanation:</strong> No matter which instruction the robot begins execution from, it would move off the grid.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>m == s.length</code></li>
 * 	<li><code>1 &lt;= n, m &lt;= 500</code></li>
 * 	<li><code>startPos.length == 2</code></li>
 * 	<li><code>0 &lt;= start<sub>row</sub>, start<sub>col</sub> &lt; n</code></li>
 * 	<li><code>s</code> consists of <code>&#39;L&#39;</code>, <code>&#39;R&#39;</code>, <code>&#39;U&#39;</code>, and
 * 	<code>&#39;D&#39;</code>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2120_ExecutionOfAllSuffixInstructionsStayingInAGrid {

    @Answer
    public int[] executeInstructions(int n, int[] startPos, String s) {
        final int m = s.length();
        final int[][] DIRS = new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        int[][] dirs = new int[m][];
        for (int i = 0; i < m; i++) {
            switch (s.charAt(i)) {
                case 'L':
                    dirs[i] = DIRS[0];
                    break;
                case 'R':
                    dirs[i] = DIRS[1];
                    break;
                case 'U':
                    dirs[i] = DIRS[2];
                    break;
                case 'D':
                    dirs[i] = DIRS[3];
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

        int[] res = new int[m];
        for (int i = 0; i < m; i++) {
            int y = startPos[0], x = startPos[1];
            int step = i;
            while (step < m) {
                y += dirs[step][0];
                x += dirs[step][1];
                if (y < 0 || y >= n || x < 0 || x >= n) {
                    break;
                }
                step++;
            }
            res[i] = step - i;
        }
        return res;
    }

    /**
     * leetcode 上最快的做法, 时间复杂度 O(N)
     * 这个解法比较难以理解, 可以参考这里的图
     * <a href="https://leetcode.com/problems/execution-of-all-suffix-instructions-staying-in-a-grid/solutions/1647526/o-m-g-vs-brute-force/" />
     */
    @Answer
    public int[] executeInstructions2(int n, int[] startPos, String s) {
        final int m = s.length();

        // m + n <= 1000
        int h = 1000;
        int[] horizonPos = new int[2001];
        Arrays.fill(horizonPos, m);

        int v = 1000;
        int[] verticalPos = new int[2001];
        Arrays.fill(verticalPos, m);

        // 向左走到边界外的步数
        int leftBorder = startPos[1] + 1;
        int rightBorder = n - startPos[1];
        int upperBorder = startPos[0] + 1;
        int downBorder = n - startPos[0];

        int[] res = new int[m];
        for (int i = m - 1; i >= 0; --i) {
            switch (s.charAt(i)) {
                case 'L':
                    horizonPos[h++] = i;
                    break;
                case 'R':
                    horizonPos[h--] = i;
                    break;
                case 'U':
                    verticalPos[v++] = i;
                    break;
                case 'D':
                    verticalPos[v--] = i;
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            int step = m;
            step = Math.min(step, horizonPos[h - leftBorder]);
            step = Math.min(step, horizonPos[h + rightBorder]);
            step = Math.min(step, verticalPos[v - upperBorder]);
            step = Math.min(step, verticalPos[v + downBorder]);
            res[i] = step - i;
        }

        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(3, new int[]{0, 1}, "RRDDLU")
            .expect(new int[]{1, 5, 4, 3, 1, 0});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(2, new int[]{1, 1}, "LURD")
            .expect(new int[]{4, 1, 0, 0});

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(1, new int[]{0, 0}, "LRUD")
            .expect(new int[]{0, 0, 0, 0});

}
