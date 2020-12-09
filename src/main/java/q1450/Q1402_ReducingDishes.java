package q1450;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1402. Reducing Dishes
 * https://leetcode.com/problems/reducing-dishes/
 *
 * A chef has collected data on the satisfaction level of his n dishes. Chef can cook any dish in 1 unit of time.
 *
 * Like-time coefficient of a dish is defined as the time taken to cook that dish including previous dishes multiplied
 * by its satisfaction level  i.e.  time[i]*satisfaction[i]
 *
 * Return the maximum sum of Like-time coefficient that the chef can obtain after dishes preparation.
 *
 * Dishes can be prepared in any order and the chef can discard some dishes to get this maximum value.
 *
 * Example 1:
 *
 * Input: satisfaction = [-1,-8,0,5,-9]
 * Output: 14
 * Explanation: After Removing the second and last dish, the maximum total Like-time coefficient will be equal to (-1*1
 * + 0*2 + 5*3 = 14). Each dish is prepared in one unit of time.
 *
 * Example 2:
 *
 * Input: satisfaction = [4,3,2]
 * Output: 20
 * Explanation: Dishes can be prepared in any order, (2*1 + 3*2 + 4*3 = 20)
 *
 * Example 3:
 *
 * Input: satisfaction = [-1,-4,-5]
 * Output: 0
 * Explanation: People don't like the dishes. No dish is prepared.
 *
 * Example 4:
 *
 * Input: satisfaction = [-2,5,-1,0,3,-3]
 * Output: 35
 *
 * Constraints:
 *
 * n == satisfaction.length
 * 1 <= n <= 500
 * -10^3 <= satisfaction[i] <= 10^3
 */
@RunWith(LeetCodeRunner.class)
public class Q1402_ReducingDishes {

    @Answer
    public int maxSatisfaction(int[] satisfaction) {
        final int n = satisfaction.length;
        Arrays.sort(satisfaction);
        int sum = 0, res = 0;
        for (int i = 0; i < n; i++) {
            sum += satisfaction[i];
            res += (i + 1) * satisfaction[i];
        }
        // 扣掉前面的负数
        for (int i = 0, max = res;
                i < n && satisfaction[i] < 0;
                i++) {
            max -= sum;
            sum -= satisfaction[i];
            res = Math.max(max, res);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{-1, -8, 0, 5, -9}).expect(14);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{4, 3, 2}).expect(20);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{-1, -4, -5}).expect(0);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{-2, 5, -1, 0, 3, -3}).expect(35);

}
