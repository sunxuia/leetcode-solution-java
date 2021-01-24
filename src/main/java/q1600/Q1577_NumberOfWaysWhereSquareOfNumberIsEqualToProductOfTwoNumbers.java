package q1600;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1577. Number of Ways Where Square of Number Is Equal to Product of Two Numbers
 * https://leetcode.com/problems/number-of-ways-where-square-of-number-is-equal-to-product-of-two-numbers/
 *
 * Given two arrays of integers nums1 and nums2, return the number of triplets formed (type 1 and type 2) under the
 * following rules:
 *
 * Type 1: Triplet (i, j, k) if nums1[i]2 == nums2[j] * nums2[k] where 0 <= i < nums1.length and 0 <= j < k <
 * nums2.length.
 * Type 2: Triplet (i, j, k) if nums2[i]2 == nums1[j] * nums1[k] where 0 <= i < nums2.length and 0 <= j < k <
 * nums1.length.
 *
 * Example 1:
 *
 * Input: nums1 = [7,4], nums2 = [5,2,8,9]
 * Output: 1
 * Explanation: Type 1: (1,1,2), nums1[1]^2 = nums2[1] * nums2[2]. (4^2 = 2 * 8).
 *
 * Example 2:
 *
 * Input: nums1 = [1,1], nums2 = [1,1,1]
 * Output: 9
 * Explanation: All Triplets are valid, because 1^2 = 1 * 1.
 * Type 1: (0,0,1), (0,0,2), (0,1,2), (1,0,1), (1,0,2), (1,1,2).  nums1[i]^2 = nums2[j] * nums2[k].
 * Type 2: (0,0,1), (1,0,1), (2,0,1). nums2[i]^2 = nums1[j] * nums1[k].
 *
 * Example 3:
 *
 * Input: nums1 = [7,7,8,3], nums2 = [1,2,9,7]
 * Output: 2
 * Explanation: There are 2 valid triplets.
 * Type 1: (3,0,2).  nums1[3]^2 = nums2[0] * nums2[2].
 * Type 2: (3,0,1).  nums2[3]^2 = nums1[0] * nums1[1].
 *
 * Example 4:
 *
 * Input: nums1 = [4,7,9,11,23], nums2 = [3,5,1024,12,18]
 * Output: 0
 * Explanation: There are no valid triplets.
 *
 * Constraints:
 *
 * 1 <= nums1.length, nums2.length <= 1000
 * 1 <= nums1[i], nums2[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1577_NumberOfWaysWhereSquareOfNumberIsEqualToProductOfTwoNumbers {

    @Answer
    public int numTriplets(int[] nums1, int[] nums2) {
        final int m = nums1.length, n = nums2.length;
        Map<Integer, Integer> counts1 = new HashMap<>();
        Map<Integer, Integer> counts2 = new HashMap<>();
        for (int num : nums1) {
            counts1.put(num, 1 + counts1.getOrDefault(num, 0));
        }
        for (int num : nums2) {
            counts2.put(num, 1 + counts2.getOrDefault(num, 0));
        }

        int res = 0, sames = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int num = (int) ((long) nums1[i] * nums1[i] / nums2[j]);
                if (num <= nums2[j] && (long) num * nums2[j] == (long) nums1[i] * nums1[i]) {
                    Integer count = counts2.get(num);
                    if (count != null) {
                        if (num == nums2[j]) {
                            count--;
                            sames += count;
                        }
                        res += count;
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int num = (int) ((long) nums2[i] * nums2[i] / nums1[j]);
                if (num <= nums1[j] && (long) num * nums1[j] == (long) nums2[i] * nums2[i]) {
                    Integer count = counts1.get(num);
                    if (count != null) {
                        if (num == nums1[j]) {
                            count--;
                            sames += count;
                        }
                        res += count;
                    }
                }
            }
        }
        return res - sames / 2;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{7, 4}, new int[]{5, 2, 8, 9})
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 1}, new int[]{1, 1, 1})
            .expect(9);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{7, 7, 8, 3}, new int[]{1, 2, 9, 7})
            .expect(2);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{4, 7, 9, 11, 23}, new int[]{3, 5, 1024, 12, 18})
            .expect(0);

    @TestData
    public DataExpectation normal1() {
        int[] nums1 = new int[225];
        int[] nums2 = new int[260];
        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = 10_0000;
        }
        for (int i = 0; i < nums2.length; i++) {
            nums2[i] = 10_0000;
        }
        return DataExpectation.createWith(nums1, nums2).expect(14127750);
    }

}
