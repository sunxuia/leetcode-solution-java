package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1954. Minimum Garden Perimeter to Collect Enough Apples
 * https://leetcode.com/problems/minimum-garden-perimeter-to-collect-enough-apples/
 *
 * In a garden represented as an infinite 2D grid, there is an apple tree planted at every integer coordinate. The apple
 * tree planted at an integer coordinate (i, j) has |i| + |j| apples growing on it.
 *
 * You will buy an axis-aligned square plot of land that is centered at (0, 0).
 *
 * Given an integer neededApples, return the minimum perimeter of a plot such that at least neededApples apples are
 * inside or on the perimeter of that plot.
 *
 * The value of |x| is defined as:
 *
 * x if x >= 0
 * -x if x < 0
 *
 * Example 1:
 * (图Q1954_PIC.png)
 * Input: neededApples = 1
 * Output: 8
 * Explanation: A square plot of side length 1 does not contain any apples.
 * However, a square plot of side length 2 has 12 apples inside (as depicted in the image above).
 * The perimeter is 2 * 4 = 8.
 *
 * Example 2:
 *
 * Input: neededApples = 13
 * Output: 16
 *
 * Example 3:
 *
 * Input: neededApples = 1000000000
 * Output: 5040
 *
 * Constraints:
 *
 * 1 <= neededApples <= 10^15
 */
@RunWith(LeetCodeRunner.class)
public class Q1954_MinimumGardenPerimeterToCollectEnoughApples {

    /**
     * 找数学规律的题
     */
    @Answer
    public long minimumPerimeter(long neededApples) {
        long appleSum = 0, appleEdge = 0;
        long x = 0, treeEdge = 0;
        while (appleSum < neededApples) {
            appleEdge += treeEdge + 16 * x + 12;
            treeEdge += 8;
            x++;
            appleSum += appleEdge;
        }
        return treeEdge;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(1L).expect(8L);

    @TestData
    public DataExpectation example2 = DataExpectation.create(13L).expect(16L);

    @TestData
    public DataExpectation example3 = DataExpectation.create(10_0000_0000L).expect(5040L);

}
