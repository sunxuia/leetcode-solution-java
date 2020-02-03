package q250;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/happy-number/
 *
 * Write an algorithm to determine if a number is "happy".
 *
 * A happy number is a number defined by the following process: Starting with any positive integer, replace the
 * number by the sum of the squares of its digits, and repeat the process until the number equals 1 (where it will
 * stay), or it loops endlessly in a cycle which does not include 1. Those numbers for which this process ends in 1
 * are happy numbers.
 *
 * Example:
 *
 * Input: 19
 * Output: true
 * Explanation:
 * 1^2 + 9^2 = 82
 * 8^2 + 2^2 = 68
 * 6^2 + 8^2 = 100
 * 1^2 + 0^2 + 0^2 = 1
 */
@RunWith(LeetCodeRunner.class)
public class Q202_HappyNumber {

    /**
     * 这题LeetCode 上有solution:
     * 首先确定范围: 对于所有的数字, 其按位拆分和计算后的结果都会 <= 243 ( = 9^2 + 9^2 + 9^2), 4 位数和以上的数字
     * 拆分到最后都会降到3 位数字的结果, 因此我们只需要考虑 <= 243 的数字.
     * 这个数字有2 种可能: 被计算到只剩1, 或进入一个循环之中, 计算的结果是之前计算的一个值.
     *
     * 这样就把题目变为检测环的题目了. 检测环可以使用Set 或者快慢指针的方式.
     */
    @Answer
    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        while (n != 1 && !seen.contains(n)) {
            seen.add(n);
            n = getNext(n);
        }
        return n == 1;
    }

    private int getNext(int n) {
        int sum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            sum += d * d;
        }
        return sum;
    }

    @Answer
    public boolean isHappy2(int n) {
        int slow = n, fast = getNext(n);
        while (fast != 1 && slow != fast) {
            slow = getNext(slow);
            fast = getNext(getNext(fast));
        }
        return fast == 1;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(19).expect(true);

    @TestData
    public DataExpectation nIs1 = DataExpectation.create(1).expect(true);

    @TestData
    public DataExpectation nIs2 = DataExpectation.create(2).expect(false);

    @TestData
    public DataExpectation nIs10 = DataExpectation.create(10).expect(true);

    @TestData
    public DataExpectation nIs49 = DataExpectation.create(49).expect(true);

    @TestData
    public DataExpectation nIs116 = DataExpectation.create(116).expect(false);

}
