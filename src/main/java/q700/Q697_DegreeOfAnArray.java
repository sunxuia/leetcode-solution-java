package q700;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/degree-of-an-array/
 *
 * Given a non-empty array of non-negative integers nums, the degree of this array is defined as the maximum
 * frequency of any one of its elements.
 *
 * Your task is to find the smallest possible length of a (contiguous) subarray of nums, that has the same degree as
 * nums.
 *
 * Example 1:
 *
 * Input: [1, 2, 2, 3, 1]
 * Output: 2
 * Explanation:
 * The input array has a degree of 2 because both elements 1 and 2 appear twice.
 * Of the subarrays that have the same degree:
 * [1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
 * The shortest length is 2. So return 2.
 *
 * Example 2:
 *
 * Input: [1,2,2,3,1,4,2]
 * Output: 6
 *
 * Note:
 * nums.length will be between 1 and 50,000.
 * nums[i] will be an integer between 0 and 49,999.
 */
@RunWith(LeetCodeRunner.class)
public class Q697_DegreeOfAnArray {

    @Answer
    public int findShortestSubArray(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();
        int degree = 0;
        for (int num : nums) {
            int count = counts.getOrDefault(num, 0) + 1;
            counts.put(num, count);
            degree = Math.max(degree, count);
        }

        Map<Integer, Integer> left = new HashMap<>();
        Map<Integer, Integer> right = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int count = counts.get(nums[i]);
            if (count < degree) {
                continue;
            }
            left.putIfAbsent(nums[i], i);
            right.put(nums[i], i);
        }

        int res = nums.length;
        for (Integer num : left.keySet()) {
            res = Math.min(res, right.get(num) - left.get(num) + 1);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 2, 3, 1}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 2, 3, 1, 4, 2}).expect(6);

}
