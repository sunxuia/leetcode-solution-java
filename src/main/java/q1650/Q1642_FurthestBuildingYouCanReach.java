package q1650;

import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1642. Furthest Building You Can Reach
 * https://leetcode.com/problems/furthest-building-you-can-reach/
 *
 * You are given an integer array heights representing the heights of buildings, some bricks, and some ladders.
 *
 * You start your journey from building 0 and move to the next building by possibly using bricks or ladders.
 *
 * While moving from building i to building i+1 (0-indexed),
 *
 * If the current building's height is greater than or equal to the next building's height, you do not need a ladder or
 * bricks.
 * If the current building's height is less than the next building's height, you can either use one ladder or (h[i+1] -
 * h[i]) bricks.
 *
 * Return the furthest building index (0-indexed) you can reach if you use the given ladders and bricks optimally.
 *
 * Example 1:
 * <img src="./Q1642_PIC.gif">
 * Input: heights = [4,2,7,6,9,14,12], bricks = 5, ladders = 1
 * Output: 4
 * Explanation: Starting at building 0, you can follow these steps:
 * - Go to building 1 without using ladders nor bricks since 4 >= 2.
 * - Go to building 2 using 5 bricks. You must use either bricks or ladders because 2 < 7.
 * - Go to building 3 without using ladders nor bricks since 7 >= 6.
 * - Go to building 4 using your only ladder. You must use either bricks or ladders because 6 < 9.
 * It is impossible to go beyond building 4 because you do not have any more bricks or ladders.
 *
 * Example 2:
 *
 * Input: heights = [4,12,2,7,3,18,20,3,19], bricks = 10, ladders = 2
 * Output: 7
 *
 * Example 3:
 *
 * Input: heights = [14,3,19,3], bricks = 17, ladders = 0
 * Output: 3
 *
 * Constraints:
 *
 * 1 <= heights.length <= 10^5
 * 1 <= heights[i] <= 10^6
 * 0 <= bricks <= 10^9
 * 0 <= ladders <= heights.length
 */
@RunWith(LeetCodeRunner.class)
public class Q1642_FurthestBuildingYouCanReach {

    /**
     * 贪婪算法, 思路就是尽量用砖头, 如果砖头不够了再用梯子替换最费砖头的台阶.
     */
    @Answer
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        final int n = heights.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        int build = 0;
        while (build < n - 1) {
            for (; build < n - 1; build++) {
                int require = heights[build + 1] - heights[build];
                if (require <= 0) {
                    continue;
                }
                if (require > bricks) {
                    break;
                }
                bricks -= require;
                pq.offer(require);
            }
            if (ladders == 0 || build == n - 1) {
                break;
            }
            ladders--;
            if (pq.isEmpty() || pq.peek() < heights[build + 1] - heights[build]) {
                build++;
            } else {
                bricks += pq.poll();
            }
        }
        return build;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{4, 2, 7, 6, 9, 14, 12}, 5, 1)
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{4, 12, 2, 7, 3, 18, 20, 3, 19}, 10, 2)
            .expect(7);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{14, 3, 19, 3}, 17, 0)
            .expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{7, 5, 13}, 0, 0)
            .expect(1);

    @TestData
    public DataExpectation overMemory = DataExpectation
            .createWith(TestDataFileHelper.read(int[].class), 33671263, 108)
            .expect(589);

}
