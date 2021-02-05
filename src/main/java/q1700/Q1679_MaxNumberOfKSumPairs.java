package q1700;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1679. Max Number of K-Sum Pairs
 * https://leetcode.com/problems/max-number-of-k-sum-pairs/
 *
 * You are given an integer array nums and an integer k.
 *
 * In one operation, you can pick two numbers from the array whose sum equals k and remove them from the array.
 *
 * Return the maximum number of operations you can perform on the array.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4], k = 5
 * Output: 2
 * Explanation: Starting with nums = [1,2,3,4]:
 * - Remove numbers 1 and 4, then nums = [2,3]
 * - Remove numbers 2 and 3, then nums = []
 * There are no more pairs that sum up to 5, hence a total of 2 operations.
 *
 * Example 2:
 *
 * Input: nums = [3,1,3,4,3], k = 6
 * Output: 1
 * Explanation: Starting with nums = [3,1,3,4,3]:
 * - Remove the first two 3's, then nums = [1,4,3]
 * There are no more pairs that sum up to 6, hence a total of 1 operation.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 * 1 <= k <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1679_MaxNumberOfKSumPairs {

    /**
     * 时间复杂度 O(n) 但是在 LeetCode 上比较慢
     */
    @Answer
    public int maxOperations(int[] nums, int k) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }

        int res = 0;
        for (int num : nums) {
            int curr = counts.get(num);
            int other = counts.getOrDefault(k - num, 0);
            if (num == k - num) {
                if (curr >= 2) {
                    res++;
                    counts.put(num, curr - 2);
                }
            } else {
                if (curr > 0 && other > 0) {
                    res++;
                    counts.put(num, curr - 1);
                    counts.put(k - num, other - 1);
                }
            }
        }
        return res;
    }

    /**
     * 另一种二分查找的方式, 时间复杂度 O(NlogN).
     * 这个在LeetCode 上比较快.
     */
    @Answer
    public int maxOperations2(int[] nums, int k) {
        final int n = nums.length;
        Arrays.sort(nums);
        int res = 0;
        int left = 0, right = n - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == k) {
                res++;
                left++;
                right--;
            } else if (sum > k) {
                right--;
            } else {
                left++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4}, 5)
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{3, 1, 3, 4, 3}, 6)
            .expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{3, 5, 1, 5}, 2)
            .expect(0);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{4, 4, 1, 3, 1, 3, 2, 2, 5, 5, 1, 5, 2, 1, 2, 3, 5, 4}, 2)
            .expect(2);

}
