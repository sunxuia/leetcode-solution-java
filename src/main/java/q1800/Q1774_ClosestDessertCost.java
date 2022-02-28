package q1800;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1774. Closest Dessert Cost
 * https://leetcode.com/problems/closest-dessert-cost/
 *
 * You would like to make dessert and are preparing to buy the ingredients. You have n ice cream base flavors and m
 * types of toppings to choose from. You must follow these rules when making your dessert:
 *
 * There must be exactly one ice cream base.
 * You can add one or more types of topping or have no toppings at all.
 * There are at most two of each type of topping.
 *
 * You are given three inputs:
 *
 * baseCosts, an integer array of length n, where each baseCosts[i] represents the price of the ith ice cream base
 * flavor.
 * toppingCosts, an integer array of length m, where each toppingCosts[i] is the price of one of the ith topping.
 * target, an integer representing your target price for dessert.
 *
 * You want to make a dessert with a total cost as close to target as possible.
 *
 * Return the closest possible cost of the dessert to target. If there are multiple, return the lower one.
 *
 * Example 1:
 *
 * Input: baseCosts = [1,7], toppingCosts = [3,4], target = 10
 * Output: 10
 * Explanation: Consider the following combination (all 0-indexed):
 * - Choose base 1: cost 7
 * - Take 1 of topping 0: cost 1 x 3 = 3
 * - Take 0 of topping 1: cost 0 x 4 = 0
 * Total: 7 + 3 + 0 = 10.
 *
 * Example 2:
 *
 * Input: baseCosts = [2,3], toppingCosts = [4,5,100], target = 18
 * Output: 17
 * Explanation: Consider the following combination (all 0-indexed):
 * - Choose base 1: cost 3
 * - Take 1 of topping 0: cost 1 x 4 = 4
 * - Take 2 of topping 1: cost 2 x 5 = 10
 * - Take 0 of topping 2: cost 0 x 100 = 0
 * Total: 3 + 4 + 10 + 0 = 17. You cannot make a dessert with a total cost of 18.
 *
 * Example 3:
 *
 * Input: baseCosts = [3,10], toppingCosts = [2,5], target = 9
 * Output: 8
 * Explanation: It is possible to make desserts with cost 8 and 10. Return 8 as it is the lower cost.
 *
 * Constraints:
 *
 * n == baseCosts.length
 * m == toppingCosts.length
 * 1 <= n, m <= 10
 * 1 <= baseCosts[i], toppingCosts[i] <= 10^4
 * 1 <= target <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1774_ClosestDessertCost {

    @Answer
    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        Arrays.sort(toppingCosts);
        int res = 100000;
        for (int baseCost : baseCosts) {
            int cost = baseCost + recursion(target - baseCost, toppingCosts, toppingCosts.length - 1);
            int tr = Math.abs(target - res);
            int tc = Math.abs(target - cost);
            if (tr > tc || tr == tc && res > cost) {
                res = cost;
            }
        }
        return res;
    }

    private int recursion(int target, int[] tcs, int idx) {
        if (idx == -1 || target <= 0) {
            return 0;
        }
        int cost0 = recursion(target, tcs, idx - 1);
        int cost1 = tcs[idx] + recursion(target - tcs[idx], tcs, idx - 1);
        int cost2 = 2 * tcs[idx] + recursion(target - 2 * tcs[idx], tcs, idx - 1);
        return closest(target, closest(target, cost0, cost1), cost2);
    }

    private int closest(int target, int c1, int c2) {
        int dist1 = Math.abs(target - c1);
        int dist2 = Math.abs(target - c2);
        if (dist1 == dist2) {
            return Math.min(c1, c2);
        } else {
            return dist1 < dist2 ? c1 : c2;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 7}, new int[]{3, 4}, 10)
            .expect(10);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{2, 3}, new int[]{4, 5, 100}, 18)
            .expect(17);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{3, 10}, new int[]{2, 5}, 9)
            .expect(8);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{10}, new int[]{1}, 1)
            .expect(10);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{4153, 8358, 3426}, new int[]{9923, 7073, 342, 3693}, 293)
            .expect(3426);

}
