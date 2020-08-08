package q950;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 914. X of a Kind in a Deck of Cards
 * https://leetcode.com/problems/x-of-a-kind-in-a-deck-of-cards/
 *
 * In a deck of cards, each card has an integer written on it.
 *
 * Return true if and only if you can choose X >= 2 such that it is possible to split the entire deck into 1 or more
 * groups of cards, where:
 *
 * Each group has exactly X cards.
 * All the cards in each group have the same integer.
 *
 * Example 1:
 *
 * Input: deck = [1,2,3,4,4,3,2,1]
 * Output: true
 * Explanation: Possible partition [1,1],[2,2],[3,3],[4,4].
 *
 * Example 2:
 *
 * Input: deck = [1,1,1,2,2,2,3,3]
 * Output: false
 * Explanation: No possible partition.
 *
 * Example 3:
 *
 * Input: deck = [1]
 * Output: false
 * Explanation: No possible partition.
 *
 * Example 4:
 *
 * Input: deck = [1,1]
 * Output: true
 * Explanation: Possible partition [1,1].
 *
 * Example 5:
 *
 * Input: deck = [1,1,2,2,2,2]
 * Output: true
 * Explanation: Possible partition [1,1],[2,2],[2,2].
 *
 * Constraints:
 *
 * 1 <= deck.length <= 10^4
 * 0 <= deck[i] < 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q914_XOfAKindInADeckOfCards {

    @Answer
    public boolean hasGroupsSizeX(int[] deck) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int card : deck) {
            map.put(card, map.getOrDefault(card, 0) + 1);
        }
        int gcd = map.values().iterator().next();
        for (int count : map.values()) {
            gcd = gcd(gcd, count);
            if (gcd == 1) {
                return false;
            }
        }
        return true;
    }

    private int gcd(int x, int y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 4, 4, 3, 2, 1}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 1, 1, 2, 2, 2, 3, 3}).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1}).expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{1, 1}).expect(true);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[]{1, 1, 2, 2, 2, 2}).expect(true);

}
