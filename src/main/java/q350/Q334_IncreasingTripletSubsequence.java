package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/increasing-triplet-subsequence/
 *
 * Given an unsorted array return whether an increasing subsequence of length 3 exists or not in the array.
 *
 * Formally the function should:
 *
 * Return true if there exists i, j, k
 * such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return false.
 *
 * Note: Your algorithm should run in O(n) time complexity and O(1) space complexity.
 *
 * Example 1:
 *
 * Input: [1,2,3,4,5]
 * Output: true
 *
 * Example 2:
 *
 * Input: [5,4,3,2,1]
 * Output: false
 */
@RunWith(LeetCodeRunner.class)
public class Q334_IncreasingTripletSubsequence {

    // 题目要求 O(n) 的时间复杂度和 O(1) 的空间复杂度
    @Answer
    public boolean increasingTriplet(int[] nums) {
        int min1 = 0, min2 = 0;
        for (int i = 1; i < nums.length; i++) {
            if (min2 == 0) {
                // 没有min2 的情况
                if (nums[min1] < nums[i]) {
                    min2 = i;
                } else {
                    min1 = i;
                }
            } else {
                // 有 2 个递增的情况
                if (nums[min2] < nums[i]) {
                    return true;
                } else if (nums[min1] < nums[i] && nums[min2] > nums[i]) {
                    min2 = i;
                } else if (nums[min1] > nums[i]) {
                    // 这里设置了min1, 则之后的值 :
                    // 如果 > nums[min2] 则符合条件, 返回 true.
                    // 如果介于 (nums[min1], nums[min2]), 则min2 = i, min1 和min2 构成序列
                    // 如果 < nums[min1] 则继续进入当前分支, 重新设置 min1
                    min1 = i;
                }
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 4, 5}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{5, 4, 3, 2, 1}).expect(false);

}
