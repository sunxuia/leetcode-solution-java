package q1750;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1711. Count Good Meals
 * https://leetcode.com/problems/count-good-meals/
 *
 * A good meal is a meal that contains exactly two different food items with a sum of deliciousness equal to a power of
 * two.
 *
 * You can pick any two different foods to make a good meal.
 *
 * Given an array of integers deliciousness where deliciousness[i] is the deliciousness of the ith item of
 * food, return the number of different good meals you can make from this list modulo 10^9 + 7.
 *
 * Note that items with different indices are considered different even if they have the same deliciousness value.
 *
 * Example 1:
 *
 * Input: deliciousness = [1,3,5,7,9]
 * Output: 4
 * Explanation: The good meals are (1,3), (1,7), (3,5) and, (7,9).
 * Their respective sums are 4, 8, 8, and 16, all of which are powers of 2.
 *
 * Example 2:
 *
 * Input: deliciousness = [1,1,1,3,3,3,7]
 * Output: 15
 * Explanation: The good meals are (1,1) with 3 ways, (1,3) with 9 ways, and (1,7) with 3 ways.
 *
 * Constraints:
 *
 * 1 <= deliciousness.length <= 10^5
 * 0 <= deliciousness[i] <= 2^20
 *
 * 题解: 从 deliciousness 找出 2 个数的组合, 让这 2 个数的和是 2 的冥, 求这种组合的数量.
 */
@RunWith(LeetCodeRunner.class)
public class Q1711_CountGoodMeals {

    @Answer
    public int countPairs(int[] deliciousness) {
        final int mod = 10_0000_0007;
        Map<Integer, Integer> nums = new HashMap<>();
        int res = 0;
        for (int num : deliciousness) {
            for (int i = 0; i <= 21; i++) {
                int expect = (1 << i) - num;
                if (nums.containsKey(expect)) {
                    res = (res + nums.get(expect)) % mod;
                }
            }
            nums.put(num, nums.getOrDefault(num, 0) + 1);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 3, 5, 7, 9}).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 1, 3, 3, 3, 7}).expect(15);

    @TestData
    public DataExpectation overflow() {
        int[] nums = new int[10_0000];
        Arrays.fill(nums, 32);
        return DataExpectation.create(nums).expect(999949972);
    }

}
