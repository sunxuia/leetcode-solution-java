package q1200;

import org.junit.runner.RunWith;
import q100.Q053_MaximumSubarray;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1186. Maximum Subarray Sum with One Deletion
 * https://leetcode.com/problems/maximum-subarray-sum-with-one-deletion/
 *
 * Given an array of integers, return the maximum sum for a non-empty subarray (contiguous elements) with at most one
 * element deletion. In other words, you want to choose a subarray and optionally delete one element from it so that
 * there is still at least one element left and the sum of the remaining elements is maximum possible.
 *
 * Note that the subarray needs to be non-empty after deleting one element.
 *
 * Example 1:
 *
 * Input: arr = [1,-2,0,3]
 * Output: 4
 * Explanation: Because we can choose [1, -2, 0, 3] and drop -2, thus the subarray [1, 0, 3] becomes the maximum value.
 *
 * Example 2:
 *
 * Input: arr = [1,-2,-2,3]
 * Output: 3
 * Explanation: We just choose [3] and it's the maximum sum.
 *
 * Example 3:
 *
 * Input: arr = [-1,-1,-1,-1]
 * Output: -1
 * Explanation: The final subarray needs to be non-empty. You can't choose [-1] and delete -1 from it, then get an empty
 * subarray to make the sum equals to 0.
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^5
 * -10^4 <= arr[i] <= 10^4
 *
 * 相关题目 {@link Q053_MaximumSubarray}
 */
@RunWith(LeetCodeRunner.class)
public class Q1186_MaximumSubarraySumWithOneDeletion {

    /**
     * 参考文档 https://dongweidotblog.wordpress.com/2019/09/14/1186-maximum-subarray-sum-with-one-deletion/
     */
    @Answer
    public int maximumSum(int[] arr) {
        int res = arr[0], sum = arr[0], deletedSum = 0;
        for (int i = 1; i < arr.length; i++) {
            // 之前删除数字之和+当前数字
            res = Math.max(res, deletedSum + arr[i]);
            // 删除前面的某个数字, 还是删除当前数字结果更大
            deletedSum = Math.max(deletedSum + arr[i], sum);
            // sum 去掉负数影响
            sum = Math.max(sum, 0) + arr[i];
            res = Math.max(res, sum);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, -2, 0, 3}).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, -2, -2, 3}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{-1, -1, -1, -1}).expect(-1);

}
