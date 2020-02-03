package q100;

import java.util.Arrays;
import java.util.function.BiConsumer;
import org.junit.runner.RunWith;
import util.asserthelper.AssertUtils;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/
 *
 * Given a sorted array nums, remove the duplicates in-place such that duplicates appeared at most twice and return
 * the new length.
 *
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1)
 * extra memory.
 *
 * Example 1:
 *
 * Given nums = [1,1,1,2,2,3],
 *
 * Your function should return length = 5, with the first five elements of nums being 1, 1, 2, 2 and 3 respectively.
 *
 * It doesn't matter what you leave beyond the returned length.
 *
 * Example 2:
 *
 * Given nums = [0,0,1,1,1,1,2,3,3],
 *
 * Your function should return length = 7, with the first seven elements of nums being modified to 0, 0, 1, 1, 2, 3
 * and 3 respectively.
 *
 * It doesn't matter what values are set beyond the returned length.
 *
 * Clarification:
 *
 * Confused why the returned value is an integer but your answer is an array?
 *
 * Note that the input array is passed in by reference, which means modification to the input array will be known to
 * the caller as well.
 *
 * Internally you can think of this:
 *
 * // nums is passed in by reference. (i.e., without making a copy)
 * int len = removeDuplicates(nums);
 *
 * // any modification to nums in your function would be known by the caller.
 * // using the length returned by your function, it prints the first len elements.
 * for (int i = 0; i < len; i++) {
 * print(nums[i]);
 * }
 */
@RunWith(LeetCodeRunner.class)
public class Q080_RemoveDuplicatesFromSortedArrayII {

    @Answer
    public int removeDuplicates(int[] nums) {
        if (nums.length < 3) {
            return nums.length;
        }
        int next = 2;
        for (int i = 2; i < nums.length; i++) {
            // Leet Code 上最快的解答对此判断的优化:
            // 题目允许有2 个相同的, 而且数组是按顺序排列的, 如果nums[i] != nums[next - 2],
            // 那么就肯定最多只有1 个相同 (nums[i] == nums[next - 1] 的情况);
            // 如果 nums[i] == nums[next - 2], 那么肯定也等于 nums[next - 1].
            // 所以这里可以省略一个判断.
            if (/*nums[i] != nums[next - 1] && */nums[i] != nums[next - 2]) {
                nums[next++] = nums[i];
            }
        }
        return next;
    }

    private BiConsumer<int[], int[]> arrayAssertMethod = (e, a) -> {
        AssertUtils.assertEquals(e, Arrays.copyOf(a, e.length));
    };

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{1, 1, 1, 2, 2, 3})
            .expect(5)
            .expectArgument(0, new int[]{1, 1, 2, 2, 3})
            .argumentAssertMethod(0, arrayAssertMethod)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{0, 0, 1, 1, 1, 1, 2, 3, 3})
            .expect(7)
            .expectArgument(0, new int[]{0, 0, 1, 1, 2, 3})
            .argumentAssertMethod(0, arrayAssertMethod)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{0, 0, 0, 1, 1, 1})
            .expect(4)
            .expectArgument(0, new int[]{0, 0, 1, 1})
            .argumentAssertMethod(0, arrayAssertMethod)
            .build();

}
