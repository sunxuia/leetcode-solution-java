package q1500;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1477. Find Two Non-overlapping Sub-arrays Each With Target Sum
 * https://leetcode.com/problems/find-two-non-overlapping-sub-arrays-each-with-target-sum/
 *
 * Given an array of integers arr and an integer target.
 *
 * You have to find two non-overlapping sub-arrays of arr each with sum equal target. There can be multiple answers so
 * you have to find an answer where the sum of the lengths of the two sub-arrays is minimum.
 *
 * Return the minimum sum of the lengths of the two required sub-arrays, or return -1 if you cannot find such two
 * sub-arrays.
 *
 * Example 1:
 *
 * Input: arr = [3,2,2,4,3], target = 3
 * Output: 2
 * Explanation: Only two sub-arrays have sum = 3 ([3] and [3]). The sum of their lengths is 2.
 *
 * Example 2:
 *
 * Input: arr = [7,3,4,7], target = 7
 * Output: 2
 * Explanation: Although we have three non-overlapping sub-arrays of sum = 7 ([7], [3,4] and [7]), but we will choose
 * the first and third sub-arrays as the sum of their lengths is 2.
 *
 * Example 3:
 *
 * Input: arr = [4,3,2,6,2,3,4], target = 6
 * Output: -1
 * Explanation: We have only one sub-array of sum = 6.
 *
 * Example 4:
 *
 * Input: arr = [5,5,4,4,5], target = 3
 * Output: -1
 * Explanation: We cannot find a sub-array of sum = 3.
 *
 * Example 5:
 *
 * Input: arr = [3,1,1,1,5,1,2,1], target = 3
 * Output: 3
 * Explanation: Note that sub-arrays [1,2] and [2,1] cannot be an answer because they overlap.
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^5
 * 1 <= arr[i] <= 1000
 * 1 <= target <= 10^8
 */
@RunWith(LeetCodeRunner.class)
public class Q1477_FindTwoNonOverlappingSubArraysEachWithTargetSum {

    @Answer
    public int minSumOfLengths(int[] arr, int target) {
        final int n = arr.length;
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + arr[i];
        }

        int res = n + 1;
        // lens[i] 保存在i 之前(不包含)的最短的子数组长度.
        int[] lens = new int[n + 1];
        // 哨兵
        lens[0] = n + 1;
        for (int i = 0; i < n; i++) {
            // 获取以i 结尾的子数组的长度
            int expect = sums[i + 1] - target;
            int prev = Arrays.binarySearch(sums, 0, i + 1, expect);
            if (prev >= 0) {
                res = Math.min(res, i - prev + 1 + lens[prev]);
                lens[i + 1] = Math.min(lens[i], i - prev + 1);
            } else {
                lens[i + 1] = lens[i];
            }
        }
        return res > n ? -1 : res;
    }

    /**
     * 滑动窗口求和的做法, 优化了上面的二分查找的方式.
     */
    @Answer
    public int minSumOfLengths2(int[] arr, int target) {
        final int n = arr.length;
        int res = n + 1;
        // lens[i] 保存在i 之前(不包含)的最短的子数组长度.
        int[] lens = new int[n + 2];
        lens[0] = n + 1;
        int left = 0, right = 0, sum = 0;
        while (left < n) {
            while (right < n && sum < target) {
                lens[right + 1] = lens[right];
                sum += arr[right++];
            }
            if (sum == target) {
                res = Math.min(res, right - left + lens[left]);
                lens[right] = Math.min(lens[right - 1], right - left);
            }
            sum -= arr[left++];
        }
        return res > n ? -1 : res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{3, 2, 2, 4, 3}, 3).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{7, 3, 4, 7}, 7).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{4, 3, 2, 6, 2, 3, 4}, 6).expect(-1);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(new int[]{5, 5, 4, 4, 5}, 3).expect(-1);

    @TestData
    public DataExpectation example5 = DataExpectation.createWith(new int[]{3, 1, 1, 1, 5, 1, 2, 1}, 3).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{1, 1, 1, 2, 2, 2, 4, 4}, 6).expect(6);

}
