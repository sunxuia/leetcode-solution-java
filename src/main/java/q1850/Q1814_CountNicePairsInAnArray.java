package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Medium] 1814. Count Nice Pairs in an Array
 * https://leetcode.com/problems/count-nice-pairs-in-an-array/
 *
 * You are given an array nums that consists of non-negative integers. Let us define rev(x) as the reverse of the
 * non-negative integer x. For example, rev(123) = 321, and rev(120) = 21. A pair of indices (i, j) is nice if it
 * satisfies all of the following conditions:
 *
 * 0 <= i < j < nums.length
 * nums[i] + rev(nums[j]) == nums[j] + rev(nums[i])
 *
 * Return the number of nice pairs of indices. Since that number can be too large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: nums = [42,11,1,97]
 * Output: 2
 * Explanation: The two pairs are:
 * - (0,3) : 42 + rev(97) = 42 + 79 = 121, 97 + rev(42) = 97 + 24 = 121.
 * - (1,2) : 11 + rev(1) = 11 + 1 = 12, 1 + rev(11) = 1 + 11 = 12.
 *
 * Example 2:
 *
 * Input: nums = [13,10,35,24,76]
 * Output: 4
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1814_CountNicePairsInAnArray {

    /**
     * x + r(y) = r(x) + y 变换后就变为 x - r(x) = y - r(y)
     */
    @Answer
    public int countNicePairs(int[] nums) {
        final int n = nums.length;
        int res = 0;
        Map<Integer, Integer> counts = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int rev = 0;
            for (int num = nums[i]; num > 0; num /= 10) {
                rev = rev * 10 + num % 10;
            }
            int count = counts.getOrDefault(nums[i] - rev, 0);
            counts.put(nums[i] - rev, count + 1);
            // 当前数字与其他相同结果匹配的数量
            res = (res + count) % 10_0000_0007;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{42, 11, 1, 97}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{13, 10, 35, 24, 76}).expect(4);

}
