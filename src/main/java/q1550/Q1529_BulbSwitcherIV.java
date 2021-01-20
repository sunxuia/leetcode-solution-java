package q1550;

import org.junit.runner.RunWith;
import q1400.Q1375_BulbSwitcherIII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1529. Bulb Switcher IV
 * https://leetcode.com/problems/bulb-switcher-iv/
 *
 * There is a room with n bulbs, numbered from 0 to n-1, arranged in a row from left to right. Initially all the bulbs
 * are turned off.
 *
 * Your task is to obtain the configuration represented by target where target[i] is '1' if the i-th bulb is turned on
 * and is '0' if it is turned off.
 *
 * You have a switch to flip the state of the bulb, a flip operation is defined as follows:
 *
 * Choose any bulb (index i) of your current configuration.
 * Flip each bulb from index i to n-1.
 *
 * When any bulb is flipped it means that if it is 0 it changes to 1 and if it is 1 it changes to 0.
 *
 * Return the minimum number of flips required to form target.
 *
 * Example 1:
 *
 * Input: target = "10111"
 * Output: 3
 * Explanation: Initial configuration "00000".
 * flip from the third bulb:  "00000" -> "00111"
 * flip from the first bulb:  "00111" -> "11000"
 * flip from the second bulb:  "11000" -> "10111"
 * We need at least 3 flip operations to form target.
 *
 * Example 2:
 *
 * Input: target = "101"
 * Output: 3
 * Explanation: "000" -> "111" -> "100" -> "101".
 *
 * Example 3:
 *
 * Input: target = "00000"
 * Output: 0
 *
 * Example 4:
 *
 * Input: target = "001011101"
 * Output: 5
 *
 * Constraints:
 *
 * 1 <= target.length <= 10^5
 * target[i] == '0' or target[i] == '1'
 *
 * 上一题 {@link Q1375_BulbSwitcherIII}
 */
@RunWith(LeetCodeRunner.class)
public class Q1529_BulbSwitcherIV {

    @Answer
    public int minFlips(String target) {
        int res = 0, flip = 0;
        for (int i = 0; i < target.length(); i++) {
            int on = target.charAt(i) - '0';
            if (flip != on) {
                flip = on;
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("10111").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create("101").expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create("00000").expect(0);

    @TestData
    public DataExpectation example4 = DataExpectation.create("001011101").expect(5);

}
