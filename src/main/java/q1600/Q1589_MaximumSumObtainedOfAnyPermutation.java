package q1600;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1589. Maximum Sum Obtained of Any Permutation
 * https://leetcode.com/problems/maximum-sum-obtained-of-any-permutation/
 *
 * We have an array of integers, nums, and an array of requests where requests[i] = [starti, endi]. The ith request asks
 * for the sum of nums[starti] + nums[starti + 1] + ... + nums[endi - 1] + nums[endi]. Both starti and endi are
 * 0-indexed.
 *
 * Return the maximum total sum of all requests among all permutations of nums.
 *
 * Since the answer may be too large, return it modulo 109 + 7.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4,5], requests = [[1,3],[0,1]]
 * Output: 19
 * Explanation: One permutation of nums is [2,1,3,4,5] with the following result:
 * requests[0] -> nums[1] + nums[2] + nums[3] = 1 + 3 + 4 = 8
 * requests[1] -> nums[0] + nums[1] = 2 + 1 = 3
 * Total sum: 8 + 3 = 11.
 * A permutation with a higher total sum is [3,5,4,2,1] with the following result:
 * requests[0] -> nums[1] + nums[2] + nums[3] = 5 + 4 + 2 = 11
 * requests[1] -> nums[0] + nums[1] = 3 + 5  = 8
 * Total sum: 11 + 8 = 19, which is the best that you can do.
 *
 * Example 2:
 *
 * Input: nums = [1,2,3,4,5,6], requests = [[0,1]]
 * Output: 11
 * Explanation: A permutation with the max total sum is [6,5,4,3,2,1] with request sums [11].
 *
 * Example 3:
 *
 * Input: nums = [1,2,3,4,5,10], requests = [[0,2],[1,3],[1,1]]
 * Output: 47
 * Explanation: A permutation with the max total sum is [4,10,5,3,2,1] with request sums [19,18,10].
 *
 * Constraints:
 *
 * n == nums.length
 * 1 <= n <= 10^5
 * 0 <= nums[i] <= 10^5
 * 1 <= requests.length <= 10^5
 * requests[i].length == 2
 * 0 <= starti <= endi < n
 */
@RunWith(LeetCodeRunner.class)
public class Q1589_MaximumSumObtainedOfAnyPermutation {

    @Answer
    public int maxSumRangeQuery(int[] nums, int[][] requests) {
        final int mod = 10_0000_0007;
        final int n = nums.length;
        int[] counts = new int[n + 1];
        for (int[] request : requests) {
            counts[request[0]]++;
            counts[request[1] + 1]--;
        }
        for (int i = 1; i < n; i++) {
            counts[i] += counts[i - 1];
        }
        Arrays.sort(counts);
        Arrays.sort(nums);
        long res = 0;
        for (int i = n - 1; i >= 0; i--) {
            res += (long) counts[i + 1] * nums[i] % mod;
        }
        return (int) (res % mod);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5}, new int[][]{{1, 3}, {0, 1}})
            .expect(19);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5, 6}, new int[][]{{0, 1}})
            .expect(11);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5, 10}, new int[][]{{0, 2}, {1, 3}, {1, 1}})
            .expect(47);

}
