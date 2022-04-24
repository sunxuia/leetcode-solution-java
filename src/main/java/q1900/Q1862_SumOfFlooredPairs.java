package q1900;

import java.util.stream.Stream;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Hard] 1862. Sum of Floored Pairs
 * https://leetcode.com/problems/sum-of-floored-pairs/
 *
 * Given an integer array nums, return the sum of floor(nums[i] / nums[j]) for all pairs of indices 0 <= i, j <
 * nums.length in the array. Since the answer may be too large, return it modulo 10^9 + 7.
 *
 * The floor() function returns the integer part of the division.
 *
 * Example 1:
 *
 * Input: nums = [2,5,9]
 * Output: 10
 * Explanation:
 * floor(2 / 5) = floor(2 / 9) = floor(5 / 9) = 0
 * floor(2 / 2) = floor(5 / 5) = floor(9 / 9) = 1
 * floor(5 / 2) = 2
 * floor(9 / 2) = 4
 * floor(9 / 5) = 1
 * We calculate the floor of the division for every pair of indices in the array then sum them up.
 *
 * Example 2:
 *
 * Input: nums = [7,7,7,7,7,7,7]
 * Output: 49
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1862_SumOfFlooredPairs {

    @Answer
    public int sumOfFlooredPairs(int[] nums) {
        int max = 0;
        for (int num : nums) {
            max = Math.max(num, max);
        }
        int[] sums = new int[max + 1];
        for (int num : nums) {
            sums[num]++;
        }
        for (int i = 1; i <= max; i++) {
            sums[i] += sums[i - 1];
        }

        // 通过桶避免重复overTime 中重复数字的超时情况
        int res = 0;
        for (int num = 1; num <= max; num++) {
            int count = sums[num] - sums[num - 1];
            if (count != 0) {
                long sum = 0;
                int i = 2 * num - 1;
                while (i < max) {
                    sum += i / num * (sums[i] - sums[i - num]);
                    i += num;
                }
                sum += max / num * (sums[max] - sums[i - num]);
                res = (int) ((res + sum * count) % 10_0000_0007);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 5, 9}).expect(10);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{7, 7, 7, 7, 7, 7, 7}).expect(49);

    @TestData
    public DataExpectation overTime() {
        int[] nums = new int[10_0000];
        Arrays.fill(nums, 1);
        nums[9_9999] = 10_0000;
        return DataExpectation.create(nums).expect(999699869);
    }

}
