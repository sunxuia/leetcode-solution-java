package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1033. Moving Stones Until Consecutive
 * https://leetcode.com/problems/moving-stones-until-consecutive/
 *
 * Three stones are on a number line at positions a, b, and c.
 *
 * Each turn, you pick up a stone at an endpoint (ie., either the lowest or highest position stone), and move it to an
 * unoccupied position between those endpoints.  Formally, let's say the stones are currently at positions x, y, z with
 * x < y < z.  You pick up the stone at either position x or position z, and move that stone to an integer position k,
 * with x < k < z and k != y.
 *
 * The game ends when you cannot make any more moves, ie. the stones are in consecutive positions.
 *
 * When the game ends, what is the minimum and maximum number of moves that you could have made?  Return the answer as
 * an length 2 array: answer = [minimum_moves, maximum_moves]
 *
 * Example 1:
 *
 * Input: a = 1, b = 2, c = 5
 * Output: [1,2]
 * Explanation: Move the stone from 5 to 3, or move the stone from 5 to 4 to 3.
 *
 * Example 2:
 *
 * Input: a = 4, b = 3, c = 2
 * Output: [0,0]
 * Explanation: We cannot make any moves.
 *
 * Example 3:
 *
 * Input: a = 3, b = 5, c = 1
 * Output: [1,2]
 * Explanation: Move the stone from 1 to 4; or move the stone from 1 to 2 to 4.
 *
 * Note:
 *
 * 1 <= a <= 100
 * 1 <= b <= 100
 * 1 <= c <= 100
 * a != b, b != c, c != a
 */
@RunWith(LeetCodeRunner.class)
public class Q1033_MovingStonesUntilConsecutive {

    @Answer
    public int[] numMovesStones(int a, int b, int c) {
        if (a > b) {
            int t = a;
            a = b;
            b = t;
        }
        if (b > c) {
            int t = b;
            b = c;
            c = t;
        }
        if (a > b) {
            int t = a;
            a = b;
            b = t;
        }
        if (c - b == 2 || b - a == 2) {
            return new int[]{1, b - a - 1 + c - b - 1};
        }
        return new int[]{Math.min(b - a - 1, 1) + Math.min(c - b - 1, 1), b - a - 1 + c - b - 1};
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 2, 5).expect(new int[]{1, 2});

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(4, 3, 2).expect(new int[]{0, 0});

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(3, 5, 1).expect(new int[]{1, 2});

}
