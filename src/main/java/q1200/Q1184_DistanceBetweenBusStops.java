package q1200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1184. Distance Between Bus Stops
 * https://leetcode.com/problems/distance-between-bus-stops/
 *
 * A bus has n stops numbered from 0 to n - 1 that form a circle. We know the distance between all pairs of neighboring
 * stops where distance[i] is the distance between the stops number i and (i + 1) % n.
 *
 * The bus goes along both directions i.e. clockwise and counterclockwise.
 *
 * Return the shortest distance between the given start and destination stops.
 *
 * Example 1:
 * <img src="Q1184_PIC1.png" />
 * Input: distance = [1,2,3,4], start = 0, destination = 1
 * Output: 1
 * Explanation: Distance between 0 and 1 is 1 or 9, minimum is 1.
 *
 * Example 2:
 * <img src="Q1184_PIC2.png" />
 * Input: distance = [1,2,3,4], start = 0, destination = 2
 * Output: 3
 * Explanation: Distance between 0 and 2 is 3 or 7, minimum is 3.
 *
 * Example 3:
 * <img src="Q1184_PIC3.png" />
 * Input: distance = [1,2,3,4], start = 0, destination = 3
 * Output: 4
 * Explanation: Distance between 0 and 3 is 6 or 4, minimum is 4.
 *
 * Constraints:
 *
 * 1 <= n <= 10^4
 * distance.length == n
 * 0 <= start, destination < n
 * 0 <= distance[i] <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1184_DistanceBetweenBusStops {

    @Answer
    public int distanceBetweenBusStops(int[] distance, int start, int destination) {
        final int n = distance.length;
        int clockwise = 0;
        for (int i = start; i != destination; i = (i + 1) % n) {
            clockwise += distance[i];
        }
        int counterclockwise = 0;
        for (int i = start; i != destination; i = (i + n - 1) % n) {
            counterclockwise += distance[(i + n - 1) % n];
        }
        return Math.min(clockwise, counterclockwise);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2, 3, 4}, 0, 1).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 2, 3, 4}, 0, 2).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 2, 3, 4}, 0, 3).expect(4);

}
