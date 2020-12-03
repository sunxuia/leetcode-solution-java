package q1400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1359. Count All Valid Pickup and Delivery Options
 * https://leetcode.com/problems/count-all-valid-pickup-and-delivery-options/
 *
 * Given n orders, each order consist in pickup and delivery services.
 *
 * Count all valid pickup/delivery possible sequences such that delivery(i) is always after of pickup(i).
 *
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: n = 1
 * Output: 1
 * Explanation: Unique order (P1, D1), Delivery 1 always is after of Pickup 1.
 *
 * Example 2:
 *
 * Input: n = 2
 * Output: 6
 * Explanation: All possible orders:
 * (P1,P2,D1,D2), (P1,P2,D2,D1), (P1,D1,P2,D2), (P2,P1,D1,D2), (P2,P1,D2,D1) and (P2,D2,P1,D1).
 * This is an invalid order (P1,D2,P2,D1) because Pickup 2 is after of Delivery 2.
 *
 * Example 3:
 *
 * Input: n = 3
 * Output: 90
 *
 * Constraints:
 *
 * 1 <= n <= 500
 */
@RunWith(LeetCodeRunner.class)
public class Q1359_CountAllValidPickupAndDeliveryOptions {

    @Answer
    public int countOrders(int n) {
        long res = 1;
        for (int i = 1; i < n; i++) {
            long count = 0;
            // 在上一个组合的缝隙中首先插入P
            for (int j = 0; j <= 2 * i; j++) {
                // 插入P 之后D 的可选插入位置
                count += 2 * i - j + 1;
            }
            res = res * count % 10_0000_0007;
        }
        return (int) res;
    }

    /**
     * 对上面里面的循环优化后的算法
     * 规律:
     * i = 1, count = 3 + 2 + 1 = 6
     * i = 2, count = 5 + 4 + 3 + 2 + 1 = 15
     * i = 3, count = 7 + 6 + 5 + 4 + 3 + 2 + 1 = 28
     * ...
     * count 是等差数列的和.
     */
    @Answer
    public int countOrders2(int n) {
        long res = 1, count = 1;
        for (int i = 1; i < n; i++) {
            count += i * 4 + 1;
            res = res * count % 10_0000_0007;
        }
        return (int) res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(1).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(2).expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.create(3).expect(90);

    @TestData
    public DataExpectation val4 = DataExpectation.create(4).expect(2520);

}
