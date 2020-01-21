package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/power-of-four/
 *
 * Given an integer (signed 32 bits), write a function to check whether it is a power of 4.
 *
 * Example 1:
 *
 * Input: 16
 * Output: true
 *
 * Example 2:
 *
 * Input: 5
 * Output: false
 *
 * Follow up: Could you solve it without loops/recursion?
 *
 * 上一题 {@link Q326_PowerOfThree}
 */
@RunWith(LeetCodeRunner.class)
public class Q342_PowerOfFour {

    // 找出所有4 的次方数来判断, 这个可以改写成不使用循环的形式
    @Answer
    public boolean isPowerOfFour(int num) {
        for (int i = 0x40000000; i > 0; i /= 4) {
            if (num == i) {
                return true;
            }
        }
        return false;
    }

    // 循环的做法
    @Answer
    public boolean isPowerOfFour2(int num) {
        while (num > 0 && num % 4 == 0) {
            num /= 4;
        }
        return num == 1;
    }

    // 类似 isPowerOfTwo 中的巧妙解法, 找出2 的次方数中的4 的次方数
    @Answer
    public boolean isPowerOfFour3(int num) {
        return num > 0
                && (num & (num - 1)) == 0
                && (num & 0x55555555) == num;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(16).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(5).expect(false);

    @TestData
    public DataExpectation value1 = DataExpectation.create(1).expect(true);

    @TestData
    public DataExpectation value2 = DataExpectation.create(2).expect(false);

    @TestData
    public DataExpectation value4 = DataExpectation.create(4).expect(true);

    @TestData
    public DataExpectation value8 = DataExpectation.create(8).expect(false);

    @TestData
    public DataExpectation value268435456 = DataExpectation.create(268435456).expect(true);

}
