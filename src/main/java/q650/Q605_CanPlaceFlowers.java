package q650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/can-place-flowers/
 *
 * Suppose you have a long flowerbed in which some of the plots are planted and some are not. However, flowers cannot
 * be planted in adjacent plots - they would compete for water and both would die.
 *
 * Given a flowerbed (represented as an array containing 0 and 1, where 0 means empty and 1 means not empty), and a
 * number n, return if n new flowers can be planted in it without violating the no-adjacent-flowers rule.
 *
 * Example 1:
 *
 * Input: flowerbed = [1,0,0,0,1], n = 1
 * Output: True
 *
 * Example 2:
 *
 * Input: flowerbed = [1,0,0,0,1], n = 2
 * Output: False
 *
 * Note:
 *
 * 1. The input array won't violate no-adjacent-flowers rule.
 * 2. The input array size is in the range of [1, 20000].
 * 3. n is a non-negative integer which won't exceed the input array size.
 */
@RunWith(LeetCodeRunner.class)
public class Q605_CanPlaceFlowers {

    @Answer
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        for (int i = 0; i < flowerbed.length && n > 0; i++) {
            if (flowerbed[i] == 1
                    || i > 0 && flowerbed[i - 1] == 1
                    || i < flowerbed.length - 1 && flowerbed[i + 1] == 1) {
                continue;
            }
            flowerbed[i] = 1;
            n--;
        }
        return n == 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 0, 0, 0, 1}, 1).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 0, 0, 0, 1}, 2).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{1, 0, 1, 0, 1, 0, 1}, 0).expect(true);

}
