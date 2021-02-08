package q1750;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1710. Maximum Units on a Truck
 * https://leetcode.com/problems/maximum-units-on-a-truck/
 *
 * You are assigned to put some amount of boxes onto one truck. You are given a 2D array boxTypes, where boxTypes[i] =
 * [numberOfBoxesi, numberOfUnitsPerBoxi]:
 *
 * numberOfBoxesi is the number of boxes of type i.
 * numberOfUnitsPerBoxi is the number of units in each box of the type i.
 *
 * You are also given an integer truckSize, which is the maximum number of boxes that can be put on the truck. You can
 * choose any boxes to put on the truck as long as the number of boxes does not exceed truckSize.
 *
 * Return the maximum total number of units that can be put on the truck.
 *
 * Example 1:
 *
 * Input: boxTypes = [[1,3],[2,2],[3,1]], truckSize = 4
 * Output: 8
 * Explanation: There are:
 * - 1 box of the first type that contains 3 units.
 * - 2 boxes of the second type that contain 2 units each.
 * - 3 boxes of the third type that contain 1 unit each.
 * You can take all the boxes of the first and second types, and one box of the third type.
 * The total number of units will be = (1 * 3) + (2 * 2) + (1 * 1) = 8.
 *
 * Example 2:
 *
 * Input: boxTypes = [[5,10],[2,5],[4,7],[3,9]], truckSize = 10
 * Output: 91
 *
 * Constraints:
 *
 * 1 <= boxTypes.length <= 1000
 * 1 <= numberOfBoxesi, numberOfUnitsPerBoxi <= 1000
 * 1 <= truckSize <= 10^6
 *
 * 题解:
 * 题目的意思: 一辆卡车可以装 truckSize 个箱子, boxTypes[i][0] 表示某种类型箱子的数量,
 * boxTypes[i][1] 表示某种类型箱子中每个箱子可以放的单元的数量, 现在要取不超过 truckSize 个箱子,
 * 求取得的单元的最大数量.
 */
@RunWith(LeetCodeRunner.class)
public class Q1710_MaximumUnitsOnATruck {

    @Answer
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        Arrays.sort(boxTypes, Comparator.comparingInt(a -> a[1]));
        int res = 0;
        for (int i = boxTypes.length - 1; i >= 0 && truckSize > 0; i--) {
            if (truckSize > boxTypes[i][0]) {
                res += boxTypes[i][0] * boxTypes[i][1];
                truckSize -= boxTypes[i][0];
            } else {
                res += truckSize * boxTypes[i][1];
                truckSize = 0;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{1, 3}, {2, 2}, {3, 1}}, 4)
            .expect(8);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{5, 10}, {2, 5}, {4, 7}, {3, 9}}, 10)
            .expect(91);

}
