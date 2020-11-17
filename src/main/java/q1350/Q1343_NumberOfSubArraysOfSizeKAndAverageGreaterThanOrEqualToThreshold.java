package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1343. Number of Sub-arrays of Size K and Average Greater than or Equal to Threshold
 * https://leetcode.com/problems/number-of-sub-arrays-of-size-k-and-average-greater-than-or-equal-to-threshold/
 *
 * Given an array of integers arr and two integers k and threshold.
 *
 * Return the number of sub-arrays of size k and average greater than or equal to threshold.
 *
 * Example 1:
 *
 * Input: arr = [2,2,2,2,5,5,5,8], k = 3, threshold = 4
 * Output: 3
 * Explanation: Sub-arrays [2,5,5],[5,5,5] and [5,5,8] have averages 4, 5 and 6 respectively. All other sub-arrays of
 * size 3 have averages less than 4 (the threshold).
 *
 * Example 2:
 *
 * Input: arr = [1,1,1,1,1], k = 1, threshold = 0
 * Output: 5
 *
 * Example 3:
 *
 * Input: arr = [11,13,17,23,29,31,7,5,2,3], k = 3, threshold = 5
 * Output: 6
 * Explanation: The first 6 sub-arrays of size 3 have averages greater than 5. Note that averages are not integers.
 *
 * Example 4:
 *
 * Input: arr = [7,7,7,7,7,7,7], k = 7, threshold = 7
 * Output: 1
 *
 * Example 5:
 *
 * Input: arr = [4,4,4,4], k = 4, threshold = 1
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^5
 * 1 <= arr[i] <= 10^4
 * 1 <= k <= arr.length
 * 0 <= threshold <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1343_NumberOfSubArraysOfSizeKAndAverageGreaterThanOrEqualToThreshold {

    @Answer
    public int numOfSubarrays(int[] arr, int k, int threshold) {
        final int n = arr.length;
        int res = 0, sum = 0;
        for (int i = 0; i < k; i++) {
            sum += arr[i];
        }
        res += sum >= k * threshold ? 1 : 0;
        for (int i = k; i < n; i++) {
            sum += arr[i] - arr[i - k];
            res += sum >= k * threshold ? 1 : 0;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{2, 2, 2, 2, 5, 5, 5, 8}, 3, 4)
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 1, 1, 1, 1}, 1, 0)
            .expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{11, 13, 17, 23, 29, 31, 7, 5, 2, 3}, 3, 5)
            .expect(6);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{7, 7, 7, 7, 7, 7, 7}, 7, 7)
            .expect(1);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(new int[]{4, 4, 4, 4}, 4, 1)
            .expect(1);

}
