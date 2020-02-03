package q200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/rotate-array/
 *
 * Given an array, rotate the array to the right by k steps, where k is non-negative.
 *
 * Example 1:
 *
 * Input: [1,2,3,4,5,6,7] and k = 3
 * Output: [5,6,7,1,2,3,4]
 * Explanation:
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 * rotate 3 steps to the right: [5,6,7,1,2,3,4]
 *
 * Example 2:
 *
 * Input: [-1,-100,3,99] and k = 2
 * Output: [3,99,-1,-100]
 * Explanation:
 * rotate 1 steps to the right: [99,-1,-100,3]
 * rotate 2 steps to the right: [3,99,-1,-100]
 *
 * Note:
 *
 * Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.
 * Could you do it in-place with O(1) extra space?
 */
@RunWith(LeetCodeRunner.class)
public class Q189_RotateArray {

    // 使用额外空间的方式
    @Answer
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        int[] temp = new int[k];
        System.arraycopy(nums, nums.length - k, temp, 0, k);
        System.arraycopy(nums, 0, nums, k, nums.length - k);
        System.arraycopy(temp, 0, nums, 0, k);
    }

    // 不使用额外空间的方式, 一次一次地移动, 这个比较慢.
    @Answer
    public void rotate2(int[] nums, int k) {
        k %= nums.length;
        for (int i = 0; i < k; i++) {
            int last = nums[nums.length - 1];
            System.arraycopy(nums, 0, nums, 1, nums.length - 1);
            nums[0] = last;
        }
    }

    /**
     * leetcode 上通过翻转实现旋转的算法.
     * nums.length - k 分隔的2 片区域是 [A, B], 翻转后变为 [B, A], 那么再分别翻转B 和 A 就得到旋转后的结果.
     */
    @Answer
    public void rotate3(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    /**
     * LeetCode 中的测试用例没有空数组
     */

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 3, 4, 5, 6, 7})
            .addArgument(3)
            .expectArgument(0, new int[]{5, 6, 7, 1, 2, 3, 4})
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{-1, -100, 3, 99})
            .addArgument(2)
            .expectArgument(0, new int[]{3, 99, -1, -100})
            .build();

    @TestData
    public DataExpectation border = DataExpectation.builder()
            .addArgument(new int[]{0, 1, 2, 3})
            .addArgument(0)
            .expectArgument(0, new int[]{0, 1, 2, 3})
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{0})
            .addArgument(2)
            .expectArgument(0, new int[]{0})
            .build();

}
