package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1330. Reverse Subarray To Maximize Array Value
 * https://leetcode.com/problems/reverse-subarray-to-maximize-array-value/
 *
 * You are given an integer array nums. The value of this array is defined as the sum of |nums[i]-nums[i+1]| for all 0
 * <= i < nums.length-1.
 *
 * You are allowed to select any subarray of the given array and reverse it. You can perform this operation only once.
 *
 * Find maximum possible value of the final array.
 *
 * Example 1:
 *
 * Input: nums = [2,3,1,5,4]
 * Output: 10
 * Explanation: By reversing the subarray [3,1,5] the array becomes [2,5,1,3,4] whose value is 10.
 *
 * Example 2:
 *
 * Input: nums = [2,4,9,24,2,1,10]
 * Output: 68
 *
 * Constraints:
 *
 * 1 <= nums.length <= 3*10^4
 * -10^5 <= nums[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1330_ReverseSubarrayToMaximizeArrayValue {

    /**
     * 暴力求解, 时间复杂度 O(N^2), 会超时.
     * 翻转后, 翻转的区间内部的值不会变, 只有翻转的2 边会出现值的变化.
     */
//    @Answer
    public int maxValueAfterReverse_bf(int[] nums) {
        final int n = nums.length;
        int value = 0;
        for (int i = 1; i < n; i++) {
            value += Math.abs(nums[i - 1] - nums[i]);
        }
        int res = value;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int reversed = value;
                if (i > 0) {
                    // 头翻转
                    reversed += Math.abs(nums[i - 1] - nums[j])
                            - Math.abs(nums[i - 1] - nums[i]);
                }
                if (j < n - 1) {
                    // 尾翻转
                    reversed += Math.abs(nums[j + 1] - nums[i])
                            - Math.abs(nums[j + 1] - nums[j]);
                }
                res = Math.max(reversed, res);
            }
        }
        return res;
    }

    /**
     * 参考文档
     * https://zxi.mytechroad.com/blog/greedy/leetcode-1330-reverse-subarray-to-maximize-array-value/
     * https://www.cnblogs.com/habibah-chang/p/12992939.html
     *
     * 根据hint 中的提示, 利用 | (a-i) - (a-j) | = max( ±(i-j), ±(2a-i-j) ) 的特性.
     * 令要翻转的区间值为 ... a [i ... j] b ... 其中 i, j 对应的下标区间是要翻转的区间, ab 分别是区间边上的点的值.
     * 则上面的解答中的reversed 的计算公式变为 ±2( 0, a-i, b-i, a-j, b-j, a-b, i-j, a-i+b-j ) 中的最大值.
     * 简化后得到下面的结果.
     */
    @Answer
    public int maxValueAfterReverse(int[] nums) {
        final int n = nums.length;
        int sum = 0, gain = 0;
        int high = Integer.MIN_VALUE;
        int low = Integer.MAX_VALUE;
        for (int i = 0; i < n - 1; i++) {
            int curr = nums[i];
            int next = nums[i + 1];
            sum += Math.abs(curr - next);
            // 从0 位置翻转
            gain = Math.max(gain, Math.abs(nums[0] - next) - Math.abs(curr - next));
            // 从n-1 位置翻转
            gain = Math.max(gain, Math.abs(nums[n - 1] - curr) - Math.abs(curr - next));
            // 前最大值
            high = Math.max(high, Math.min(curr, next));
            // 后最小值
            low = Math.min(low, Math.max(curr, next));
        }
        return sum + Math.max(gain, (high - low) * 2);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 3, 1, 5, 4}).expect(10);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 4, 9, 24, 2, 1, 10}).expect(68);

    /**
     * 3万个数据
     */
    @TestData
    public DataExpectation example3 = DataExpectation
            .create(TestDataFileHelper.readIntegerArray("Q1330_TestData"))
            .expect(1988832659);

}
