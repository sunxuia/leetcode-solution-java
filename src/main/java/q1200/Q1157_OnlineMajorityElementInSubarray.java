package q1200;

import org.junit.Test;
import util.asserthelper.AssertUtils;

/**
 * [Hard] 1157. Online Majority Element In Subarray
 * https://leetcode.com/problems/online-majority-element-in-subarray/
 *
 * Implementing the class MajorityChecker, which has the following API:
 *
 * MajorityChecker(int[] arr) constructs an instance of MajorityChecker with the given array arr;
 * int query(int left, int right, int threshold) has arguments such that:
 *
 * 0 <= left <= right < arr.length representing a subarray of arr;
 * 2 * threshold > right - left + 1, ie. the threshold is always a strict majority of the length of the subarray
 *
 *
 *
 * Each query(...) returns the element in arr[left], arr[left+1], ..., arr[right] that occurs at least threshold times,
 * or -1 if no such element exists.
 *
 * Example:
 *
 * MajorityChecker majorityChecker = new MajorityChecker([1,1,2,2,1,1]);
 * majorityChecker.query(0,5,4); // returns 1
 * majorityChecker.query(0,3,3); // returns -1
 * majorityChecker.query(2,3,2); // returns 2
 *
 * Constraints:
 *
 * 1 <= arr.length <= 20000
 * 1 <= arr[i] <= 20000
 * For each query, 0 <= left <= right < len(arr)
 * For each query, 2 * threshold > right - left + 1
 * The number of queries is at most 10000
 */
public class Q1157_OnlineMajorityElementInSubarray {

    private static class MajorityChecker {

        final int[] arr;

        public MajorityChecker(int[] arr) {
            this.arr = arr;
        }

        public int query(int left, int right, int threshold) {
            // 首先找出次数超过一半的数字
            int res = -1, count = 0;
            for (int i = left; i <= right; i++) {
                if (res == arr[i]) {
                    count++;
                } else if (count == 0) {
                    res = arr[i];
                    count = 1;
                } else {
                    count--;
                }
            }
            // 计算这个数字的数量是否达到了要求
            for (int i = left; i <= right; i++) {
                if (res == arr[i]) {
                    threshold--;
                }
            }
            return threshold <= 0 ? res : -1;
        }
    }

    /**
     * Your MajorityChecker object will be instantiated and called as such:
     * MajorityChecker obj = new MajorityChecker(arr);
     * int param_1 = obj.query(left,right,threshold);
     */

    @Test
    public void testMethod() {
        MajorityChecker tested = new MajorityChecker(new int[]{1, 1, 2, 2, 1, 1});
        AssertUtils.assertEquals(1, tested.query(0, 5, 4));
        AssertUtils.assertEquals(-1, tested.query(0, 3, 3));
        AssertUtils.assertEquals(2, tested.query(2, 3, 2));
    }

}
