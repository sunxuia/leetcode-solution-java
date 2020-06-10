package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/race-car/
 *
 * Your car starts at position 0 and speed +1 on an infinite number line.  (Your car can go into negative positions.)
 *
 * Your car drives automatically according to a sequence of instructions A (accelerate) and R (reverse).
 *
 * When you get an instruction "A", your car does the following: position += speed, speed *= 2.
 *
 * When you get an instruction "R", your car does the following: if your speed is positive then speed = -1 ,
 * otherwise speed = 1.  (Your position stays the same.)
 *
 * For example, after commands "AAR", your car goes to positions 0->1->3->3, and your speed goes to 1->2->4->-1.
 *
 * Now for some target position, say the length of the shortest sequence of instructions to get there.
 *
 * Example 1:
 * Input:
 * target = 3
 * Output: 2
 * Explanation:
 * The shortest instruction sequence is "AA".
 * Your position goes from 0->1->3.
 *
 * Example 2:
 * Input:
 * target = 6
 * Output: 5
 * Explanation:
 * The shortest instruction sequence is "AAARA".
 * Your position goes from 0->1->3->7->7->6.
 *
 *
 *
 * Note:
 *
 * 1 <= target <= 10000.
 */
@RunWith(LeetCodeRunner.class)
public class Q818_RaceCar {

    /**
     * 没什么思路, 参考 https://www.cnblogs.com/grandyang/p/10360655.html
     */
    @Answer
    public int racecar(int target) {
        int[] dp = new int[target + 1];
        for (int i = 1; i <= target; i++) {
            dp[i] = Integer.MAX_VALUE;
            int j = 1, count1 = 1;
            while (j < i) {
                for (int k = 0, count2 = 0; k < j; k = (1 << ++count2) - 1) {
                    dp[i] = Math.min(dp[i], count1 + 1 + count2 + 1 + dp[i - (j - k)]);
                }
                j = (1 << ++count1) - 1;
            }
            dp[i] = Math.min(dp[i], count1 + (i == j ? 0 : 1 + dp[j - i]));
        }
        return dp[target];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(3).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(6).expect(5);

}
