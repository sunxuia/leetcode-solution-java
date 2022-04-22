package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Medium] 1846. Maximum Element After Decreasing and Rearranging
 * https://leetcode.com/problems/maximum-element-after-decreasing-and-rearranging/
 *
 * You are given an array of positive integers arr. Perform some operations (possibly none) on arr so that it satisfies
 * these conditions:
 *
 * The value of the first element in arr must be 1.
 * The absolute difference between any 2 adjacent elements must be less than or equal to 1. In other words, abs(arr[i] -
 * arr[i - 1]) <= 1 for each i where 1 <= i < arr.length (0-indexed). abs(x) is the absolute value of x.
 *
 * There are 2 types of operations that you can perform any number of times:
 *
 * Decrease the value of any element of arr to a smaller positive integer.
 * Rearrange the elements of arr to be in any order.
 *
 * Return the maximum possible value of an element in arr after performing the operations to satisfy the conditions.
 *
 * Example 1:
 *
 * Input: arr = [2,2,1,2,1]
 * Output: 2
 * Explanation:
 * We can satisfy the conditions by rearranging arr so it becomes [1,2,2,2,1].
 * The largest element in arr is 2.
 *
 * Example 2:
 *
 * Input: arr = [100,1,1000]
 * Output: 3
 * Explanation:
 * One possible way to satisfy the conditions is by doing the following:
 * 1. Rearrange arr so it becomes [1,100,1000].
 * 2. Decrease the value of the second element to 2.
 * 3. Decrease the value of the third element to 3.
 * Now arr = [1,2,3], which satisfies the conditions.
 * The largest element in arr is 3.
 *
 * Example 3:
 *
 * Input: arr = [1,2,3,4,5]
 * Output: 5
 * Explanation: The array already satisfies the conditions, and the largest element is 5.
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^5
 * 1 <= arr[i] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1846_MaximumElementAfterDecreasingAndRearranging {

    /**
     * 贪婪算法, 这题没限制调整次数, 只要求最大值
     */
    @Answer
    public int maximumElementAfterDecrementingAndRearranging(int[] arr) {
        Arrays.sort(arr);
        if (arr[0] != 1) {
            arr[0] = 1;
        }
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] - arr[i] < -1) {
                // 向下调整
                arr[i] = arr[i - 1] + 1;
            }
        }
        return arr[arr.length - 1];
    }

    /**
     * leetcode 上最快的解法.
     */
    @Answer
    public int maximumElementAfterDecrementingAndRearranging2(int[] arr) {
        final int n = arr.length;
        // 桶排序, 因为题目要求是找出最大的值, 而arr[0]是1, 且数组元素不能变大, 因此可能的最大值 <= n.
        int[] counts = new int[n + 1];
        for (int num : arr) {
            if (num >= n) {
                counts[n]++;
            } else {
                counts[num]++;
            }
        }

        // cur 表示到arr[i] 的最大值
        int cur = 0;
        for (int i = 1; i <= n; i++) {
            if (cur + counts[i] >= i) {
                // arr[i] 处的值可以大于 i, 则arr[i] 就是最大值 i
                cur = i;
            } else {
                // 否则只能从i 值的counts[i] 个元素降下来
                cur = cur + counts[i];
            }
        }
        return cur;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 2, 1, 2, 1}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{100, 1, 1000}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3, 4, 5}).expect(5);

}
