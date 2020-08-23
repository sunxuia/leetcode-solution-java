package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 991. Broken Calculator
 * https://leetcode.com/problems/broken-calculator/
 *
 * On a broken calculator that has a number showing on its display, we can perform two operations:
 *
 * Double: Multiply the number on the display by 2, or;
 * Decrement: Subtract 1 from the number on the display.
 *
 * Initially, the calculator is displaying the number X.
 *
 * Return the minimum number of operations needed to display the number Y.
 *
 * Example 1:
 *
 * Input: X = 2, Y = 3
 * Output: 2
 * Explanation: Use double operation and then decrement operation {2 -> 4 -> 3}.
 *
 * Example 2:
 *
 * Input: X = 5, Y = 8
 * Output: 2
 * Explanation: Use decrement and then double {5 -> 4 -> 8}.
 *
 * Example 3:
 *
 * Input: X = 3, Y = 10
 * Output: 3
 * Explanation:  Use double, decrement and double {3 -> 6 -> 5 -> 10}.
 *
 * Example 4:
 *
 * Input: X = 1024, Y = 1
 * Output: 1023
 * Explanation: Use decrement operations 1023 times.
 *
 * Note:
 *
 * 1 <= X <= 10^9
 * 1 <= Y <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q991_BrokenCalculator {

    // 参考Solution 中的解答
    @Answer
    public int brokenCalc(int X, int Y) {
        int res = 0;
        while (Y > X) {
            res++;
            if (Y % 2 == 1) {
                // Y 是奇数且 Y > X, 则 Y 是 X 减出来的结果
                // 不可能是 X * 2 得到的
                Y++;
            } else {
                // Y 是偶数且 Y > X, 则 Y 是 X * 2 的结果
                // 不可能是 X - 1 得到的
                Y /= 2;
            }
        }

        return res + X - Y;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, 3).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(5, 8).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(3, 10).expect(3);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(1024, 1).expect(1023);

    @TestData
    public DataExpectation overTime = DataExpectation.createWith(1, 1000000000).expect(39);

}
