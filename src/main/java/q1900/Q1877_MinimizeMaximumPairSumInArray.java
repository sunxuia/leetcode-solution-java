package q1900;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1877. Minimize Maximum Pair Sum in Array
 * https://leetcode.com/problems/minimize-maximum-pair-sum-in-array/
 *
 * The pair sum of a pair (a,b) is equal to a + b. The maximum pair sum is the largest pair sum in a list of pairs.
 *
 * For example, if we have pairs (1,5), (2,3), and (4,4), the maximum pair sum would be max(1+5, 2+3, 4+4) = max(6, 5,
 * 8) = 8.
 *
 * Given an array nums of even length n, pair up the elements of nums into n / 2 pairs such that:
 *
 * Each element of nums is in exactly one pair, and
 * The maximum pair sum is minimized.
 *
 * Return the minimized maximum pair sum after optimally pairing up the elements.
 *
 * Example 1:
 *
 * Input: nums = [3,5,2,3]
 * Output: 7
 * Explanation: The elements can be paired up into pairs (3,3) and (5,2).
 * The maximum pair sum is max(3+3, 5+2) = max(6, 7) = 7.
 *
 * Example 2:
 *
 * Input: nums = [3,5,4,2,4,6]
 * Output: 8
 * Explanation: The elements can be paired up into pairs (3,5), (4,4), and (6,2).
 * The maximum pair sum is max(3+5, 4+4, 6+2) = max(8, 8, 8) = 8.
 *
 * Constraints:
 *
 * n == nums.length
 * 2 <= n <= 10^5
 * n is even.
 * 1 <= nums[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1877_MinimizeMaximumPairSumInArray {

    /**
     * 时间复杂度 O(NlogN)
     */
    @Answer
    public int minPairSum(int[] nums) {
        final int n = nums.length;
        Arrays.sort(nums);
        int res = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, nums[i] + nums[n - 1 - i]);
        }
        return res;
    }

    /**
     * 桶排序, 时间复杂度 O(n)
     */
    @Answer
    public int minPairSum_bucket(int[] nums) {
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        int[] bucket = new int[max + 1];
        for (int num : nums) {
            bucket[num]++;
        }

        int i = 0, j = max, res = 0;
        for (int t = 0; t < nums.length / 2; t++) {
            while (bucket[i] == 0) {
                i++;
            }
            while (bucket[j] == 0) {
                j--;
            }
            res = Math.max(res, i + j);
            bucket[i]--;
            bucket[j]--;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 5, 2, 3}).expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{3, 5, 4, 2, 4, 6}).expect(8);

}
