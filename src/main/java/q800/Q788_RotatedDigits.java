package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/rotated-digits/
 *
 * X is a good number if after rotating each digit individually by 180 degrees, we get a valid number that is
 * different from X.  Each digit must be rotated - we cannot choose to leave it alone.
 *
 * A number is valid if each digit remains a digit after rotation. 0, 1, and 8 rotate to themselves; 2 and 5 rotate
 * to each other (on this case they are rotated in a different direction, in other words 2 or 5 gets mirrored); 6 and
 * 9 rotate to each other, and the rest of the numbers do not rotate to any other number and become invalid.
 *
 * Now given a positive number N, how many numbers X from 1 to N are good?
 *
 * Example:
 * Input: 10
 * Output: 4
 * Explanation:
 * There are four good numbers in the range [1, 10] : 2, 5, 6, 9.
 * Note that 1 and 10 are not good numbers, since they remain unchanged after rotating.
 *
 * Note:
 *
 * N  will be in range [1, 10000].
 */
@RunWith(LeetCodeRunner.class)
public class Q788_RotatedDigits {

    @Answer
    public int rotatedDigits(int N) {
        int res = 0;
        for (int i = 0; i <= N; i++) {
            if (isGood(i)) {
                res++;
            }
        }
        return res;
    }

    private boolean isGood(int val) {
        int mask = 0;
        while (val > 0 && mask >= 0) {
            mask |= ROTATE[val % 10];
            val /= 10;
        }
        return mask == 1;
    }

    // 0 表示颠倒过来是自己, 1 表示颠倒过来是别的, -1 表示不能颠倒
    private static final int[] ROTATE = new int[]{0, 0, 1, -1, -1, 1, 1, -1, 0, 1};

    /**
     * https://www.cnblogs.com/grandyang/p/9154892.html 中给出的动态规划的解法.
     */
    @Answer
    public int rotatedDigits_DP(int N) {
        int res = 0;
        // 表示这个数字是否good.
        int[] dp = new int[N + 1];
        System.arraycopy(ROTATE, 0, dp, 0, Math.min(dp.length, ROTATE.length));
        for (int i = 0; i <= N; i++) {
            dp[i] = dp[i / 10] | dp[i % 10];
            res += Math.max(0, dp[i]);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(10).expect(4);

    @TestData
    public DataExpectation value0 = DataExpectation.create(0).expect(0);

    @TestData
    public DataExpectation value1 = DataExpectation.create(1).expect(0);

    @TestData
    public DataExpectation value2 = DataExpectation.create(2).expect(1);

}
