package q750;

import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-pivot-index/
 *
 * Given an array of integers nums, write a method that returns the "pivot" index of this array.
 *
 * We define the pivot index as the index where the sum of the numbers to the left of the index is equal to the sum
 * of the numbers to the right of the index.
 *
 * If no such index exists, we should return -1. If there are multiple pivot indexes, you should return the left-most
 * pivot index.
 *
 * Example 1:
 *
 * Input:
 * nums = [1, 7, 3, 6, 5, 6]
 * Output: 3
 * Explanation:
 * The sum of the numbers to the left of index 3 (nums[3] = 6) is equal to the sum of numbers to the right of index 3.
 * Also, 3 is the first index where this occurs.
 *
 *
 *
 * Example 2:
 *
 * Input:
 * nums = [1, 2, 3]
 * Output: -1
 * Explanation:
 * There is no index that satisfies the conditions in the problem statement.
 *
 *
 *
 * Note:
 *
 * The length of nums will be in the range [0, 10000].
 * Each element nums[i] will be an integer in the range [-1000, 1000].
 *
 * 题解: 找出下标i, i 左右的数字之和相等(都不包括i).
 */
@RunWith(LeetCodeRunner.class)
public class Q724_FindPivotIndex {

    @Answer
    public int pivotIndex(int[] nums) {
        final int n = nums.length;
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }

        for (int i = 0; i < n; i++) {
            if (sums[i] == sums[n] - sums[i + 1]) {
                return i;
            }
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 7, 3, 6, 5, 6}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 3}).expect(-1);

    @TestData
    public List<DataExpectation> examples = Arrays.asList(example1, example2);

    @TestData
    public DataExpectation border = DataExpectation.create(new int[0]).expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{-1, -1, -1, -1, -1, 0}).expect(2);

}
