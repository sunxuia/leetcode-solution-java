package q350;

import org.junit.runner.RunWith;
import q250.Q231_PowerOfTwo;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/power-of-three/
 *
 * Given an integer, write a function to determine if it is a power of three.
 *
 * Example 1:
 *
 * Input: 27
 * Output: true
 *
 * Example 2:
 *
 * Input: 0
 * Output: false
 *
 * Example 3:
 *
 * Input: 9
 * Output: true
 *
 * Example 4:
 *
 * Input: 45
 * Output: false
 *
 * Follow up:
 * Could you do it without using any loop / recursion?
 *
 * 相关题目 {@link Q231_PowerOfTwo}, {@link Q342_PowerOfFour}
 */
@RunWith(LeetCodeRunner.class)
public class Q326_PowerOfThree {

    // 1162261467 (3^19) 是 小于 Integer.MAX_VALUE 的最大的3 的次方数.
    @Answer
    public boolean isPowerOfThree(int n) {
        return n > 0 && 1162261467 % n == 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(27).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(0).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(9).expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation.create(45).expect(false);

}
