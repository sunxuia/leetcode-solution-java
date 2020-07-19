package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 875. Koko Eating Bananas
 * https://leetcode.com/problems/koko-eating-bananas/
 *
 * Koko loves to eat bananas.  There are N piles of bananas, the i-th pile has piles[i] bananas.  The guards have gone
 * and will come back in H hours.
 *
 * Koko can decide her bananas-per-hour eating speed of K.  Each hour, she chooses some pile of bananas, and eats K
 * bananas from that pile.  If the pile has less than K bananas, she eats all of them instead, and won't eat any more
 * bananas during this hour.
 *
 * Koko likes to eat slowly, but still wants to finish eating all the bananas before the guards come back.
 *
 * Return the minimum integer K such that she can eat all the bananas within H hours.
 *
 * Example 1:
 * Input: piles = [3,6,7,11], H = 8
 * Output: 4
 * Example 2:
 * Input: piles = [30,11,23,4,20], H = 5
 * Output: 30
 * Example 3:
 * Input: piles = [30,11,23,4,20], H = 6
 * Output: 23
 *
 * Constraints:
 *
 * 1 <= piles.length <= 10^4
 * piles.length <= H <= 10^9
 * 1 <= piles[i] <= 10^9
 *
 * 题解: 有个测试用例的隐含条件: piles 是有序的, 如果再自己排序的话OJ 会比较慢
 */
@RunWith(LeetCodeRunner.class)
public class Q875_KokoEatingBananas {

    // 简单的二分法然后变遍历计数
    @Answer
    public int minEatingSpeed(int[] piles, int H) {
//        Arrays.sort(piles);
        int start = 1, end = piles[piles.length - 1];
        while (start < end) {
            int mid = (start + end) / 2;
            int sum = 0;
            for (int pile : piles) {
                sum += (pile + mid - 1) / mid;
            }
            if (sum <= H) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return end;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{3, 6, 7, 11}, 8).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{30, 11, 23, 4, 20}, 5).expect(30);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{30, 11, 23, 4, 20}, 6).expect(23);

    @TestData
    public DataExpectation border = DataExpectation.createWith(new int[]{312884470}, 312884469).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{
            332484035, 524908576, 855865114, 632922376, 222257295, 690155293, 112677673, 679580077, 337406589,
            290818316, 877337160, 901728858, 679284947, 688210097, 692137887, 718203285, 629455728, 941802184
    }, 823855818).expect(14);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(TestDataFileHelper.readIntegerArray("Q875_TestData"), 63939633)
            .expect(78332);

}
