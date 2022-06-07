package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1929. Concatenation of Array
 * https://leetcode.com/problems/concatenation-of-array/
 *
 * Given an integer array nums of length n, you want to create an array ans of length 2n where ans[i] == nums[i] and
 * ans[i + n] == nums[i] for 0 <= i < n (0-indexed).
 *
 * Specifically, ans is the concatenation of two nums arrays.
 *
 * Return the array ans.
 *
 * Example 1:
 *
 * Input: nums = [1,2,1]
 * Output: [1,2,1,1,2,1]
 * Explanation: The array ans is formed as follows:
 * - ans = [nums[0],nums[1],nums[2],nums[0],nums[1],nums[2]]
 * - ans = [1,2,1,1,2,1]
 *
 * Example 2:
 *
 * Input: nums = [1,3,2,1]
 * Output: [1,3,2,1,1,3,2,1]
 * Explanation: The array ans is formed as follows:
 * - ans = [nums[0],nums[1],nums[2],nums[3],nums[0],nums[1],nums[2],nums[3]]
 * - ans = [1,3,2,1,1,3,2,1]
 *
 * Constraints:
 *
 * n == nums.length
 * 1 <= n <= 1000
 * 1 <= nums[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1929_ConcatenationOfArray {

    @Answer
    public int[] getConcatenation(int[] nums) {
        final int n = nums.length;
        int[] res = new int[2 * n];
        for (int i = 0; i < n; i++) {
            res[i] = nums[i];
            res[i + n] = nums[i];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{1, 2, 1})
            .expect(new int[]{1, 2, 1, 1, 2, 1});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{1, 3, 2, 1})
            .expect(new int[]{1, 3, 2, 1, 1, 3, 2, 1});

}
