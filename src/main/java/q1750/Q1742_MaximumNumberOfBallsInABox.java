package q1750;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1742. Maximum Number of Balls in a Box
 * https://leetcode.com/problems/maximum-number-of-balls-in-a-box/
 *
 * You are working in a ball factory where you have n balls numbered from lowLimit up to highLimit inclusive (i.e., n ==
 * highLimit - lowLimit + 1), and an infinite number of boxes numbered from 1 to infinity.
 *
 * Your job at this factory is to put each ball in the box with a number equal to the sum of digits of the ball's
 * number. For example, the ball number 321 will be put in the box number 3 + 2 + 1 = 6 and the ball number 10 will be
 * put in the box number 1 + 0 = 1.
 *
 * Given two integers lowLimit and highLimit, return the number of balls in the box with the most balls.
 *
 * Example 1:
 *
 * Input: lowLimit = 1, highLimit = 10
 * Output: 2
 * Explanation:
 * Box Number:  1 2 3 4 5 6 7 8 9 10 11 ...
 * Ball Count:  2 1 1 1 1 1 1 1 1 0  0  ...
 * Box 1 has the most number of balls with 2 balls.
 *
 * Example 2:
 *
 * Input: lowLimit = 5, highLimit = 15
 * Output: 2
 * Explanation:
 * Box Number:  1 2 3 4 5 6 7 8 9 10 11 ...
 * Ball Count:  1 1 1 1 2 2 1 1 1 0  0  ...
 * Boxes 5 and 6 have the most number of balls with 2 balls in each.
 *
 * Example 3:
 *
 * Input: lowLimit = 19, highLimit = 28
 * Output: 2
 * Explanation:
 * Box Number:  1 2 3 4 5 6 7 8 9 10 11 12 ...
 * Ball Count:  0 1 1 1 1 1 1 1 1 2  0  0  ...
 * Box 10 has the most number of balls with 2 balls.
 *
 * Constraints:
 *
 * 1 <= lowLimit <= highLimit <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1742_MaximumNumberOfBallsInABox {

    @Answer
    public int countBalls(int lowLimit, int highLimit) {
        int[] counts = new int[46];
        for (int i = lowLimit; i <= highLimit; i++) {
            counts[getBoxNo(i)]++;
        }
        return Arrays.stream(counts).max().getAsInt();
    }

    private int getBoxNo(int val) {
        int res = 0;
        while (val > 0) {
            res += val % 10;
            val /= 10;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 10).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(5, 15).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(19, 28).expect(2);

}
