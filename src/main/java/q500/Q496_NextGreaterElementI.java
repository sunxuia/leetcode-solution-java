package q500;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import q550.Q503_NextGreaterElementII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/next-greater-element-i/
 *
 * You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset of nums2. Find
 * all the next greater numbers for nums1's elements in the corresponding places of nums2.
 *
 * The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2. If it does not
 * exist, output -1 for this number.
 *
 * Example 1:
 *
 * Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
 * Output: [-1,3,-1]
 * Explanation:
 * For number 4 in the first array, you cannot find the next greater number for it in the second array, so output
 * -1.
 * For number 1 in the first array, the next greater number for it in the second array is 3.
 * For number 2 in the first array, there is no next greater number for it in the second array, so output -1.
 *
 * Example 2:
 *
 * Input: nums1 = [2,4], nums2 = [1,2,3,4].
 * Output: [3,-1]
 * Explanation:
 * For number 2 in the first array, the next greater number for it in the second array is 3.
 * For number 4 in the first array, there is no next greater number for it in the second array, so output -1.
 *
 * Note:
 *
 * All elements in nums1 and nums2 are unique.
 * The length of both nums1 and nums2 would not exceed 1000.
 *
 * 下一题 {@link Q503_NextGreaterElementII}
 */
@RunWith(LeetCodeRunner.class)
public class Q496_NextGreaterElementI {

    @Answer
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums2.length; i++) {
            for (int j = i + 1; j < nums2.length; j++) {
                if (nums2[j] > nums2[i]) {
                    map.put(nums2[i], nums2[j]);
                    break;
                }
            }
        }
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            res[i] = map.getOrDefault(nums1[i], -1);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2}).expect(new int[]{-1, 3, -1});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{2, 4}, new int[]{1, 2, 3, 4}).expect(new int[]{3, -1});

}
