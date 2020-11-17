package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1344. Angle Between Hands of a Clock
 * https://leetcode.com/problems/angle-between-hands-of-a-clock/
 *
 * Given two numbers, hour and minutes. Return the smaller angle (in degrees) formed between the hour and the minute
 * hand.
 *
 * Example 1:
 * <img src="Q1344_PIC1.png">
 * Input: hour = 12, minutes = 30
 * Output: 165
 *
 * Example 2:
 * <img src="Q1344_PIC2.png">
 * Input: hour = 3, minutes = 30
 * Output: 75
 *
 * Example 3:
 * <img src="Q1344_PIC3.png">
 * Input: hour = 3, minutes = 15
 * Output: 7.5
 *
 * Example 4:
 *
 * Input: hour = 4, minutes = 50
 * Output: 155
 *
 * Example 5:
 *
 * Input: hour = 12, minutes = 0
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= hour <= 12
 * 0 <= minutes <= 59
 * Answers within 10^-5 of the actual value will be accepted as correct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1344_AngleBetweenHandsOfAClock {

    @Answer
    public double angleClock(int hour, int minutes) {
        double hourAngle = (hour * 30 + minutes / 2.0) % 360;
        double minuteAngle = minutes * 6;
        double diff = hourAngle - minuteAngle;
        double res = Math.abs(diff);
        res = Math.min(res, Math.abs(diff + 360));
        res = Math.min(res, Math.abs(diff - 360));
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(12, 30).expect(165.0);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(3, 30).expect(75.0);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(3, 15).expect(7.5);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(4, 50).expect(155.0);

    @TestData
    public DataExpectation example5 = DataExpectation.createWith(12, 0).expect(0.0);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(8, 7).expect(158.5);

}
