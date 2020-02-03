package q050;

import org.junit.runner.RunWith;
import util.asserthelper.AssertUtils;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.UsingTestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/next-permutation/
 *
 * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
 *
 * If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending
 * order).
 *
 * The replacement must be in-place and use only constant extra memory.
 *
 * Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand
 * column.
 *
 * 1,2,3 → 1,3,2
 * 3,2,1 → 1,2,3
 * 1,1,5 → 1,5,1
 *
 * 题解:
 * 数组随机排列, 每个排列出来的数字从前往后有大有小, 现在就是要找出比当前排列方式大的最小排列.
 * 如果当前排列方式已经是最大的, 那么就找出最小排列.
 */
@RunWith(LeetCodeRunner.class)
public class Q031_NextPermutation {

    /**
     * 这题套用特定公式即可
     */
    @Answer
    @UsingTestData({})
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        // 从右往左找出第一个比右边值小的元素 nums[i]
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        // 找到了这个数字, 就找出 i 右边比 i + 1 值小的元素 nums[j], 然后交换.
        // 如果找不到, 那就说明 i+ 1 本身就是最小的数字, 直接和前面交换即可.
        if (i >= 0) {
            int j = nums.length - 1;
            while (j > i + 1 && nums[i] >= nums[j]) {
                j--;
            }
            swap(nums, i, j);
        }
        // 剩下的数字都是逆序的, 进行反序, 找到最小值
        reverse(nums, i + 1, nums.length);
    }

    private void reverse(int[] nums, int start, int end) {
        for (int i = 0; i < (end - start) / 2; i++) {
            swap(nums, start + i, end - i - 1);
        }
    }

    private void swap(int[] nums, int a, int b) {
        int t = nums[a];
        nums[a] = nums[b];
        nums[b] = t;
    }

    @TestData
    public DataExpectation example1() {
        int[] input = new int[]{1, 2, 3};
        return DataExpectation.builder()
                .addArgument(input)
                .assertMethod((e, a) -> {
                    AssertUtils.assertEquals(new int[]{1, 3, 2}, input);
                }).build();
    }

    @TestData
    public DataExpectation example2() {
        int[] input = new int[]{3, 2, 1};
        return DataExpectation.builder()
                .addArgument(input)
                .assertMethod((e, a) -> {
                    AssertUtils.assertEquals(new int[]{1, 2, 3}, input);
                }).build();
    }

    @TestData
    public DataExpectation example3() {
        int[] input = new int[]{1, 1, 5};
        return DataExpectation.builder()
                .addArgument(input)
                .assertMethod((e, a) -> {
                    AssertUtils.assertEquals(new int[]{1, 5, 1}, input);
                }).build();
    }

    @TestData
    public DataExpectation normal1() {
        int[] input = new int[]{1, 3, 2};
        return DataExpectation.builder()
                .addArgument(input)
                .assertMethod((e, a) -> {
                    AssertUtils.assertEquals(new int[]{2, 1, 3}, input);
                }).build();
    }

    @TestData
    public DataExpectation normal2() {
        int[] input = new int[]{2, 3, 1};
        return DataExpectation.builder()
                .addArgument(input)
                .assertMethod((e, a) -> {
                    AssertUtils.assertEquals(new int[]{3, 1, 2}, input);
                }).build();
    }

    @TestData
    public DataExpectation normal3() {
        int[] input = new int[]{4, 2, 0, 2, 3, 2, 0};
        return DataExpectation.builder()
                .addArgument(input)
                .assertMethod((e, a) -> {
                    AssertUtils.assertEquals(new int[]{4, 2, 0, 3, 0, 2, 2}, input);
                }).build();
    }
}
