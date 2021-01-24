package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1574. Shortest Subarray to be Removed to Make Array Sorted
 * https://leetcode.com/problems/shortest-subarray-to-be-removed-to-make-array-sorted/
 *
 * Given an integer array arr, remove a subarray (can be empty) from arr such that the remaining elements in arr are
 * non-decreasing.
 *
 * A subarray is a contiguous subsequence of the array.
 *
 * Return the length of the shortest subarray to remove.
 *
 * Example 1:
 *
 * Input: arr = [1,2,3,10,4,2,3,5]
 * Output: 3
 * Explanation: The shortest subarray we can remove is [10,4,2] of length 3. The remaining elements after that will be
 * [1,2,3,3,5] which are sorted.
 * Another correct solution is to remove the subarray [3,10,4].
 *
 * Example 2:
 *
 * Input: arr = [5,4,3,2,1]
 * Output: 4
 * Explanation: Since the array is strictly decreasing, we can only keep a single element. Therefore we need to remove a
 * subarray of length 4, either [5,4,3,2] or [4,3,2,1].
 *
 * Example 3:
 *
 * Input: arr = [1,2,3]
 * Output: 0
 * Explanation: The array is already non-decreasing. We do not need to remove any elements.
 *
 * Example 4:
 *
 * Input: arr = [1]
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^5
 * 0 <= arr[i] <= 10^9
 *
 * 题解: 非递减的要求是所有元素满足 arr[i] <= arr[i+1]; 子数组要求是个连续区间, 可以为空.
 */
@RunWith(LeetCodeRunner.class)
public class Q1574_ShortestSubarrayToBeRemovedToMakeArraySorted {

    @Answer
    public int findLengthOfShortestSubarray(int[] arr) {
        final int n = arr.length;
        // [left, right] 是肯定要删除的区间
        int left = 1, right = n - 2;
        while (left < n && arr[left - 1] <= arr[left]) {
            left++;
        }
        if (left == n) {
            return 0;
        }
        while (left < right && arr[right] <= arr[right + 1]) {
            right--;
        }

        // 从3 个候选区间中删除最小的.
        // (farleft, right], [left, farRight1), [left-1, farRigh2)
        int farLeft = left - 1, farRight1 = right + 1, farRight2 = n;
        while (farLeft >= 0 && arr[farLeft] > arr[right + 1]) {
            farLeft--;
        }
        while (farRight1 < n && arr[left - 1] > arr[farRight1]) {
            farRight1++;
        }
        if (left >= 2) {
            farRight2 = right + 1;
            while (farRight2 < n && arr[left - 2] > arr[farRight2]) {
                farRight2++;
            }
        }
        return Math.min(right - farLeft, Math.min(farRight1 - left, farRight2 - left + 1));
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 10, 4, 2, 3, 5}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{5, 4, 3, 2, 1}).expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3}).expect(0);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{1}).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[]{6, 3, 10, 11, 15, 20, 13, 3, 18, 12})
            .expect(8);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[]{10, 13, 17, 21, 15, 15, 9, 17, 22, 22, 13})
            .expect(7);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .create(new int[]{6, 11, 20, 20, 7, 22, 22, 22, 6, 4, 9})
            .expect(7);

    @TestData
    public DataExpectation normal4 = DataExpectation.create(new int[]{
            //0  1   2  3  4   5   6   7   8   9  10  11  12  13  14  15  16 17 18  19  20  21  22  23  24  25  26  27
            14, 18, 20, 1, 54, 31, 10, 56, 4, 45, 48, 51, 52, 28, 53, 27, 0, 4, 44, 13, 14, 13, 52, 38, 27, 23, 41, 35
    }).expect(24);

    @TestData
    public DataExpectation normal5 = DataExpectation.create(new int[]{1, 2, 3, 10, 0, 7, 8, 9}).expect(2);

}
