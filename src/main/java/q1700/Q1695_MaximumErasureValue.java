package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1695. Maximum Erasure Value
 * https://leetcode.com/problems/maximum-erasure-value/
 *
 * You are given an array of positive integers nums and want to erase a subarray containing unique elements. The score
 * you get by erasing the subarray is equal to the sum of its elements.
 *
 * Return the maximum score you can get by erasing exactly one subarray.
 *
 * An array b is called to be a subarray of a if it forms a contiguous subsequence of a, that is, if it is equal to
 * a[l],a[l+1],...,a[r] for some (l,r).
 *
 * Example 1:
 *
 * Input: nums = [4,2,4,5,6]
 * Output: 17
 * Explanation: The optimal subarray here is [2,4,5,6].
 *
 * Example 2:
 *
 * Input: nums = [5,2,1,2,5,2,1,2,5]
 * Output: 8
 * Explanation: The optimal subarray here is [5,2,1] or [1,2,5].
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1695_MaximumErasureValue {

    @Answer
    public int maximumUniqueSubarray(int[] nums) {
        int res = 0, sum = 0;
        int left = 0, right = 0;
        boolean[] exists = new boolean[10001];
        while (right < nums.length) {
            if (exists[nums[right]]) {
                exists[nums[left]] = false;
                sum -= nums[left];
                left++;
            } else {
                exists[nums[right]] = true;
                sum += nums[right];
                res = Math.max(res, sum);
                right++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{4, 2, 4, 5, 6}).expect(17);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{5, 2, 1, 2, 5, 2, 1, 2, 5}).expect(8);

}
