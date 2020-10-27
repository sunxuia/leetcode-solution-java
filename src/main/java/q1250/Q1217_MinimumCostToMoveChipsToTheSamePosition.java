package q1250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1217. Minimum Cost to Move Chips to The Same Position
 * https://leetcode.com/problems/minimum-cost-to-move-chips-to-the-same-position/
 *
 * We have n chips, where the position of the ith chip is position[i].
 *
 * We need to move all the chips to the same position. In one step, we can change the position of the ith chip from
 * position[i] to:
 *
 * position[i] + 2 or position[i] - 2 with cost = 0.
 * position[i] + 1 or position[i] - 1 with cost = 1.
 *
 * Return the minimum cost needed to move all the chips to the same position.
 *
 * Example 1:
 * <img src="Q1217_PIC1.png">
 * Input: position = [1,2,3]
 * Output: 1
 * Explanation: First step: Move the chip at position 3 to position 1 with cost = 0.
 * Second step: Move the chip at position 2 to position 1 with cost = 1.
 * Total cost is 1.
 *
 * Example 2:
 * <img src="Q1217_PIC2.png">
 * Input: position = [2,2,2,3,3]
 * Output: 2
 * Explanation: We can move the two chips at poistion 3 to position 2. Each move has cost = 1. The total cost = 2.
 *
 * Example 3:
 *
 * Input: position = [1,1000000000]
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= position.length <= 100
 * 1 <= position[i] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1217_MinimumCostToMoveChipsToTheSamePosition {

    @Answer
    public int minCostToMoveChips(int[] position) {
        int odd = 0, even = 0;
        for (int pos : position) {
            if (pos % 2 == 0) {
                even += 1;
            } else {
                odd += 1;
            }
        }
        return Math.min(even, odd);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 2, 2, 3, 3}).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 10_0000_0000}).expect(1);

}
