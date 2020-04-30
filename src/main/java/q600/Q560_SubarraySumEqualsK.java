package q600;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import q550.Q519_RandomFlipMatrix;
import q550.Q523_ContinuousSubarraySum;
import q550.Q525_ContiguousArray;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/subarray-sum-equals-k/
 *
 * Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum
 * equals to k.
 *
 * Example 1:
 *
 * Input:nums = [1,1,1], k = 2
 * Output: 2
 *
 * Note:
 *
 * The length of the array is in range [1, 20,000].
 * The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].
 */
@RunWith(LeetCodeRunner.class)
public class Q560_SubarraySumEqualsK {

    /**
     * 解法类似
     * {@link Q525_ContiguousArray},
     * {@link Q523_ContinuousSubarraySum},
     * {@link Q519_RandomFlipMatrix}
     */
    @Answer
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int res = 0, total = 0;
        for (int num : nums) {
            total += num;
            res += map.getOrDefault(total - k, 0);
            map.put(total, map.getOrDefault(total, 0) + 1);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[]{1, 1, 1}, 2).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 0).expect(55);

}
