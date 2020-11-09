package q850;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import q1300.Q1296_DivideArrayInSetsOfKConsecutiveNumbers;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 846. Hand of Straights
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
 *
 * 相同题目 {@link Q1296_DivideArrayInSetsOfKConsecutiveNumbers}
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
        while (!counts.isEmpty()) {
            for (int val : hand) {
                if (!counts.containsKey(val - 1) && counts.containsKey(val)) {
                    for (int i = val; i < val + W; i++) {
                        Integer count = counts.get(i);
                        if (count == null) {
                            return false;
                        }
                        if (count == 1) {
                            counts.remove(i);
                        } else {
                            counts.put(i, count - 1);
                        }
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


    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{
            9, 13, 15, 23, 22, 25, 4, 4, 29, 15, 8, 23, 12, 19, 24, 17, 18, 11, 22, 24, 17, 17, 10, 23, 21, 18, 14, 18,
            7, 6, 3, 6, 19, 11, 16, 11, 12, 13, 8, 26, 17, 20, 13, 19, 22, 21, 27, 9, 20, 15, 20, 27, 8, 13, 25, 23, 22,
            15, 9, 14, 20, 10, 6, 5, 14, 12, 7, 16, 21, 18, 21, 24, 23, 10, 21, 16, 18, 16, 18, 5, 20, 19, 20, 10, 14,
            26, 2, 9, 19, 12, 28, 17, 5, 7, 25, 22, 16, 17, 21, 11
    }, 10).expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{13, 14, 15, 7, 8, 9, 20, 21, 22, 4, 5, 6}, 3).expect(true);

}
