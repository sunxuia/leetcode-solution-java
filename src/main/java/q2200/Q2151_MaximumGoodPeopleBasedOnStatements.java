package q2200;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2151. Maximum Good People Based on Statements</h3>
 * <a href="https://leetcode.com/problems/maximum-good-people-based-on-statements/">
 * https://leetcode.com/problems/maximum-good-people-based-on-statements/
 * </a><br/>
 *
 * <p>There are two types of persons:</p>
 *
 * <ul>
 * 	<li>The <strong>good person</strong>: The person who always tells the truth.</li>
 * 	<li>The <strong>bad person</strong>: The person who might tell the truth and might lie.</li>
 * </ul>
 *
 * <p>You are given a <strong>0-indexed</strong> 2D integer array <code>statements</code> of size <code>n x n</code>
 * that represents the statements made by <code>n</code> people about each other. More specifically,
 * <code>statements[i][j]</code> could be one of the following:</p>
 *
 * <ul>
 * 	<li><code>0</code> which represents a statement made by person <code>i</code> that person <code>j</code> is a
 * 	<strong>bad</strong> person.</li>
 * 	<li><code>1</code> which represents a statement made by person <code>i</code> that person <code>j</code> is a
 * 	<strong>good</strong> person.</li>
 * 	<li><code>2</code> represents that <strong>no statement</strong> is made by person <code>i</code> about person
 * 	<code>j</code>.</li>
 * </ul>
 *
 * <p>Additionally, no person ever makes a statement about themselves. Formally, we have that <code>statements[i][i]
 * = 2</code> for all <code>0 &lt;= i &lt; n</code>.</p>
 *
 * <p>Return <em>the <strong>maximum</strong> number of people who can be <strong>good</strong> based on the
 * statements made by the </em><code>n</code><em> people</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2022/01/15/logic1.jpg" style="width: 600px; height: 262px;" />
 * <pre>
 * <strong>Input:</strong> statements = [[2,1,2],[1,2,2],[2,0,2]]
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong> Each person makes a single statement.
 * - Person 0 states that person 1 is good.
 * - Person 1 states that person 0 is good.
 * - Person 2 states that person 1 is bad.
 * Let&#39;s take person 2 as the key.
 * - Assuming that person 2 is a good person:
 *     - Based on the statement made by person 2, person 1 is a bad person.
 *     - Now we know for sure that person 1 is bad and person 2 is good.
 *     - Based on the statement made by person 1, and since person 1 is bad, they could be:
 *         - telling the truth. There will be a contradiction in this case and this assumption is invalid.
 *         - lying. In this case, person 0 is also a bad person and lied in their statement.
 *     - <strong>Following that person 2 is a good person, there will be only one good person in the group</strong>.
 * - Assuming that person 2 is a bad person:
 *     - Based on the statement made by person 2, and since person 2 is bad, they could be:
 *         - telling the truth. Following this scenario, person 0 and 1 are both bad as explained before.
 *             - <strong>Following that person 2 is bad but told the truth, there will be no good persons in the group</strong>.
 *         - lying. In this case person 1 is a good person.
 *             - Since person 1 is a good person, person 0 is also a good person.
 *             - <strong>Following that person 2 is bad and lied, there will be two good persons in the group</strong>.
 * We can see that at most 2 persons are good in the best case, so we return 2.
 * Note that there is more than one way to arrive at this conclusion.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2022/01/15/logic2.jpg" style="width: 600px; height: 262px;" />
 * <pre>
 * <strong>Input:</strong> statements = [[2,0],[0,2]]
 * <strong>Output:</strong> 1
 * <strong>Explanation:</strong> Each person makes a single statement.
 * - Person 0 states that person 1 is bad.
 * - Person 1 states that person 0 is bad.
 * Let&#39;s take person 0 as the key.
 * - Assuming that person 0 is a good person:
 *     - Based on the statement made by person 0, person 1 is a bad person and was lying.
 *     - <strong>Following that person 0 is a good person, there will be only one good person in the group</strong>.
 * - Assuming that person 0 is a bad person:
 *     - Based on the statement made by person 0, and since person 0 is bad, they could be:
 *         - telling the truth. Following this scenario, person 0 and 1 are both bad.
 *             - <strong>Following that person 0 is bad but told the truth, there will be no good persons in the group</strong>.
 *         - lying. In this case person 1 is a good person.
 *             - <strong>Following that person 0 is bad and lied, there will be only one good person in the group</strong>.
 * We can see that at most, one person is good in the best case, so we return 1.
 * Note that there is more than one way to arrive at this conclusion.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>n == statements.length == statements[i].length</code></li>
 * 	<li><code>2 &lt;= n &lt;= 15</code></li>
 * 	<li><code>statements[i][j]</code> is either <code>0</code>, <code>1</code>, or <code>2</code>.</li>
 * 	<li><code>statements[i][i] == 2</code></li>
 * </ul>
 *
 * <pre>
 *     题解: 题目不好, 示例有误导性.
 *     坏人说的话不是全部假话或者全部真话, 而是有真有假, 不可信.
 * </pre>
 */
@RunWith(LeetCodeRunner.class)
public class Q2151_MaximumGoodPeopleBasedOnStatements {

