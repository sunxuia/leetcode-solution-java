package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/maximum-swap/
 *
 * Given a non-negative integer, you could swap two digits at most once to get the maximum valued number. Return the
 * maximum valued number you could get.
 *
 * Example 1:
 *
 * Input: 2736
 * Output: 7236
 * Explanation: Swap the number 2 and the number 7.
 *
 * Example 2:
 *
 * Input: 9973
 * Output: 9973
 * Explanation: No swap.
 *
 * Note:
 *
 * The given number is in the range [0, 10^8]
 */
@RunWith(LeetCodeRunner.class)
public class Q670_MaximumSwap {

    /**
     * 具体思路就是找到最大的数字, 然后找到这个数字前面(比它高的位) 中小于它的数字, 然后交换.
     * 边界条件是可能没有比它小的数字, 则要求是要有一个比它小的数字才行.
     */
    @Answer
    public int maximumSwap(int num) {
        // 最大值的位数, 最大值
        int max = 0, maxValue = 0;
        // 上一个最大值的位, 上一个最大值
        int prevMax = 0, prevMaxValue = 0;
        // 高位数中小于最大值的最高位的位数, 高位数中小于最大值的最高位数字
        int smaller = 0, smallerValue = 0;
        for (int digit = 1, n = num; n > 0; digit *= 10, n /= 10) {
            int value = n % 10;
            // 有更大的最大值, 则更新最大值记录
            if (maxValue < value) {
                // 如果上一个已经形成了 高位数字 < 低位数字, 则留作备用
                if (smaller > max) {
                    prevMax = max;
                    prevMaxValue = maxValue;
                }
                max = digit;
                maxValue = value;
            } else if (value < maxValue) {
                smaller = digit;
                smallerValue = value;
            }
        }
        // 如果最大值没有比它小的高位数, 就使用上一个最大值
        if (smaller < max) {
            max = prevMax;
            maxValue = prevMaxValue;
        }
        // 如果存在高位数小于低位数数字的情况
        if (smaller > max) {
            num += (maxValue - smallerValue) * (smaller - max);
        }
        return num;
    }

    /**
     * LeetCode 上最快的解法.
     * 通过保存最大值数字的位数(不同位数相同值则低位优先), 然后和高位比较, 找到一个不匹配的就就直接替换.
     */
    @Answer
    public int maximumSwap2(int num) {
        char[] digits = Integer.toString(num).toCharArray();
        int[] buckets = new int[10];
        for (int i = 0; i < digits.length; i++) {
            buckets[digits[i] - '0'] = i;
        }

        for (int i = 0; i < digits.length; i++) {
            // 寻找 bucket 中比值比该位数字大且位数比该位数字小的位数, 然后进行替换.
            for (int k = 9; k > digits[i] - '0'; k--) {
                if (buckets[k] > i) {
                    char tmp = digits[i];
                    digits[i] = digits[buckets[k]];
                    digits[buckets[k]] = tmp;
                    return Integer.parseInt(new String(digits));
                }
            }
        }
        return num;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(2736).expect(7236);

    @TestData
    public DataExpectation example2 = DataExpectation.create(9973).expect(9973);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(1993).expect(9913);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(98368).expect(98863);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(98004365).expect(98604305);

}
