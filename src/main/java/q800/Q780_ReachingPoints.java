package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/swim-in-rising-water/
 *
 * A move consists of taking a point (x, y) and transforming it to either (x, x+y) or (x+y, y).
 *
 * Given a starting point (sx, sy) and a target point (tx, ty), return True if and only if a sequence of moves exists
 * to transform the point (sx, sy) to (tx, ty). Otherwise, return False.
 *
 * Examples:
 * Input: sx = 1, sy = 1, tx = 3, ty = 5
 * Output: True
 * Explanation:
 * One series of moves that transforms the starting point to the target is:
 * (1, 1) -> (1, 2)
 * (1, 2) -> (3, 2)
 * (3, 2) -> (3, 5)
 *
 * Input: sx = 1, sy = 1, tx = 2, ty = 2
 * Output: False
 *
 * Input: sx = 1, sy = 1, tx = 1, ty = 1
 * Output: True
 *
 * Note:
 *
 * sx, sy, tx, ty will all be integers in the range [1, 10^9].
 */
@RunWith(LeetCodeRunner.class)
public class Q780_ReachingPoints {

    /**
     * 这题不能使用栈, 递归方式会出现方法栈 StackOverflowError, 使用堆中的栈会出现超时问题.
     * 参考 https://www.cnblogs.com/grandyang/p/9033955.html 是一种取余操作
     */
    @Answer
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        while (tx >= sx && ty >= sy) {
            if (tx > ty) {
                if (ty == sy) {
                    return (tx - sx) % ty == 0;
                }
                tx %= ty;
            } else {
                if (tx == sx) {
                    return (ty - sy) % tx == 0;
                } else {
                    ty %= tx;
                }
            }
        }
        return tx == sx && ty == sy;
    }


    @TestData
    public DataExpectation example = DataExpectation.createWith(1, 1, 3, 5).expect(true);

    @TestData
    public DataExpectation stackOverFlow = DataExpectation.createWith(35, 13, 455955547, 420098884).expect(false);

}
