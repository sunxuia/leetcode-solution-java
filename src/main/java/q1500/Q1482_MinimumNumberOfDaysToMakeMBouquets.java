package q1500;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1482. Minimum Number of Days to Make m Bouquets
 * https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/
 *
 * Given an integer array bloomDay, an integer m and an integer k.
 *
 * We need to make m bouquets. To make a bouquet, you need to use k adjacent flowers from the garden.
 *
 * The garden consists of n flowers, the ith flower will bloom in the bloomDay[i] and then can be used in exactly one
 * bouquet.
 *
 * Return the minimum number of days you need to wait to be able to make m bouquets from the garden. If it is impossible
 * to make m bouquets return -1.
 *
 * Example 1:
 *
 * Input: bloomDay = [1,10,3,10,2], m = 3, k = 1
 * Output: 3
 * Explanation: Let's see what happened in the first three days. x means flower bloomed and _ means flower didn't bloom
 * in the garden.
 * We need 3 bouquets each should contain 1 flower.
 * After day 1: [x, _, _, _, _]   // we can only make one bouquet.
 * After day 2: [x, _, _, _, x]   // we can only make two bouquets.
 * After day 3: [x, _, x, _, x]   // we can make 3 bouquets. The answer is 3.
 *
 * Example 2:
 *
 * Input: bloomDay = [1,10,3,10,2], m = 3, k = 2
 * Output: -1
 * Explanation: We need 3 bouquets each has 2 flowers, that means we need 6 flowers. We only have 5 flowers so it is
 * impossible to get the needed bouquets and we return -1.
 *
 * Example 3:
 *
 * Input: bloomDay = [7,7,7,7,12,7,7], m = 2, k = 3
 * Output: 12
 * Explanation: We need 2 bouquets each should have 3 flowers.
 * Here's the garden after the 7 and 12 days:
 * After day 7: [x, x, x, x, _, x, x]
 * We can make one bouquet of the first three flowers that bloomed. We cannot make another bouquet from the last three
 * flowers that bloomed because they are not adjacent.
 * After day 12: [x, x, x, x, x, x, x]
 * It is obvious that we can make two bouquets in different ways.
 *
 * Example 4:
 *
 * Input: bloomDay = [1000000000,1000000000], m = 1, k = 1
 * Output: 1000000000
 * Explanation: You need to wait 1000000000 days to have a flower ready for a bouquet.
 *
 * Example 5:
 *
 * Input: bloomDay = [1,10,2,9,3,8,4,7,5,6], m = 4, k = 2
 * Output: 9
 *
 * Constraints:
 *
 * bloomDay.length == n
 * 1 <= n <= 10^5
 * 1 <= bloomDay[i] <= 10^9
 * 1 <= m <= 10^6
 * 1 <= k <= n
 */
@RunWith(LeetCodeRunner.class)
public class Q1482_MinimumNumberOfDaysToMakeMBouquets {

    /**
     * 按开花时间排序后再按前后顺序合并的方式
     */
    @Answer
    public int minDays(int[] bloomDay, int m, int k) {
        final int n = bloomDay.length;
        PriorityQueue<Cluster> pq = new PriorityQueue<>(
                n, Comparator.comparingInt(a -> a.bloomDay));
        for (int i = 0; i < n; i++) {
            Cluster c = new Cluster();
            c.bloomDay = bloomDay[i];
            c.start = c.end = i;
            pq.offer(c);
        }

        int sum = 0;
        TreeSet<Cluster> set = new TreeSet<>();
        while (!pq.isEmpty()) {
            Cluster c = pq.poll();
            Cluster left = set.lower(c);
            if (left != null && left.end + 1 == c.start) {
                sum -= (left.end - left.start + 1) / k;
                c.start = left.start;
                set.remove(left);
            }
            Cluster right = set.higher(c);
            if (right != null && c.end + 1 == right.start) {
                sum -= (right.end - right.start + 1) / k;
                c.end = right.end;
                set.remove(right);
            }
            sum += (c.end - c.start + 1) / k;
            if (sum >= m) {
                return c.bloomDay;
            }
            set.add(c);
        }
        return -1;
    }

    /**
     * 表示 [start, end] 的花都开了
     */
    private static class Cluster implements Comparable<Cluster> {

        int start, end, bloomDay;

        @Override
        public int compareTo(Cluster o) {
            return start - o.start;
        }
    }

    /**
     * LeetCode 上比较快的方式是通过二分查找的方式来做的.
     */
    @Answer
    public int minDays2(int[] bloomDay, int m, int k) {
        int start = Integer.MAX_VALUE, end = Integer.MIN_VALUE;
        for (int day : bloomDay) {
            start = Math.min(start, day);
            end = Math.max(end, day);
        }
        while (start < end) {
            int middle = (start + end) / 2;
            int sum = getBouquet(bloomDay, middle, k);
            if (sum >= m) {
                end = middle;
            } else {
                start = middle + 1;
            }
        }
        return getBouquet(bloomDay, start, k) >= m ? start : -1;
    }

    private int getBouquet(int[] bloomDay, int day, int k) {
        final int n = bloomDay.length;
        int sum = 0, prev = -1;
        for (int i = 0; i < n; i++) {
            if (bloomDay[i] > day) {
                sum += (i - prev - 1) / k;
                prev = i;
            }
        }
        sum += (n - prev - 1) / k;
        return sum;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 10, 3, 10, 2}, 3, 1)
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 10, 3, 10, 2}, 3, 2)
            .expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{7, 7, 7, 7, 12, 7, 7}, 2, 3)
            .expect(12);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{1000000000, 1000000000}, 1, 1)
            .expect(1000000000);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(new int[]{1, 10, 2, 9, 3, 8, 4, 7, 5, 6}, 4, 2)
            .expect(9);

}
