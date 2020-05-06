package q600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/non-negative-integers-without-consecutive-ones/
 *
 * Given a positive integer n, find the number of non-negative integers less than or equal to n, whose binary
 * representations do NOT contain consecutive ones.
 *
 * Example 1:
 *
 * Input: 5
 * Output: 5
 * Explanation:
 * Here are the non-negative integers <= 5 with their corresponding binary representations:
 * 0 : 0
 * 1 : 1
 * 2 : 10
 * 3 : 11
 * 4 : 100
 * 5 : 101
 * Among them, only integer 3 disobeys the rule (two consecutive ones) and the other 5 satisfy the rule.
 *
 * Note: 1 <= n <= 109
 */
@RunWith(LeetCodeRunner.class)
public class Q600_NonNegativeIntegersWithoutConsecutiveOnes {

    // https://www.cnblogs.com/grandyang/p/6959585.html
    @Answer
    public int findIntegers(int num) {
        int res = 0, pre = 0;
        int[] f = new int[32];
        f[0] = 1;
        f[1] = 2;
        for (int i = 2; i < 31; ++i) {
            f[i] = f[i - 2] + f[i - 1];
        }
        for (int k = 31; k >= 0; k--) {
            if ((num & (1 << k)) != 0) {
                res += f[k];
                if (pre != 0) {
                    return res;
                }
                pre = 1;
            } else {
                pre = 0;
            }
        }
        return res + 1;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(5).expect(5);

}
