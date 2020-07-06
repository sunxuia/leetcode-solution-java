package q850;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/hand-of-straights/
 *
 * Alice has a hand of cards, given as an array of integers.
 *
 * Now she wants to rearrange the cards into groups so that each group is size W, and consists of W consecutive cards.
 *
 * Return true if and only if she can.
 *
 *
 *
 * Example 1:
 *
 * Input: hand = [1,2,3,6,2,3,4,7,8], W = 3
 * Output: true
 * Explanation: Alice's hand can be rearranged as [1,2,3],[2,3,4],[6,7,8].
 *
 * Example 2:
 *
 * Input: hand = [1,2,3,4,5], W = 4
 * Output: false
 * Explanation: Alice's hand can't be rearranged into groups of 4.
 *
 *
 *
 * Constraints:
 *
 * 1 <= hand.length <= 10000
 * 0 <= hand[i] <= 10^9
 * 1 <= W <= hand.length
 *
 * Note: This question is the same as 1296: https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/
 */
@RunWith(LeetCodeRunner.class)
public class Q846_HandOfStraights {

    @Answer
    public boolean isNStraightHand(int[] hand, int W) {
        Arrays.sort(hand);
        Map<Integer, Integer> counts = new HashMap<>(W);
        for (int val : hand) {
            counts.put(val, counts.getOrDefault(val, 0) + 1);
            if (counts.size() == W) {
                for (int i = val; i > val - W; i--) {
                    int times = counts.getOrDefault(i, 0);
                    if (times == 0) {
                        return false;
                    } else if (times == 1) {
                        counts.remove(i);
                    } else {
                        counts.put(i, times - 1);
                    }
                }
            }
        }
        return counts.isEmpty();
    }

    // 不排序的解法
    @Answer
    public boolean isNStraightHand2(int[] hand, int W) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int val : hand) {
            counts.put(val, counts.getOrDefault(val, 0) + 1);
        }
        for (int val : hand) {
            if (!counts.containsKey(val - 1) && counts.containsKey(val)) {
                for (int i = val; i < val + W; i++) {
                    int count = counts.getOrDefault(i, 0);
                    if (count == 0) {
                        return false;
                    } else if (count == 1) {
                        counts.remove(i);
                    } else {
                        counts.put(i, count - 1);
                    }
                }
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2, 3, 6, 2, 3, 4, 7, 8}, 3).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 2, 3, 4, 5}, 4).expect(false);

}
