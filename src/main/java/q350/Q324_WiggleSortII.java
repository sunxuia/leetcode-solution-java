package q350;

import java.util.Arrays;
import java.util.function.BiConsumer;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/wiggle-sort-ii/
 *
 * Given an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....
 *
 * Example 1:
 *
 * Input: nums = [1, 5, 1, 1, 6, 4]
 * Output: One possible answer is [1, 4, 1, 5, 1, 6].
 *
 * Example 2:
 *
 * Input: nums = [1, 3, 2, 2, 3, 1]
 * Output: One possible answer is [2, 3, 1, 3, 1, 2].
 *
 * Note:
 * You may assume all input has valid answer.
 *
 * Follow Up:
 * Can you do it in O(n) time and/or in-place with O(1) extra space?
 */
@RunWith(LeetCodeRunner.class)
public class Q324_WiggleSortII {

    @Answer
    public void wiggleSort(int[] nums) {
        final int len = nums.length;
        int[] temp = Arrays.copyOf(nums, len);
        Arrays.sort(temp);
        for (int i = 0, j = (len - 1) / 2, k = len - 1; i < len; i++) {
            nums[i] = (i & 1) == 0 ? temp[j--] : temp[k--];
        }
    }

    // 题目要求 O(n) 的时间复杂度和 O(1) 的空间复杂度
    // 网上的解法如下 (O(1) 的堆复杂度)
    // https://www.jianshu.com/p/98ed84a8e219
    @Answer
    public void followUp(int[] nums) {
        int n = nums.length;
        if(n < 2) {
            return;
        }
        // 找到中位数
        int median = findKthLargest(nums, (nums.length + 1) / 2);

        int left = 0, i = 0, right = n - 1;
        while (i <= right) {
            if (nums[newIndex(i, n)] > median) {
                swap(nums, newIndex(left++, n), newIndex(i++, n));
            } else if (nums[newIndex(i, n)] < median) {
                swap(nums, newIndex(right--, n), newIndex(i, n));
            } else {
                i++;
            }
        }
    }
    public int findKthLargest(int[] nums, int k) {
        return findKthLargest(nums, 0, nums.length - 1, k);
    }

    private int findKthLargest(int[] nums, int l, int r, int k) {
        int index = partition(nums, l, r);
        if (index == nums.length - k) {
            return nums[index];
        } else if (index < nums.length - k) {
            return findKthLargest(nums, index + 1, r, k);
        } else {
            return findKthLargest(nums, l, index - 1, k);
        }
    }

    // 将当前下标映射到预期的新的下标上
    private int newIndex(int index, int n) {
        return (1 + 2 * index) % (n | 1);
    }


    private int partition(int[] nums, int l, int r) {
        int pivot = nums[r];
        int i = l;
        while (l < r) {
            if (nums[l] <= pivot) {
                swap(nums, l, i++);
            }
            ++l;
        }

        swap(nums, r, i);
        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }


    private BiConsumer<int[], int[]> assertMethod = (e, a) -> {
        for (int i = 1; i < a.length - 1; i += 2) {
            Assert.assertTrue(a[i - 1] < a[i] && a[i] > a[i + 1]);
        }
    };

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{1, 5, 1, 1, 6, 4})
            .argumentAssertMethod(0, assertMethod)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{})
            .argumentAssertMethod(0, assertMethod)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{1, 3, 2, 2, 3, 1})
            .argumentAssertMethod(0, assertMethod)
            .build();

}