    /**
     * BFS, 时间复杂度 O(2^n)
     */
    @Answer
    public int maximumGood(int[][] statements) {
        final int n = statements.length;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0, 0});
        for (int i = 0; i < n && !queue.isEmpty(); i++) {
            // 自己认为自己是好人
            int good = 1 << i;
            int bad = 0;
            for (int j = 0; j < n; j++) {
                if (statements[i][j] == 0) {
                    bad |= 1 << j;
                } else if (statements[i][j] == 1) {
                    good |= 1 << j;
                }
            }

            for (int cycle = queue.size(); cycle > 0; cycle--) {
                int[] prev = queue.poll();
                int count = prev[0];
                int preGood = prev[1];
                int preBad = prev[2];
                boolean beGood = (preBad & good) == 0 && (preGood & bad) == 0;
                if (beGood) {
                    queue.offer(new int[]{count + 1, preGood | good, preBad | bad});
                }
                boolean beBad = (preGood & (1 << i)) == 0;
                if (beBad) {
                    queue.offer(new int[]{count, preGood, preBad | (1 << i)});
                }
            }
        }

        int res = 0;
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            res = Math.max(res, pos[0]);
        }
        return res;
    }

    /**
     * bit-dp 的解法, 相当于是上面 bfs 解法的 dp 改造.
     * 稍微慢一些, 可以优化到比 BFS 稍微快一点, 但是不影响时间复杂度.
     */
    @Answer
    public int maximumGood2(int[][] statements) {
        final int n = statements.length;
        final int maskLen = 1 << n;
        int[][] stats = new int[n][2];
        for (int i = 0; i < n; i++) {
            // 自己认定自己是好人
            stats[i][1] = 1 << i;
            for (int j = 0; j < n; j++) {
                if (statements[i][j] <= 1) {
                    stats[i][statements[i][j]] |= 1 << j;
                }
            }
        }

        // dp[mask][0] 表示当前 mask 对应的推测坏人掩码
        // dp[mask][1] 表示当前 mask 对应的推测好人掩码
        // dp[mask][2] 表示遍历到此的最多好人数量
        int[][] dp = new int[maskLen][3];
        // 每次循环就最高位标识的人是好人坏人的情况进行检查
        int p = 0;
        for (int mask = 1; mask < maskLen; mask++) {
            if (mask == 2 << p) {
                // 检查到下一个人了
                p++;
            }
            int[] curr = dp[mask];
            int bit = 1 << p;
            // 去掉最高位的掩码, 从这个已检查过的来推出加上高位的 curr 的结果.
            int[] low = dp[mask ^ bit];
            if (low[2] < 0) {
                curr[2] = -1;
                continue;
            }

            // 检查如果高位是好人, curr 是否能成立
            boolean beGood = (low[0] & stats[p][1]) == 0
                    && (low[1] & stats[p][0]) == 0;
            if (beGood) {
                curr[0] = low[0] | stats[p][0];
                curr[1] = low[1] | stats[p][1];
                curr[2] = low[2] + 1;
            } else {
                curr[2] = -1;
            }

            // 检查如果高位是坏人, low 能否成立
            boolean beBad = (low[1] & bit) == 0;
            if (beBad) {
                low[0] = low[0] | bit;
            } else {
                low[2] = -1;
            }
        }

        int res = 0;
        for (int i = 1; i < maskLen; i++) {
            res = Math.max(res, dp[i][2]);
        }
        return res;
    }

    /**
     * dfs 的解法, 时间复杂度一样, 比 bfs 快一点.
     */
    @Answer
    public int maximumGood3(int[][] statements) {
        final int n = statements.length;
        int[][] stats = new int[n][2];
        for (int i = 0; i < n; i++) {
            // 自己认定自己是好人
            stats[i][1] = 1 << i;
            for (int j = 0; j < n; j++) {
                if (statements[i][j] <= 1) {
                    stats[i][statements[i][j]] |= 1 << j;
                }
            }
        }

        return dfs(stats, 0, 0, 0);
    }

    private int dfs(int[][] stats, int good, int bad, int person) {
        final int n = stats.length;
        if ((good & bad) != 0) {
            return Integer.MIN_VALUE;
        }
        if (person == n) {
            return 0;
        }
        int beGood = dfs(stats, good | stats[person][1], bad | stats[person][0], person + 1);
        int beBad = dfs(stats, good, bad | (1 << person), person + 1);
        return Math.max(beGood + 1, beBad);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
                    {2, 1, 2},
                    {1, 2, 2},
                    {2, 0, 2}})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
                    {2, 0},
                    {0, 2}})
            .expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
                    {2, 2, 2, 2},
                    {1, 2, 1, 0},
                    {0, 2, 2, 2},
                    {0, 0, 0, 2}})
            .expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[][]{
                    {2, 1, 0, 0, 2},
                    {2, 2, 1, 0, 2},
                    {0, 2, 2, 1, 0},
                    {2, 0, 0, 2, 0},
                    {2, 0, 0, 1, 2}})
            .expect(1);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new int[][]{
                    {2, 0, 0, 2, 2, 0, 1, 2},
                    {0, 2, 1, 0, 0, 1, 1, 0},
                    {0, 0, 2, 0, 2, 0, 1, 2},
                    {1, 0, 2, 2, 1, 0, 0, 1},
                    {1, 1, 2, 1, 2, 2, 1, 0},
                    {2, 0, 0, 0, 1, 2, 0, 0},
                    {0, 2, 2, 0, 2, 1, 2, 0},
                    {2, 1, 2, 0, 0, 1, 0, 2}})
            .expect(0);

    @TestData
    public DataExpectation normal4 = DataExpectation.create(new int[][]{
                    {2, 2},
                    {2, 2}})
            .expect(2);

}
