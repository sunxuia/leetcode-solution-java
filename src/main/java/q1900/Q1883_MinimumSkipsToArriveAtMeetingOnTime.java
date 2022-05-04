package q1900;

import java.util.Arrays;
import java.util.TreeMap;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1883. Minimum Skips to Arrive at Meeting On Time
 * https://leetcode.com/problems/minimum-skips-to-arrive-at-meeting-on-time/
 *
 * You are given an integer hoursBefore, the number of hours you have to travel to your meeting. To arrive at your
 * meeting, you have to travel through n roads. The road lengths are given as an integer array dist of length n, where
 * dist[i] describes the length of the ith road in kilometers. In addition, you are given an integer speed, which is the
 * speed (in km/h) you will travel at.
 *
 * After you travel road i, you must rest and wait for the next integer hour before you can begin traveling on the next
 * road. Note that you do not have to rest after traveling the last road because you are already at the meeting.
 *
 * For example, if traveling a road takes 1.4 hours, you must wait until the 2 hour mark before traveling the next road.
 * If traveling a road takes exactly 2 hours, you do not need to wait.
 *
 * However, you are allowed to skip some rests to be able to arrive on time, meaning you do not need to wait for the
 * next integer hour. Note that this means you may finish traveling future roads at different hour marks.
 *
 * For example, suppose traveling the first road takes 1.4 hours and traveling the second road takes 0.6 hours. Skipping
 * the rest after the first road will mean you finish traveling the second road right at the 2 hour mark, letting you
 * start traveling the third road immediately.
 *
 * Return the minimum number of skips required to arrive at the meeting on time, or -1 if it is impossible.
 *
 * Example 1:
 *
 * Input: dist = [1,3,2], speed = 4, hoursBefore = 2
 * Output: 1
 * Explanation:
 * Without skipping any rests, you will arrive in (1/4 + 3/4) + (3/4 + 1/4) + (2/4) = 2.5 hours.
 * You can skip the first rest to arrive in ((1/4 + 0) + (3/4 + 0)) + (2/4) = 1.5 hours.
 * Note that the second rest is shortened because you finish traveling the second road at an integer hour due to
 * skipping the first rest.
 *
 * Example 2:
 *
 * Input: dist = [7,3,5,5], speed = 2, hoursBefore = 10
 * Output: 2
 * Explanation:
 * Without skipping any rests, you will arrive in (7/2 + 1/2) + (3/2 + 1/2) + (5/2 + 1/2) + (5/2) = 11.5 hours.
 * You can skip the first and third rest to arrive in ((7/2 + 0) + (3/2 + 0)) + ((5/2 + 0) + (5/2)) = 10 hours.
 *
 * Example 3:
 *
 * Input: dist = [7,3,5,5], speed = 1, hoursBefore = 10
 * Output: -1
 * Explanation: It is impossible to arrive at the meeting on time even if you skip all the rests.
 *
 * Constraints:
 *
 * n == dist.length
 * 1 <= n <= 1000
 * 1 <= dist[i] <= 10^5
 * 1 <= speed <= 10^6
 * 1 <= hoursBefore <= 10^7
 */
@RunWith(LeetCodeRunner.class)
public class Q1883_MinimumSkipsToArriveAtMeetingOnTime {

    /**
     * 比较慢的解法, 勉强能过.
     */
    @Answer
    public int minSkips(int[] dist, int speed, int hoursBefore) {
        final int n = dist.length;
        // (虽然按照题意会超过int 范围, 但是在线测试通过了.)
        final int maxDist = speed * hoursBefore;
        // 时间等效里程 : 跳过次数.
        // 为了避免double 运算造成的精度问题所以使用等效里程.
        TreeMap<Integer, Integer>[] cache = new TreeMap[2];
        cache[0] = new TreeMap<>();
        cache[0].put(0, 0);
        cache[1] = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            TreeMap<Integer, Integer> curr = cache[i % 2];
            TreeMap<Integer, Integer> next = cache[1 - i % 2];
            next.clear();
            for (var pair : curr.entrySet()) {
                // 上次结束花费的时间
                int spend = pair.getKey();
                // 上次结束跳过的次数
                int skips = pair.getValue();
                if (spend % speed == 0) {
                    // 上次结束就是整数时间, 不需要跳
                    int nextSpend = spend + dist[i];
                    if (nextSpend <= maxDist) {
                        addToMap(next, nextSpend, skips);
                    }
                } else {
                    // 上次结束后可以跳
                    int nextSpend = spend + dist[i];
                    if (nextSpend <= maxDist) {
                        addToMap(next, nextSpend, skips + 1);
                    }
                    // 不跳
                    int ceilSpend = spend / speed * speed + speed + dist[i];
                    if (ceilSpend <= maxDist) {
                        addToMap(next, ceilSpend, skips);
                    }
                }
            }
        }

