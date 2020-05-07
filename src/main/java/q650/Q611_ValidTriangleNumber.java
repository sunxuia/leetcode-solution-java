package q650;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/valid-triangle-number/
 *
 * Given an array consists of non-negative integers, your task is to count the number of triplets chosen from the
 * array that can make triangles if we take them as side lengths of a triangle.
 *
 * Example 1:
 *
 * Input: [2,2,3,4]
 * Output: 3
 * Explanation:
 * Valid combinations are:
 * 2,3,4 (using the first 2)
 * 2,3,4 (using the second 2)
 * 2,2,3
 *
 * Note:
 *
 * 1. The length of the given array won't exceed 1000.
 * 2. The integers in the given array are in the range of [0, 1000].
 */
@RunWith(LeetCodeRunner.class)
public class Q611_ValidTriangleNumber {

    @Answer
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int res = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] == 0) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 1; j++) {
                int upper = Arrays.binarySearch(nums, j + 1, nums.length, nums[i] + nums[j]);
                upper = upper > 0 ? upper - 1 : -upper - 2;
                while (upper > j && nums[upper] == nums[i] + nums[j]) {
                    upper--;
                }
                res += upper - j;
            }
        }
        return res;
    }

    // LeetCode 上最快的解法, 是从最长边开始找的, 这样时间复杂度就降低为 O(NlogN) 了.
    @Answer
    public int triangleNumber2(int[] nums) {
        Arrays.sort(nums);
        int res = 0;
        for (int i = nums.length - 1; i >= 2; i--) {
            int left = 0, right = i - 1;
            while (left < right) {
                if (nums[left] + nums[right] > nums[i]) {
                    res += right - left;
                    right--;
                } else {
                    left++;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{2, 2, 3, 4}).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 1, 3, 4}).expect(0);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{82, 15, 23, 82, 67, 0, 3, 92, 11}).expect(17);

}
