package q850;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/card-flipping-game/
 *
 * On a table are N cards, with a positive integer printed on the front and back of each card (possibly different).
 *
 * We flip any number of cards, and after we choose one card.
 *
 * If the number X on the back of the chosen card is not on the front of any card, then this number X is good.
 *
 * What is the smallest number that is good?  If no number is good, output 0.
 *
 * Here, fronts[i] and backs[i] represent the number on the front and back of card i.
 *
 * A flip swaps the front and back numbers, so the value on the front is now on the back and vice versa.
 *
 * Example:
 *
 * Input: fronts = [1,2,4,4,7], backs = [1,3,4,1,3]
 * Output: 2
 * Explanation: If we flip the second card, the fronts are [1,3,4,4,7] and the backs are [1,2,4,1,3].
 * We choose the second card, which has number 2 on the back, and it isn't on the front of any card, so 2 is good.
 *
 *
 *
 * Note:
 *
 * 1 <= fronts.length == backs.length <= 1000.
 * 1 <= fronts[i] <= 2000.
 * 1 <= backs[i] <= 2000.
 */
@RunWith(LeetCodeRunner.class)
public class Q822_CardFlippingGame {

    @Answer
    public int flipgame(int[] fronts, int[] backs) {
        final int n = fronts.length;
        Set<Integer> sames = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (fronts[i] == backs[i]) {
                sames.add(fronts[i]);
            }
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (fronts[i] == backs[i]) {
                continue;
            }
            if (!sames.contains(fronts[i])) {
                res = Math.min(res, fronts[i]);
            }
            if (!sames.contains(backs[i])) {
                res = Math.min(res, backs[i]);
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[]{1, 2, 4, 4, 7}, new int[]{1, 3, 4, 1, 3})
            .expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{1}, new int[]{1}).expect(0);

}
