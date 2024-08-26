package q2150;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2126. Destroying Asteroids</h3>
 * <a href="https://leetcode.com/problems/destroying-asteroids/">
 * https://leetcode.com/problems/destroying-asteroids/
 * </a><br/>
 *
 * <p>You are given an integer <code>mass</code>, which represents the original mass of a planet. You are further given
 * an integer array <code>asteroids</code>, where <code>asteroids[i]</code> is the mass of the
 * <code>i<sup>th</sup></code> asteroid.</p>
 *
 * <p>You can arrange for the planet to collide with the asteroids in <strong>any arbitrary order</strong>. If the mass
 * of the planet is <b>greater than or equal to</b> the mass of the asteroid, the asteroid is <strong>destroyed</strong>
 * and the planet <strong>gains</strong> the mass of the asteroid. Otherwise, the planet is destroyed.</p>
 *
 * <p>Return <code>true</code><em> if <strong>all</strong> asteroids can be destroyed. Otherwise, return
 * </em><code>false</code><em>.</em></p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> mass = 10, asteroids = [3,9,19,5,21]
 * <strong>Output:</strong> true
 * <strong>Explanation:</strong> One way to order the asteroids is [9,19,5,3,21]:
 * - The planet collides with the asteroid with a mass of 9. New planet mass: 10 + 9 = 19
 * - The planet collides with the asteroid with a mass of 19. New planet mass: 19 + 19 = 38
 * - The planet collides with the asteroid with a mass of 5. New planet mass: 38 + 5 = 43
 * - The planet collides with the asteroid with a mass of 3. New planet mass: 43 + 3 = 46
 * - The planet collides with the asteroid with a mass of 21. New planet mass: 46 + 21 = 67
 * All asteroids are destroyed.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> mass = 5, asteroids = [4,9,23,4]
 * <strong>Output:</strong> false
 * <strong>Explanation:</strong>
 * The planet cannot ever gain enough mass to destroy the asteroid with a mass of 23.
 * After the planet destroys the other asteroids, it will have a mass of 5 + 4 + 9 + 4 = 22.
 * This is less than 23, so a collision would not destroy the last asteroid.</pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= mass &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= asteroids.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= asteroids[i] &lt;= 10<sup>5</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2126_DestroyingAsteroids {

    @Answer
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        Arrays.sort(asteroids);
        for (int asteroid : asteroids) {
            if (mass >= asteroid) {
                mass += asteroid;
                if (mass >= 10_0000) {
                    // 超过其他小行星的最大质量
                    return true;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(10, new int[]{3, 9, 19, 5, 21}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(5, new int[]{4, 9, 23, 4}).expect(false);

}
