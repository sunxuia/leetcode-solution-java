package q950;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 904. Fruit Into Baskets
 * https://leetcode.com/problems/fruit-into-baskets/
 *
 * In a row of trees, the i-th tree produces fruit with type tree[i].
 *
 * You start at any tree of your choice, then repeatedly perform the following steps:
 *
 * Add one piece of fruit from this tree to your baskets.  If you cannot, stop.
 * Move to the next tree to the right of the current tree.  If there is no tree to the right, stop.
 *
 * Note that you do not have any choice after the initial choice of starting tree: you must perform step 1, then step 2,
 * then back to step 1, then step 2, and so on until you stop.
 *
 * You have two baskets, and each basket can carry any quantity of fruit, but you want each basket to only carry one
 * type of fruit each.
 *
 * What is the total amount of fruit you can collect with this procedure?
 *
 * Example 1:
 *
 * Input: [1,2,1]
 * Output: 3
 * Explanation: We can collect [1,2,1].
 *
 * Example 2:
 *
 * Input: [0,1,2,2]
 * Output: 3
 * Explanation: We can collect [1,2,2].
 * If we started at the first tree, we would only collect [0, 1].
 *
 * Example 3:
 *
 * Input: [1,2,3,2,2]
 * Output: 4
 * Explanation: We can collect [2,3,2,2].
 * If we started at the first tree, we would only collect [1, 2].
 *
 * Example 4:
 *
 * Input: [3,3,3,1,2,1,1,2,3,3,4]
 * Output: 5
 * Explanation: We can collect [1,2,1,1,2].
 * If we started at the first tree or the eighth tree, we would only collect 4 fruits.
 *
 * Note:
 *
 * 1 <= tree.length <= 40000
 * 0 <= tree[i] < tree.length
 */
@RunWith(LeetCodeRunner.class)
public class Q904_FruitIntoBaskets {

    @Answer
    public int totalFruit(int[] tree) {
        Map<Integer, Integer> map = new HashMap<>(3);
        int res = 0;
        for (int i = 0, start = -1; i < tree.length; i++) {
            map.put(tree[i], map.getOrDefault(tree[i], 0) + 1);
            while (map.size() > 2) {
                start++;
                map.put(tree[start], map.get(tree[start]) - 1);
                map.remove(tree[start], 0);
            }
            res = Math.max(res, i - start);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 1}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{0, 1, 2, 2}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3, 2, 2}).expect(4);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{3, 3, 3, 1, 2, 1, 1, 2, 3, 3, 4}).expect(5);

}
