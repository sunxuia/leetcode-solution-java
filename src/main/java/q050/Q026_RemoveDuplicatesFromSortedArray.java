package q050;

import org.junit.Assert;
import org.junit.runner.RunWith;
import util.asserthelper.AssertUtils;
import util.asserthelper.ObjectEqualsHelper;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 *
 * Given a sorted array nums, remove the duplicates in-place such that each element appear only once and return the
 * new length.
 *
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1)
 * extra memory.
 *
 * Example 1:
 *
 * Given nums = [1,1,2],
 *
 * Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively.
 *
 * It doesn't matter what you leave beyond the returned length.
 * Example 2:
 *
 * Given nums = [0,0,1,1,1,2,2,3,3,4],
 *
 * Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4
 * respectively.
 *
 * It doesn't matter what values are set beyond the returned length.
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
public class Q026_RemoveDuplicatesFromSortedArray {

    @Answer
    public int removeDuplicates(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }
        int cur = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] != nums[i]) {
                nums[++cur] = nums[i];
            }
        }
        return cur + 1;
    }

    @TestData
    public DataExpectation example1() {
        int[] input = new int[]{1, 1, 2};
        return DataExpectation.builder()
                .addArgument(input)
                .expect(2)
                .assertMethod((expected, actual) -> {
                    Assert.assertEquals(2, actual);
                    ObjectEqualsHelper helper = new ObjectEqualsHelper();
                    helper.check("[0]").check("[1]");
                    AssertUtils.assertEquals(new int[]{1, 2, 'x'}, input, helper);
                })
                .build();
    }

    @TestData
    public DataExpectation example2() {
        int[] input = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        return DataExpectation.builder()
                .addArgument(input)
                .expect(5)
                .assertMethod((expected, actual) -> {
                    Assert.assertEquals(5, actual);
                    ObjectEqualsHelper helper = new ObjectEqualsHelper();
                    helper.check("[0]").check("[1]").check("[2]").check("[3]").check("[4]");
                    AssertUtils.assertEquals(new int[]{0, 1, 2, 3, 4, 'x', 'x', 'x', 'x', 'x'}, input, helper);
                })
                .build();
    }
}
