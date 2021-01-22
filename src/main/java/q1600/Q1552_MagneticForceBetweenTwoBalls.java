package q1600;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1552. Magnetic Force Between Two Balls
 * https://leetcode.com/problems/magnetic-force-between-two-balls/
 *
 * In universe Earth C-137, Rick discovered a special form of magnetic force between two balls if they are put in his
 * new invented basket. Rick has n empty baskets, the ith basket is at position[i], Morty has m balls and needs to
 * distribute the balls into the baskets such that the minimum magnetic force between any two balls is maximum.
 *
 * Rick stated that magnetic force between two different balls at positions x and y is |x - y|.
 *
 * Given the integer array position and the integer m. Return the required force.
 *
 * Example 1:
 * <img src="./Q1552_PIC.png">
 * Input: position = [1,2,3,4,7], m = 3
 * Output: 3
 * Explanation: Distributing the 3 balls into baskets 1, 4 and 7 will make the magnetic force between ball pairs [3, 3,
 * 6]. The minimum magnetic force is 3. We cannot achieve a larger minimum magnetic force than 3.
 *
 * Example 2:
 *
 * Input: position = [5,4,3,2,1,1000000000], m = 2
 * Output: 999999999
 * Explanation: We can use baskets 1 and 1000000000.
 *
 * Constraints:
 *
 * n == position.length
 * 2 <= n <= 10^5
 * 1 <= position[i] <= 10^9
 * All integers in position are distinct.
 * 2 <= m <= position.length
 *
 * 题解:
 * 在position 指定的点中找出 m 个点, 让相邻两点之间距离的最小值最大化的结果.
 */
@RunWith(LeetCodeRunner.class)
public class Q1552_MagneticForceBetweenTwoBalls {

    /**
     * 根据提示, 可以使用二分搜索寻找可能的结果.
     */
    @Answer
    public int maxDistance(int[] position, int m) {
        final int n = position.length;
        Arrays.sort(position);
        int start = 1;
        int end = (position[n - 1] - position[0]) / (m - 1);
        while (start < end) {
            int mid = (start + end + 1) / 2;
            if (valid(position, m, mid)) {
                start = mid;
            } else {
                end = mid - 1;
            }
        }
        return start;
    }

    private boolean valid(int[] position, int m, int dist) {
        final int n = position.length;
        int idx = 0;
        while (idx < n && --m > 0) {
            idx = Arrays.binarySearch(position, idx + 1, n, position[idx] + dist);
            idx = idx < 0 ? -idx - 1 : idx;
        }
        return idx < n;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 7}, 3)
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{5, 4, 3, 2, 1, 1000000000}, 2)
            .expect(999999999);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{79, 74, 57, 22}, 4)
            .expect(5);

}
