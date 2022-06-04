package q1950;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1921. Eliminate Maximum Number of Monsters
 * https://leetcode.com/problems/eliminate-maximum-number-of-monsters/
 *
 * You are playing a video game where you are defending your city from a group of n monsters. You are given a 0-indexed
 * integer array dist of size n, where dist[i] is the initial distance in kilometers of the ith monster from the city.
 *
 * The monsters walk toward the city at a constant speed. The speed of each monster is given to you in an integer array
 * speed of size n, where speed[i] is the speed of the ith monster in kilometers per minute.
 *
 * You have a weapon that, once fully charged, can eliminate a single monster. However, the weapon takes one minute to
 * charge.The weapon is fully charged at the very start.
 *
 * You lose when any monster reaches your city. If a monster reaches the city at the exact moment the weapon is fully
 * charged, it counts as a loss, and the game ends before you can use your weapon.
 *
 * Return the maximum number of monsters that you can eliminate before you lose, or n if you can eliminate all the
 * monsters before they reach the city.
 *
 * Example 1:
 *
 * Input: dist = [1,3,4], speed = [1,1,1]
 * Output: 3
 * Explanation:
 * In the beginning, the distances of the monsters are [1,3,4]. You eliminate the first monster.
 * After a minute, the distances of the monsters are [X,2,3]. You eliminate the second monster.
 * After a minute, the distances of the monsters are [X,X,2]. You eliminate the thrid monster.
 * All 3 monsters can be eliminated.
 *
 * Example 2:
 *
 * Input: dist = [1,1,2,3], speed = [1,1,1,1]
 * Output: 1
 * Explanation:
 * In the beginning, the distances of the monsters are [1,1,2,3]. You eliminate the first monster.
 * After a minute, the distances of the monsters are [X,0,1,2], so you lose.
 * You can only eliminate 1 monster.
 *
 * Example 3:
 *
 * Input: dist = [3,2,4], speed = [5,3,2]
 * Output: 1
 * Explanation:
 * In the beginning, the distances of the monsters are [3,2,4]. You eliminate the first monster.
 * After a minute, the distances of the monsters are [X,0,2], so you lose.
 * You can only eliminate 1 monster.
 *
 * Constraints:
 *
 * n == dist.length == speed.length
 * 1 <= n <= 10^5
 * 1 <= dist[i], speed[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1921_EliminateMaximumNumberOfMonsters {

    @Answer
    public int eliminateMaximum(int[] dist, int[] speed) {
        final int n = dist.length;
        Integer[] sort = new Integer[n];
        for (int i = 0; i < n; i++) {
            sort[i] = i;
        }
        // 按照monster 到达城市的时间排序
        Arrays.sort(sort, (a, b) -> dist[a] * speed[b] - dist[b] * speed[a]);

        for (int i = 0; i < n; i++) {
            int arrive = dist[sort[i]] / speed[sort[i]];
            boolean arriveOnTime = dist[sort[i]] % speed[sort[i]] == 0;
            if (i > arrive || i == arrive && arriveOnTime) {
                return i;
            }
        }
        return n;
    }


    @Answer
    public int eliminateMaximum_bucket(int[] dist, int[] speed) {
        final int n = dist.length;
        int[] buckets = new int[n];
        for (int i = 0; i < n; i++) {
            int time = dist[i] / speed[i];
            if (dist[i] % speed[i] != 0) {
                time += 1;
            }
            if (time < n) {
                buckets[time]++;
            }
        }

        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += buckets[i];
            if (sum > i) {
                return i;
            }
        }
        return n;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 3, 4}, new int[]{1, 1, 1})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 1, 2, 3}, new int[]{1, 1, 1, 1})
            .expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{3, 2, 4}, new int[]{5, 3, 2})
            .expect(1);

}
