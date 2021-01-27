package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1599. Maximum Profit of Operating a Centennial Wheel
 * https://leetcode.com/problems/maximum-profit-of-operating-a-centennial-wheel/
 *
 * You are the operator of a Centennial Wheel that has four gondolas, and each gondola has room for up to four people.
 * You have the ability to rotate the gondolas counterclockwise, which costs you runningCost dollars.
 *
 * You are given an array customers of length n where customers[i] is the number of new customers arriving just before
 * the ith rotation (0-indexed). This means you must rotate the wheel i times before the customers[i] customers arrive.
 * You cannot make customers wait if there is room in the gondola. Each customer pays boardingCost dollars when they
 * board on the gondola closest to the ground and will exit once that gondola reaches the ground again.
 *
 * You can stop the wheel at any time, including before serving all customers. If you decide to stop serving customers,
 * all subsequent rotations are free in order to get all the customers down safely. Note that if there are currently
 * more than four customers waiting at the wheel, only four will board the gondola, and the rest will wait for the next
 * rotation.
 *
 * Return the minimum number of rotations you need to perform to maximize your profit. If there is no scenario where the
 * profit is positive, return -1.
 *
 * Example 1:
 * <img src="./Q1599_PIC.png">
 * Input: customers = [8,3], boardingCost = 5, runningCost = 6
 * Output: 3
 * Explanation: The numbers written on the gondolas are the number of people currently there.
 * 1. 8 customers arrive, 4 board and 4 wait for the next gondola, the wheel rotates. Current profit is 4 * $5 - 1 * $6
 * = $14.
 * 2. 3 customers arrive, the 4 waiting board the wheel and the other 3 wait, the wheel rotates. Current profit is 8 *
 * $5 - 2 * $6 = $28.
 * 3. The final 3 customers board the gondola, the wheel rotates. Current profit is 11 * $5 - 3 * $6 = $37.
 * The highest profit was $37 after rotating the wheel 3 times.
 *
 * Example 2:
 *
 * Input: customers = [10,9,6], boardingCost = 6, runningCost = 4
 * Output: 7
 * Explanation:
 * 1. 10 customers arrive, 4 board and 6 wait for the next gondola, the wheel rotates. Current profit is 4 * $6 - 1 * $4
 * = $20.
 * 2. 9 customers arrive, 4 board and 11 wait (2 originally waiting, 9 newly waiting), the wheel rotates. Current profit
 * is 8 * $6 - 2 * $4 = $40.
 * 3. The final 6 customers arrive, 4 board and 13 wait, the wheel rotates. Current profit is 12 * $6 - 3 * $4 = $60.
 * 4. 4 board and 9 wait, the wheel rotates. Current profit is 16 * $6 - 4 * $4 = $80.
 * 5. 4 board and 5 wait, the wheel rotates. Current profit is 20 * $6 - 5 * $4 = $100.
 * 6. 4 board and 1 waits, the wheel rotates. Current profit is 24 * $6 - 6 * $4 = $120.
 * 7. 1 boards, the wheel rotates. Current profit is 25 * $6 - 7 * $4 = $122.
 * The highest profit was $122 after rotating the wheel 7 times.
 *
 * Example 3:
 *
 * Input: customers = [3,4,0,5,1], boardingCost = 1, runningCost = 92
 * Output: -1
 * Explanation:
 * 1. 3 customers arrive, 3 board and 0 wait, the wheel rotates. Current profit is 3 * $1 - 1 * $92 = -$89.
 * 2. 4 customers arrive, 4 board and 0 wait, the wheel rotates. Current profit is 7 * $1 - 2 * $92 = -$177.
 * 3. 0 customers arrive, 0 board and 0 wait, the wheel rotates. Current profit is 7 * $1 - 3 * $92 = -$269.
 * 4. 5 customers arrive, 4 board and 1 waits, the wheel rotates. Current profit is 11 * $1 - 4 * $92 = -$357.
 * 5. 1 customer arrives, 2 board and 0 wait, the wheel rotates. Current profit is 13 * $1 - 5 * $92 = -$447.
 * The profit was never positive, so return -1.
 *
 * Example 4:
 *
 * Input: customers = [10,10,6,4,7], boardingCost = 3, runningCost = 8
 * Output: 9
 * Explanation:
 * 1. 10 customers arrive, 4 board and 6 wait, the wheel rotates. Current profit is 4 * $3 - 1 * $8 = $4.
 * 2. 10 customers arrive, 4 board and 12 wait, the wheel rotates. Current profit is 8 * $3 - 2 * $8 = $8.
 * 3. 6 customers arrive, 4 board and 14 wait, the wheel rotates. Current profit is 12 * $3 - 3 * $8 = $12.
 * 4. 4 customers arrive, 4 board and 14 wait, the wheel rotates. Current profit is 16 * $3 - 4 * $8 = $16.
 * 5. 7 customers arrive, 4 board and 17 wait, the wheel rotates. Current profit is 20 * $3 - 5 * $8 = $20.
 * 6. 4 board and 13 wait, the wheel rotates. Current profit is 24 * $3 - 6 * $8 = $24.
 * 7. 4 board and 9 wait, the wheel rotates. Current profit is 28 * $3 - 7 * $8 = $28.
 * 8. 4 board and 5 wait, the wheel rotates. Current profit is 32 * $3 - 8 * $8 = $32.
 * 9. 4 board and 1 waits, the wheel rotates. Current profit is 36 * $3 - 9 * $8 = $36.
 * 10. 1 board and 0 wait, the wheel rotates. Current profit is 37 * $3 - 10 * $8 = $31.
 * The highest profit was $36 after rotating the wheel 9 times.
 *
 * Constraints:
 *
 * n == customers.length
 * 1 <= n <= 10^5
 * 0 <= customers[i] <= 50
 * 1 <= boardingCost, runningCost <= 100
 *
 * 题解: 停下来后不用考虑把上面的客户放下来的问题.
 */
@RunWith(LeetCodeRunner.class)
public class Q1599_MaximumProfitOfOperatingACentennialWheel {

    @Answer
    public int minOperationsMaxProfit(int[] customers, int boardingCost, int runningCost) {
        final int n = customers.length;
        if (boardingCost * 4 <= runningCost) {
            return -1;
        }
        int res = -1, max = 0, sum = 0, people = 0;
        for (int i = 0; i < n; i++) {
            people += customers[i];
            if (people > 4) {
                sum += boardingCost * 4 - runningCost;
                people -= 4;
            } else {
                sum += boardingCost * people - runningCost;
                people = 0;
            }
            if (sum > max) {
                res = i + 1;
                max = sum;
            }
        }
        int remains = people % 4, lastRound;
        if (remains > 0 && remains * boardingCost <= runningCost) {
            sum += boardingCost * (people - remains) - people / 4 * runningCost;
            lastRound = n + people / 4;
        } else {
            sum += boardingCost * people - (people + 3) / 4 * runningCost;
            lastRound = n + (people + 3) / 4;
        }
        if (sum > max) {
            res = lastRound;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{8, 3}, 5, 6)
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{10, 9, 6}, 6, 4)
            .expect(7);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{3, 4, 0, 5, 1}, 1, 92)
            .expect(-1);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{10, 10, 6, 4, 7}, 3, 8)
            .expect(9);

}
