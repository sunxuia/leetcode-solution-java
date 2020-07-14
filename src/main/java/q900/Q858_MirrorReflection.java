package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] Mirror Reflection
 * https://leetcode.com/problems/mirror-reflection/
 *
 * There is a special square room with mirrors on each of the four walls.  Except for the southwest corner, there are
 * receptors on each of the remaining corners, numbered 0, 1, and 2.
 *
 * The square room has walls of length p, and a laser ray from the southwest corner first meets the east wall at a
 * distance q from the 0th receptor.
 *
 * Return the number of the receptor that the ray meets first.  (It is guaranteed that the ray will meet a receptor
 * eventually.)
 *
 * Example 1:
 *
 * Input: p = 2, q = 1
 * Output: 2
 * Explanation: The ray meets receptor 2 the first time it gets reflected back to the left wall.
 * (图Q858_PIC.png)
 *
 * Note:
 *
 * 1 <= p <= 1000
 * 0 <= q <= p
 */
@RunWith(LeetCodeRunner.class)
public class Q858_MirrorReflection {

    // 参考文档 https://www.cnblogs.com/grandyang/p/10646040.html
    @Answer
    public int mirrorReflection(int p, int q) {
        while (p % 2 == 0 && q % 2 == 0) {
            p /= 2;
            q /= 2;
        }
        return 1 - p % 2 + q % 2;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, 1).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(3, 1).expect(1);

}
