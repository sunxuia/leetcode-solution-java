package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/maximize-distance-to-closest-person/
 *
 * In a row of seats, 1 represents a person sitting in that seat, and 0 represents that the seat is empty.
 *
 * There is at least one empty seat, and at least one person sitting.
 *
 * Alex wants to sit in the seat such that the distance between him and the closest person to him is maximized.
 *
 * Return that maximum distance to closest person.
 *
 * Example 1:
 *
 * Input: [1,0,0,0,1,0,1]
 * Output: 2
 * Explanation:
 * If Alex sits in the second open seat (seats[2]), then the closest person has distance 2.
 * If Alex sits in any other open seat, the closest person has distance 1.
 * Thus, the maximum distance to the closest person is 2.
 *
 * Example 2:
 *
 * Input: [1,0,0,0]
 * Output: 3
 * Explanation:
 * If Alex sits in the last seat, the closest person is 3 seats away.
 * This is the maximum distance possible, so the answer is 3.
 *
 *
 *
 * Constraints:
 *
 * 2 <= seats.length <= 20000
 * seats contains only 0s or 1s, at least one 0, and at least one 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q849_MaximizeDistanceToClosestPerson {

    @Answer
    public int maxDistToClosest(int[] seats) {
        final int n = seats.length;
        int[] left = new int[n];
        int prev = -n;
        for (int i = 0; i < n; i++) {
            if (seats[i] == 1) {
                prev = i;
            }
            left[i] = i - prev;
        }

        int res = 0;
        prev = 2 * n;
        for (int i = n - 1; i >= 0; i--) {
            if (seats[i] == 1) {
                prev = i;
            }
            res = Math.max(res, Math.min(left[i], prev - i));
        }
        return res;
    }

    // LeetCode 上比较快的做法
    @Answer
    public int maxDistToClosest2(int[] seats) {
        final int n = seats.length;
        int res = 0, start = 0, end = 0;
        while (end < n) {
            while (end < n && seats[end] == 0) {
                end++;
            }
            if (start == 0 || end > n - 1) {
                // 一边没人
                res = Math.max(res, end - start);
            } else {
                // 两边有人
                res = Math.max(res, (end - start + 1) / 2);
            }
            while (end < n && seats[end] == 1) {
                end++;
            }
            start = end;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 0, 0, 0, 1, 0, 1}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 0, 0, 0}).expect(3);

}
