package q150;

import org.junit.runner.RunWith;
import q300.Q260_SingleNumberIII;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/single-number-ii/
 *
 * Given a non-empty array of integers, every element appears three times except for one, which appears exactly once.
 * Find that single one.
 *
 * Note:
 *
 * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
 *
 * Example 1:
 *
 * Input: [2,2,3,2]
 * Output: 3
 *
 * Example 2:
 *
 * Input: [0,1,0,1,0,1,99]
 * Output: 99
 *
 * 题解: 相比上题出现的次数由2 变成了3 次.
 *
 * 输入的nums 是非空的, 其中除了1 个元素只出现1 次外每个元素都会出现3 次, 现在要找出这个元素.
 * 题设要求 O(n) 时间复杂度和 O(1) 的空间复杂度.
 *
 * 相关题目: {@link Q260_SingleNumberIII}
 */
@RunWith(LeetCodeRunner.class)
public class Q137_SingleNumberII {

    @Answer
    public int singleNumber(int[] nums) {
        int a = 0, b = 0;
        for (int num : nums) {
            a = (a ^ num) & ~b;
            b = (b ^ num) & ~a;
        }
        return a;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 2, 3, 2}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{0, 1, 0, 1, 0, 1, 99}).expect(99);

}
