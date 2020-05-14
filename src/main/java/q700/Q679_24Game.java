package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/24-game/
 *
 * You have 4 cards each containing a number from 1 to 9. You need to judge whether they could operated through *,
 * /, +, -, (, ) to get the value of 24.
 *
 * Example 1:
 *
 * Input: [4, 1, 8, 7]
 * Output: True
 * Explanation: (8-4) * (7-1) = 24
 *
 * Example 2:
 *
 * Input: [1, 2, 1, 2]
 * Output: False
 *
 * Note:
 *
 * 1. The division operator / represents real division, not integer division. For example, 4 / (1 - 2/3) = 12.
 * 2. Every operation done is between two numbers. In particular, we cannot use - as a unary operator. For example,
 * with [1, 1, 1, 1] as input, the expression -1 - 1 - 1 - 1 is not allowed.
 * 3. You cannot concatenate numbers together. For example, if the input is [1, 2, 1, 2], we cannot write this as 12
 * + 12.
 */
@RunWith(LeetCodeRunner.class)
public class Q679_24Game {

    /**
     * 没想出什么特别好的方法, LeetCode 中比较快的解法如下, 也是一般的回溯.
     * Solution 中的解法与之思路一致.
     */
    @Answer

    public boolean judgePoint24(int[] nums) {
        double[] doubleNums = new double[nums.length];
        for (int i = 0; i < doubleNums.length; i++) {
            doubleNums[i] = nums[i];
        }
        return dfs(doubleNums, nums.length);
    }

    private boolean dfs(double[] nums, int n) {
        if (n == 1 && Math.abs(nums[0] - 24) <= 1e-6) {
            return true;
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double a = nums[i];
                double b = nums[j];

                nums[j] = nums[n - 1];

                nums[i] = a + b;
                if (dfs(nums, n - 1)) {
                    return true;
                }

                nums[i] = a - b;
                if (dfs(nums, n - 1)) {
                    return true;
                }

                nums[i] = a * b;
                if (dfs(nums, n - 1)) {
                    return true;
                }

                nums[i] = b - a;
                if (dfs(nums, n - 1)) {
                    return true;
                }

                if (a != 0) {
                    nums[i] = b / a;
                    if (dfs(nums, n - 1)) {
                        return true;
                    }
                }

                if (b != 0) {
                    nums[i] = a / b;
                    if (dfs(nums, n - 1)) {
                        return true;
                    }
                }

                nums[i] = a;
                nums[j] = b;
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{4, 1, 8, 7}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 1, 2}).expect(false);

}
