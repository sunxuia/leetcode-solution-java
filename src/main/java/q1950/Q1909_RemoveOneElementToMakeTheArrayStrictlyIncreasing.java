package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1909. Remove One Element to Make the Array Strictly Increasing
 * https://leetcode.com/problems/remove-one-element-to-make-the-array-strictly-increasing/
 *
 * Given a 0-indexed integer array nums, return true if it can be made strictly increasing after removing exactly one
 * element, or false otherwise. If the array is already strictly increasing, return true.
 *
 * The array nums is strictly increasing if nums[i - 1] < nums[i] for each index (1 <= i < nums.length).
 *
 * Example 1:
 *
 * Input: nums = [1,2,10,5,7]
 * Output: true
 * Explanation: By removing 10 at index 2 from nums, it becomes [1,2,5,7].
 * [1,2,5,7] is strictly increasing, so return true.
 *
 * Example 2:
 *
 * Input: nums = [2,3,1,2]
 * Output: false
 * Explanation:
 * [3,1,2] is the result of removing the element at index 0.
 * [2,1,2] is the result of removing the element at index 1.
 * [2,3,2] is the result of removing the element at index 2.
 * [2,3,1] is the result of removing the element at index 3.
 * No resulting array is strictly increasing, so return false.
 *
 * Example 3:
 *
 * Input: nums = [1,1,1]
 * Output: false
 * Explanation: The result of removing any element is [1,1].
 * [1,1] is not strictly increasing, so return false.
 *
 * Constraints:
 *
 * 2 <= nums.length <= 1000
 * 1 <= nums[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1909_RemoveOneElementToMakeTheArrayStrictlyIncreasing {

    @Answer
    public boolean canBeIncreasing(int[] nums) {
        final int n = nums.length;
        boolean remove = nums[0] >= nums[1];
        for (int i = 2; i < n; i++) {
            if (nums[i - 1] >= nums[i]) {
                if (remove) {
                    return false;
                }
                remove = true;
                // 判断是否 i 是山谷, 否则 i-1 就是山峰
                if (nums[i - 2] >= nums[i]) {
                    // i 是山谷的话, 要再将nums[i-1] 与 nums[i+1] 进行比较
                    nums[i] = nums[i - 1];
                }
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 10, 5, 7}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 3, 1, 2}).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 1, 1}).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 2, 3}).expect(true);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{2, 3, 1, 4}).expect(true);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new int[]{2, 1}).expect(true);

    @TestData
    public DataExpectation normal4 = DataExpectation.create(new int[]{2, 3, 4, 5, 1}).expect(true);

    @TestData
    public DataExpectation normal5 = DataExpectation.create(new int[]{1, 4, 1, 2, 3}).expect(false);

}
