package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1575. Count All Possible Routes
 * https://leetcode.com/problems/count-all-possible-routes/
 *
 * You are given an array of distinct positive integers locations where locations[i] represents the position of city i.
 * You are also given integers start, finish and fuel representing the starting city, ending city, and the initial
 * amount of fuel you have, respectively.
 *
 * At each step, if you are at city i, you can pick any city j such that j != i and 0 <= j < locations.length and move
 * to city j. Moving from city i to city j reduces the amount of fuel you have by |locations[i] - locations[j]|. Please
 * notice that |x| denotes the absolute value of x.
 *
 * Notice that fuel cannot become negative at any point in time, and that you are allowed to visit any city more than
 * once (including start and finish).
 *
 * Return the count of all possible routes from start to finish.
 *
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: locations = [2,3,6,8,4], start = 1, finish = 3, fuel = 5
 * Output: 4
 * Explanation: The following are all possible routes, each uses 5 units of fuel:
 * 1 -> 3
 * 1 -> 2 -> 3
 * 1 -> 4 -> 3
 * 1 -> 4 -> 2 -> 3
 *
 * Example 2:
 *
 * Input: locations = [4,3,1], start = 1, finish = 0, fuel = 6
 * Output: 5
 * Explanation: The following are all possible routes:
 * 1 -> 0, used fuel = 1
 * 1 -> 2 -> 0, used fuel = 5
 * 1 -> 2 -> 1 -> 0, used fuel = 5
 * 1 -> 0 -> 1 -> 0, used fuel = 3
 * 1 -> 0 -> 1 -> 0 -> 1 -> 0, used fuel = 5
 *
 * Example 3:
 *
 * Input: locations = [5,2,1], start = 0, finish = 2, fuel = 3
 * Output: 0
 * Explanation: It's impossible to get from 0 to 2 using only 3 units of fuel since the shortest route needs 4 units of
 * fuel.
 *
 * Example 4:
 *
 * Input: locations = [2,1,5], start = 0, finish = 0, fuel = 3
 * Output: 2
 * Explanation: There are two possible routes, 0 and 0 -> 1 -> 0.
 *
 * Example 5:
 *
 * Input: locations = [1,2,3], start = 0, finish = 2, fuel = 40
 * Output: 615088286
 * Explanation: The total number of possible routes is 2615088300. Taking this number modulo 10^9 + 7 gives us
 * 615088286.
 *
 * Constraints:
 *
 * 2 <= locations.length <= 100
 * 1 <= locations[i] <= 10^9
 * All integers in locations are distinct.
 * 0 <= start, finish < locations.length
 * 1 <= fuel <= 200
 */
@RunWith(LeetCodeRunner.class)
public class Q1575_CountAllPossibleRoutes {

    @Answer
    public int countRoutes(int[] locations, int start, int finish, int fuel) {
        final int mod = 10_0000_0007;
        final int n = locations.length;
        // dp[f][i] 表示从 start 到城市 i 花费了 f 油的路径数量.
        int[][] dp = new int[fuel + 1][n];
        dp[0][start] = 1;
        for (int f = 0; f < fuel; f++) {
            for (int p = 0; p < n; p++) {
                for (int i = 0; i < n; i++) {
                    if (i != p) {
                        int cost = f + Math.abs(locations[i] - locations[p]);
                        if (cost <= fuel) {
                            dp[cost][i] = (dp[cost][i] + dp[f][p]) % mod;
                        }
                    }
                }
            }
        }

        int res = 0;
        for (int i = 0; i <= fuel; i++) {
            res = (res + dp[i][finish]) % mod;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{2, 3, 6, 8, 4}, 1, 3, 5)
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{4, 3, 1}, 1, 0, 6)
            .expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{5, 2, 1}, 0, 2, 3)
            .expect(0);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[]{2, 1, 5}, 0, 0, 3)
            .expect(2);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(new int[]{1, 2, 3}, 0, 2, 40)
            .expect(615088286);

}
