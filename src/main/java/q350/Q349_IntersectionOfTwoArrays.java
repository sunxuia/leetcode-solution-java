package q350;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/intersection-of-two-arrays/
 *
 * Given two arrays, write a function to compute their intersection.
 *
 * Example 1:
 *
 * Input: nums1 = [1,2,2,1], nums2 = [2,2]
 * Output: [2]
 *
 * Example 2:
 *
 * Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * Output: [9,4]
 *
 * Note:
 *
 * Each element in the result must be unique.
 * The result can be in any order.
 *
 * 题解: 找出数组 nums1 和 nums2 中都有的元素.
 */
@RunWith(LeetCodeRunner.class)
public class Q349_IntersectionOfTwoArrays {

    @Answer
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>(nums1.length);
        for (int i : nums1) {
            set.add(i);
        }

        Set<Integer> common = new HashSet<>();
        for (int i : nums2) {
            if (set.contains(i)) {
                common.add(i);
            }
        }

        int[] res = new int[common.size()];
        int i = 0;
        for (Integer val : common) {
            res[i++] = val;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 2, 1}, new int[]{2, 2})
            .expect(new int[]{2});

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{4, 9, 5})
            .addArgument(new int[]{9, 4, 9, 8, 4})
            .expect(new int[]{9, 4})
            .unorderResult()
            .build();

}
