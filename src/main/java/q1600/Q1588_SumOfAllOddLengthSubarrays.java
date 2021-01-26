package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1588. Sum of All Odd Length Subarrays
 * https://leetcode.com/problems/sum-of-all-odd-length-subarrays/
 *
 * Given an array of positive integers arr, calculate the sum of all possible odd-length subarrays.
 *
 * A subarray is a contiguous subsequence of the array.
 *
 * Return the sum of all odd-length subarrays of arr.
 *
 * Example 1:
 *
 * Input: arr = [1,4,2,5,3]
 * Output: 58
 * Explanation: The odd-length subarrays of arr and their sums are:
 * [1] = 1
 * [4] = 4
 * [2] = 2
 * [5] = 5
 * [3] = 3
 * [1,4,2] = 7
 * [4,2,5] = 11
 * [2,5,3] = 10
 * [1,4,2,5,3] = 15
 * If we add all these together we get 1 + 4 + 2 + 5 + 3 + 7 + 11 + 10 + 15 = 58
 *
 * Example 2:
 *
 * Input: arr = [1,2]
 * Output: 3
 * Explanation: There are only 2 subarrays of odd length, [1] and [2]. Their sum is 3.
 *
 * Example 3:
 *
 * Input: arr = [10,11,12]
 * Output: 66
 *
 * Constraints:
 *
 * 1 <= arr.length <= 100
 * 1 <= arr[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1588_SumOfAllOddLengthSubarrays {

    @Answer
    public int sumOddLengthSubarrays(int[] arr) {
        final int n = arr.length;
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + arr[i];
        }
        int res = 0;
        for (int len = 1; len <= n; len += 2) {
            for (int i = 0; i <= n - len; i++) {
                res += sums[i + len] - sums[i];
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 4, 2, 5, 3}).expect(58);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{10, 11, 12}).expect(66);

}
