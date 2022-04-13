package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Medium] 1824. Minimum Sideway Jumps
 * https://leetcode.com/problems/minimum-sideway-jumps/
 *
 * There is a 3 lane road of length n that consists of n + 1 points labeled from 0 to n. A frog starts at point 0 in the
 * second lane and wants to jump to point n. However, there could be obstacles along the way.
 *
 * You are given an array obstacles of length n + 1 where each obstacles[i] (ranging from 0 to 3) describes an obstacle
 * on the lane obstacles[i] at point i. If obstacles[i] == 0, there are no obstacles at point i. There will be at most
 * one obstacle in the 3 lanes at each point.
 *
 * For example, if obstacles[2] == 1, then there is an obstacle on lane 1 at point 2.
 *
 * The frog can only travel from point i to point i + 1 on the same lane if there is not an obstacle on the lane at
 * point i + 1. To avoid obstacles, the frog can also perform a side jump to jump to another lane (even if they are not
 * adjacent) at the same point if there is no obstacle on the new lane.
 *
 * For example, the frog can jump from lane 3 at point 3 to lane 1 at point 3.
 *
 * Return the minimum number of side jumps the frog needs to reach any lane at point n starting from lane 2 at point 0.
 *
 * Note: There will be no obstacles on points 0 and n.
 *
 * Example 1:
 * (图Q1824_PIC1.png)
 * Input: obstacles = [0,1,2,3,0]
 * Output: 2
 * Explanation: The optimal solution is shown by the arrows above. There are 2 side jumps (red arrows).
 * Note that the frog can jump over obstacles only when making side jumps (as shown at point 2).
 *
 * Example 2:
 * (图Q1824_PIC2.png)
 * Input: obstacles = [0,1,1,3,3,0]
 * Output: 0
 * Explanation: There are no obstacles on lane 2. No side jumps are required.
 *
 * Example 3:
 * (图Q1824_PIC3.png)
 * Input: obstacles = [0,2,1,0,3,0]
 * Output: 2
 * Explanation: The optimal solution is shown by the arrows above. There are 2 side jumps.
 *
 * Constraints:
 *
 * obstacles.length == n + 1
 * 1 <= n <= 5 * 10^5
 * 0 <= obstacles[i] <= 3
 * obstacles[0] == obstacles[n] == 0
 */
@RunWith(LeetCodeRunner.class)
public class Q1824_MinimumSidewayJumps {

    @Answer
    public int minSideJumps(int[] obstacles) {
        final int max = 50_0000;
        int lane1 = max, lane2 = 0, lane3 = max;
        for (int i = 0; i < obstacles.length; i++) {
            int n1, n2, n3;
            switch (obstacles[i]) {
                case 1:
                    n1 = max;
                    n2 = Math.min(lane2, lane3 + 1);
                    n3 = Math.min(lane3, lane2 + 1);
                    break;
                case 2:
                    n1 = Math.min(lane1, lane3 + 1);
                    n2 = max;
                    n3 = Math.min(lane3, lane1 + 1);
                    break;
                case 3:
                    n1 = Math.min(lane1, lane2 + 1);
                    n2 = Math.min(lane2, lane1 + 1);
                    n3 = max;
                    break;
                default:
                    n1 = Math.min(lane1, Math.min(lane2, lane3) + 1);
                    n2 = Math.min(lane2, Math.min(lane1, lane3) + 1);
                    n3 = Math.min(lane3, Math.min(lane1, lane2) + 1);
            }
            lane1 = n1;
            lane2 = n2;
            lane3 = n3;
        }
        return Math.min(lane1, Math.min(lane2, lane3));
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{0, 1, 2, 3, 0}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{0, 1, 1, 3, 3, 0}).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{0, 2, 1, 0, 3, 0}).expect(2);

}
