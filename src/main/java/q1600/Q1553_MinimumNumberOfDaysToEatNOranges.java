package q1600;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1553. Minimum Number of Days to Eat N Oranges
 * https://leetcode.com/problems/minimum-number-of-days-to-eat-n-oranges/
 *
 * There are n oranges in the kitchen and you decided to eat some of these oranges every day as follows:
 *
 * Eat one orange.
 * If the number of remaining oranges (n) is divisible by 2 then you can eat  n/2 oranges.
 * If the number of remaining oranges (n) is divisible by 3 then you can eat  2*(n/3) oranges.
 *
 * You can only choose one of the actions per day.
 *
 * Return the minimum number of days to eat n oranges.
 *
 * Example 1:
 *
 * Input: n = 10
 * Output: 4
 * Explanation: You have 10 oranges.
 * Day 1: Eat 1 orange,  10 - 1 = 9.
 * Day 2: Eat 6 oranges, 9 - 2*(9/3) = 9 - 6 = 3. (Since 9 is divisible by 3)
 * Day 3: Eat 2 oranges, 3 - 2*(3/3) = 3 - 2 = 1.
 * Day 4: Eat the last orange  1 - 1  = 0.
 * You need at least 4 days to eat the 10 oranges.
 *
 * Example 2:
 *
 * Input: n = 6
 * Output: 3
 * Explanation: You have 6 oranges.
 * Day 1: Eat 3 oranges, 6 - 6/2 = 6 - 3 = 3. (Since 6 is divisible by 2).
 * Day 2: Eat 2 oranges, 3 - 2*(3/3) = 3 - 2 = 1. (Since 3 is divisible by 3)
 * Day 3: Eat the last orange  1 - 1  = 0.
 * You need at least 3 days to eat the 6 oranges.
 *
 * Example 3:
 *
 * Input: n = 1
 * Output: 1
 *
 * Example 4:
 *
 * Input: n = 56
 * Output: 6
 *
 * Constraints:
 *
 * 1 <= n <= 2*10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1553_MinimumNumberOfDaysToEatNOranges {

    /**
     * 根据提示, 可以利用 f(n) = min(n%2+1+f(n/2), n%3+1+f(n/3)) 的转移方程.
     */
    @Answer
    public int minDays(int n) {
        if (n <= 1) {
            return n;
        }
        if (CACHE.containsKey(n)) {
            return CACHE.get(n);
        }
        int res = Math.min(
                n % 2 + 1 + minDays(n / 2),
                n % 3 + 1 + minDays(n / 3));
        CACHE.put(n, res);
        return res;
    }

    /**
     * 不超时的关键在这里: 将多次运算的结果缓存起来 ╮(╯_╰)╭
     */
    private static Map<Integer, Integer> CACHE = new HashMap<>();

    @TestData
    public DataExpectation example1 = DataExpectation.create(10).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(6).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(1).expect(1);

    @TestData
    public DataExpectation example4 = DataExpectation.create(56).expect(6);

    @TestData
    public DataExpectation val16 = DataExpectation.create(16).expect(5);

    @TestData
    public DataExpectation val834827 = DataExpectation.create(834827).expect(23);

    @TestData
    public DataExpectation val2e9 = DataExpectation.create((int) 2e9).expect(32);

    @TestData
    public DataExpectation val1684352734 = DataExpectation.create(1684352734).expect(33);

}
