package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1583. Count Unhappy Friends
 * https://leetcode.com/problems/count-unhappy-friends/
 *
 * You are given a list of preferences for n friends, where n is always even.
 *
 * For each person i, preferences[i] contains a list of friends sorted in the order of preference. In other words, a
 * friend earlier in the list is more preferred than a friend later in the list. Friends in each list are denoted by
 * integers from 0 to n-1.
 *
 * All the friends are divided into pairs. The pairings are given in a list pairs, where pairs[i] = [xi, yi] denotes xi
 * is paired with yi and yi is paired with xi.
 *
 * However, this pairing may cause some of the friends to be unhappy. A friend x is unhappy if x is paired with y and
 * there exists a friend u who is paired with v but:
 *
 * x prefers u over y, and
 * u prefers x over v.
 *
 * Return the number of unhappy friends.
 *
 * Example 1:
 *
 * Input: n = 4, preferences = [[1, 2, 3], [3, 2, 0], [3, 1, 0], [1, 2, 0]], pairs = [[0, 1], [2, 3]]
 * Output: 2
 * Explanation:
 * Friend 1 is unhappy because:
 * - 1 is paired with 0 but prefers 3 over 0, and
 * - 3 prefers 1 over 2.
 * Friend 3 is unhappy because:
 * - 3 is paired with 2 but prefers 1 over 2, and
 * - 1 prefers 3 over 0.
 * Friends 0 and 2 are happy.
 *
 * Example 2:
 *
 * Input: n = 2, preferences = [[1], [0]], pairs = [[1, 0]]
 * Output: 0
 * Explanation: Both friends 0 and 1 are happy.
 *
 * Example 3:
 *
 * Input: n = 4, preferences = [[1, 3, 2], [2, 3, 0], [1, 3, 0], [0, 2, 1]], pairs = [[1, 3], [0, 2]]
 * Output: 4
 *
 * Constraints:
 *
 * 2 <= n <= 500
 * n is even.
 * preferences.length == n
 * preferences[i].length == n - 1
 * 0 <= preferences[i][j] <= n - 1
 * preferences[i] does not contain i.
 * All values in preferences[i] are unique.
 * pairs.length == n/2
 * pairs[i].length == 2
 * xi != yi
 * 0 <= xi, yi <= n - 1
 * Each person is contained in exactly one pair.
 */
@RunWith(LeetCodeRunner.class)
public class Q1583_CountUnhappyFriends {

    @Answer
    public int unhappyFriends(int n, int[][] preferences, int[][] pairs) {
        int[][] ranks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                ranks[i][preferences[i][j]] = j + 1;
            }
        }
        boolean[] unhappy = new boolean[n];
        int res = 0;
        for (int i = 0; i < pairs.length; i++) {
            for (int j = 0; j < pairs.length; j++) {
                if (i == j) {
                    continue;
                }
                int x = pairs[i][0], y = pairs[i][1];
                int u = pairs[j][0], v = pairs[j][1];
                if (!unhappy[x] && (ranks[x][u] < ranks[x][y] && ranks[u][x] < ranks[u][v]
                        || ranks[x][v] < ranks[x][y] && ranks[v][x] < ranks[v][u])
                ) {
                    unhappy[x] = true;
                    res++;
                }
                if (!unhappy[y] && (ranks[y][u] < ranks[y][x] && ranks[u][y] < ranks[u][v]
                        || ranks[y][v] < ranks[y][x] && ranks[v][y] < ranks[v][u])) {
                    unhappy[y] = true;
                    res++;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(4, new int[][]{{1, 2, 3}, {3, 2, 0}, {3, 1, 0}, {1, 2, 0}}, new int[][]{{0, 1}, {2, 3}})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(2, new int[][]{{1}, {0}}, new int[][]{{1, 0}})
            .expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(4, new int[][]{{1, 3, 2}, {2, 3, 0}, {1, 3, 0}, {0, 2, 1}}, new int[][]{{1, 3}, {0, 2}})
            .expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(6,
                    new int[][]{{1, 4, 3, 2, 5}, {0, 5, 4, 3, 2}, {3, 0, 1, 5, 4}, {2, 1, 4, 0, 5}, {2, 1, 0, 3, 5},
                            {3, 4, 2, 0, 1}}, new int[][]{{3, 1}, {2, 0}, {5, 4}})
            .expect(5);

}
