package q1950;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1906. Minimum Absolute Difference Queries
 * https://leetcode.com/problems/minimum-absolute-difference-queries/
 *
 * The minimum absolute difference of an array a is defined as the minimum value of |a[i] - a[j]|, where 0 <= i < j <
 * a.length and a[i] != a[j]. If all elements of a are the same, the minimum absolute difference is -1.
 *
 * For example, the minimum absolute difference of the array [5,2,3,7,2] is |2 - 3| = 1. Note that it is not 0 because
 * a[i] and a[j] must be different.
 *
 * You are given an integer array nums and the array queries where queries[i] = [li, ri]. For each query i, compute the
 * minimum absolute difference of the subarray nums[li...ri] containing the elements of nums between the 0-based indices
 * li and ri (inclusive).
 *
 * Return an array ans where ans[i] is the answer to the ith query.
 *
 * A subarray is a contiguous sequence of elements in an array.
 *
 * The value of |x| is defined as:
 *
 * x if x >= 0.
 * -x if x < 0.
 *
 * Example 1:
 *
 * Input: nums = [1,3,4,8], queries = [[0,1],[1,2],[2,3],[0,3]]
 * Output: [2,1,4,1]
 * Explanation: The queries are processed as follows:
 * - queries[0] = [0,1]: The subarray is [1,3] and the minimum absolute difference is |1-3| = 2.
 * - queries[1] = [1,2]: The subarray is [3,4] and the minimum absolute difference is |3-4| = 1.
 * - queries[2] = [2,3]: The subarray is [4,8] and the minimum absolute difference is |4-8| = 4.
 * - queries[3] = [0,3]: The subarray is [1,3,4,8] and the minimum absolute difference is |3-4| = 1.
 *
 * Example 2:
 *
 * Input: nums = [4,5,2,2,7,10], queries = [[2,3],[0,2],[0,5],[3,5]]
 * Output: [-1,1,1,3]
 * Explanation: The queries are processed as follows:
 * - queries[0] = [2,3]: The subarray is [2,2] and the minimum absolute difference is -1 because all the
 * elements are the same.
 * - queries[1] = [0,2]: The subarray is [4,5,2] and the minimum absolute difference is |4-5| = 1.
 * - queries[2] = [0,5]: The subarray is [4,5,2,2,7,10] and the minimum absolute difference is |4-5| = 1.
 * - queries[3] = [3,5]: The subarray is [2,7,10] and the minimum absolute difference is |7-10| = 3.
 *
 * Constraints:
 *
 * 2 <= nums.length <= 10^5
 * 1 <= nums[i] <= 100
 * 1 <= queries.length <= 2 * 10^4
 * 0 <= li < ri < nums.length
 */
@RunWith(LeetCodeRunner.class)
public class Q1906_MinimumAbsoluteDifferenceQueries {

    @Answer
    public int[] minDifference(int[] nums, int[][] queries) {
        final int max = 100;
        final int m = nums.length;
        final int n = queries.length;

        int[][] buckets = new int[m + 1][];
        buckets[0] = new int[max + 1];
        for (int i = 0; i < m; i++) {
            buckets[i + 1] = buckets[i].clone();
            buckets[i + 1][nums[i]]++;
        }

        int[] res = new int[n];
        Arrays.fill(res, max);
        for (int i = 0; i < n; i++) {
            int left = queries[i][0];
            int right = queries[i][1];
            int prev = -max;
            for (int j = 1; j <= max; j++) {
                int v = buckets[right + 1][j] - buckets[left][j];
                if (v != 0) {
                    res[i] = Math.min(res[i], j - prev);
                    prev = j;
                }
            }
            if (res[i] == max) {
                res[i] = -1;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 3, 4, 8}, new int[][]{{0, 1}, {1, 2}, {2, 3}, {0, 3}})
            .expect(new int[]{2, 1, 4, 1});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{4, 5, 2, 2, 7, 10}, new int[][]{{2, 3}, {0, 2}, {0, 5}, {3, 5}})
            .expect(new int[]{-1, 1, 1, 3});

}
