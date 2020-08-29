package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1010. Pairs of Songs With Total Durations Divisible by 60
 * https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/
 *
 * In a list of songs, the i-th song has a duration of time[i] seconds.
 *
 * Return the number of pairs of songs for which their total duration in seconds is divisible by 60.  Formally, we want
 * the number of indices i, j such that i < j with (time[i] + time[j]) % 60 == 0.
 *
 * Example 1:
 *
 * Input: [30,20,150,100,40]
 * Output: 3
 * Explanation: Three pairs have a total duration divisible by 60:
 * (time[0] = 30, time[2] = 150): total duration 180
 * (time[1] = 20, time[3] = 100): total duration 120
 * (time[1] = 20, time[4] = 40): total duration 60
 *
 * Example 2:
 *
 * Input: [60,60,60]
 * Output: 3
 * Explanation: All three pairs have a total duration of 120, which is divisible by 60.
 *
 * Note:
 *
 * 1 <= time.length <= 60000
 * 1 <= time[i] <= 500
 */
@RunWith(LeetCodeRunner.class)
public class Q1010_PairsOfSongsWithTotalDurationsDivisibleBy60 {

    @Answer
    public int numPairsDivisibleBy60(int[] time) {
        int[] counts = new int[501];
        for (int t : time) {
            counts[t]++;
        }
        int res = 0;
        for (int t : time) {
            counts[t]--;
            for (int i = 0; i < 1000; i += 60) {
                if (0 < i - t && i - t <= 500) {
                    res += counts[i - t];
                }
            }
        }
        return res;
    }

    /**
     * LeetCode 上最快的解法.
     */
    @Answer
    public int numPairsDivisibleBy60_LeetCode(int[] time) {
        // 获取t 相对60 的余数
        int[] remains = new int[60];
        for (int t : time) {
            remains[t % 60]++;
        }

        // 通过余数计算和为倍数的情况
        int res = 0;
        // 边界值
        res += remains[0] * (remains[0] - 1) / 2;
        res += remains[30] * (remains[30] - 1) / 2;
        for (int i = 1; i < 30; i++) {
            res += remains[i] * remains[60 - i];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{30, 20, 150, 100, 40}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{60, 60, 60}).expect(3);

}
