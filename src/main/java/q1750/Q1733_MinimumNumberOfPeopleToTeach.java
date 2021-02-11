package q1750;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1733. Minimum Number of People to Teach
 * https://leetcode.com/problems/minimum-number-of-people-to-teach/
 *
 * On a social network consisting of m users and some friendships between users, two users can communicate with each
 * other if they know a common language.
 *
 * You are given an integer n, an array languages, and an array friendships where:
 *
 * There are n languages numbered 1 through n,
 * languages[i] is the set of languages the ith user knows, and
 * friendships[i] = [ui, vi] denotes a friendship between the users ui and vi.
 *
 * You can choose one language and teach it to some users so that all friends can communicate with each other. Return <i
 * data-stringify-type="italic">the minimum <i data-stringify-type="italic">number of users you need to teach.
 * Note that friendships are not transitive, meaning if x is a friend of y and y is a friend of z, this doesn't
 * guarantee that x is a friend of z.
 *
 * Example 1:
 *
 * Input: n = 2, languages = [[1],[2],[1,2]], friendships = [[1,2],[1,3],[2,3]]
 * Output: 1
 * Explanation: You can either teach user 1 the second language or user 2 the first language.
 *
 * Example 2:
 *
 * Input: n = 3, languages = [[2],[1,3],[1,2],[3]], friendships = [[1,4],[1,2],[3,4],[2,3]]
 * Output: 2
 * Explanation: Teach the third language to users 1 and 3, yielding two users to teach.
 *
 * Constraints:
 *
 * 2 <= n <= 500
 * languages.length == m
 * 1 <= m <= 500
 * 1 <= languages[i].length <= n
 * 1 <= languages[i][j] <= n
 * 1 <= ui < vi <= languages.length
 * 1 <= friendships.length <= 500
 * All tuples (ui, vi) are unique
 * languages[i] contains only unique values
 *
 * 题解: n 表示语言个数(编号 1-n), m 表示人数(编号 1-m),
 * languages[i-1] 表示编号为 i 的人会说的语言, friendships[i] 中的 2 个元素表示 2 个人是朋友关系.
 */
@RunWith(LeetCodeRunner.class)
public class Q1733_MinimumNumberOfPeopleToTeach {

    /**
     * 按照 hint 的提示暴力求解即可.
     */
    @Answer
    public int minimumTeachings(int n, int[][] languages, int[][] friendships) {
        final int m = languages.length;
        // 每个人可以说的语言
        int[][] canSpeak = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int lang : languages[i]) {
                canSpeak[i + 1][lang]++;
            }
        }

        // 需要翻译的朋友关系
        List<int[]> translations = new ArrayList<>();
        for (int[] friendship : friendships) {
            int u = friendship[0], v = friendship[1];
            boolean com = false;
            for (int i = 1; i <= n && !com; i++) {
                com = canSpeak[u][i] > 0 && canSpeak[v][i] > 0;
            }
            if (!com) {
                translations.add(friendship);
            }
        }

        // 遍历每个语言, 找出最小结果
        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            // 标记需要服务的用户
            for (int[] translation : translations) {
                int u = translation[0], v = translation[1];
                if (canSpeak[u][i] == 0) {
                    canSpeak[u][i] = 2;
                }
                if (canSpeak[v][i] == 0) {
                    canSpeak[v][i] = 2;
                }
            }

            // 统计用户数
            int count = 0;
            for (int p = 1; p <= m; p++) {
                if (canSpeak[p][i] == 2) {
                    canSpeak[p][i] = 0;
                    count++;
                }
            }
            res = Math.min(res, count);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(2, new int[][]{{1}, {2}, {1, 2}}, new int[][]{{1, 2}, {1, 3}, {2, 3}})
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(3, new int[][]{{2}, {1, 3}, {1, 2}, {3}}, new int[][]{{1, 4}, {1, 2}, {3, 4}, {2, 3}})
            .expect(2);

}
