package q1250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1227. Airplane Seat Assignment Probability
 * https://leetcode.com/problems/airplane-seat-assignment-probability/
 *
 * n passengers board an airplane with exactly n seats. The first passenger has lost the ticket and picks a seat
 * randomly. But after that, the rest of passengers will:
 *
 * Take their own seat if it is still available,
 * Pick other seats randomly when they find their seat occupied
 *
 * What is the probability that the n-th person can get his own seat?
 *
 * Example 1:
 *
 * Input: n = 1
 * Output: 1.00000
 * Explanation: The first person can only get the first seat.
 *
 * Example 2:
 *
 * Input: n = 2
 * Output: 0.50000
 * Explanation: The second person has a probability of 0.5 to get the second seat (when first person gets the first
 * seat).
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1227_AirplaneSeatAssignmentProbability {

    /**
     * 参考文档 https://blog.csdn.net/Orientliu96/article/details/103516617
     */
    @Answer
    public double nthPersonGetsNthSeat(int n) {
        return n == 1 ? 1.0 : 0.5;
    }

    @TestData
    public DataExpectation arg1 = DataExpectation.create(1).expectDouble(1.00000, 0.00001);

    @TestData
    public DataExpectation arg2 = DataExpectation.create(2).expectDouble(0.50000, 0.00001);

    @TestData
    public DataExpectation arg3 = DataExpectation.create(3).expectDouble(0.50000, 0.00001);

}