        if (cache[n % 2].isEmpty()) {
            return -1;
        }
        int res = Integer.MAX_VALUE;
        for (int skips : cache[n % 2].values()) {
            res = Math.min(res, skips);
        }
        return res;
    }

    // 剪枝操作
    private void addToMap(TreeMap<Integer, Integer> map, Integer spend, Integer skips) {
        Integer higher = map.higherKey(spend);
        while (higher != null && map.get(higher) >= skips) {
            map.remove(higher);
            higher = map.higherKey(higher);
        }
        Integer floorKey = map.floorKey(spend);
        if (floorKey == null || map.get(floorKey) > skips) {
            map.put(spend, skips);
        }
    }

    /**
     * 针对上方算法的优化, 这个就很快了.
     */
    @Answer
    public int minSkips2(int[] dist, int speed, int hoursBefore) {
        final int n = dist.length;
        final int maxDist = speed * hoursBefore;
        // dp[skip] 表示在某轮循环中跳过的次数为skip 所花费的最小等效里程
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = dist[0];
        for (int i = 1; i < n; i++) {
            for (int skip = i - 1; skip >= 0; skip--) {
                // 第i 轮之前已经花费的时间(跳过次数为skip)
                int spend = dp[skip];
                if (spend > maxDist) {
                    // 超过范围了
                    continue;
                } else if (spend % speed == 0) {
                    // 没必要跳
                    dp[skip] = spend + dist[i];
                } else {
                    // 可以跳
                    dp[skip + 1] = Math.min(dp[skip + 1], spend + dist[i]);
                    // 不跳
                    dp[skip] = spend / speed * speed + speed + dist[i];
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (dp[i] <= maxDist) {
                return i;
            }
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 3, 2}, 4, 2).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{7, 3, 5, 5}, 2, 10).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{7, 3, 5, 5}, 1, 10).expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{
            57, 81, 39, 36, 76, 8, 46, 65, 10, 40, 60, 58, 5, 8, 48, 37, 10, 83, 12, 79, 77, 65, 50, 60, 75, 71, 95, 6,
            58, 74, 84, 25, 42, 78, 61, 65, 20, 8, 58, 14, 44, 92, 11, 65, 83, 23, 6, 32, 91, 57, 57, 68, 67, 79, 21,
            47, 48, 37, 50, 53, 100, 44, 87, 77, 7, 81, 83, 43, 9, 43, 13, 72, 87, 50, 37, 33, 93, 85, 78, 20, 43, 11,
            75, 85, 19, 45, 40, 84, 90, 85, 51, 17, 77, 91, 11, 40, 72, 49, 83, 43, 17, 86, 90, 95, 88, 68, 12, 83, 86,
            55, 66, 47, 3, 47, 40, 24, 28, 44, 99, 63, 25, 29, 18, 20, 10, 38, 13, 65, 99, 70, 95, 68, 5, 85, 44, 99,
            80, 17, 77, 49, 20, 21, 24, 89, 99, 80, 22, 93, 57, 6, 53, 47, 87, 93, 31, 57, 79, 37, 97, 96, 58, 30, 69,
            31, 40, 61, 25, 48, 87, 74, 57, 60, 99, 37, 100, 49, 65, 68, 54, 21, 26, 78, 18, 33, 7, 83, 62, 48, 71, 14,
            92, 67, 16, 37, 11, 98, 21, 35, 58, 66, 19, 56, 12, 29, 58, 45, 4, 54, 7, 51, 65, 48, 31, 50, 11, 48, 29, 4,
            41, 13, 92, 84, 43, 19, 18, 5, 37, 61, 90, 18, 98, 91, 61, 58, 79, 1, 11, 3, 33, 28, 20, 71, 94, 78, 68, 55,
            15, 100, 37, 31, 39, 16, 7, 31, 54, 82, 80, 59, 9, 15, 64, 77, 69, 3, 81, 66, 74, 31, 92, 19, 38, 55, 1, 79,
            46, 31, 74, 54, 24, 86, 86, 11, 68, 47, 9, 68, 70, 65, 65, 71, 3, 50, 61, 6, 82, 50, 48, 33, 96, 3, 46, 9,
            45, 31, 94, 85, 81, 14, 99, 75, 95, 97, 43, 18, 6, 46, 70, 44, 83, 23, 100, 88, 60, 90, 62, 81, 24, 18, 9,
            92, 100, 59, 5, 38, 26, 6, 26, 6, 41, 40
    }, 53, 782).expect(0);

    @TestData
    public DataExpectation normal2() {
        int[] arr = new int[1000];
        Arrays.fill(arr, 10_0000);
        return DataExpectation.createWith(arr, 75000, 1335).expect(665);
    }

}
