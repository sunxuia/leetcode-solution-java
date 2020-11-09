package q1300;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import q850.Q846_HandOfStraights;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1296. Divide Array in Sets of K Consecutive Numbers
 * https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/
 *
 * Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into sets
 * of k consecutive numbers
 *
 * Return True if its possible otherwise return False.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,3,4,4,5,6], k = 4
 * Output: true
 * Explanation: Array can be divided into [1,2,3,4] and [3,4,5,6].
 *
 * Example 2:
 *
 * Input: nums = [3,2,1,2,3,4,3,4,5,9,10,11], k = 3
 * Output: true
 * Explanation: Array can be divided into [1,2,3] , [2,3,4] , [3,4,5] and [9,10,11].
 *
 * Example 3:
 *
 * Input: nums = [3,3,2,2,1,1], k = 3
 * Output: true
 *
 * Example 4:
 *
 * Input: nums = [1,2,3,4], k = 3
 * Output: false
 * Explanation: Each array should be divided in subarrays of size 3.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 * 1 <= k <= nums.length
 *
 * Note: This question is the same as 846: https://leetcode.com/problems/hand-of-straights/
 *
 * 相同题目 {@link Q846_HandOfStraights}
 */
@RunWith(LeetCodeRunner.class)
public class Q1296_DivideArrayInSetsOfKConsecutiveNumbers {

    @Answer
    public boolean isPossibleDivide(int[] nums, int k) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int val : nums) {
            counts.put(val, counts.getOrDefault(val, 0) + 1);
        }
        while (!counts.isEmpty()) {
            for (int val : nums) {
                if (!counts.containsKey(val - 1) && counts.containsKey(val)) {
                    for (int i = val; i < val + k; i++) {
                        Integer count = counts.get(i);
                        if (count == null) {
                            return false;
                        }
                        if (count == 1) {
                            counts.remove(i);
                        } else {
                            counts.put(i, count - 1);
                        }
                    }
                }
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 3, 4, 4, 5, 6}, 4).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{3, 2, 1, 2, 3, 4, 3, 4, 5, 9, 10, 11}, 3).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{3, 3, 2, 2, 1, 1}, 3).expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4}, 3).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{
            9, 13, 15, 23, 22, 25, 4, 4, 29, 15, 8, 23, 12, 19, 24, 17, 18, 11, 22, 24, 17, 17, 10, 23, 21, 18, 14, 18,
            7, 6, 3, 6, 19, 11, 16, 11, 12, 13, 8, 26, 17, 20, 13, 19, 22, 21, 27, 9, 20, 15, 20, 27, 8, 13, 25, 23, 22,
            15, 9, 14, 20, 10, 6, 5, 14, 12, 7, 16, 21, 18, 21, 24, 23, 10, 21, 16, 18, 16, 18, 5, 20, 19, 20, 10, 14,
            26, 2, 9, 19, 12, 28, 17, 5, 7, 25, 22, 16, 17, 21, 11
    }, 10).expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{13, 14, 15, 7, 8, 9, 20, 21, 22, 4, 5, 6}, 3).expect(true);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(new int[]{16, 21, 26, 35}, 4).expect(false);

}
