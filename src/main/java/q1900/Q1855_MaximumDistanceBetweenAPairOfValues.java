package q1900;

import java.util.function.BinaryOperator;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Medium] 1855. Maximum Distance Between a Pair of Values
 * https://leetcode.com/problems/maximum-distance-between-a-pair-of-values/
 *
 * You are given two non-increasing 0-indexed integer arrays nums1 and nums2.
 *
 * A pair of indices (i, j), where 0 <= i < nums1.length and 0 <= j < nums2.length, is valid if both i <= j and nums1[i]
 * <= nums2[j]. The distance of the pair is j - i.
 *
 * Return the maximum distance of any valid pair (i, j). If there are no valid pairs, return 0.
 *
 * An array arr is non-increasing if arr[i-1] >= arr[i] for every 1 <= i < arr.length.
 *
 * Example 1:
 *
 * Input: nums1 = [55,30,5,4,2], nums2 = [100,20,10,10,5]
 * Output: 2
 * Explanation: The valid pairs are (0,0), (2,2), (2,3), (2,4), (3,3), (3,4), and (4,4).
 * The maximum distance is 2 with pair (2,4).
 *
 * Example 2:
 *
 * Input: nums1 = [2,2,2], nums2 = [10,10,1]
 * Output: 1
 * Explanation: The valid pairs are (0,0), (0,1), and (1,1).
 * The maximum distance is 1 with pair (0,1).
 *
 * Example 3:
 *
 * Input: nums1 = [30,29,19,5], nums2 = [25,25,25,25,25]
 * Output: 2
 * Explanation: The valid pairs are (2,2), (2,3), (2,4), (3,3), and (3,4).
 * The maximum distance is 2 with pair (2,4).
 *
 * Constraints:
 *
 * 1 <= nums1.length, nums2.length <= 10^5
 * 1 <= nums1[i], nums2[j] <= 10^5
 * Both nums1 and nums2 are non-increasing.
 */
@RunWith(LeetCodeRunner.class)
public class Q1855_MaximumDistanceBetweenAPairOfValues {

    /**
     * 时间复杂度 O(NlogN)
     */
    @Answer
    public int maxDistance(int[] nums1, int[] nums2) {
        final int m = nums1.length, n = nums2.length;
        Num[] arr = new Num[n];
        for (int i = 0; i < n; i++) {
            arr[i] = new Num();
            arr[i].index = i;
            arr[i].value = nums2[i];
        }
        Arrays.sort(arr);

        arr[n - 1].right = arr[n - 1].index;
        for (int i = n - 2; i >= 0; i--) {
            arr[i].right = Math.max(arr[i].index, arr[i + 1].right);
        }

        int res = 0;
        Num dummy = new Num();
        for (int i = 0; i < m; i++) {
            dummy.value = nums1[i];
            int idx = Arrays.binarySearch(arr, dummy);
            idx = idx < 0 ? -idx - 1 : idx;
            if (idx < n) {
                res = Math.max(res, arr[idx].right - i);
            }
        }
        return res;
    }

    private static class Num implements Comparable<Num> {

        int index, value, right;

        @Override
        public int compareTo(Num o) {
            return value == o.value ? index - o.index : value - o.value;
        }
    }

    /**
     * leetcode 上最快的做法.
     * 利用了题目中已有的特性: 两个数组已经降序排列好了.
     */
    @Answer
    public int maxDistance2(int[] nums1, int[] nums2) {
        int i = 0, j = 0, res = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums2[j] >= nums1[i]) {
                // 满足题意, 扩大范围
                res = Math.max(res, j - i);
                j++;
            } else {
                // 不满足题意, i与j 同增
                i++;
                j++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{55, 30, 5, 4, 2}, new int[]{100, 20, 10, 10, 5})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{2, 2, 2}, new int[]{10, 10, 1})
            .expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{30, 29, 19, 5}, new int[]{25, 25, 25, 25, 25})
            .expect(2);

}
