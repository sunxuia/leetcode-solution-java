package q400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/water-and-jug-problem/
 *
 * You are given two jugs with capacities x and y litres. There is an infinite amount of water supply available. You
 * need to determine whether it is possible to measure exactly z litres using these two jugs.
 *
 * If z liters of water is measurable, you must have z liters of water contained within one or both buckets by the end.
 *
 * Operations allowed:
 *
 * Fill any of the jugs completely with water.
 * Empty any of the jugs.
 * Pour water from one jug into another till the other jug is completely full or the first jug itself is empty.
 *
 * Example 1: (From the famous "Die Hard" example)
 *
 * Input: x = 3, y = 5, z = 4
 * Output: True
 *
 * Example 2:
 *
 * Input: x = 2, y = 6, z = 5
 * Output: False
 *
 * 题解: 水壶装水的问题.
 * 有2 个水壶, 一个能装x 升水, 一个能装y 升水, 问是否能够使用这2 个水壶装出z 升水(1个水壶z 升或 2 个水壶各z 升).
 * 对水壶的操作仅限于 1. 向注满水; 2. 从倒空水; 3. 从一个水壶将水倒进另一个水壶, 直到注入的水壶满了或倒出的水壶空了.
 */
@RunWith(LeetCodeRunner.class)
public class Q365_WaterAndJugProblem {

    // https://www.cnblogs.com/grandyang/p/5628836.html
    @Answer
    public boolean canMeasureWater(int x, int y, int z) {
        return z == 0 || (x + y >= z && z % gcd(x, y) == 0);
    }

    private int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(3, 5, 4).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, 6, 5).expect(false);

}
