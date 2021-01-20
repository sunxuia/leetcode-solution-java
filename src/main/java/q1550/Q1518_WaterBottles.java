package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Easy] 1518. Water Bottles
 * https://leetcode.com/problems/water-bottles/
 *
 * Given numBottles full water bottles, you can exchange numExchange empty water bottles for one full water bottle.
 *
 * The operation of drinking a full water bottle turns it into an empty bottle.
 *
 * Return the maximum number of water bottles you can drink.
 *
 * Example 1:
 * <img src="./Q1518_PIC1.png">
 * Input: numBottles = 9, numExchange = 3
 * Output: 13
 * Explanation: You can exchange 3 empty bottles to get 1 full water bottle.
 * Number of water bottles you can drink: 9 + 3 + 1 = 13.
 *
 * Example 2:
 * <img src="./Q1518_PIC2.png">
 * Input: numBottles = 15, numExchange = 4
 * Output: 19
 * Explanation: You can exchange 4 empty bottles to get 1 full water bottle.
 * Number of water bottles you can drink: 15 + 3 + 1 = 19.
 *
 * Example 3:
 *
 * Input: numBottles = 5, numExchange = 5
 * Output: 6
 *
 * Example 4:
 *
 * Input: numBottles = 2, numExchange = 3
 * Output: 2
 *
 * Constraints:
 *
 * 1 <= numBottles <= 100
 * 2 <= numExchange <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1518_WaterBottles {

    @Answer
    public int numWaterBottles(int numBottles, int numExchange) {
        int res = numBottles, rest = numBottles;
        while (rest >= numExchange) {
            res += rest / numExchange;
            rest = rest / numExchange + rest % numExchange;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(9, 3).expect(13);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(15, 4).expect(19);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(5, 5).expect(6);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(2, 3).expect(2);

}
