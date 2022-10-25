package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1984. Minimum Difference Between Highest and Lowest of K Scores
 * https://leetcode.com/problems/minimum-difference-between-highest-and-lowest-of-k-scores/
 *
 * You are given a 0-indexed integer array nums, where nums[i] represents the score of the ith student. You are also
 * given an integer k.
 *
 * Pick the scores of any k students from the array so that the difference between the highest and the lowest of the k
 * scores is minimized.
 *
 * Return the minimum possible difference.
 *
 * Example 1:
 *
 * Input: nums = [90], k = 1
 * Output: 0
 * Explanation: There is one way to pick score(s) of one student:
 * - [90]. The difference between the highest and lowest score is 90 - 90 = 0.
 * The minimum possible difference is 0.
 *
 * Example 2:
 *
 * Input: nums = [9,4,1,7], k = 2
 * Output: 2
 * Explanation: There are six ways to pick score(s) of two students:
 * - [9,4,1,7]. The difference between the highest and lowest score is 9 - 4 = 5.
 * - [9,4,1,7]. The difference between the highest and lowest score is 9 - 1 = 8.
 * - [9,4,1,7]. The difference between the highest and lowest score is 9 - 7 = 2.
 * - [9,4,1,7]. The difference between the highest and lowest score is 4 - 1 = 3.
 * - [9,4,1,7]. The difference between the highest and lowest score is 7 - 4 = 3.
 * - [9,4,1,7]. The difference between the highest and lowest score is 7 - 1 = 6.
 * The minimum possible difference is 2.
 *
 * Constraints:
 *
 * 1 <= k <= nums.length <= 1000
 * 0 <= nums[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1984_MinimumDifferenceBetweenHighestAndLowestOfKScores {

    /**
     * 桶排序, OJ 上反而比正常排序慢.
     */
    @Answer
    public int minimumDifference(int[] nums, int k) {
        int max = 0, min = 10_0000;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        int diff = max - min;
        if (k >= nums.length) {
            return diff;
        }

        int[] buckets = new int[diff + 1];
        for (int num : nums) {
            buckets[num - min]++;
        }

        int res = diff, size = 0;
        for (int i = 0, j = 0; i <= diff; i++) {
            size += buckets[i];
            while (size >= k) {
                res = Math.min(res, i - j);
                size -= buckets[j++];
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{90}, 1).expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{9, 4, 1, 7}, 2).expect(2);

}
