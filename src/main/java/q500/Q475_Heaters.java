package q500;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/heaters
 *
 * Winter is coming! Your first job during the contest is to design a standard heater with fixed warm radius to warm
 * all the houses.
 *
 * Now, you are given positions of houses and heaters on a horizontal line, find out minimum radius of heaters so
 * that all houses could be covered by those heaters.
 *
 * So, your input will be the positions of houses and heaters seperately, and your expected output will be the
 * minimum radius standard of heaters.
 *
 * Note:
 *
 * Numbers of houses and heaters you are given are non-negative and will not exceed 25000.
 * Positions of houses and heaters you are given are non-negative and will not exceed 10^9.
 * As long as a house is in the heaters' warm radius range, it can be warmed.
 * All the heaters follow your radius standard and the warm radius will the same.
 *
 *
 *
 * Example 1:
 *
 * Input: [1,2,3],[2]
 * Output: 1
 * Explanation: The only heater was placed in the position 2, and if we use the radius 1 standard, then all the
 * houses can be warmed.
 *
 *
 *
 * Example 2:
 *
 * Input: [1,2,3,4],[1,4]
 * Output: 1
 * Explanation: The two heater was placed in the position 1 and 4. We need to use radius 1 standard, then all the
 * houses can be warmed.
 */
@RunWith(LeetCodeRunner.class)
public class Q475_Heaters {

    // 下面的解法可以优化一下边界条件
    @Answer
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int res = 0, idx = 0;
        for (int i = 0; i < houses.length; i++) {
            int length;
            if (houses[i] < heaters[idx]) {
                length = heaters[idx] - houses[i];
            } else if (idx == heaters.length - 1) {
                length = houses[i] - heaters[idx];
            } else if (heaters[idx + 1] < houses[i]) {
                idx++;
                i--;
                continue;
            } else {
                length = Math.min(houses[i] - heaters[idx], heaters[idx + 1] - houses[i]);
            }
            res = Math.max(res, length);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2, 3}, new int[]{2}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 2, 3, 4}, new int[]{1, 4}).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(
            new int[]{282475249, 622650073, 984943658, 144108930, 470211272, 101027544, 457850878, 458777923},
            new int[]{823564440, 115438165, 784484492, 74243042, 114807987, 137522503, 441282327, 16531729, 823378840,
                    143542612})
            .expect(161834419);

}
