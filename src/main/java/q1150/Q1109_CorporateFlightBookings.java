package q1150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1109. Corporate Flight Bookings
 * https://leetcode.com/problems/corporate-flight-bookings/
 *
 * There are n flights, and they are labeled from 1 to n.
 *
 * We have a list of flight bookings.  The i-th booking bookings[i] = [i, j, k] means that we booked k seats from
 * flights labeled i to j inclusive.
 *
 * Return an array answer of length n, representing the number of seats booked on each flight in order of their label.
 *
 * Example 1:
 *
 * Input: bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
 * Output: [10,55,45,25,25]
 *
 * Constraints:
 *
 * 1 <= bookings.length <= 20000
 * 1 <= bookings[i][0] <= bookings[i][1] <= n <= 20000
 * 1 <= bookings[i][2] <= 10000
 *
 * 题解: 这里的flights 指的是类似火车一样的需要从站1 顺序开到站n, 订座数在终点站也要算.
 */
@RunWith(LeetCodeRunner.class)
public class Q1109_CorporateFlightBookings {

    @Answer
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] counts = new int[n + 2];
        for (int[] booking : bookings) {
            counts[booking[0]] += booking[2];
            counts[booking[1] + 1] -= booking[2];
        }
        int[] res = new int[n];
        int seats = 0;
        for (int i = 1; i <= n; i++) {
            seats += counts[i];
            res[i - 1] = seats;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[][]{{1, 2, 10}, {2, 3, 20}, {2, 5, 25}}, 5)
            .expect(new int[]{10, 55, 45, 25, 25});

}
