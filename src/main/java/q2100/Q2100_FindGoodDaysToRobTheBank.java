package q2100;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2100. Find Good Days to Rob the Bank</h3>
 * <a href="https://leetcode.com/problems/find-good-days-to-rob-the-bank/">
 * https://leetcode.com/problems/find-good-days-to-rob-the-bank/
 * </a><br/>
 *
 * <p>You and a gang of thieves are planning on robbing a bank. You are given a <strong>0-indexed</strong> integer array
 * <code>security</code>, where <code>security[i]</code> is the number of guards on duty on the
 * <code>i<sup>th</sup></code> day. The days are numbered starting from <code>0</code>. You are also given an integer
 * <code>time</code>.</p>
 *
 * <p>The <code>i<sup>th</sup></code> day is a good day to rob the bank if:</p>
 *
 * <ul>
 * 	<li>There are at least <code>time</code> days before and after the <code>i<sup>th</sup></code> day,</li>
 * 	<li>The number of guards at the bank for the <code>time</code> days <strong>before</strong> <code>i</code> are
 * 	<strong>non-increasing</strong>, and</li>
 * 	<li>The number of guards at the bank for the <code>time</code> days <strong>after</strong> <code>i</code> are
 * 	<strong>non-decreasing</strong>.</li>
 * </ul>
 *
 * <p>More formally, this means day <code>i</code> is a good day to rob the bank if and only if <code>security[i -
 * time] &gt;= security[i - time + 1] &gt;= ... &gt;= security[i] &lt;= ... &lt;= security[i + time - 1] &lt;=
 * security[i + time]</code>.</p>
 *
 * <p>Return <em>a list of <strong>all</strong> days <strong>(0-indexed) </strong>that are good days to rob the
 * bank</em>.<em> The order that the days are returned in does<strong> </strong><strong>not</strong> matter.</em></p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> security = [5,3,3,3,5,6,2], time = 2
 * <strong>Output:</strong> [2,3]
 * <strong>Explanation:</strong>
 * On day 2, we have security[0] &gt;= security[1] &gt;= security[2] &lt;= security[3] &lt;= security[4].
 * On day 3, we have security[1] &gt;= security[2] &gt;= security[3] &lt;= security[4] &lt;= security[5].
 * No other days satisfy this condition, so days 2 and 3 are the only good days to rob the bank.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> security = [1,1,1,1,1], time = 0
 * <strong>Output:</strong> [0,1,2,3,4]
 * <strong>Explanation:</strong>
 * Since time equals 0, every day is a good day to rob the bank, so return every day.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> security = [1,2,3,4,5,6], time = 2
 * <strong>Output:</strong> []
 * <strong>Explanation:</strong>
 * No day has 2 days before it that have a non-increasing number of guards.
 * Thus, no day is a good day to rob the bank, so return an empty list.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= security.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>0 &lt;= security[i], time &lt;= 10<sup>5</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2100_FindGoodDaysToRobTheBank {

    @Answer
    public List<Integer> goodDaysToRobBank(int[] security, int time) {
        final int n = security.length;
        if (time == 0) {
            List<Integer> res = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                res.add(i);
            }
            return res;
        }
        if (time * 2 + 1 > n) {
            return List.of();
        }

        int left = 0;
        for (int i = 1; i < time; i++) {
            if (security[i - 1] < security[i]) {
                left = i;
            }
        }
        int right = time;
        for (int i = time; i < time * 2; i++) {
            if (security[i] > security[i + 1]) {
                right = i + 1;
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = time; i < n - time; i++) {
            if (security[i - 1] < security[i]) {
                left = i;
            }
            int r = i + time;
            if (security[r - 1] > security[r]) {
                right = r;
            }
            if (left <= i - time && right <= i) {
                res.add(i);
            }
        }

        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{5, 3, 3, 3, 5, 6, 2})
            .addArgument(2)
            .expect(List.of(2, 3))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{1, 1, 1, 1, 1})
            .addArgument(0)
            .expect(List.of(0, 1, 2, 3, 4))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5, 6}, 2)
            .expect(List.of());

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{4, 3, 2, 1}, 1)
            .expect(List.of());

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 5, 4, 1, 0, 2, 4, 5, 3, 1, 2, 4, 3, 2, 4, 8})
            .addArgument(2)
            .expect(List.of(5, 10, 14))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(new int[]{3, 1, 3, 2, 0, 1, 1, 3, 1, 1}, 3)
            .expect(List.of());

}
