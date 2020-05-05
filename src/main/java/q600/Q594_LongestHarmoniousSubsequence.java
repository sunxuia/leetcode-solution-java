package q600;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/longest-harmonious-subsequence/
 *
 * We define a harmounious array as an array where the difference between its maximum value and its minimum value is
 * exactly 1.
 *
 * Now, given an integer array, you need to find the length of its longest harmonious subsequence among all its
 * possible subsequences.
 *
 * Example 1:
 *
 * Input: [1,3,2,2,5,2,3,7]
 * Output: 5
 * Explanation: The longest harmonious subsequence is [3,2,2,2,3].
 *
 *
 *
 * Note: The length of the input array will not exceed 20,000.
 */
@RunWith(LeetCodeRunner.class)
public class Q594_LongestHarmoniousSubsequence {

    @Answer
    public int findLHS(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }
        int res = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            int count = entry.getValue() + counts.getOrDefault(entry.getKey() + 1, -entry.getValue());
            res = Math.max(res, count);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[]{1, 3, 2, 2, 5, 2, 3, 7}).expect(5);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{1, 1, 1, 1, 1}).expect(0);

}
