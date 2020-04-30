package q600;

import org.junit.runner.RunWith;
import q550.Q503_NextGreaterElementII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.UsingTestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/next-greater-element-iii/
 *
 * Given a positive 32-bit integer n, you need to find the smallest 32-bit integer which has exactly the same digits
 * existing in the integer n and is greater in value than n. If no such positive 32-bit integer exists, you need to
 * return -1.
 *
 * Example 1:
 *
 * Input: 12
 * Output: 21
 *
 *
 *
 * Example 2:
 *
 * Input: 21
 * Output: -1
 *
 * 上一题 {@link Q503_NextGreaterElementII}
 */
@RunWith(LeetCodeRunner.class)
public class Q556_NextGreaterElementIII {

    /**
     * 找出本位小于低1位数字的位置, 然后从低位中找出比它大的最小数,
     * 替换到本位, 其余数字按顺序排列即可.
     */
    @UsingTestData("normal2")
    @Answer
    public int nextGreaterElement(int n) {
        // 采用桶排序找出本位小于低1位数字的位置和为低位的各个数字排序
        int[] sort = new int[10];
        long res = n, digit = 1;
        int last;
        do {
            last = (int) res % 10;
            sort[last]++;
            digit *= 10;
            res /= 10;
        } while (res % 10 >= last);
        if (res == 0) {
            return -1;
        }
        last = (int) res % 10;
        sort[last]++;
        // 高位的数字不用动
        res = (res - last) * digit;

        // 找出低位中比要转换的位的数字大的最小数字
        int i = last + 1;
        while (sort[i] == 0) {
            i++;
        }
        res += i * digit;
        sort[i]--;
        digit /= 10;
        // 其余低位按照从小到大的顺序排列
        for (int idx = 0; idx < 10; idx++) {
            while (sort[idx] > 0) {
                res += digit * idx;
                sort[idx]--;
                digit /= 10;
            }
        }
        return res > Integer.MAX_VALUE ? -1 : (int) res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(12).expect(21);

    @TestData
    public DataExpectation example2 = DataExpectation.create(21).expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(5798).expect(5879);

    // 9199999999 超过了int 范围, 所以是-1
    @TestData
    public DataExpectation normal2 = DataExpectation.create(1999999999).expect(-1);

}
