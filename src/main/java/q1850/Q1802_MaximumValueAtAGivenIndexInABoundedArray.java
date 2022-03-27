package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1802. Maximum Value at a Given Index in a Bounded Array
 * https://leetcode.com/problems/maximum-value-at-a-given-index-in-a-bounded-array/
 *
 * You are given three positive integers: n, index, and maxSum. You want to construct an array nums (0-indexed) that
 * satisfies the following conditions:
 *
 * nums.length == n
 * nums[i] is a positive integer where 0 <= i < n. (nums[i] > 0)
 * abs(nums[i] - nums[i+1]) <= 1 where 0 <= i < n-1.
 * The sum of all the elements of nums does not exceed maxSum.
 * nums[index] is maximized.
 *
 * Return nums[index] of the constructed array.
 *
 * Note that abs(x) equals x if x >= 0, and -x otherwise.
 *
 * Example 1:
 *
 * Input: n = 4, index = 2,  maxSum = 6
 * Output: 2
 * Explanation: nums = [1,2,2,1] is one array that satisfies all the conditions.
 * There are no arrays that satisfy all the conditions and have nums[2] == 3, so 2 is the maximum nums[2].
 *
 * Example 2:
 *
 * Input: n = 6, index = 1,  maxSum = 10
 * Output: 3
 *
 * Constraints:
 *
 * 1 <= n <= maxSum <= 10^9
 * 0 <= index < n
 */
@RunWith(LeetCodeRunner.class)
public class Q1802_MaximumValueAtAGivenIndexInABoundedArray {

    /**
     * 计算公式然后套用, 时间复杂度 O(1), leetcode 上最多的是binary search
     */
    @Answer
    public int maxValue(int n, int index, int maxSum) {
        // 简化计算, 所有元素-1, 以0 为底
        maxSum -= n;
        // 将坐标整理为 [长度x的区域, nums[index], 长度y的区域], x<=y
        long x = Math.min(index, n - index - 1);
        long y = n - x - 1;
        if (getMinArea(x, y, y) <= maxSum) {
            return (int) ((2 * maxSum + x * x + y * y + x + y) / (2 * n)) + 1;
        }
        if (getMinArea(x, x, y) <= maxSum) {
            double b = 2 * x + 1, c = -x * x - x - 2 * maxSum;
            return (int) ((Math.sqrt(b * b - 4 * c) - b) / 2) + 1;
        }
        return (int) Math.sqrt(maxSum) + 1;
    }

    // 获取最小面积高度(山峰状), h=nums[index]
    private long getMinArea(long x, long h, long y) {
        if (y < h) {
            // h'=h-1, 总面积为 (h'+h'-x+1)*x/2 + h + (h'+h'-y+1)*y/2
            return (x + y + 1) * h - (x * (x + 1) + y * (y + 1)) / 2;
        } else if (x < h) {
            // h'=h-1, 总面积为 (h'+h'-x+1)*x/2 + h + (h'+1)*h'/2
            return (h * h + (2 * x + 1) * h - x * x - x) / 2;
        } else {
            // h'=h-1, 总面积为 (h'+1)*h'/2 + h + (h'+1)*h'/2
            return h * h;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(4, 2, 6).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(6, 1, 10).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(3, 2, 18).expect(7);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(8, 7, 14).expect(4);

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith(1, 0, 24).expect(24);

    @TestData
    public DataExpectation normal4 = DataExpectation.createWith(4, 0, 4).expect(1);

    @TestData
    public DataExpectation normal5 = DataExpectation.createWith(9, 3, 9).expect(1);

}
