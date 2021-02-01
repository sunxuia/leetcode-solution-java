package q1700;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1654. Minimum Jumps to Reach Home
 * https://leetcode.com/problems/minimum-jumps-to-reach-home/
 *
 * A certain bug's home is on the x-axis at position x. Help them get there from position 0.
 *
 * The bug jumps according to the following rules:
 *
 * It can jump exactly a positions forward (to the right).
 * It can jump exactly b positions backward (to the left).
 * It cannot jump backward twice in a row.
 * It cannot jump to any forbidden positions.
 *
 * The bug may jump forward beyond its home, but it cannot jump to positions numbered with negative integers.
 *
 * Given an array of integers forbidden, where forbidden[i] means that the bug cannot jump to the position forbidden[i],
 * and integers a, b, and x, return the minimum number of jumps needed for the bug to reach its home. If there is no
 * possible sequence of jumps that lands the bug on position x, return -1.
 *
 * Example 1:
 *
 * Input: forbidden = [14,4,18,1,15], a = 3, b = 15, x = 9
 * Output: 3
 * Explanation: 3 jumps forward (0 -> 3 -> 6 -> 9) will get the bug home.
 *
 * Example 2:
 *
 * Input: forbidden = [8,3,16,6,12,20], a = 15, b = 13, x = 11
 * Output: -1
 *
 * Example 3:
 *
 * Input: forbidden = [1,6,2,14,5,17,4], a = 16, b = 9, x = 7
 * Output: 2
 * Explanation: One jump forward (0 -> 16) then one jump backward (16 -> 7) will get the bug home.
 *
 * Constraints:
 *
 * 1 <= forbidden.length <= 1000
 * 1 <= a, b, forbidden[i] <= 2000
 * 0 <= x <= 2000
 * All the elements in forbidden are distinct.
 * Position x is not forbidden.
 */
@RunWith(LeetCodeRunner.class)
public class Q1654_MinimumJumpsToReachHome {

    @Answer
    public int minimumJumps(int[] forbidden, int a, int b, int x) {
        // 这个limit 的值参考LeetCode 中的解答,
        // 是一个比较大的数字, 其他解答也有用更小数字的
        final int limit = 10000;
        boolean[] excludes = new boolean[limit + 1];
        for (int idx : forbidden) {
            if (idx <= limit) {
                excludes[idx] = true;
            }
        }
        int res = 0, max = 0;
        boolean[][] visited = new boolean[limit + 1][2];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0});
        while (!queue.isEmpty()) {
            for (int len = queue.size(); len > 0; len--) {
                int[] pair = queue.poll();
                int pos = pair[0], status = pair[1];
                max = Math.max(max, pos);
                if (pos < 0 || pos > limit || excludes[pos]
                        || visited[pos][0] || visited[pos][status]) {
                    continue;
                }
                if (pos == x) {
                    return res;
                }
                visited[pos][status] = true;
                queue.offer(new int[]{pos + a, 0});
                if (status == 0) {
                    queue.offer(new int[]{pos - b, 1});
                }
            }
            res++;
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{14, 4, 18, 1, 15}, 3, 15, 9)
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{8, 3, 16, 6, 12, 20}, 15, 13, 11)
            .expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 6, 2, 14, 5, 17, 4}, 16, 9, 7)
            .expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{
            162, 118, 178, 152, 167, 100, 40, 74, 199, 186, 26, 73, 200, 127, 30, 124, 193, 84, 184, 36, 103, 149, 153,
            9, 54, 154, 133, 95, 45, 198, 79, 157, 64, 122, 59, 71, 48, 177, 82, 35, 14, 176, 16, 108, 111, 6, 168, 31,
            134, 164, 136, 72, 98}, 29, 98, 80)
            .expect(121);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{1998}, 1999, 2000, 2000)
            .expect(3998);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(
                    new int[]{1362, 873, 1879, 725, 305, 794, 1135, 1358, 1717, 159, 1370, 1861, 583, 1193, 1921, 778,
                            1263, 239, 1224, 1925, 1505, 566, 5, 15}, 560, 573, 64)
            .expect(1036);

}
