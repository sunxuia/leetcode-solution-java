package q1250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1250. Check If It Is a Good Array
 * https://leetcode.com/problems/check-if-it-is-a-good-array/
 *
 * Given an array nums of positive integers. Your task is to select some subset of nums, multiply each element by an
 * integer and add all these numbers. The array is said to be good if you can obtain a sum of 1 from the array by any
 * possible subset and multiplicand.
 *
 * Return True if the array is good otherwise return False.
 *
 * Example 1:
 *
 * Input: nums = [12,5,7,23]
 * Output: true
 * Explanation: Pick numbers 5 and 7.
 * 5*3 + 7*(-2) = 1
 *
 * Example 2:
 *
 * Input: nums = [29,6,10]
 * Output: true
 * Explanation: Pick numbers 29, 6 and 10.
 * 29*1 + 6*(-3) + 10*(-1) = 1
 *
 * Example 3:
 *
 * Input: nums = [3,6]
 * Output: false
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1250_CheckIfItIsAGoodArray {

    /**
     * 纯粹的观察规律的题目.
     * 如果所有数字的最大公约数不是1 则不可以通过运算得到1 的结果.
     */
    @Answer
    public boolean isGoodArray(int[] nums) {
        int val = nums[0];
        for (int i = 1; i < nums.length; i++) {
            val = gcd(val, nums[i]);
        }
        return val == 1;
    }

    private int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{12, 5, 7, 23}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{29, 6, 10}).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{3, 6}).expect(false);

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[]{1}).expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{18, 18, 18, 15, 54, 45, 45, 45}).expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{6, 10, 15}).expect(true);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create(new int[]{100, 90, 70, 80, 40, 75, 135, 120, 150, 150, 1000})
            .expect(false);

}
