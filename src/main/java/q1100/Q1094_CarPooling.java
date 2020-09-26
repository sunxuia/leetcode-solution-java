package q1100;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1094. Car Pooling
 * https://leetcode.com/problems/car-pooling/
 *
 * You are driving a vehicle that has capacity empty seats initially available for passengers.  The vehicle only drives
 * east (ie. it cannot turn around and drive west.)
 *
 * Given a list of trips, trip[i] = [num_passengers, start_location, end_location] contains information about the i-th
 * trip: the number of passengers that must be picked up, and the locations to pick them up and drop them off.  The
 * locations are given as the number of kilometers due east from your vehicle's initial location.
 *
 * Return true if and only if it is possible to pick up and drop off all passengers for all the given trips.
 *
 * Example 1:
 *
 * Input: trips = [[2,1,5],[3,3,7]], capacity = 4
 * Output: false
 *
 * Example 2:
 *
 * Input: trips = [[2,1,5],[3,3,7]], capacity = 5
 * Output: true
 *
 * Example 3:
 *
 * Input: trips = [[2,1,5],[3,5,7]], capacity = 3
 * Output: true
 *
 * Example 4:
 *
 * Input: trips = [[3,2,7],[3,7,9],[8,3,9]], capacity = 11
 * Output: true
 *
 * Constraints:
 *
 * trips.length <= 1000
 * trips[i].length == 3
 * 1 <= trips[i][0] <= 100
 * 0 <= trips[i][1] < trips[i][2] <= 1000
 * 1 <= capacity <= 100000
 */
@RunWith(LeetCodeRunner.class)
public class Q1094_CarPooling {

    @Answer
    public boolean carPooling(int[][] trips, int capacity) {
        Arrays.sort(trips, Comparator.comparingInt(t -> t[1]));
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(t -> t[2]));
        int sum = 0;
        for (int[] trip : trips) {
            while (!pq.isEmpty() && pq.peek()[2] <= trip[1]) {
                sum -= pq.poll()[0];
            }
            sum += trip[0];
            if (sum > capacity) {
                return false;
            }
            pq.add(trip);
        }
        return true;
    }

    /**
     * 桶排序的方法, 利用了 0 <= trips[i][1] < trips[i][2] <= 1000 的限制条件.
     */
    @Answer
    public boolean carPooling2(int[][] trips, int capacity) {
        int[] nums = new int[1001];
        for (int[] trip : trips) {
            nums[trip[1]] += trip[0];
            nums[trip[2]] -= trip[0];
        }
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum > capacity) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{2, 1, 5}, {3, 3, 7}}, 4)
            .expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{2, 1, 5}, {3, 3, 7}}, 5)
            .expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[][]{{2, 1, 5}, {3, 5, 7}}, 3)
            .expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[][]{{3, 2, 7}, {3, 7, 9}, {8, 3, 9}}, 11)
            .expect(true);

}
