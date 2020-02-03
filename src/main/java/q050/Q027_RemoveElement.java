package q050;

import org.junit.runner.RunWith;
import util.asserthelper.AssertUtils;
import util.asserthelper.ObjectEqualsHelper;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/remove-element/
 *
 * Given an array nums and a value val, remove all instances of that value in-place and return the new length.
 *
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1)
 * extra memory.
 *
 * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
 *
 * Example 1:
 *
 * Given nums = [3,2,2,3], val = 3,
 *
 * Your function should return length = 2, with the first two elements of nums being 2.
 *
 * It doesn't matter what you leave beyond the returned length.
 * Example 2:
 *
 * Given nums = [0,1,2,2,3,0,4,2], val = 2,
 *
 * Your function should return length = 5, with the first five elements of nums containing 0, 1, 3, 0, and 4.
 *
 * Note that the order of those five elements can be arbitrary.
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
 * int len = removeElement(nums, val);
 *
 * // any modification to nums in your function would be known by the caller.
 * // using the length returned by your function, it prints the first len elements.
 * for (int i = 0; i < len; i++) {
 * print(nums[i]);
 * }
 *
 * 题解:
 * 这题和上一题不一样, 这一题需要在一个无序数组(nums) 中移除指定的数字(val) 的元素, 直接在
 * 数组上修改且空间复杂度为O(1), 返回值 n 表示去除了指定数字的数组的长度, 数组的前 n 位都应
 * 当是原来的数组去掉了val 的结果. n 位以后的数字无所谓.
 */
@RunWith(LeetCodeRunner.class)
public class Q027_RemoveElement {

    @Answer
    public int removeElement(int[] nums, int val) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[res++] = nums[i];
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1() {
        int[] input = new int[]{3, 2, 2, 3};
        return DataExpectation.builder()
                .addArgument(input)
                .addArgument(3)
                .expect("return value and input match")
                .assertMethod((expected, actual) -> {
                    AssertUtils.assertEquals(2, actual);
                    ObjectEqualsHelper helper = new ObjectEqualsHelper();
                    helper.unorder("*");
                    AssertUtils.assertEquals(new int[]{2, 2}, input, 0, 2, helper);
                })
                .build();
    }

    @TestData
    public DataExpectation example2() {
        int[] input = new int[]{0, 1, 2, 2, 3, 0, 4, 2};
        return DataExpectation.builder()
                .addArgument(input)
                .addArgument(2)
                .expect("return value and input match")
                .assertMethod((expected, actual, o) -> {
                    AssertUtils.assertEquals(5, actual);
                    ObjectEqualsHelper helper = new ObjectEqualsHelper();
                    helper.unorder("*");
                    AssertUtils.assertEquals(new int[]{0, 1, 3, 0, 4}, input, 0, 5, helper);
                })
                .build();
    }
}
