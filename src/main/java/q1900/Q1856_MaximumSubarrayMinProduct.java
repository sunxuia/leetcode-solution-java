package q1900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Medium] 1856. Maximum Subarray Min-Product
 * https://leetcode.com/problems/maximum-subarray-min-product/
 *
 * The min-product of an array is equal to the minimum value in the array multiplied by the array's sum.
 *
 * For example, the array [3,2,5] (minimum value is 2) has a min-product of 2 * (3+2+5) = 2 * 10 = 20.
 *
 * Given an array of integers nums, return the maximum min-product of any non-empty subarray of nums. Since the answer
 * may be large, return it modulo 10^9 + 7.
 *
 * Note that the min-product should be maximized before performing the modulo operation. Testcases are generated such
 * that the maximum min-product without modulo will fit in a 64-bit signed integer.
 *
 * A subarray is a contiguous part of an array.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,2]
 * Output: 14
 * Explanation: The maximum min-product is achieved with the subarray [2,3,2] (minimum value is 2).
 * 2 * (2+3+2) = 2 * 7 = 14.
 *
 * Example 2:
 *
 * Input: nums = [2,3,3,1,2]
 * Output: 18
 * Explanation: The maximum min-product is achieved with the subarray [3,3] (minimum value is 3).
 * 3 * (3+3) = 3 * 6 = 18.
 *
 * Example 3:
 *
 * Input: nums = [3,1,5,6,4,2]
 * Output: 60
 * Explanation: The maximum min-product is achieved with the subarray [5,6,4] (minimum value is 4).
 * 4 * (5+6+4) = 4 * 15 = 60.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^7
 */
@RunWith(LeetCodeRunner.class)
public class Q1856_MaximumSubarrayMinProduct {

    /**
     * 令a <= i <= b, 且 nums[a:b] 区间内所有元素都 >=nums[i], a和b 都尽可能的向左右扩张,
     * 则此时得到的以 nums[i] 为乘数的数字结果最大.
     */
    @Answer
    public int maxSumMinProduct(int[] nums) {
        final int m = nums.length;

        // 计算数字的和
        long[] sums = new long[m + 1];
        for (int i = 0; i < m; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }

        // 最小栈计算左边界
        Stack<Integer> minStack = new Stack<>();
        int[] left = new int[m];
        for (int i = 0; i < m; i++) {
            while (!minStack.isEmpty()
                    && nums[minStack.peek()] > nums[i]) {
                minStack.pop();
            }
            if (minStack.isEmpty()) {
                left[i] = -1;
            } else if (nums[i] == nums[minStack.peek()]) {
                left[i] = left[minStack.peek()];
            } else {
                left[i] = minStack.peek();
            }
            minStack.push(i);
        }

        // 最小栈计算右边界
        minStack.clear();
        int[] right = new int[m];
        for (int i = m - 1; i >= 0; i--) {
            while (!minStack.isEmpty()
                    && nums[minStack.peek()] > nums[i]) {
                minStack.pop();
            }
            if (minStack.isEmpty()) {
                right[i] = m;
            } else if (nums[i] == nums[minStack.peek()]) {
                right[i] = right[minStack.peek()];
            } else {
                right[i] = minStack.peek();
            }
            minStack.push(i);
        }

        // 计算结果
        long res = 0;
        for (int i = 0; i < m; i++) {
            long minProduct = (sums[right[i]] - sums[left[i] + 1]) * nums[i];
            res = Math.max(res, minProduct);
        }
        return (int) (res % 10_0000_0007);
    }

    /**
     * leetcode 上比较快的解法
     */
    @Answer
    public int maxSumMinProduct2(int[] nums) {
        final int m = nums.length;
        long[] sums = new long[m + 1];
        for (int i = 0; i < m; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }

        long res = 0;
        // 最小栈保存到到此为止的最小值的下标
        Stack<Integer> minStack = new Stack<>();
        // (哨兵)
        minStack.push(-1);
        for (int i = 0; i <= m; i++) {
            while (minStack.size() > 1
                    && (i == m || nums[minStack.peek()] > nums[i])) {
                // 以这个nums[min] 为最小值的情况
                int min = minStack.pop();
                long minProduct = (sums[i] - sums[minStack.peek() + 1]) * nums[min];
                res = Math.max(res, minProduct);
            }
            minStack.push(i);
        }
        return (int) (res % 10_0000_0007);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 2}).expect(14);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 3, 3, 1, 2}).expect(18);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{3, 1, 5, 6, 4, 2}).expect(60);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[]{1, 1, 3, 2, 2, 2, 1, 5, 1, 5})
            .expect(25);

    @TestData
    public DataExpectation normal2() {
        int[] arr = new int[10000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1000_0000 - i;
        }
        return DataExpectation.create(arr).expect(910500506);
    }

}
