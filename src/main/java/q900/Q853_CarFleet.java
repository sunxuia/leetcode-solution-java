package q900;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/car-fleet/
 *
 * N cars are going to the same destination along a one lane road.  The destination is target miles away.
 *
 * Each car i has a constant speed speed[i] (in miles per hour), and initial position position[i] miles towards the
 * target along the road.
 *
 * A car can never pass another car ahead of it, but it can catch up to it, and drive bumper to bumper at the same
 * speed.
 *
 * The distance between these two cars is ignored - they are assumed to have the same position.
 *
 * A car fleet is some non-empty set of cars driving at the same position and same speed.  Note that a single car is
 * also a car fleet.
 *
 * If a car catches up to a car fleet right at the destination point, it will still be considered as one car fleet.
 *
 *
 * How many car fleets will arrive at the destination?
 *
 *
 *
 * Example 1:
 *
 * Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
 * Output: 3
 * Explanation:
 * The cars starting at 10 and 8 become a fleet, meeting each other at 12.
 * The car starting at 0 doesn't catch up to any other car, so it is a fleet by itself.
 * The cars starting at 5 and 3 become a fleet, meeting each other at 6.
 * Note that no other cars meet these fleets before the destination, so the answer is 3.
 *
 *
 * Note:
 *
 * 0 <= N <= 10 ^ 4
 * 0 < target <= 10 ^ 6
 * 0 < speed[i] <= 10 ^ 6
 * 0 <= position[i] < target
 * All initial positions are different.
 *
 * 题解: 额外的条件: 后面的车撞上前面的车, 则形成的车队会按照前面车(慢车) 的速度行驶.
 */
@RunWith(LeetCodeRunner.class)
public class Q853_CarFleet {

    @Answer
    public int carFleet(int target, int[] position, int[] speed) {
        final int n = position.length;
        Integer[] cars = new Integer[n];
        for (int i = 0; i < n; i++) {
            cars[i] = i;
        }
        Arrays.sort(cars, Comparator.comparingInt(a -> position[a]));

        int res = 0;
        // 表示上一个车队的过线时间, 如果本车过线时间 time <= prevTime
        // 则说明会在过线前可以合成一个车队, 否则不可以.
        double prevTime = 0;
        for (int i = n - 1; i >= 0; i--) {
            double time = ((double) target - position[cars[i]]) / speed[cars[i]];
            if (time > prevTime) {
                res++;
                prevTime = time;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(12, new int[]{10, 8, 0, 5, 3}, new int[]{2, 4, 1, 1, 3})
            .expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(5, new int[]{0, 1, 2}, new int[]{10, 1, 1})
            .expect(2);

}
