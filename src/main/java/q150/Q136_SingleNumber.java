package q150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/single-number/
 *
 * Given a non-empty array of integers, every element appears twice except for one. Find that single one.
 *
 * Note:
 *
 * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
 *
 * Example 1:
 *
 * Input: [2,2,1]
 * Output: 1
 *
 * Example 2:
 *
 * Input: [4,1,2,1,2]
 * Output: 4
 *
 * 题解: 输入的nums 是非空的, 其中除了1 个元素只出现1 次外每个元素都会出现2 次, 现在要找出这个元素.
 * 题设要求 O(n) 时间复杂度和 O(1) 的空间复杂度.
 */
@RunWith(LeetCodeRunner.class)
public class Q136_SingleNumber {

    /**
     * 利用异或的特性
     */
    @Answer
    public int singleNumber(int[] nums) {
        int res = 0;
        for (int num : nums) {
            res ^= num;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 2, 1}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{4, 1, 2, 1, 2}).expect(4);

}
