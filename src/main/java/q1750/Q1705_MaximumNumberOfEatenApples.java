package q1750;

import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1705. Maximum Number of Eaten Apples
 * https://leetcode.com/problems/maximum-number-of-eaten-apples/
 *
 * There is a special kind of apple tree that grows apples every day for n days. On the ith day, the tree grows
 * apples[i] apples that will rot after days[i] days, that is on day i + days[i] the apples will be rotten and cannot be
 * eaten. On some days, the apple tree does not grow any apples, which are denoted by apples[i] == 0 and days[i] == 0.
 *
 * You decided to eat at most one apple a day (to keep the doctors away). Note that you can keep eating after the first
 * n days.
 *
 * Given two integer arrays days and apples of length n, return the maximum number of apples you can eat.
 *
 * Example 1:
 *
 * Input: apples = [1,2,3,5,2], days = [3,2,1,4,2]
 * Output: 7
 * Explanation: You can eat 7 apples:
 * - On the first day, you eat an apple that grew on the first day.
 * - On the second day, you eat an apple that grew on the second day.
 * - On the third day, you eat an apple that grew on the second day. After this day, the apples that grew on the third
 * day rot.
 * - On the fourth to the seventh days, you eat apples that grew on the fourth day.
 *
 * Example 2:
 *
 * Input: apples = [3,0,0,0,0,2], days = [3,0,0,0,0,2]
 * Output: 5
 * Explanation: You can eat 5 apples:
 * - On the first to the third day you eat apples that grew on the first day.
 * - Do nothing on the fouth and fifth days.
 * - On the sixth and seventh days you eat apples that grew on the sixth day.
 *
 * Constraints:
 *
 * apples.length == n
 * days.length == n
 * 1 <= n <= 2 * 10^4
 * 0 <= apples[i], days[i] <= 2 * 10^4
 * days[i] = 0 if and only if apples[i] = 0.
 */
@RunWith(LeetCodeRunner.class)
public class Q1705_MaximumNumberOfEatenApples {

    /**
     * 这题有多种解法, 因为题目限制给的比较多.
     */
    @Answer
    public int eatenApples(int[] apples, int[] days) {
        final int n = apples.length;
        // 先吃快过期的
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        int res = 0;
        for (int i = 0; i < n; i++) {
            while (!pq.isEmpty() && (pq.peek()[1] < i || pq.peek()[0] == 0)) {
                pq.poll();
            }
            if (!pq.isEmpty()) {
                res++;
                pq.peek()[0]--;
            }
            if (apples[i] > 0) {
                pq.offer(new int[]{apples[i], i + days[i]});
            }
        }
        for (int i = n - 1; !pq.isEmpty(); ) {
            int count = pq.peek()[0], expire = pq.poll()[1];
            if (i + count > expire) {
                res += expire - i;
                i = Math.max(i, expire);
            } else {
                res += count;
                i += count;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 5, 2}, new int[]{3, 2, 1, 4, 2})
            .expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{3, 0, 0, 0, 0, 2}, new int[]{3, 0, 0, 0, 0, 2})
            .expect(5);

}
