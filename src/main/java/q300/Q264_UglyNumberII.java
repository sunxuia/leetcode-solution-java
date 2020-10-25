package q300;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import q1250.Q1201_UglyNumberIII;
import q350.Q313_SuperUglyNumber;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/ugly-number-ii/
 *
 * Write a program to find the n-th ugly number.
 *
 * Ugly numbers are positive numbers whose prime factors only include 2, 3, 5.
 *
 * Example:
 *
 * Input: n = 10
 * Output: 12
 * Explanation: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 is the sequence of the first 10 ugly numbers.
 *
 * Note:
 *
 * 1 is typically treated as an ugly number.
 * n does not exceed 1690.
 *
 * 相关题目 {@link Q313_SuperUglyNumber}
 * 下一题 {@link Q1201_UglyNumberIII}
 */
@RunWith(LeetCodeRunner.class)
public class Q264_UglyNumberII {

    // n 序号从1 开始.
    // 网上的解法
    @Answer
    public int nthUglyNumber(int n) {
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        int i2 = 0, i3 = 0, i5 = 0;
        while (nums.size() < n) {
            int m2 = nums.get(i2) * 2, m3 = nums.get(i3) * 3, m5 = nums.get(i5) * 5;
            int min = Math.min(m2, Math.min(m3, m5));
            nums.add(min);
            if (min == m2) {
                ++i2;
            }
            if (min == m3) {
                ++i3;
            }
            if (min == m5) {
                ++i5;
            }
        }
        return nums.get(nums.size() - 1);
    }

    // LeetCode 上最快的解法, 将上面的nums 换成了ugly 数组.
    @Answer
    public int leetCode(int n) {
        int[] ugly = new int[n];
        ugly[0] = 1;
        int index2 = 0;
        int index3 = 0;
        int index5 = 0;
        int factor2 = 2;
        int factor3 = 3;
        int factor5 = 5;
        for (int i = 1; i < n; i++) {
            int min = Math.min(Math.min(factor2, factor3), factor5);
            ugly[i] = min;
            if (factor2 == min) {
                factor2 = 2 * ugly[++index2];
            }
            if (factor3 == min) {
                factor3 = 3 * ugly[++index3];
            }
            if (factor5 == min) {
                factor5 = 5 * ugly[++index5];
            }
        }
        return ugly[n - 1];
    }

    @TestData
    public DataExpectation example = DataExpectation.create(10).expect(12);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(1).expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(2).expect(2);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(3).expect(3);

    @TestData
    public DataExpectation normal4 = DataExpectation.create(4).expect(4);

    @TestData
    public DataExpectation normal7 = DataExpectation.create(7).expect(8);

}
