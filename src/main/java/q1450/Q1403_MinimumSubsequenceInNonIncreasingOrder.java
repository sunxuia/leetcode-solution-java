package q1450;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1403. Minimum Subsequence in Non-Increasing Order
 * https://leetcode.com/problems/minimum-subsequence-in-non-increasing-order/
 *
 * Given the array nums, obtain a subsequence of the array whose sum of elements is strictly greater than the sum of the
 * non included elements in such subsequence.
 *
 * If there are multiple solutions, return the subsequence with minimum size and if there still exist multiple
 * solutions, return the subsequence with the maximum total sum of all its elements. A subsequence of an array can be
 * obtained by erasing some (possibly zero) elements from the array.
 *
 * Note that the solution with the given constraints is guaranteed to be unique. Also return the answer sorted in
 * non-increasing order.
 *
 * Example 1:
 *
 * Input: nums = [4,3,10,9,8]
 * Output: [10,9]
 * Explanation: The subsequences [10,9] and [10,8] are minimal such that the sum of their elements is strictly greater
 * than the sum of elements not included, however, the subsequence [10,9] has the maximum total sum of its elements.
 *
 * Example 2:
 *
 * Input: nums = [4,4,7,6,7]
 * Output: [7,7,6]
 * Explanation: The subsequence [7,7] has the sum of its elements equal to 14 which is not strictly greater than the sum
 * of elements not included (14 = 4 + 4 + 6). Therefore, the subsequence [7,6,7] is the minimal satisfying the
 * conditions. Note the subsequence has to returned in non-decreasing order.
 *
 * Example 3:
 *
 * Input: nums = [6]
 * Output: [6]
 *
 * Constraints:
 *
 * 1 <= nums.length <= 500
 * 1 <= nums[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1403_MinimumSubsequenceInNonIncreasingOrder {

    /**
     * 做法1, 时间复杂度 O(NlogN)
     */
    @Answer
    public List<Integer> minSubsequence(int[] nums) {
        Arrays.sort(nums);
        int left = Arrays.stream(nums).sum();
        int right = 0;
        List<Integer> res = new ArrayList<>();
        for (int i = nums.length - 1; i >= 0 && left >= right; i--) {
            left -= nums[i];
            right += nums[i];
            res.add(nums[i]);
        }
        return res;
    }

    /**
     * 另一种做法, 使用桶排序, 时间复杂度 O(N)
     */
    @Answer
    public List<Integer> minSubsequence2(int[] nums) {
        int[] bucket = new int[101];
        int left = 0, right = 0;
        for (int num : nums) {
            bucket[num]++;
            left += num;
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 100; i > 0 && left >= right; i--) {
            for (int j = bucket[i]; j > 0 && left >= right; j--) {
                right += i;
                left -= i;
                res.add(i);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{4, 3, 10, 9, 8}).expect(List.of(10, 9));

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{4, 4, 7, 6, 7}).expect(List.of(7, 7, 6));

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{6}).expect(List.of(6));

}
