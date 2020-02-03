package q050;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 *
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 * Example:
 *
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
@RunWith(LeetCodeRunner.class)
public class Q001_TwoSum {

    /**
     * 这里就是通过map 的方式来实现O(n) 的时间复杂度的.
     */
    @Answer
    public int[] answer(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            Integer res = map.get(target - nums[i]);
            if (res != null) {
                return new int[]{res, i};
            }
            map.put(nums[i], i);
        }
        throw new RuntimeException("should not run to here");
    }

    @TestData
    public DataExpectation normalCase = DataExpectation.builder()
            .addArgument(new int[]{2, 7, 11, 15})
            .addArgument(9)
            .expect(new int[]{0, 1})
            .build();

    @TestData
    public DataExpectation borderCase = DataExpectation.builder()
            .addArgument(new int[]{3, 2, 4})
            .addArgument(6)
            .expect(new int[]{1, 2})
            .build();
}
