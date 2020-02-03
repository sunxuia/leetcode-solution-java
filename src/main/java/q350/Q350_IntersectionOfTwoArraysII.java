package q350;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/intersection-of-two-arrays-ii/
 *
 * Given two arrays, write a function to compute their intersection.
 *
 * Example 1:
 *
 * Input: nums1 = [1,2,2,1], nums2 = [2,2]
 * Output: [2,2]
 *
 * Example 2:
 *
 * Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * Output: [4,9]
 *
 * Note:
 *
 * Each element in the result should appear as many times as it shows in both arrays.
 * The result can be in any order.
 *
 * Follow up:
 *
 * What if the given array is already sorted? How would you optimize your algorithm?
 * What if nums1's size is small compared to nums2's size? Which algorithm is better?
 * What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements
 * into the memory at once?
 */
@RunWith(LeetCodeRunner.class)
public class Q350_IntersectionOfTwoArraysII {

    @Answer
    public int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums1) {
            map.put(i, 1 + map.getOrDefault(i, 0));
        }

        List<Integer> list = new ArrayList<>();
        for (int i : nums2) {
            Integer count = map.get(i);
            if (count != null) {
                list.add(i);
                if (count == 1) {
                    map.remove(i);
                } else {
                    map.put(i, count - 1);
                }
            }
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    // Follow Up 的解答:
    @Answer
    public int[] intersect_followUp(int[] nums1, int[] nums2) {
        // 准备工作
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        if (nums1.length > nums2.length) {
            int[] t = nums1;
            nums1 = nums2;
            nums2 = t;
        }

        int[] res = new int[nums1.length];
        int next = 0;
        for (int i1 = 0, i2 = 0; i1 < nums1.length && i2 < nums2.length; ) {
            if (nums1[i1] == nums2[i2]) {
                res[next++] = nums1[i1];
                i1++;
                i2++;
            } else if (nums1[i1] < nums2[i2]) {
                i1++;
            } else {
                i2++;
            }
        }

        if (next < res.length) {
            res = Arrays.copyOf(res, next);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 2, 1}, new int[]{2, 2})
            .expect(new int[]{2, 2});

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{4, 9, 5})
            .addArgument(new int[]{9, 4, 9, 8, 4})
            .expect(new int[]{9, 4})
            .unorderResult()
            .build();

}
