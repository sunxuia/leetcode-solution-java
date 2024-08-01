package q2100;

import java.util.function.IntSupplier;
import org.junit.Assert;
import org.junit.Test;
import q900.Q874_WalkingRobotSimulation;

/**
 * <h3>[Medium] 2069. Walking Robot Simulation II</h3>
 * <a href="https://leetcode.com/problems/walking-robot-simulation-ii/">
 * https://leetcode.com/problems/walking-robot-simulation-ii/
 * </a><br/>
 *
 * <p>A <code>width x height</code> grid is on an XY-plane with the <strong>bottom-left</strong> cell at <code>(0,
 * 0)</code> and the <strong>top-right</strong> cell at <code>(width - 1, height - 1)</code>. The grid is aligned with
 * the four cardinal directions (<code>&quot;North&quot;</code>, <code>&quot;East&quot;</code>,
 * <code>&quot;South&quot;</code>, and <code>&quot;West&quot;</code>). A robot is <strong>initially</strong> at cell
 * <code>(0, 0)</code> facing direction <code>&quot;East&quot;</code>.</p>
 *
 * <p>The robot can be instructed to move for a specific number of <strong>steps</strong>. For each step, it does the
 * following.</p>
 *
 * <ol>
 * 	<li>Attempts to move <strong>forward one</strong> cell in the direction it is facing.</li>
 * 	<li>If the cell the robot is <strong>moving to</strong> is <strong>out of bounds</strong>, the robot instead
 * 	<strong>turns</strong> 90 degrees <strong>counterclockwise</strong> and retries the step.</li>
 * </ol>
 *
 * <p>After the robot finishes moving the number of steps required, it stops and awaits the next instruction.</p>
 *
 * <p>Implement the <code>Robot</code> class:</p>
 *
 * <ul>
 * 	<li><code>Robot(int width, int height)</code> Initializes the <code>width x height</code> grid with the robot at
 * 	<code>(0, 0)</code> facing <code>&quot;East&quot;</code>.</li>
 * 	<li><code>void step(int num)</code> Instructs the robot to move forward <code>num</code> steps.</li>
 * 	<li><code>int[] getPos()</code> Returns the current cell the robot is at, as an array of length 2, <code>[x,
 * 	y]</code>.</li>
 * 	<li><code>String getDir()</code> Returns the current direction of the robot, <code>&quot;North&quot;</code>,
 * 	<code>&quot;East&quot;</code>, <code>&quot;South&quot;</code>, or <code>&quot;West&quot;</code>.</li>
 * </ul>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="example-1" src="https://assets.leetcode.com/uploads/2021/10/09/example-1.png" style="width: 498px;
 * height: 268px;" />
 * <pre>
 * <strong>Input</strong>
 * [&quot;Robot&quot;, &quot;step&quot;, &quot;step&quot;, &quot;getPos&quot;, &quot;getDir&quot;, &quot;step&quot;, &quot;step&quot;, &quot;step&quot;, &quot;getPos&quot;, &quot;getDir&quot;]
 * [[6, 3], [2], [2], [], [], [2], [1], [4], [], []]
 * <strong>Output</strong>
 * [null, null, null, [4, 0], &quot;East&quot;, null, null, null, [1, 2], &quot;West&quot;]
 *
 * <strong>Explanation</strong>
 * Robot robot = new Robot(6, 3); // Initialize the grid and the robot at (0, 0) facing East.
 * robot.step(2);  // It moves two steps East to (2, 0), and faces East.
 * robot.step(2);  // It moves two steps East to (4, 0), and faces East.
 * robot.getPos(); // return [4, 0]
 * robot.getDir(); // return &quot;East&quot;
 * robot.step(2);  // It moves one step East to (5, 0), and faces East.
 *                 // Moving the next step East would be out of bounds, so it turns and faces North.
 *                 // Then, it moves one step North to (5, 1), and faces North.
 * robot.step(1);  // It moves one step North to (5, 2), and faces <strong>North</strong> (not West).
 * robot.step(4);  // Moving the next step North would be out of bounds, so it turns and faces West.
 *                 // Then, it moves four steps West to (1, 2), and faces West.
 * robot.getPos(); // return [1, 2]
 * robot.getDir(); // return &quot;West&quot;
 *
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>2 &lt;= width, height &lt;= 100</code></li>
 * 	<li><code>1 &lt;= num &lt;= 10<sup>5</sup></code></li>
 * 	<li>At most <code>10<sup>4</sup></code> calls <strong>in total</strong> will be made to <code>step</code>,
 * 	<code>getPos</code>, and <code>getDir</code>.</li>
 * </ul>
 *
 * <br>
 * <pre>
 * 上一题 {@link Q874_WalkingRobotSimulation}
 * </pre>
 */
public class Q2069_WalkingRobotSimulationII {

    private static class Robot {

        // DIRECTION[x][y]
        private static final int[][] DIRECTION = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        private static final String[] DIRECTION_NAME = new String[]{"East", "North", "West", "South"};

        private final IntSupplier[] dists;

        private final int round;

        int dir;

        int x, y;

        public Robot(int width, int height) {
            round = width * 2 + height * 2 - 4;
            dists = new IntSupplier[]{
                    () -> width - x - 1,
                    () -> height - y - 1,
                    () -> x,
                    () -> y
            };
        }

        public void step(int num) {
            int dist = dists[dir].getAsInt();
            // 扣掉在棋盘四周转圈圈的步数
            num = (num - dist) % round + dist;
            while (num > 0) {
                if (dist >= num) {
                    // 不需要转向
                    x += DIRECTION[dir][0] * num;
                    y += DIRECTION[dir][1] * num;
                    return;
                }

                // 需要转向
                x += DIRECTION[dir][0] * dist;
                y += DIRECTION[dir][1] * dist;
                num -= dist;
                dir = (dir + 1) % 4;
                dist = dists[dir].getAsInt();
            }
        }

        public int[] getPos() {
            return new int[]{x, y};
        }

        public String getDir() {
            return DIRECTION_NAME[dir];
        }
    }

    /**
     * Your Robot object will be instantiated and called as such:
     * Robot obj = new Robot(width, height);
     * obj.step(num);
     * int[] param_2 = obj.getPos();
     * String param_3 = obj.getDir();
     */

    @Test
    public void testMethod() {
        Robot robot = new Robot(6, 3); // Initialize the grid and the robot at (0, 0) facing East.
        robot.step(2);  // It moves two steps East to (2, 0), and faces East.
        robot.step(2);  // It moves two steps East to (4, 0), and faces East.
        Assert.assertArrayEquals(new int[]{4, 0}, robot.getPos()); // return [4, 0]
        Assert.assertEquals("East", robot.getDir()); // return "East"
        robot.step(2);  // It moves one step East to (5, 0), and faces East.
        // Moving the next step East would be out of bounds, so it turns and faces North.
        // Then, it moves one step North to (5, 1), and faces North.
        robot.step(1);  // It moves one step North to (5, 2), and faces North (not West).
        robot.step(4);  // Moving the next step North would be out of bounds, so it turns and faces West.
        // Then, it moves four steps West to (1, 2), and faces West.
        Assert.assertArrayEquals(new int[]{1, 2}, robot.getPos());  // return [1, 2]
        Assert.assertEquals("West", robot.getDir()); // return "West"
    }

}
