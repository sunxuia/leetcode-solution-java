package q2050;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 2023. Number of Pairs of Strings With Concatenation Equal to Target
 * https://leetcode.com/problems/number-of-pairs-of-strings-with-concatenation-equal-to-target/
 *
 * Given an array of digit strings nums and a digit string target, return the number of pairs of indices (i, j)
 * (where i != j) such that the concatenation of nums[i] + nums[j] equals target.
 *
 * Example 1:
 *
 * Input: nums = ["777","7","77","77"], target = "7777"
 * Output: 4
 * Explanation: Valid pairs are:
 * - (0, 1): "777" + "7"
 * - (1, 0): "7" + "777"
 * - (2, 3): "77" + "77"
 * - (3, 2): "77" + "77"
 *
 * Example 2:
 *
 * Input: nums = ["123","4","12","34"], target = "1234"
 * Output: 2
 * Explanation: Valid pairs are:
 * - (0, 1): "123" + "4"
 * - (2, 3): "12" + "34"
 *
 * Example 3:
 *
 * Input: nums = ["1","1","1"], target = "11"
 * Output: 6
 * Explanation: Valid pairs are:
 * - (0, 1): "1" + "1"
 * - (1, 0): "1" + "1"
 * - (0, 2): "1" + "1"
 * - (2, 0): "1" + "1"
 * - (1, 2): "1" + "1"
 * - (2, 1): "1" + "1"
 *
 * Constraints:
 *
 * 2 <= nums.length <= 100
 * 1 <= nums[i].length <= 100
 * 2 <= target.length <= 100
 * nums[i] and target consist of digits.
 * nums[i] and target do not have leading zeros.
 */
@RunWith(LeetCodeRunner.class)
public class Q2023_NumberOfPairsOfStringsWithConcatenationEqualToTarget {

    @Answer
    public int numOfPairs(String[] nums, String target) {
        Map<String, Integer> map = new HashMap<>();
        for (String num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        int res = 0;
        for (String prefix : map.keySet()) {
            if (target.startsWith(prefix)) {
                int count = map.get(prefix);
                String suffix = target.substring(prefix.length());
                if (prefix.equals(suffix)) {
                    res += count * (count - 1);
                } else {
                    res += count * map.getOrDefault(suffix, 0);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"777", "7", "77", "77"}, "7777")
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"123", "4", "12", "34"}, "1234")
            .expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new String[]{"1", "1", "1"}, "11")
            .expect(6);

}
