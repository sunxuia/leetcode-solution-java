package q2050;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 2032. Two Out of Three
 * https://leetcode.com/problems/two-out-of-three/
 *
 * Given three integer arrays nums1, nums2, and nums3, return a distinct array containing all the values that are
 * present in at least two out of the three arrays. You may return the values in any order.
 *
 * Example 1:
 *
 * Input: nums1 = [1,1,3,2], nums2 = [2,3], nums3 = [3]
 * Output: [3,2]
 * Explanation: The values that are present in at least two arrays are:
 * - 3, in all three arrays.
 * - 2, in nums1 and nums2.
 *
 * Example 2:
 *
 * Input: nums1 = [3,1], nums2 = [2,3], nums3 = [1,2]
 * Output: [2,3,1]
 * Explanation: The values that are present in at least two arrays are:
 * - 2, in nums2 and nums3.
 * - 3, in nums1 and nums2.
 * - 1, in nums1 and nums3.
 *
 * Example 3:
 *
 * Input: nums1 = [1,2,2], nums2 = [4,3,3], nums3 = [5]
 * Output: []
 * Explanation: No value is present in at least two arrays.
 *
 * Constraints:
 *
 * 1 <= nums1.length, nums2.length, nums3.length <= 100
 * 1 <= nums1[i], nums2[j], nums3[k] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q2032_TwoOutOfThree {

    @Answer
    public List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {
        int[] map = new int[101];
        for (int num : nums1) {
            map[num] = 1;
        }
        for (int num : nums2) {
            map[num] |= 2;
        }
        for (int num : nums3) {
            map[num] |= 4;
        }
        List<Integer> res = new ArrayList<>();
        for (int num = 1; num <= 100; num++) {
            // 去掉最后一位1 的位运算
            if ((map[num] & (map[num] - 1)) != 0) {
                res.add(num);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 1, 3, 2}, new int[]{2, 3}, new int[]{3})
            .expect(List.of(3, 2)).unOrder();

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{3, 1}, new int[]{2, 3}, new int[]{1, 2})
            .expect(List.of(2, 3, 1)).unOrder();

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 2, 2}, new int[]{4, 3, 3}, new int[]{5})
            .expect(List.of()).unOrder();

}
