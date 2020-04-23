package q500;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

/**
 * https://leetcode.com/problems/generate-random-point-in-a-circle/
 *
 * Given the radius and x-y positions of the center of a circle, write a function randPoint which generates a uniform
 * random point in the circle.
 *
 * Note:
 *
 * input and output values are in floating-point.
 * radius and x-y position of the center of the circle is passed into the class constructor.
 * a point on the circumference of the circle is considered to be in the circle.
 * randPoint returns a size 2 array containing x-position and y-position of the random point, in that order.
 *
 * Example 1:
 *
 * Input:
 * ["Solution","randPoint","randPoint","randPoint"]
 * [[1,0,0],[],[],[]]
 * Output: [null,[-0.72939,-0.65505],[-0.78502,-0.28626],[-0.83119,-0.19803]]
 *
 * Example 2:
 *
 * Input:
 * ["Solution","randPoint","randPoint","randPoint"]
 * [[10,5,-7.5],[],[],[]]
 * Output: [null,[11.52438,-8.33273],[2.46992,-16.21705],[11.13430,-12.42337]]
 *
 * Explanation of Input Syntax:
 *
 * The input is two lists: the subroutines called and their arguments. Solution's constructor has three arguments,
 * the radius, x-position of the center, and y-position of the center of the circle. randPoint has no arguments.
 * Arguments are always wrapped with a list, even if there aren't any.
 */
public class Q478_GenerateRandomPointInACircle {

    private static class Solution {

        private final double radius, x_center, y_center;

        private final Random random = new Random();

        public Solution(double radius, double x_center, double y_center) {
            this.radius = radius;
            this.x_center = x_center;
            this.y_center = y_center;
        }

        public double[] randPoint() {
            // 因为面积公式是 r*r*π, 因为面积概率相同, 所以半径的概率就是越长概率越大, 概率成平方数扩大.
            double length = radius * Math.sqrt(random.nextDouble());
            double radian = Math.toRadians(random.nextInt(360));
            double[] res = new double[2];
            res[0] = x_center + length * Math.cos(radian);
            res[1] = y_center + length * Math.sin(radian);
            return res;
        }
    }

    @Test
    public void testMethod() {
        Solution solution = new Solution(1, 0, 0);
        double[] res = solution.randPoint();
        Assert.assertTrue(res[0] * res[0] + res[1] * res[1] < 1);
    }

}
