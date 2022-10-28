package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1997. First Day Where You Have Been in All the Rooms
 * https://leetcode.com/problems/first-day-where-you-have-been-in-all-the-rooms/
 *
 * There are n rooms you need to visit, labeled from 0 to n - 1. Each day is labeled, starting from 0. You will go in
 * and visit one room a day.
 *
 * Initially on day 0, you visit room 0. The order you visit the rooms for the coming days is determined by the
 * following rules and a given 0-indexed array nextVisit of length n:
 *
 * Assuming that on a day, you visit room i,
 * if you have been in room i an odd number of times (including the current visit), on the next day you will visit a
 * room with a lower or equal room number specified by nextVisit[i] where 0 <= nextVisit[i] <= i;
 * if you have been in room i an even number of times (including the current visit), on the next day you will visit room
 * (i + 1) mod n.
 *
 * Return the label of the first day where you have been in all the rooms. It can be shown that such a day exists. Since
 * the answer may be very large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: nextVisit = [0,0]
 * Output: 2
 * Explanation:
 * - On day 0, you visit room 0. The total times you have been in room 0 is 1, which is odd.
 * On the next day you will visit room nextVisit[0] = 0
 * - On day 1, you visit room 0, The total times you have been in room 0 is 2, which is even.
 * On the next day you will visit room (0 + 1) mod 2 = 1
 * - On day 2, you visit room 1. This is the first day where you have been in all the rooms.
 *
 * Example 2:
 *
 * Input: nextVisit = [0,0,2]
 * Output: 6
 * Explanation:
 * Your room visiting order for each day is: [0,0,1,0,0,1,2,...].
 * Day 6 is the first day where you have been in all the rooms.
 *
 * Example 3:
 *
 * Input: nextVisit = [0,1,2,0]
 * Output: 6
 * Explanation:
 * Your room visiting order for each day is: [0,0,1,1,2,2,3,...].
 * Day 6 is the first day where you have been in all the rooms.
 *
 * Constraints:
 *
 * n == nextVisit.length
 * 2 <= n <= 10^5
 * 0 <= nextVisit[i] <= i
 */
@RunWith(LeetCodeRunner.class)
public class Q1997_FirstDayWhereYouHaveBeenInAllTheRooms {

    @Answer
    public int firstDayBeenInAllRooms(int[] nextVisit) {
        final int mod = 10_0000_0007;
        final int n = nextVisit.length;
        long[][] dp = new long[n][2];
        dp[0][1] = 1;
        for (int i = 1; i < n; i++) {
            // 奇数次访问, 肯定来自于偶数次访问的 i-1
            dp[i][0] = (dp[i - 1][1] + 1) % mod;
            // 偶数次访问, 也来自于偶数次访问的i-1,
            // 奇数次访问后, 会跳到 j = nextVisit[i], 对于j 而言肯定是奇数次访问,
            // 则对于j 的奇数次访问再到i 的奇数次访问, 中间就间隔了 dp[i][0]-dp[j][1] 的次数,
            // +1 是最开始从i 跳到 j 的跳转次数.
            dp[i][1] = (dp[i][0] + (1 + dp[i][0] - dp[nextVisit[i]][0]) + mod) % mod;
        }
        return (int) dp[n - 1][0];
    }

    /**
     * leetcode 上最快的解法.
     */
    @Answer
    public int firstDayBeenInAllRooms_leetcode(int[] nextVisit) {
        final int mod = 10_0000_0007;
        final int n = nextVisit.length;
        // prev=nextVisit[i-1]
        int prev = 0;
        for (int i = 1; i < n; i++) {
            // 奇数次访问i 的天数 = (i-1奇数次访问算出来的i-1偶数次访问) + 1
            long days = (long) nextVisit[i - 1] + (nextVisit[i - 1] - nextVisit[prev] + 1) + 1;
            // p=这个i 奇数次访问跳转回的地址, 因为nextVisit[i] 将被挪作他用(空间优化)
            prev = nextVisit[i];
            // 用这个数组来缓存结果
            nextVisit[i] = (int) ((days + mod) % mod);
        }
        return nextVisit[n - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{0, 0}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{0, 0, 2}).expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{0, 1, 2, 0}).expect(6);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[74]).expect(320260018);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{
            0, 0, 1, 2, 4, 0, 1, 6, 0, 0, 2, 3, 4, 3, 4, 11, 6, 0, 16, 14, 20, 16, 9, 9, 1, 8, 8, 4, 14, 13, 5, 12, 8,
            18, 27, 34, 36, 13, 10, 35, 13, 31, 13, 29, 2, 45, 17, 30, 10, 18, 41, 14, 41, 22, 2, 4, 1, 15, 27, 35, 12,
            10, 46, 25, 61, 8, 65, 57, 48, 61, 8, 35, 2, 66, 47, 5, 54, 76, 73, 51, 13, 64, 15, 2
    }).expect(409272772);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new int[]{
            0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 15, 15, 16,
            16, 17, 17, 18, 18, 19, 19, 20, 20, 21, 21, 22, 22, 23, 23, 24, 24, 25, 25, 26, 26, 27, 27, 28, 28, 29, 29,
            30, 30, 31, 31, 32, 32, 33, 33, 34, 34, 35, 35, 36, 36, 37, 37, 38, 38, 39, 39, 40, 40, 41, 41, 42, 42, 43,
            43, 44, 44, 45, 45, 46, 46, 47, 47, 48
    }).expect(86417750);

}
