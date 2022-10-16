package q2000;

import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1962. Remove Stones to Minimize the Total
 * https://leetcode.com/problems/remove-stones-to-minimize-the-total/
 *
 * You are given a 0-indexed integer array piles, where piles[i] represents the number of stones in the ith pile, and an
 * integer k. You should apply the following operation exactly k times:
 *
 * Choose any piles[i] and remove floor(piles[i] / 2) stones from it.
 *
 * Notice that you can apply the operation on the same pile more than once.
 *
 * Return the minimum possible total number of stones remaining after applying the k operations.
 *
 * floor(x) is the greatest integer that is smaller than or equal to x (i.e., rounds x down).
 *
 * Example 1:
 *
 * Input: piles = [5,4,9], k = 2
 * Output: 12
 * Explanation: Steps of a possible scenario are:
 * - Apply the operation on pile 2. The resulting piles are [5,4,5].
 * - Apply the operation on pile 0. The resulting piles are [3,4,5].
 * The total number of stones in [3,4,5] is 12.
 *
 * Example 2:
 *
 * Input: piles = [4,3,6,7], k = 3
 * Output: 12
 * Explanation: Steps of a possible scenario are:
 * - Apply the operation on pile 2. The resulting piles are [4,3,3,7].
 * - Apply the operation on pile 3. The resulting piles are [4,3,3,4].
 * - Apply the operation on pile 0. The resulting piles are [2,3,3,4].
 * The total number of stones in [2,3,3,4] is 12.
 *
 * Constraints:
 *
 * 1 <= piles.length <= 10^5
 * 1 <= piles[i] <= 10^4
 * 1 <= k <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1962_RemoveStonesToMinimizeTheTotal {

    @Answer
    public int minStoneSum(int[] piles, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for (int pile : piles) {
            pq.offer(pile);
        }

        int res = 0;
        while (k > 0 && !pq.isEmpty()) {
            int val = pq.poll();
            val -= val / 2;
            if (val > 1) {
                pq.offer(val);
            } else {
                res++;
            }
            k--;
        }

        for (Integer val : pq) {
            res += val;
        }
        return res;
    }


    // 桶排序
    @Answer
    public int minStoneSum2(int[] piles, int k) {
        int max = 0;
        for (int pile : piles) {
            max = Math.max(max, pile);
        }

        int[] buckets = new int[max + 1];
        for (int pile : piles) {
            buckets[pile]++;
        }

        int i = max + 1;
        while (i > 1 && k > 0) {
            i--;
            while (buckets[i] > 0 && k > 0) {
                buckets[i - i / 2]++;
                buckets[i]--;
                k--;
            }
        }

        int res = 0;
        for (; i > 0; i--) {
            res += buckets[i] * i;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{5, 4, 9}, 2).expect(12);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{4, 3, 6, 7}, 3).expect(12);

}
