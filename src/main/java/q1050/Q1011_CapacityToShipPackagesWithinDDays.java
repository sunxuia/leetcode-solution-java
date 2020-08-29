package q1050;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1011. Capacity To Ship Packages Within D Days
 * https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/
 *
 * A conveyor belt has packages that must be shipped from one port to another within D days.
 *
 * The i-th package on the conveyor belt has a weight of weights[i].  Each day, we load the ship with packages on the
 * conveyor belt (in the order given by weights). We may not load more weight than the maximum weight capacity of the
 * ship.
 *
 * Return the least weight capacity of the ship that will result in all the packages on the conveyor belt being shipped
 * within D days.
 *
 * Example 1:
 *
 * Input: weights = [1,2,3,4,5,6,7,8,9,10], D = 5
 * Output: 15
 * Explanation:
 * A ship capacity of 15 is the minimum to ship all the packages in 5 days like this:
 * 1st day: 1, 2, 3, 4, 5
 * 2nd day: 6, 7
 * 3rd day: 8
 * 4th day: 9
 * 5th day: 10
 *
 * Note that the cargo must be shipped in the order given, so using a ship of capacity 14 and splitting the packages
 * into parts like (2, 3, 4, 5), (1, 6, 7), (8), (9), (10) is not allowed.
 *
 * Example 2:
 *
 * Input: weights = [3,2,2,4,1,4], D = 3
 * Output: 6
 * Explanation:
 * A ship capacity of 6 is the minimum to ship all the packages in 3 days like this:
 * 1st day: 3, 2
 * 2nd day: 2, 4
 * 3rd day: 1, 4
 *
 * Example 3:
 *
 * Input: weights = [1,2,3,1,1], D = 4
 * Output: 3
 * Explanation:
 * 1st day: 1
 * 2nd day: 2
 * 3rd day: 3
 * 4th day: 1, 1
 *
 * Constraints:
 *
 * 1 <= D <= weights.length <= 50000
 * 1 <= weights[i] <= 500
 */
@RunWith(LeetCodeRunner.class)
public class Q1011_CapacityToShipPackagesWithinDDays {

    /**
     * 从最小值开始往上慢慢查找. 这种解法很慢.
     */
    @Answer
    public int shipWithinDays(int[] weights, int D) {
        final int n = weights.length;
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + weights[i];
        }
        int res = sums[n] / D;
        while (true) {
            int total = res;
            for (int i = 1; i < D; i++) {
                int idx = Arrays.binarySearch(sums, total);
                idx = idx >= 0 ? idx : -idx - 2;
                total = sums[idx] + res;
            }
            if (total >= sums[n]) {
                return res;
            }
            res++;
        }
    }

    /**
     * LeetCode 上的解法, 使用二分查找的方式.
     */
    @Answer
    public int shipWithinDays2(int[] weights, int D) {
        // 找出轮船吨位的最小值和最大值
        int start = 0, end = 0;
        for (int weight : weights) {
            start = Math.max(weight, start);
            end += weight;
        }

        while (start <= end) {
            int mid = start + (end - start) / 2;
            // 计算轮船吨位为mid 的情况下需要花多少天
            int days = 1, sum = 0;
            for (int wt : weights) {
                sum += wt;
                if (sum > mid) {
                    days++;
                    sum = wt;
                }
            }
            if (days > D) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return start;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 5)
            .expect(15);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{3, 2, 2, 4, 1, 4}, 3).expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 2, 3, 1, 1}, 4).expect(3);

}
