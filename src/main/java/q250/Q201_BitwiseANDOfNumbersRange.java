package q250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/bitwise-and-of-numbers-range/
 *
 * Given a range [m, n] where 0 <= m <= n <= 2147483647, return the bitwise AND of all numbers in this range, inclusive.
 *
 * Example 1:
 *
 * Input: [5,7]
 * Output: 4
 *
 * Example 2:
 *
 * Input: [0,1]
 * Output: 0
 */
@RunWith(LeetCodeRunner.class)
public class Q201_BitwiseANDOfNumbersRange {

    // 简单的按位操作会超时, 所以需要找到2 个点之间的公共点.
    @Answer
    public int rangeBitwiseAnd(int m, int n) {
        if (m == n) {
            return m;
        }
        int mask = 0x40000000;
        while ((m & mask) == (n & mask)) {
            mask = mask | (mask >>> 1);
        }
        mask <<= 1;
        return m & mask;
    }

    // LeetCode 上更好的一种改进, 代码更简洁, 速度一样.
    @Answer
    public int rangeBitwiseAnd2(int m, int n) {
        int mask = Integer.MAX_VALUE;
        while ((m & mask) != (n & mask)) {
            mask = mask << 1;
        }
        return m & mask;
    }

    // LeetCode 上最快的方式, n & (n-1) 将末尾的值取0, 减少了上面那种方式的循环次数.
    @Answer
    public int rangeBitwiseAnd3(int m, int n) {
        while (n > m) {
            n &= (n - 1);
        }
        return n;
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.createWith(5, 7).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(0, 1).expect(0);

    @TestData
    public DataExpectation wildRange = DataExpectation.createWith(20000, 2147483647).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(1, 1).expect(1);

}
