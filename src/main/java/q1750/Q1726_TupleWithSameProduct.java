package q1750;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1726. Tuple with Same Product
 * https://leetcode.com/problems/tuple-with-same-product/
 *
 * Given an array nums of distinct positive integers, return the number of tuples (a, b, c, d) such that a * b = c * d
 * where a, b, c, and d are elements of nums, and a != b != c != d.
 *
 * Example 1:
 *
 * Input: nums = [2,3,4,6]
 * Output: 8
 * Explanation: There are 8 valid tuples:
 * (2,6,3,4) , (2,6,4,3) , (6,2,3,4) , (6,2,4,3)
 * (3,4,2,6) , (4,3,2,6) , (3,4,6,2) , (4,3,6,2)
 *
 * Example 2:
 *
 * Input: nums = [1,2,4,5,10]
 * Output: 16
 * Explanation: There are 16 valids tuples:
 * (1,10,2,5) , (1,10,5,2) , (10,1,2,5) , (10,1,5,2)
 * (2,5,1,10) , (2,5,10,1) , (5,2,1,10) , (5,2,10,1)
 * (2,10,4,5) , (2,10,5,4) , (10,2,4,5) , (10,2,4,5)
 * (4,5,2,10) , (4,5,10,2) , (5,4,2,10) , (5,4,10,2)
 *
 * Example 3:
 *
 * Input: nums = [2,3,4,6,8,12]
 * Output: 40
 *
 * Example 4:
 *
 * Input: nums = [2,3,5,7]
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= nums.length <= 1000
 * 1 <= nums[i] <= 10^4
 * All elements in nums are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1726_TupleWithSameProduct {

    @Answer
    public int tupleSameProduct(int[] nums) {
        final int n = nums.length;
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int prod = nums[i] * nums[j];
                int count = map.getOrDefault(prod, 0);
                // 4 个不同元素互换位置总共有8 种组合
                res += 8 * count;
                map.put(prod, count + 1);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 3, 4, 6}).expect(8);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 4, 5, 10}).expect(16);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{2, 3, 4, 6, 8, 12}).expect(40);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{2, 3, 5, 7}).expect(0);

}
