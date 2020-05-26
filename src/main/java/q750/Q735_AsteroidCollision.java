package q750;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/asteroid-collision/
 *
 * We are given an array asteroids of integers representing asteroids in a row.
 *
 * For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning
 * right, negative meaning left). Each asteroid moves at the same speed.
 *
 * Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode. If
 * both are the same size, both will explode. Two asteroids moving in the same direction will never meet.
 *
 * Example 1:
 *
 * Input:
 * asteroids = [5, 10, -5]
 * Output: [5, 10]
 * Explanation:
 * The 10 and -5 collide resulting in 10.  The 5 and 10 never collide.
 *
 * Example 2:
 *
 * Input:
 * asteroids = [8, -8]
 * Output: []
 * Explanation:
 * The 8 and -8 collide exploding each other.
 *
 * Example 3:
 *
 * Input:
 * asteroids = [10, 2, -5]
 * Output: [10]
 * Explanation:
 * The 2 and -5 collide resulting in -5.  The 10 and -5 collide resulting in 10.
 *
 * Example 4:
 *
 * Input:
 * asteroids = [-2, -1, 1, 2]
 * Output: [-2, -1, 1, 2]
 * Explanation:
 * The -2 and -1 are moving left, while the 1 and 2 are moving right.
 * Asteroids moving the same direction never meet, so no asteroids will meet each other.
 *
 * Note:
 * The length of asteroids will be at most 10000.
 * Each asteroid will be a non-zero integer in the range [-1000, 1000]..
 */
@RunWith(LeetCodeRunner.class)
public class Q735_AsteroidCollision {

    @Answer
    public int[] asteroidCollision(int[] asteroids) {
        int r = 0;
        for (int asteroid : asteroids) {
            asteroids[r] = asteroid;
            while (r > 0 && asteroids[r - 1] > 0 && asteroids[r] < 0) {
                if (asteroids[r - 1] == -asteroids[r]) {
                    r -= 2;
                } else if (asteroids[r - 1] < -asteroids[r]) {
                    asteroids[r - 1] = asteroids[r];
                    r--;
                } else {
                    r--;
                }
            }
            r++;
        }
        return Arrays.copyOf(asteroids, r);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{5, 10, -5}).expect(new int[]{5, 10});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{8, -8}).expect(new int[0]);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{10, 2, -5}).expect(new int[]{10});

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{-2, -1, 1, 2}).expect(new int[]{-2, -1, 1, 2});

}
