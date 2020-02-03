package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/self-crossing/
 *
 * You are given an array x of n positive numbers. You start at point (0,0) and moves x[0] metres to the north, then
 * x[1] metres to the west, x[2] metres to the south, x[3] metres to the east and so on. In other words, after each
 * move your direction changes counter-clockwise.
 *
 * Write a one-pass algorithm with O(1) extra space to determine, if your path crosses itself, or not.
 *
 *
 *
 * Example 1:
 *
 * >  ┌───┐
 * >  │   │
 * >  └───┼──>
 * >      │
 *
 * Input: [2,1,1,2]
 * Output: true
 *
 * Example 2:
 *
 * >  ┌──────┐
 * >  │      │
 * >  │
 * >  │
 * >  └────────────>
 *
 * Input: [1,2,3,4]
 * Output: false
 *
 * Example 3:
 *
 * >  ┌───┐
 * >  │   │
 * >  └───┼>
 *
 * Input: [1,1,1,1]
 * Output: true
 */
@RunWith(LeetCodeRunner.class)
public class Q335_SelfCrossing {

    /**
     * 题目要求只能遍历 x 一次, 时间复杂度 O(n), 空间复杂度 O(1)
     * https://www.cnblogs.com/grandyang/p/5216856.html
     * 遍历的时候分别对应3 种类边相交的情况进行处理
     */
    @Answer
    public boolean isSelfCrossing(int[] x) {
        for (int i = 3; i < x.length; i++) {
            if (x[i] >= x[i - 2] && x[i - 3] >= x[i - 1]) {
                return true;
            }
            if (i >= 4 && x[i - 1] == x[i - 3] && x[i] >= x[i - 2] - x[i - 4]) {
                return true;
            }

            if (i >= 5 && x[i - 2] >= x[i - 4] && x[i - 3] >= x[i - 1] && x[i - 1] >= x[i - 3] - x[i - 5]
                    && x[i] >= x[i - 2] - x[i - 4]) {
                return true;
            }
        }
        return false;
    }

    @TestData
    public DataExpectation exapmle1 = DataExpectation.create(new int[]{2, 1, 1, 2}).expect(true);

    @TestData
    public DataExpectation exapmle2 = DataExpectation.create(new int[]{1, 2, 3, 4}).expect(false);

    @TestData
    public DataExpectation exapmle3 = DataExpectation.create(new int[]{1, 1, 1, 1}).expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{3, 3, 4, 2, 2}).expect(false);


}
