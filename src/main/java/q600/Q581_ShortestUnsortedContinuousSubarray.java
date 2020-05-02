package q600;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
 *
 * Given an integer array, you need to find one continuous subarray that if you only sort this subarray in ascending
 * order, then the whole array will be sorted in ascending order, too.
 *
 * You need to find the shortest such subarray and output its length.
 *
 * Example 1:
 *
 * Input: [2, 6, 4, 8, 10, 9, 15]
 * Output: 5
 * Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make the whole array sorted in ascending order.
 *
 * Note:
 *
 * 1. Then length of the input array is in range [1, 10,000].
 * 2. The input array may contain duplicates, so ascending order here means <=.
 */
@RunWith(LeetCodeRunner.class)
public class Q581_ShortestUnsortedContinuousSubarray {

    @Answer
    public int findUnsortedSubarray(int[] nums) {
        int[] sort = nums.clone();
        Arrays.sort(sort);
        int start = -1, end = -2;
        for (int i = 0; i < nums.length; i++) {
            if (sort[i] != nums[i]) {
                if (start == -1) {
                    start = i;
                }
                end = i;
            }
        }
        return end - start + 1;
    }

    // LeetCode 上最快的解法
    @Answer
    public int findUnsortedSubarray2(int[] nums) {
        final int n = nums.length;
        int start = -1, end = -2, min = nums[n - 1], max = nums[0];
        // 从前往后和从后往前找到最大值和最小值
        for (int i = 1; i < n; i++) {
            max = Math.max(max, nums[i]);
            min = Math.min(min, nums[n - 1 - i]);
            if (nums[i] < max) {
                end = i;
            }
            if (nums[n - 1 - i] > min) {
                start = n - 1 - i;
            }
        }
        return end - start + 1;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{2, 6, 4, 8, 10, 9, 15}).expect(5);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 2, 10, 1, 3}).expect(4);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{1, 2, 3, 4}).expect(0);

}
