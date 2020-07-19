package q900;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Easy] 874. Walking Robot Simulation
 * https://leetcode.com/problems/walking-robot-simulation/
 *
 * A robot on an infinite grid starts at point (0, 0) and faces north.  The robot can receive one of three possible
 * types of commands:
 *
 * -2: turn left 90 degrees
 * -1: turn right 90 degrees
 * 1 <= x <= 9: move forward x units
 *
 * Some of the grid squares are obstacles.
 *
 * The i-th obstacle is at grid point (obstacles[i][0], obstacles[i][1])
 *
 * If the robot would try to move onto them, the robot stays on the previous grid square instead (but still continues
 * following the rest of the route.)
 *
 * Return the square of the maximum Euclidean distance that the robot will be from the origin.
 *
 * Example 1:
 *
 * Input: commands = [4,-1,3], obstacles = []
 * Output: 25
 * Explanation: robot will go to (3, 4)
 *
 * Example 2:
 *
 * Input: commands = [4,-1,4,-2,4], obstacles = [[2,4]]
 * Output: 65
 * Explanation: robot will be stuck at (1, 4) before turning left and going to (1, 8)
 *
 * Note:
 *
 * 0 <= commands.length <= 10000
 * 0 <= obstacles.length <= 10000
 * -30000 <= obstacle[i][0] <= 30000
 * -30000 <= obstacle[i][1] <= 30000
 * The answer is guaranteed to be less than 2 ^ 31.
 */
@RunWith(LeetCodeRunner.class)
public class Q874_WalkingRobotSimulation {

    @Answer
    public int robotSim(int[] commands, int[][] obstacles) {
        final int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        Map<Integer, TreeSet<Integer>> xAlign = new HashMap<>();
        Map<Integer, TreeSet<Integer>> yAlign = new HashMap<>();
        for (int[] obstacle : obstacles) {
            xAlign.computeIfAbsent(obstacle[0], k -> new TreeSet<>()).add(obstacle[1]);
            yAlign.computeIfAbsent(obstacle[1], k -> new TreeSet<>()).add(obstacle[0]);
        }
        int res = 0, d = 0, x = 0, y = 0;
        for (int command : commands) {
            switch (command) {
                case -1:
                    d = (d + 1) % 4;
                    break;
                case -2:
                    d = (d + 3) % 4;
                    break;
                default:
                    TreeSet<Integer> yLine = yAlign.get(y), xLine = xAlign.get(x);
                    x = nextPos(yLine, x, command * directions[d][0]);
                    y = nextPos(xLine, y, command * directions[d][1]);
                    res = Math.max(res, x * x + y * y);
            }
        }
        return res;
    }

    private int nextPos(TreeSet<Integer> line, int pos, int dist) {
        int target = pos + dist;
        if (line != null) {
            if (target < pos) {
                Integer lower = line.lower(pos);
                if (lower != null && target <= lower) {
                    target = lower + 1;
                }
            } else if (pos < target) {
                Integer higher = line.higher(pos);
                if (higher != null && higher <= target) {
                    target = higher - 1;
                }
            }
        }
        return target;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{4, -1, 3}, new int[0][])
            .expect(25);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{4, -1, 4, -2, 4}, new int[][]{{2, 4}})
            .expect(65);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(
            new int[]{1, 2, -2, 5, -1, -2, -1, 8, 3, -1, 9, 4, -2, 3, 2, 4, 3, 9, 2, -1, -1, -2, 1, 3, -2, 4, 1, 4, -1,
                    1, 9, -1, -2, 5, -1, 5, 5, -2, 6, 6, 7, 7, 2, 8, 9, -1, 7, 4, 6, 9, 9, 9, -1, 5, 1, 3, 3, -1, 5, 9,
                    7, 4, 8, -1, -2, 1, 3, 2, 9, 3, -1, -2, 8, 8, 7, 5, -2, 6, 8, 4, 6, 2, 7, 2, -1, 7, -2, 3, 3, 2, -2,
                    6, 9, 8, 1, -2, -1, 1, 4, 7},
            TestDataFileHelper.read2DArray("Q874_TestData"))
            .expect(5140);

}
