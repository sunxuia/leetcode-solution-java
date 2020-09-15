package q1100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1052. Grumpy Bookstore Owner
 * https://leetcode.com/problems/grumpy-bookstore-owner/
 *
 * Today, the bookstore owner has a store open for customers.length minutes.  Every minute, some number of customers
 * (customers[i]) enter the store, and all those customers leave after the end of that minute.
 *
 * On some minutes, the bookstore owner is grumpy.  If the bookstore owner is grumpy on the i-th minute, grumpy[i] = 1,
 * otherwise grumpy[i] = 0.  When the bookstore owner is grumpy, the customers of that minute are not satisfied,
 * otherwise they are satisfied.
 *
 * The bookstore owner knows a secret technique to keep themselves not grumpy for X minutes straight, but can only use
 * it once.
 *
 * Return the maximum number of customers that can be satisfied throughout the day.
 *
 * Example 1:
 *
 * Input: customers = [1,0,1,2,1,1,7,5], grumpy = [0,1,0,1,0,1,0,1], X = 3
 * Output: 16
 * Explanation: The bookstore owner keeps themselves not grumpy for the last 3 minutes.
 * The maximum number of customers that can be satisfied = 1 + 1 + 1 + 1 + 7 + 5 = 16.
 *
 * Note:
 *
 * 1 <= X <= customers.length == grumpy.length <= 20000
 * 0 <= customers[i] <= 1000
 * 0 <= grumpy[i] <= 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1052_GrumpyBookstoreOwner {

    /**
     * 滑动窗口
     */
    @Answer
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        final int n = customers.length;
        // 表示满意的顾客数
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += customers[i] * (1 - grumpy[i]);
        }
        // [left, i] 滑动窗口, 这个窗口内的不满意客户数要变成满意的
        int res = sum, left = 0;
        for (int i = 0; i < n; i++) {
            if (grumpy[i] == 1) {
                sum += customers[i];
                for (; left <= i - X; left++) {
                    sum -= customers[left] * grumpy[left];
                }
                res = Math.max(res, sum);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[]{1, 0, 1, 2, 1, 1, 7, 5}, new int[]{0, 1, 0, 1, 0, 1, 0, 1}, 3)
            .expect(16);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{4, 10, 10}, new int[]{1, 1, 0}, 2)
            .expect(24);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{2, 6, 6, 9}, new int[]{0, 0, 1, 1}, 1)
            .expect(17);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(new int[]{2, 4, 1, 4, 1}, new int[]{1, 0, 1, 0, 1}, 2)
            .expect(10);

}
