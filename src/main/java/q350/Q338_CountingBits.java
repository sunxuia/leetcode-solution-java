package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/counting-bits/
 *
 * Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num calculate the number of 1's
 * in their binary representation and return them as an array.
 *
 * Example 1:
 *
 * Input: 2
 * Output: [0,1,1]
 *
 * Example 2:
 *
 * Input: 5
 * Output: [0,1,1,2,1,2]
 *
 * Follow up:
 *
 * It is very easy to come up with a solution with run time O(n*sizeof(integer)). But can you do it in linear
 * time O(n) /possibly in a single pass?
 * Space complexity should be O(n).
 * Can you do it like a boss? Do it without using any builtin function like __builtin_popcount in c++ or in any
 * other language.
 *
 * Hint 1 :
 * You should make use of what you have produced already.
 * Hint 2 :
 * Divide the numbers in ranges like [2-3], [4-7], [8-15] and so on. And try to generate new range from previous.
 * Hint 3 :
 * Or does the odd/even status of the number help you in calculating the number of 1s?
 */
@RunWith(LeetCodeRunner.class)
public class Q338_CountingBits {

    // 题目要求时间复杂度 O(N) 一遍过, 不能遍历int 的位, 空间复杂度 O(n), 且不要使用的内置函数功能
    // 按提示通过范围的方式来进行计算.
    @Answer
    public int[] countBits(int num) {
        int[] res = new int[num + 1];
        int range = 1, remains = range;
        for (int i = 1; i <= num; i++) {
            res[i] = 1 + res[i - range];
            if (--remains == 0) {
                range *= 2;
                remains = range;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(2).expect(new int[]{0, 1, 1});

    @TestData
    public DataExpectation example2 = DataExpectation.create(5).expect(new int[]{0, 1, 1, 2, 1, 2});

}
