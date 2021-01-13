package q1500;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1498. Number of Subsequences That Satisfy the Given Sum Condition
 * https://leetcode.com/problems/number-of-subsequences-that-satisfy-the-given-sum-condition/
 *
 * Given an array of integers nums and an integer target.
 *
 * Return the number of non-empty subsequences of nums such that the sum of the minimum and maximum element on it is
 * less or equal to target. Since the answer may be too large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: nums = [3,5,6,7], target = 9
 * Output: 4
 * Explanation: There are 4 subsequences that satisfy the condition.
 * [3] -> Min value + max value <= target (3 + 3 <= 9)
 * [3,5] -> (3 + 5 <= 9)
 * [3,5,6] -> (3 + 6 <= 9)
 * [3,6] -> (3 + 6 <= 9)
 *
 * Example 2:
 *
 * Input: nums = [3,3,6,8], target = 10
 * Output: 6
 * Explanation: There are 6 subsequences that satisfy the condition. (nums can have repeated numbers).
 * [3] , [3] , [3,3], [3,6] , [3,6] , [3,3,6]
 *
 * Example 3:
 *
 * Input: nums = [2,3,3,4,6,7], target = 12
 * Output: 61
 * Explanation: There are 63 non-empty subsequences, two of them don't satisfy the condition ([6,7], [7]).
 * Number of valid subsequences (63 - 2 = 61).
 *
 * Example 4:
 *
 * Input: nums = [5,2,4,1,7,6,8], target = 16
 * Output: 127
 * Explanation: All non-empty subset satisfy the condition (2^7 - 1) = 127
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^6
 * 1 <= target <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q1498_NumberOfSubsequencesThatSatisfyTheGivenSumCondition {

    @Answer
    public int numSubseq(int[] nums, int target) {
        final int n = nums.length;
        final int mod = 10_0000_0007;
        Arrays.sort(nums);
        // 暂存结果 pows[i] 表示结果中2^i 的个数.
        int[] pows = new int[n];
        for (int i = 0, end = n - 1; i < n; i++) {
            while (i < end && nums[i] + nums[end] > target) {
                end--;
            }
            if (nums[i] + nums[end] > target) {
                break;
            }
            // Cn0 + Cn1 + Cn2 + ... + Cnn = 2 ^ n
            // https://zhidao.baidu.com/question/180415888.html
            pows[end - i]++;
        }

        int res = 0;
        for (int i = 0, d = 1; i < n; i++) {
            res = (res + pows[i] * d) % mod;
            d = d * 2 % mod;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{3, 5, 6, 7}, 9).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{3, 3, 6, 8}, 10).expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{2, 3, 3, 4, 6, 7}, 12).expect(61);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(new int[]{5, 2, 4, 1, 7, 6, 8}, 16).expect(127);

    /**
     * int 表示的2^n 会超
     */
    @TestData
    public DataExpectation overflow1 = DataExpectation.createWith(new int[]{
            14, 4, 6, 6, 20, 8, 5, 6, 8, 12, 6, 10, 14, 9, 17, 16, 9, 7, 14, 11, 14, 15, 13, 11, 10, 18, 13, 17, 17, 14,
            17, 7, 9, 5, 10, 13, 8, 5, 18, 20, 7, 5, 5, 15, 19, 14}, 22)
            .expect(272187084);

    /**
     * long 表示的2^n 会超
     */
    @TestData
    public DataExpectation overflow2 = DataExpectation.createWith(new int[]{
            27, 21, 14, 2, 15, 1, 19, 8, 12, 24, 21, 8, 12, 10, 11, 30, 15, 18, 28, 14, 26, 9, 2, 24, 23, 11, 7, 12, 9,
            17, 30, 9, 28, 2, 14, 22, 19, 19, 27, 6, 15, 12, 29, 2, 30, 11, 20, 30, 21, 20, 2, 22, 6, 14, 13, 19, 21,
            10, 18, 30, 2, 20, 28, 22}, 31)
            .expect(688052206);

    /**
     * 如果使用BigDecimal 来计算2^n 则这个测试用例会超时
     */
    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.read(int[].class), 119238)
            .expect(905345521);

}
