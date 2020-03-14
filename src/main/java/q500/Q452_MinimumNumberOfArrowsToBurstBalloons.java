package q500;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/
 *
 * There are a number of spherical balloons spread in two-dimensional space. For each balloon, provided input is the
 * start and end coordinates of the horizontal diameter. Since it's horizontal, y-coordinates don't matter and hence
 * the x-coordinates of start and end of the diameter suffice. Start is always smaller than end. There will be at
 * most 10000 balloons.
 *
 * An arrow can be shot up exactly vertically from different points along the x-axis. A balloon with xstart and xend
 * bursts by an arrow shot at x if xstart ≤ x ≤ xend. There is no limit to the number of arrows that can be shot. An
 * arrow once shot keeps travelling up infinitely. The problem is to find the minimum number of arrows that must be
 * shot to burst all balloons.
 *
 * Example:
 *
 * Input:
 * [[10,16], [2,8], [1,6], [7,12]]
 *
 * Output:
 * 2
 *
 * Explanation:
 * One way is to shoot one arrow for example at x = 6 (bursting the balloons [2,8] and [1,6]) and another arrow at x
 * = 11 (bursting the other two balloons).
 *
 * 题解: 参数 points[] 中的每个数组元素表示气球的左右边界, 可以用箭射下它们, 一箭可以射穿多个气球, 问最少可以用多少箭把气球全部射下.
 */
@RunWith(LeetCodeRunner.class)
public class Q452_MinimumNumberOfArrowsToBurstBalloons {

    // 贪心匹配
    @Answer
    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (p1, p2) -> p1[0] == p2[0] ? p1[1] - p2[1] : p1[0] - p2[0]);

        int res = 0;
        long end = Long.MIN_VALUE;
        for (int[] point : points) {
            if (end < point[0]) {
                end = point[1];
                res++;
            } else {
                end = Math.min(end, point[1]);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(new int[][]{{10, 16}, {2, 8}, {1, 6}, {7, 12}}).expect(2);

    @TestData
    public DataExpectation border = DataExpectation.create(new int[0][]).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{{1, 2}, {3, 4}, {5, 6}, {7, 8}}).expect(4);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[][]{{3, 9}, {7, 12}, {3, 8}, {6, 8}, {9, 10}, {2, 9}, {0, 9}, {3, 9}, {0, 6}, {2, 8}})
            .expect(2);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create(new int[][]{{9, 12}, {1, 10}, {4, 11}, {8, 12}, {3, 9}, {6, 9}, {6, 7}})
            .expect(2);

    @TestData
    public DataExpectation normal4 = DataExpectation
            .create(new int[][]{{Integer.MIN_VALUE, Integer.MAX_VALUE}}).expect(1);

}
